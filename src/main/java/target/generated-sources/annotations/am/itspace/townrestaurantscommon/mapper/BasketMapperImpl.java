package am.itspace.townrestaurantscommon.mapper;

import am.itspace.townrestaurantscommon.dto.basket.BasketOverview;
import am.itspace.townrestaurantscommon.dto.basket.CreateBasketDto;
import am.itspace.townrestaurantscommon.entity.Basket;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.4.1 (Amazon.com Inc.)"
)
@Component
public class BasketMapperImpl implements BasketMapper {

    @Override
    public Basket mapToEntity(CreateBasketDto createBasketDto) {
        if ( createBasketDto == null ) {
            return null;
        }

        Basket.BasketBuilder basket = Basket.builder();

        return basket.build();
    }

    @Override
    public BasketOverview mapToDto(Basket basket) {
        if ( basket == null ) {
            return null;
        }

        BasketOverview.BasketOverviewBuilder basketOverview = BasketOverview.builder();

        basketOverview.id( basket.getId() );
        basketOverview.quantity( basket.getQuantity() );
        basketOverview.product( basket.getProduct() );
        basketOverview.user( basket.getUser() );

        return basketOverview.build();
    }

    @Override
    public List<BasketOverview> mapToDto(List<Basket> baskets) {
        if ( baskets == null ) {
            return null;
        }

        List<BasketOverview> list = new ArrayList<BasketOverview>( baskets.size() );
        for ( Basket basket : baskets ) {
            list.add( mapToDto( basket ) );
        }

        return list;
    }

    @Override
    public List<BasketOverview> mapToDto(Page<Basket> baskets) {
        if ( baskets == null ) {
            return null;
        }

        List<BasketOverview> list = new ArrayList<BasketOverview>();
        for ( Basket basket : baskets ) {
            list.add( mapToDto( basket ) );
        }

        return list;
    }
}
