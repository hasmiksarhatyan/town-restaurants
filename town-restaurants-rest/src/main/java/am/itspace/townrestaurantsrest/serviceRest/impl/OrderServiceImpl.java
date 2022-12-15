package am.itspace.townrestaurantsrest.serviceRest.impl;

import am.itspace.townrestaurantscommon.dto.creditCard.CreateCreditCardDto;
import am.itspace.townrestaurantscommon.dto.order.CreateOrderDto;
import am.itspace.townrestaurantscommon.dto.order.EditOrderDto;
import am.itspace.townrestaurantscommon.dto.order.OrderCreditCardDto;
import am.itspace.townrestaurantscommon.dto.order.OrderOverview;
import am.itspace.townrestaurantscommon.entity.*;
import am.itspace.townrestaurantscommon.mapper.CreditCardMapper;
import am.itspace.townrestaurantscommon.mapper.OrderMapper;
import am.itspace.townrestaurantscommon.mapper.ProductMapper;
import am.itspace.townrestaurantscommon.repository.BasketRepository;
import am.itspace.townrestaurantscommon.repository.OrderRepository;
import am.itspace.townrestaurantsrest.exception.AuthenticationException;
import am.itspace.townrestaurantsrest.exception.EntityNotFoundException;
import am.itspace.townrestaurantsrest.serviceRest.BasketService;
import am.itspace.townrestaurantsrest.serviceRest.OrderService;
import am.itspace.townrestaurantsrest.serviceRest.PaymentService;
import am.itspace.townrestaurantsrest.serviceRest.SecurityContextService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private final PaymentService paymentService;
    private final OrderRepository orderRepository;
    private final BasketRepository basketRepository;
    private final CreditCardMapper creditCardMapper;
    private final SecurityContextService securityContextService;

    @Override
    public OrderOverview save(OrderCreditCardDto dto) {
        CreateOrderDto createOrderDto = dto.getCreateOrderDto();
        CreateCreditCardDto creditCardDto = dto.getCreditCardDto();
        try {
            User user = securityContextService.getUserDetails().getUser();
            createOrderDto.setTotalPrice(basketService.getTotalPrice());
            Order order = orderMapper.mapToEntity(createOrderDto);
            addProductToOrder(order, user);
            order.setUser(user);
            order.setStatus(OrderStatus.NEW);
            order.setPaid(false);
            orderRepository.save(order);
            paymentService.addPayment(order, creditCardMapper.mapToEntity(creditCardDto));
            log.info("The order was successfully stored in the database");
            return orderMapper.mapToDto(order);
        } catch (ClassCastException e) {
            throw new AuthenticationException(NEEDS_AUTHENTICATION);
        }
    }

    private void addProductToOrder(Order order, User user) {
        List<Product> ordersProducts = new ArrayList<>();
        List<Basket> basketByUser = basketRepository.findBasketByUser(user);
        if (!basketByUser.isEmpty()) {
            for (Basket basket : basketByUser) {
                Product product = basket.getProduct();
                while (basket.getQuantity() != 0) {
                    ordersProducts.add(product);
                    basketService.delete(product.getId());
                }
                order.setProducts(ordersProducts);
            }
        } else {
            throw new EntityNotFoundException(BASKET_NOT_FOUND);
        }
    }

    @Override
    public List<OrderOverview> getAllOrders(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Order> orders = orderRepository.findAll(pageable);
        if (orders.isEmpty()) {
            log.info("Order not found");
            throw new EntityNotFoundException(ORDER_NOT_FOUND);
        }
        List<Order> listOfOrders = orders.getContent();
        log.info("Order successfully found");
        return new ArrayList<>(orderMapper.mapToDto(listOfOrders));
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
