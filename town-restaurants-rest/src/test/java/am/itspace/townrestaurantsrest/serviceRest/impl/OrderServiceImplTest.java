package am.itspace.townrestaurantsrest.serviceRest.impl;

import am.itspace.townrestaurantscommon.dto.order.OrderOverview;
import am.itspace.townrestaurantscommon.entity.Order;
import am.itspace.townrestaurantscommon.entity.OrderStatus;
import am.itspace.townrestaurantscommon.mapper.OrderMapper;
import am.itspace.townrestaurantscommon.mapper.ProductMapper;
import am.itspace.townrestaurantscommon.repository.BasketRepository;
import am.itspace.townrestaurantscommon.repository.OrderRepository;
import am.itspace.townrestaurantscommon.security.CurrentUser;
import am.itspace.townrestaurantsrest.exception.EntityNotFoundException;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static am.itspace.townrestaurantsrest.parameters.MockData.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {

    @InjectMocks
    OrderServiceImpl orderService;

    @Mock
    OrderMapper orderMapper;

    @Mock
    ProductMapper productMapper;

    @Mock
    OrderRepository orderRepository;

    @Mock
    BasketServiceImpl basketService;

    @Mock
    BasketRepository basketRepository;

    @Mock
    SecurityContextServiceImpl securityContextService;

    @Mock
    PaymentServiceImpl paymentService;

//    @Test
//    void shouldSaveOrder() {
//        //given
//        CurrentUser currentUser = new CurrentUser(getUser());
//        var order = getOrder();
//        var user = getUser();
//        var expected = getOrderOverview();
//        var createOrderDto = getCreateOrderDto();
//        var createOrderCreditCardDto = getOrderCreditCardDto();
//        var basket = getBasket();
//        var baskets = List.of(getBasket(), getBasket());
//        var creditCard = getCreditCard();
//        var orderOverview = getOrderOverview();
//        var ordersProducts = List.of(getProduct(), getProduct());
//        //when
//        doReturn(currentUser).when(securityContextService).getUserDetails();
//        when(basketService.getTotalPrice()).thenReturn(20.0);
//        createOrderDto.setTotalPrice(20.0);
//        doReturn(order).when(orderMapper).mapToEntity(createOrderDto);
//
//        lenient().doReturn(baskets).when(basketRepository).findBasketByUser(user);
//        lenient().doReturn(false).when(baskets).isEmpty();
//
//
//        order.setProducts(ordersProducts);
////        basketService.delete(getProduct().getId());
//        doReturn(order).when(orderRepository).save(any(Order.class));
////        doReturn(baskets).when(basketRepository).findBasketByUser(any());
////        MatcherAssert.assertThat(!baskets.isEmpty(), Matchers.is(true));
//        order.setUser(currentUser.getUser());
//        order.setStatus(OrderStatus.NEW);
//        order.setPaid(false);
//
//        OrderOverview actual = orderService.save(createOrderCreditCardDto);
//        doNothing().when(paymentService).addPayment(order, creditCard);
//        doReturn(orderOverview).when(orderMapper).mapToDto(order);
//        //then
////        assertThrows(EntityAlreadyExistsException.class, () -> orderService.save(createOrderCreditCardDto));
//        verify(orderRepository, times(1)).save(order);
//        assertNotNull(actual);
//        assertEquals(expected, actual);
//    }


    @Test
    void shouldGetAllOrders() {
        //given
        var listOfOrdersPage = getPageOrders();
        PageRequest pageReq = PageRequest.of(1, 1, Sort.Direction.fromString("desc"), "1");
        var expected = List.of(getOrderOverview(), getOrderOverview());
        PageRequest pageable = PageRequest.of(1, 1, Sort.Direction.fromString("DESC"), "name");
        //when
        doReturn(listOfOrdersPage).when(orderRepository).findAll(pageable);
        doReturn(expected).when(orderMapper).mapToDto(anyList());
        List<OrderOverview> actual = orderService.getAllOrders(1, 1, "name", "DESC");
        //then
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void getAllOrdersShouldThrowException() {
        //given
        var getNullPageOrders = getNullPageOrders();
        PageRequest pageable = PageRequest.of(1, 1, Sort.Direction.fromString("DESC"), "name");
        //when
        doReturn(getNullPageOrders).when(orderRepository).findAll(pageable);
        //then
        assertThrows(EntityNotFoundException.class, () -> orderService.getAllOrders(1, 1, "name", "DESC"));
    }

    @Test
    void shouldGetById() {
        //given
        var order = getOrder();
        var expected = getOrderOverview();
        //when
        doReturn(Optional.of(order)).when(orderRepository).findById(anyInt());
        doReturn(expected).when(orderMapper).mapToDto(order);
        OrderOverview actual = orderService.getById(anyInt());
        //then
        assertNotNull(actual);
        assertEquals(expected.getId(), actual.getId());
    }

    @Test
    void shouldEntityNotFoundExceptionAsOrderNotFoundById() {
        //when
        doThrow(EntityNotFoundException.class).when(orderRepository).findById(anyInt());
        //then
        assertThrows(EntityNotFoundException.class, () -> orderService.getById(anyInt()));
    }

    @Test
    void shouldUpdateOrder() {
        //given
        var order = getOrder();
        var expected = getOrderOverview();
        var editOrderDto = getEditOrderDto();
        var products = List.of(getProduct(), getProduct());
        var productOverviews = List.of(getProductOverview(), getProductOverview());
        //when
        doReturn(Optional.of(order)).when(orderRepository).findById(anyInt());
        doReturn(expected).when(orderMapper).mapToDto(order);
        doReturn(products).when(productMapper).mapToEntity(productOverviews);
        doReturn(order).when(orderRepository).save(any(Order.class));
        OrderOverview actual = orderService.update(anyInt(), editOrderDto);
        //then
        verify(orderRepository, times(1)).save(order);
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void deleteSuccess() {
        //given
        int orderId = 1;
        //when
        when(orderRepository.existsById(orderId)).thenReturn(true);
        orderService.delete(orderId);
        //then
        verify(orderRepository).deleteById(orderId);
    }

    @Test
    void shouldThrowExceptionAsOrderDoesNotExist() {
        //given
        int orderId = 1;
        //when
        when(orderRepository.existsById(orderId)).thenReturn(false);
        //then
        assertThrows(EntityNotFoundException.class, () -> orderService.delete(orderId));
    }
}
