package am.itspace.townrestaurantsrest.serviceRest;

import am.itspace.townrestaurantscommon.dto.FetchRequestDto;
import am.itspace.townrestaurantscommon.dto.order.CreateOrderDto;
import am.itspace.townrestaurantscommon.dto.order.EditOrderDto;
import am.itspace.townrestaurantscommon.dto.order.OrderOverview;
import am.itspace.townrestaurantscommon.entity.Order;

import java.util.List;

public interface OrderService {

    void delete(int id);

    List<OrderOverview> getAll();

    OrderOverview getById(int id);

    OrderOverview save(CreateOrderDto dto);

    OrderOverview update(int id, EditOrderDto editOrderDto);

    List<Order> getOrdersList(FetchRequestDto fetchRequestDto);
}
