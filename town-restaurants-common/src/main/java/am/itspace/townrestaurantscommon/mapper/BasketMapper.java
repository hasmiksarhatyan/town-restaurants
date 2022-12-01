package am.itspace.townrestaurantscommon.mapper;

import am.itspace.townrestaurantscommon.dto.basket.BasketOverview;
import am.itspace.townrestaurantscommon.dto.basket.CreateBasketDto;
import am.itspace.townrestaurantscommon.entity.Basket;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

import java.util.List;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BasketMapper {

    Basket mapToEntity(CreateBasketDto createBasketDto);

    BasketOverview mapToDto(Basket basket);

    List<BasketOverview> mapToDto(List<Basket> baskets);

    List<BasketOverview> mapToDto(Page<Basket> baskets);
}

