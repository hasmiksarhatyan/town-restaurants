package am.itspace.townrestaurantscommon.mapper;

import am.itspace.townrestaurantscommon.dto.reserve.CreateReserveDto;
import am.itspace.townrestaurantscommon.dto.reserve.ReserveOverview;
import am.itspace.townrestaurantscommon.entity.Reserve;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReserveMapper {

    @Mapping(source = "dto.restaurantId", target = "restaurant.id")
    Reserve mapToEntity(CreateReserveDto dto);

    @Mapping(source = "reserve.restaurant", target = "restaurantOverview")
    @Mapping(source = "reserve.user", target = "userOverview")
    ReserveOverview mapToOverview(Reserve reserve);

    List<ReserveOverview> mapToOverviewList(List<Reserve> reserves);
}

