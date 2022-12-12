package am.itspace.townrestaurantsrest.serviceRest.impl;

import am.itspace.townrestaurantscommon.dto.FetchRequestDto;
import am.itspace.townrestaurantscommon.dto.creditCard.CreateCreditCardDto;
import am.itspace.townrestaurantscommon.dto.order.CreateOrderDto;
import am.itspace.townrestaurantscommon.dto.order.EditOrderDto;
import am.itspace.townrestaurantscommon.dto.order.OrderCreditCardDto;
import am.itspace.townrestaurantscommon.dto.order.OrderOverview;
import am.itspace.townrestaurantscommon.dto.product.ProductOverview;
import am.itspace.townrestaurantscommon.entity.*;
import am.itspace.townrestaurantscommon.mapper.OrderMapper;
import am.itspace.townrestaurantscommon.mapper.ProductMapper;
import am.itspace.townrestaurantscommon.repository.BasketRepository;
import am.itspace.townrestaurantscommon.repository.CreditCardRepository;
import am.itspace.townrestaurantscommon.repository.OrderRepository;
import am.itspace.townrestaurantsrest.exception.AuthenticationException;
import am.itspace.townrestaurantsrest.exception.EntityNotFoundException;
import am.itspace.townrestaurantsrest.serviceRest.BasketService;
import am.itspace.townrestaurantsrest.serviceRest.CreditCardService;
import am.itspace.townrestaurantsrest.serviceRest.OrderService;
import am.itspace.townrestaurantsrest.serviceRest.SecurityContextService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static am.itspace.townrestaurantsrest.exception.Error.*;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper;
    private final ProductMapper productMapper;
    private final BasketService basketService;
    private final OrderRepository orderRepository;
    private final BasketRepository basketRepository;
    private final CreditCardService creditCardService;
    private final CreditCardRepository creditCardRepository;
    private final SecurityContextService securityContextService;

    public OrderOverview save(OrderCreditCardDto dto) {
        CreateOrderDto createOrderDto = dto.getCreateOrderDto();
        CreateCreditCardDto creditCardDto = dto.getCreditCardDto();
        List<Product> products = productMapper.mapToEntity(createOrderDto.getProductOverviews());
        try {
            User user = securityContextService.getUserDetails().getUser();
            createOrderDto.setTotalPrice(basketService.getTotalPrice());
            addProductToOrder(createOrderDto, user);
            Order order = orderMapper.mapToEntity(createOrderDto);
            orderRepository.save(order);
            if (order.getPaymentOption() == PaymentOption.CREDIT_CARD) {
                if (creditCardRepository.existsByCardNumber(creditCardDto.getCardNumber())) {
                    creditCardService.save(creditCardDto);
                } else {
                    throw new EntityNotFoundException(WRONG_CREDIT_CARD_NUMBER);
                }
            }
            log.info("The order was successfully stored in the database {}", products);
            return orderMapper.mapToDto(order);
        } catch (ClassCastException e) {
            throw new AuthenticationException(NEEDS_AUTHENTICATION);
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
                    basketService.delete(product.getId());
                }
                orderDto.setProductOverviews(ordersProducts);
            }
        } else {
            throw new EntityNotFoundException(BASKET_NOT_FOUND);
        }
    }

    @Override
    public List<OrderOverview> getAll() {
        List<Order> orders = orderRepository.findAll();
        if (orders.isEmpty()) {
            log.info("Order not found");
            throw new EntityNotFoundException(ORDER_NOT_FOUND);
        }
        log.info("Order successfully found");
        return orderMapper.mapToDto(orders);
    }

    @Override
    public List<Order> getOrdersList(FetchRequestDto dto) {
        PageRequest pageReq = PageRequest.of(dto.getPage(), dto.getSize(), Sort.Direction.fromString(dto.getSortDir()), dto.getSort());
        Page<Order> orders = orderRepository.findByAdditionalAddress(dto.getInstance(), pageReq);
        if (orders.isEmpty()) {
            log.info("Order not found");
            throw new EntityNotFoundException(ORDER_NOT_FOUND);
        }
        return orders.getContent();
    }

    @Override
    public OrderOverview getById(int id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ORDER_NOT_FOUND));
        log.info("Order successfully found {}", order.getProducts());
        return orderMapper.mapToDto(order);
    }

    @Override
    public OrderOverview update(int id, EditOrderDto editOrderDto) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ORDER_NOT_FOUND));
        log.info("Order with that id not found");
        if (editOrderDto.getAdditionalAddress() != null) {
            order.setAdditionalAddress(editOrderDto.getAdditionalAddress());
        }
        if (editOrderDto.getAdditionalPhone() != null) {
            order.setAdditionalPhone(editOrderDto.getAdditionalPhone());
        }
        if (editOrderDto.getProductOverviews() != null) {
            order.setProducts(productMapper.mapToEntity(editOrderDto.getProductOverviews()));
        }
        orderRepository.save(order);
        log.info("The order was successfully stored in the database {}", order.getProducts());
        return orderMapper.mapToDto(order);
    }

    @Override
    public void delete(int id) {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
            log.info("The order has been successfully deleted");
        } else {
            log.info("Order not found");
            throw new EntityNotFoundException(ORDER_NOT_FOUND);
        }
    }
}
