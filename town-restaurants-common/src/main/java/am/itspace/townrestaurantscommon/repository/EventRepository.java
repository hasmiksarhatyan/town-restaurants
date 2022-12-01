package am.itspace.townrestaurantscommon.repository;

import am.itspace.townrestaurantscommon.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer> {

    List<Event> findEventsByRestaurant_Id(int restaurantId);

    Page<Event> findEventsByRestaurantId(int restaurantId,Pageable pageable);

    boolean existsByName(String name);
}

