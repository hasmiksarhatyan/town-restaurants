package am.itspace.townrestaurantscommon.mapper;

import am.itspace.townrestaurantscommon.dto.reserve.CreateReserveDto;
import am.itspace.townrestaurantscommon.dto.reserve.ReserveOverview;
import am.itspace.townrestaurantscommon.entity.Reserve;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

import java.util.List;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReserveMapper {

    Reserve mapToEntity(CreateReserveDto createReserveDto);

    ReserveOverview mapToDto(Reserve reserve);

    List<ReserveOverview> mapToDto(List<Reserve> reserves);

    List<ReserveOverview> mapToDto(Page<Reserve> reserves);
}

