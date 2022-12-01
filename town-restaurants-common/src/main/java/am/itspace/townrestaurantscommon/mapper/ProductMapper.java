package am.itspace.townrestaurantscommon.mapper;

import am.itspace.townrestaurantscommon.dto.product.CreateProductDto;
import am.itspace.townrestaurantscommon.dto.product.ProductOverview;
import am.itspace.townrestaurantscommon.dto.restaurantCategory.RestaurantCategoryOverview;
import am.itspace.townrestaurantscommon.entity.Product;
import am.itspace.townrestaurantscommon.entity.RestaurantCategory;
import am.itspace.townrestaurantscommon.entity.User;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    //    @Mapping(source = "dto.restaurantId", target = "restaurant.id")
//    @Mapping(source = "dto.productCategoryId", target = "productCategory.id")
//    @Mapping(target = "user")
    Product mapToEntity(CreateProductDto dto, User user);

    //    @Mapping(source = "product.restaurant", target = "restaurantOverview")
//    @Mapping(source = "product.productCategory", target = "productCategoryOverview")
//    @Mapping(source = "product.user", target = "userOverview")
    @Named(value = "useMe")
    ProductOverview mapToOverview(Product product);

    @IterableMapping(qualifiedByName = "useMe")
    List<ProductOverview> mapToOverviewList(List<Product> products);

    //    @Mapping(source = "dto.restaurantId", target = "restaurant.id")
//    @Mapping(source = "dto.productCategoryId", target = "productCategory.id")
    Product mapToEntity(CreateProductDto createProductDto);

    ProductOverview mapToResponseDto(Product product);
}
