package am.itspace.townrestaurantscommon.mapper;

import am.itspace.townrestaurantscommon.dto.productCategory.CreateProductCategoryDto;
import am.itspace.townrestaurantscommon.dto.productCategory.ProductCategoryOverview;
import am.itspace.townrestaurantscommon.entity.ProductCategory;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductCategoryMapper {

    ProductCategory mapToEntity(CreateProductCategoryDto productCategoryDto);

    ProductCategoryOverview mapToOverview(ProductCategory productCategory);

    List<ProductCategoryOverview> mapToOverviewList(List<ProductCategory> productCategories);
}
