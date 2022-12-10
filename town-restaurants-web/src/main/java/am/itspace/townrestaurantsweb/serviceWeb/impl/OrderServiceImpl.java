package am.itspace.townrestaurantsweb.serviceWeb.impl;

import am.itspace.townrestaurantscommon.dto.creditCard.CreateCreditCardDto;
import am.itspace.townrestaurantscommon.dto.order.CreateOrderDto;
import am.itspace.townrestaurantscommon.dto.order.OrderOverview;
import am.itspace.townrestaurantscommon.dto.product.ProductOverview;
import am.itspace.townrestaurantscommon.entity.*;
import am.itspace.townrestaurantscommon.mapper.OrderMapper;
import am.itspace.townrestaurantscommon.mapper.ProductMapper;
import am.itspace.townrestaurantscommon.mapper.UserMapper;
import am.itspace.townrestaurantscommon.repository.BasketRepository;
import am.itspace.townrestaurantscommon.repository.CreditCardRepository;
import am.itspace.townrestaurantscommon.repository.OrderRepository;
import am.itspace.townrestaurantsweb.serviceWeb.BasketService;
import am.itspace.townrestaurantsweb.serviceWeb.CreditCardService;
import am.itspace.townrestaurantsweb.serviceWeb.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;
import static java.time.LocalDate.now;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final BasketRepository basketRepository;
    private final BasketService basketService;
    private final CreditCardRepository creditCardRepository;
    private final CreditCardService creditCardService;
    private final OrderMapper orderMapper;
    private final UserMapper userMapper;
    private final ProductMapper productMapper;

    @Override
    public Page<OrderOverview> getOrders(Pageable pageable) {
        List<Order> all = orderRepository.findAll();
        List<OrderOverview> orderOverviews = orderMapper.mapToDto(all);
        return new PageImpl<>(orderOverviews);
    }

    @Override
    public void addOrder(CreateOrderDto orderDto, CreateCreditCardDto creditCardDto, User user) {
        orderDto.setTotalPrice(basketService.totalPrice(user));
        addProductToOrder(orderDto, user);
        Order order = orderMapper.mapToEntity(orderDto);
        orderRepository.save(order);
        if (order.getPaymentOption() == PaymentOption.CREDIT_CARD) {
            if (!creditCardRepository.existsByCardNumber(creditCardDto.getCardNumber())) {
                creditCardService.addCreditCard(creditCardDto, user);
            }
        }
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
            throw new IllegalStateException();
        }
        orderRepository.deleteById(id);
    }
}
