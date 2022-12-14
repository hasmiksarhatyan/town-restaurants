package am.itspace.townrestaurantscommon.mapper;

import am.itspace.townrestaurantscommon.dto.order.CreateOrderDto;
import am.itspace.townrestaurantscommon.dto.order.OrderOverview;
import am.itspace.townrestaurantscommon.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {

    @Mapping(source = "dto.productOverviews", target = "products")
    Order mapToEntity(CreateOrderDto dto);

    @Mapping(source = "order.products", target = "productOverviews")
    @Mapping(source = "order.user", target = "userOverview")
    OrderOverview mapToDto(Order order);

    List<OrderOverview> mapToDto(List<Order> orders);
}

