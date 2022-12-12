package am.itspace.townrestaurantscommon.mapper;

import am.itspace.townrestaurantscommon.dto.basket.BasketOverview;
import am.itspace.townrestaurantscommon.dto.basket.CreateBasketDto;
import am.itspace.townrestaurantscommon.dto.product.ProductOverview;
import am.itspace.townrestaurantscommon.dto.user.UserOverview;
import am.itspace.townrestaurantscommon.entity.Basket;
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
public class BasketMapperImpl implements BasketMapper {

    @Override
    public Basket mapToEntity(CreateBasketDto dto) {
        if ( dto == null ) {
            return null;
        }

        Basket.BasketBuilder basket = Basket.builder();

        basket.product( createBasketDtoToProduct( dto ) );
        basket.quantity( dto.getQuantity() );

        return basket.build();
    }

    @Override
    public BasketOverview mapToDto(Basket basket) {
        if ( basket == null ) {
            return null;
        }

        BasketOverview.BasketOverviewBuilder basketOverview = BasketOverview.builder();

        basketOverview.productOverview( productToProductOverview( basket.getProduct() ) );
        basketOverview.userOverview( userToUserOverview( basket.getUser() ) );
        basketOverview.id( basket.getId() );
        basketOverview.quantity( basket.getQuantity() );

        return basketOverview.build();
    }

    @Override
    public List<BasketOverview> mapToDtoList(List<Basket> baskets) {
        if ( baskets == null ) {
            return null;
        }

        List<BasketOverview> list = new ArrayList<BasketOverview>( baskets.size() );
        for ( Basket basket : baskets ) {
            list.add( mapToDto( basket ) );
        }

        return list;
    }

    protected Product createBasketDtoToProduct(CreateBasketDto createBasketDto) {
        if ( createBasketDto == null ) {
            return null;
        }

        Product.ProductBuilder product = Product.builder();

        if ( createBasketDto.getProductId() != null ) {
            product.id( createBasketDto.getProductId() );
        }

        return product.build();
    }

    protected ProductOverview productToProductOverview(Product product) {
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
