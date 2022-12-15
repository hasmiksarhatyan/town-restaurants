package am.itspace.townrestaurantsweb.serviceWeb;

import am.itspace.townrestaurantscommon.dto.creditCard.CreateCreditCardDto;
import am.itspace.townrestaurantscommon.dto.order.CreateOrderDto;
import am.itspace.townrestaurantscommon.dto.order.EditOrderDto;
import am.itspace.townrestaurantscommon.dto.order.OrderOverview;
import am.itspace.townrestaurantscommon.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface OrderService {
    void delete(int id);

    Page<OrderOverview> getOrders(Pageable pageable);

    void addOrder(CreateOrderDto orderDto, CreateCreditCardDto cardDto, User user);

    OrderOverview getById(int id);

    void editOrder(EditOrderDto dto, int id);

    Page<OrderOverview> getOrdersByUser(int id, Pageable pageable);

    Map<Integer, Double> getQuantity(int orderId);
}


