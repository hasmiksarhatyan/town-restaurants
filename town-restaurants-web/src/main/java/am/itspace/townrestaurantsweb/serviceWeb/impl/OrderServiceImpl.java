package am.itspace.townrestaurantsweb.serviceWeb.impl;

import am.itspace.townrestaurantscommon.dto.order.CreateOrderDto;
import am.itspace.townrestaurantscommon.dto.order.OrderOverview;
import am.itspace.townrestaurantscommon.dto.product.ProductOverview;
import am.itspace.townrestaurantscommon.entity.*;
import am.itspace.townrestaurantscommon.mapper.OrderMapper;
import am.itspace.townrestaurantscommon.mapper.ProductMapper;
import am.itspace.townrestaurantscommon.repository.BasketRepository;
import am.itspace.townrestaurantscommon.repository.OrderRepository;
import am.itspace.townrestaurantsweb.serviceWeb.BasketService;
import am.itspace.townrestaurantsweb.serviceWeb.OrderService;
import am.itspace.townrestaurantsweb.serviceWeb.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper;
    private final ProductMapper productMapper;
    private final BasketService basketService;
    private final PaymentService paymentService;
    private final OrderRepository orderRepository;
    private final BasketRepository basketRepository;

    @Override
    public Page<OrderOverview> getOrders(Pageable pageable) {
        List<Order> orders = orderRepository.findAll();
        if (orders.isEmpty()) {
            throw new IllegalStateException("Order not found!");
        }
        List<OrderOverview> orderOverviews = orderMapper.mapToDto(orders);
        log.info("Order successfully found");
        return new PageImpl<>(orderOverviews);
    }

    @Override
    public void addOrder(CreateOrderDto orderDto, User user) {
        orderDto.setTotalPrice(basketService.totalPrice(user));
        addProductToOrder(orderDto, user);
        Order order = orderMapper.mapToEntity(orderDto);
        order.setUser(user);
        order.setStatus(OrderStatus.NEW);
        order.setPaid(false);
        orderRepository.save(order);
        paymentService.addPayment(order, user);
        log.info("The order was successfully stored in the database");
    }

    private void addProductToOrder(CreateOrderDto orderDto, User user) {
        List<ProductOverview> ordersProducts = new ArrayList<>();
        List<Basket> basketByUser = basketRepository.findBasketByUser(user);
        if (!basketByUser.isEmpty()) {
            for (Basket basket : basketByUser) {
                Product product = basket.getProduct();
                while (basket.getQuantity() != 0) {
                    ordersProducts.add(productMapper.mapToResponseDto(product));
                    basketService.delete(product.getId(), user);
                }
                orderDto.setProductOverviews(ordersProducts);
            }
        }
    }

    @Override
    public void delete(int id) {
        if (!orderRepository.existsById(id)) {
            log.info("Order not found");
            throw new IllegalStateException("Something went wrong, try again!");
        }
        orderRepository.deleteById(id);
        log.info("The order has been successfully deleted");
    }
}
