package am.itspace.townrestaurantscommon.mapper;

import am.itspace.townrestaurantscommon.dto.reserve.CreateReserveDto;
import am.itspace.townrestaurantscommon.dto.reserve.ReserveOverview;
import am.itspace.townrestaurantscommon.entity.Reserve;
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
public class ReserveMapperImpl implements ReserveMapper {

    @Override
    public Reserve mapToEntity(CreateReserveDto createReserveDto) {
        if ( createReserveDto == null ) {
            return null;
        }

        Reserve.ReserveBuilder reserve = Reserve.builder();

        reserve.reservedAt( createReserveDto.getReservedAt() );
        reserve.reservedFor( createReserveDto.getReservedFor() );
        reserve.restaurant( createReserveDto.getRestaurant() );
        reserve.user( createReserveDto.getUser() );
        reserve.hostCount( createReserveDto.getHostCount() );

        return reserve.build();
    }

    @Override
    public ReserveOverview mapToDto(Reserve reserve) {
        if ( reserve == null ) {
            return null;
        }

        ReserveOverview.ReserveOverviewBuilder reserveOverview = ReserveOverview.builder();

        reserveOverview.id( reserve.getId() );
        reserveOverview.reservedAt( reserve.getReservedAt() );
        reserveOverview.reservedFor( reserve.getReservedFor() );
        reserveOverview.restaurant( reserve.getRestaurant() );
        reserveOverview.user( reserve.getUser() );
        reserveOverview.hostCount( reserve.getHostCount() );

        return reserveOverview.build();
    }

    @Override
    public List<ReserveOverview> mapToDto(List<Reserve> reserves) {
        if ( reserves == null ) {
            return null;
        }

        List<ReserveOverview> list = new ArrayList<ReserveOverview>( reserves.size() );
        for ( Reserve reserve : reserves ) {
            list.add( mapToDto( reserve ) );
        }

        return list;
    }

    @Override
    public List<ReserveOverview> mapToDto(Page<Reserve> reserves) {
        if ( reserves == null ) {
            return null;
        }

        List<ReserveOverview> list = new ArrayList<ReserveOverview>();
        for ( Reserve reserve : reserves ) {
            list.add( mapToDto( reserve ) );
        }

        return list;
    }
}
