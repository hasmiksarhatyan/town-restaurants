package am.itspace.townrestaurantscommon.mapper;

import am.itspace.townrestaurantscommon.dto.product.CreateProductDto;
import am.itspace.townrestaurantscommon.dto.product.ProductOverview;
import am.itspace.townrestaurantscommon.dto.productCategory.ProductCategoryOverview;
import am.itspace.townrestaurantscommon.dto.restaurant.RestaurantOverview;
import am.itspace.townrestaurantscommon.dto.user.UserOverview;
import am.itspace.townrestaurantscommon.entity.Product;
import am.itspace.townrestaurantscommon.entity.ProductCategory;
import am.itspace.townrestaurantscommon.entity.Restaurant;
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
    public Product mapToEntity(CreateProductDto dto) {
        if ( dto == null ) {
            return null;
        }

        Product.ProductBuilder product = Product.builder();

        product.restaurant( createProductDtoToRestaurant( dto ) );
        product.productCategory( createProductDtoToProductCategory( dto ) );
        product.name( dto.getName() );
        product.description( dto.getDescription() );
        product.price( dto.getPrice() );
        List<String> list = dto.getPictures();
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

        productOverview.restaurantOverview( restaurantToRestaurantOverview( product.getRestaurant() ) );
        productOverview.productCategoryOverview( productCategoryToProductCategoryOverview( product.getProductCategory() ) );
        productOverview.userOverview( userToUserOverview( product.getUser() ) );
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
            list.add( mapToResponseDto( product ) );
        }

        return list;
    }

    protected Restaurant createProductDtoToRestaurant(CreateProductDto createProductDto) {
        if ( createProductDto == null ) {
            return null;
        }

        Restaurant.RestaurantBuilder restaurant = Restaurant.builder();

        if ( createProductDto.getRestaurantId() != null ) {
            restaurant.id( createProductDto.getRestaurantId() );
        }

        return restaurant.build();
    }

    protected ProductCategory createProductDtoToProductCategory(CreateProductDto createProductDto) {
        if ( createProductDto == null ) {
            return null;
        }

        ProductCategory.ProductCategoryBuilder productCategory = ProductCategory.builder();

        if ( createProductDto.getProductCategoryId() != null ) {
            productCategory.id( createProductDto.getProductCategoryId() );
        }

        return productCategory.build();
    }

    protected RestaurantOverview restaurantToRestaurantOverview(Restaurant restaurant) {
        if ( restaurant == null ) {
            return null;
        }

        RestaurantOverview.RestaurantOverviewBuilder restaurantOverview = RestaurantOverview.builder();

        restaurantOverview.id( restaurant.getId() );
        restaurantOverview.name( restaurant.getName() );
        restaurantOverview.address( restaurant.getAddress() );
        restaurantOverview.email( restaurant.getEmail() );
        restaurantOverview.phone( restaurant.getPhone() );
        restaurantOverview.deliveryPrice( restaurant.getDeliveryPrice() );
        List<String> list = restaurant.getPictures();
        if ( list != null ) {
            restaurantOverview.pictures( new ArrayList<String>( list ) );
        }
        restaurantOverview.user( restaurant.getUser() );

        return restaurantOverview.build();
    }

    protected ProductCategoryOverview productCategoryToProductCategoryOverview(ProductCategory productCategory) {
        if ( productCategory == null ) {
            return null;
        }

        ProductCategoryOverview.ProductCategoryOverviewBuilder productCategoryOverview = ProductCategoryOverview.builder();

        productCategoryOverview.id( productCategory.getId() );
        productCategoryOverview.name( productCategory.getName() );

        return productCategoryOverview.build();
    }

    protected UserOverview userToUserOverview(User user) {
        if ( user == null ) {
            return null;
        }

        UserOverview.UserOverviewBuilder userOverview = UserOverview.builder();

        userOverview.id( user.getId() );
        userOverview.firstName( user.getFirstName() );
        userOverview.lastName( user.getLastName() );
        userOverview.email( user.getEmail() );
        userOverview.role( user.getRole() );

        return userOverview.build();
    }
}
