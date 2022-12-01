package am.itspace.townrestaurantscommon.mapper;

import am.itspace.townrestaurantscommon.dto.product.CreateProductDto;
import am.itspace.townrestaurantscommon.dto.product.ProductOverview;
import am.itspace.townrestaurantscommon.entity.Product;
import am.itspace.townrestaurantscommon.entity.User;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.4.1 (Amazon.com Inc.)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public Product mapToEntity(CreateProductDto dto, User user) {
        if ( dto == null && user == null ) {
            return null;
        }

        Product.ProductBuilder product = Product.builder();

        if ( dto != null ) {
            product.name( dto.getName() );
            product.description( dto.getDescription() );
            product.price( dto.getPrice() );
            List<String> list = dto.getPictures();
            if ( list != null ) {
                product.pictures( new ArrayList<String>( list ) );
            }
        }
        if ( user != null ) {
            if ( user.getId() != null ) {
                product.id( user.getId() );
            }
        }

        return product.build();
    }

    @Override
    public ProductOverview mapToOverview(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductOverview.ProductOverviewBuilder productOverview = ProductOverview.builder();

        productOverview.id( product.getId() );
        productOverview.name( product.getName() );
        productOverview.description( product.getDescription() );
        productOverview.price( product.getPrice() );
        List<String> list = product.getPictures();
        if ( list != null ) {
            productOverview.pictures( new ArrayList<String>( list ) );
        }

        return productOverview.build();
    }

    @Override
    public List<ProductOverview> mapToOverviewList(List<Product> products) {
        if ( products == null ) {
            return null;
        }

        List<ProductOverview> list = new ArrayList<ProductOverview>( products.size() );
        for ( Product product : products ) {
            list.add( mapToOverview( product ) );
        }

        return list;
    }

    @Override
    public Product mapToEntity(CreateProductDto createProductDto) {
        if ( createProductDto == null ) {
            return null;
        }

        Product.ProductBuilder product = Product.builder();

        product.name( createProductDto.getName() );
        product.description( createProductDto.getDescription() );
        product.price( createProductDto.getPrice() );
        List<String> list = createProductDto.getPictures();
        if ( list != null ) {
            product.pictures( new ArrayList<String>( list ) );
        }

        return product.build();
    }

    @Override
    public ProductOverview mapToResponseDto(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductOverview.ProductOverviewBuilder productOverview = ProductOverview.builder();

        productOverview.id( product.getId() );
        productOverview.name( product.getName() );
        productOverview.description( product.getDescription() );
        productOverview.price( product.getPrice() );
        List<String> list = product.getPictures();
        if ( list != null ) {
            productOverview.pictures( new ArrayList<String>( list ) );
        }

        return productOverview.build();
    }
}
