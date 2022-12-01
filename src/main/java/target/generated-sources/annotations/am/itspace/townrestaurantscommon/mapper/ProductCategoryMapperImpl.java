package am.itspace.townrestaurantscommon.mapper;

import am.itspace.townrestaurantscommon.dto.productCategory.CreateProductCategoryDto;
import am.itspace.townrestaurantscommon.dto.productCategory.ProductCategoryOverview;
import am.itspace.townrestaurantscommon.entity.ProductCategory;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.4.1 (Amazon.com Inc.)"
)
@Component
public class ProductCategoryMapperImpl implements ProductCategoryMapper {

    @Override
    public ProductCategory mapToEntity(CreateProductCategoryDto productCategoryDto) {
        if ( productCategoryDto == null ) {
            return null;
        }

        ProductCategory.ProductCategoryBuilder productCategory = ProductCategory.builder();

        productCategory.name( productCategoryDto.getName() );

        return productCategory.build();
    }

    @Override
    public ProductCategoryOverview mapToOverview(ProductCategory productCategory) {
        if ( productCategory == null ) {
            return null;
        }

        ProductCategoryOverview.ProductCategoryOverviewBuilder productCategoryOverview = ProductCategoryOverview.builder();

        productCategoryOverview.id( productCategory.getId() );
        productCategoryOverview.name( productCategory.getName() );

        return productCategoryOverview.build();
    }

    @Override
    public List<ProductCategoryOverview> mapToOverviewList(List<ProductCategory> productCategories) {
        if ( productCategories == null ) {
            return null;
        }

        List<ProductCategoryOverview> list = new ArrayList<ProductCategoryOverview>( productCategories.size() );
        for ( ProductCategory productCategory : productCategories ) {
            list.add( mapToOverview( productCategory ) );
        }

        return list;
    }
}
