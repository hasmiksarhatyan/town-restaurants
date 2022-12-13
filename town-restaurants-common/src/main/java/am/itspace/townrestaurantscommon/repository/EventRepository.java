package am.itspace.townrestaurantscommon.repository;

import am.itspace.townrestaurantscommon.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer>, PagingAndSortingRepository<Event, Integer> {

    boolean existsByName(String name);

    List<Event> findEventsByRestaurant_Id(int restaurantId);

    Page<Event> findEventsByRestaurantId(int restaurantId, Pageable pageable);
}

