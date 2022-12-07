package am.itspace.townrestaurantscommon.repository;

import am.itspace.townrestaurantscommon.dto.event.EventOverview;
import am.itspace.townrestaurantscommon.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer>, PagingAndSortingRepository<Event, Integer> {

    boolean existsByName(String name);

    List<Event> findEventsByRestaurant_Id(int restaurantId);

    Page<Event> findEventsByRestaurantId(int restaurantId, Pageable pageable);

    @Query("select name from Event name where name=:name")
    Page<Event> findByEventName(@Param("name") String name, Pageable pageReq);

    default Page<Event> findByEventName(EventOverview eventOverview, Pageable pageReq) {
        return findByEventName(eventOverview.getName(), pageReq);
    }
}

