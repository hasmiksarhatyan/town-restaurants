package am.itspace.townrestaurantsrest.serviceRest;

import am.itspace.townrestaurantscommon.dto.order.EditOrderDto;
import am.itspace.townrestaurantscommon.dto.order.OrderCreditCardDto;
import am.itspace.townrestaurantscommon.dto.order.OrderOverview;

import java.util.List;

public interface OrderService {

    void delete(int id);

    OrderOverview getById(int id);

    OrderOverview update(int id, EditOrderDto editOrderDto);

    OrderOverview save(OrderCreditCardDto orderCreditCardDto);

    List<OrderOverview> getAllOrders(int pageNo, int pageSize, String sortBy, String sortDir);
}
