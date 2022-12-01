package am.itspace.townrestaurantscommon.mapper;

import am.itspace.townrestaurantscommon.dto.order.OrderOverview;
import am.itspace.townrestaurantscommon.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {

    OrderOverview mapToDto(Order order);

    List<OrderOverview> mapToDto(List<Order> orders);

    List<OrderOverview> mapToDto(Page<Order> orders);
}

