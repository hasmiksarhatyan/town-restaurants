package am.itspace.townrestaurantscommon.mapper;

import am.itspace.townrestaurantscommon.dto.order.OrderOverview;
import am.itspace.townrestaurantscommon.entity.Order;
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
public class OrderMapperImpl implements OrderMapper {

    @Override
    public OrderOverview mapToDto(Order order) {
        if ( order == null ) {
            return null;
        }

        OrderOverview.OrderOverviewBuilder orderOverview = OrderOverview.builder();

        orderOverview.id( order.getId() );
        orderOverview.additionalAddress( order.getAdditionalAddress() );
        orderOverview.additionalPhone( order.getAdditionalPhone() );
        orderOverview.orderAt( order.getOrderAt() );
        orderOverview.totalPrice( order.getTotalPrice() );
        if ( order.getStatus() != null ) {
            orderOverview.status( order.getStatus().name() );
        }
        orderOverview.payment( order.getPayment() );

        return orderOverview.build();
    }

    @Override
    public List<OrderOverview> mapToDto(List<Order> orders) {
        if ( orders == null ) {
            return null;
        }

        List<OrderOverview> list = new ArrayList<OrderOverview>( orders.size() );
        for ( Order order : orders ) {
            list.add( mapToDto( order ) );
        }

        return list;
    }

    @Override
    public List<OrderOverview> mapToDto(Page<Order> orders) {
        if ( orders == null ) {
            return null;
        }

        List<OrderOverview> list = new ArrayList<OrderOverview>();
        for ( Order order : orders ) {
            list.add( mapToDto( order ) );
        }

        return list;
    }
}
