package am.itspace.townrestaurantscommon.mapper;

import am.itspace.townrestaurantscommon.dto.basket.BasketOverview;
import am.itspace.townrestaurantscommon.dto.basket.CreateBasketDto;
import am.itspace.townrestaurantscommon.entity.Basket;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BasketMapper {

    @Mapping(source = "dto.productId", target = "product.id")
    Basket mapToEntity(CreateBasketDto dto);

    @Mapping(source = "basket.product", target = "productOverview")
    @Mapping(source = "basket.user", target = "userOverview")
    BasketOverview mapToDto(Basket basket);

    List<BasketOverview> mapToDtoList(List<Basket> baskets);

}

