package am.itspace.townrestaurantsweb.serviceWeb.impl;

import am.itspace.townrestaurantscommon.dto.creditCard.CreateCreditCardDto;
import am.itspace.townrestaurantscommon.dto.order.CreateOrderDto;
import am.itspace.townrestaurantscommon.dto.order.EditOrderDto;
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
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Page<Order> orders = orderRepository.findAll(pageable);
        if (orders.isEmpty()) {
            throw new IllegalStateException("Order not found!");
        }
        log.info("Order successfully found");
        return orders.map(orderMapper::mapToDto);
    }

    @Override
    public void addOrder(CreateOrderDto orderDto, CreateCreditCardDto cardDto, User user) {
        orderDto.setTotalPrice(basketService.totalPrice(user));
        addProductToOrder(orderDto, user);
        Order order = orderMapper.mapToEntity(orderDto);
        order.setUser(user);
        order.setStatus(OrderStatus.NEW);
        order.setPaid(false);
        orderRepository.save(order);
        log.info("The order was successfully stored in the database");
        paymentService.addPayment(order, cardDto, user);
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

    public Map<Integer, Double> getQuantity(int orderId) {
        double quantity = 0;
        Map<Integer, Double> sameProducts = new HashMap<>();
        Order order = orderRepository.findById(orderId).orElseThrow();
        List<Product> products = order.getProducts();
        if (products.isEmpty()) {
            throw new IllegalStateException("Something went wrong, try again!");
        }
        for (Product product : products) {
            sameProducts.put(product.getId(), quantity);
            if (sameProducts.get(product.getId()) == product.getId()) {
                sameProducts.put(product.getId(), quantity + 1);
            }
        }
        return sameProducts;
    }

    @Override
    public OrderOverview getById(int id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new IllegalStateException("Something went wrong, try again!"));
        log.info("Order successfully found");
        return orderMapper.mapToDto(order);
    }

    public void editOrder(EditOrderDto dto, int id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new IllegalStateException("Something went wrong, try again!"));
        String isPaid = dto.getIsPaid();
        if (isPaid.equals("true")) {
            order.setPaid(true);
        } else {
            order.setPaid(false);
        }
        String address = dto.getAdditionalAddress();
        if (address != null) {
            order.setAdditionalAddress(address);
        }
        String phoneNumber = dto.getAdditionalPhone();
        if (phoneNumber != null) {
            order.setAdditionalPhone(phoneNumber);
        }
        String status = dto.getStatus();
        if (status != null) {
            order.setStatus(OrderStatus.valueOf(status));
        }
        String paymentOption = dto.getPaymentOption();
        if (paymentOption != null) {
            order.setPaymentOption(PaymentOption.valueOf(paymentOption));
        }
        log.info("The order was successfully updated in the database {}", order.getId());
        orderRepository.save(order);
    }

    @Override
    public Page<OrderOverview> getOrdersByUser(int id, Pageable pageable) {
        log.info("Orders successfully found");
        return orderRepository.findByUserId(id, pageable).map(orderMapper::mapToDto);
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
