package am.itspace.townrestaurantscommon.mapper;

import am.itspace.townrestaurantscommon.dto.product.CreateProductDto;
import am.itspace.townrestaurantscommon.dto.product.ProductOverview;
import am.itspace.townrestaurantscommon.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source = "dto.restaurantId", target = "restaurant.id")
    @Mapping(source = "dto.productCategoryId", target = "productCategory.id")
    Product mapToEntity(CreateProductDto dto);

    @Mapping(source = "product.restaurant", target = "restaurantOverview")
    @Mapping(source = "product.productCategory", target = "productCategoryOverview")
    @Mapping(source = "product.user", target = "userOverview")
    ProductOverview mapToResponseDto(Product product);

    List<ProductOverview> mapToOverviewList(List<Product> products);

}
