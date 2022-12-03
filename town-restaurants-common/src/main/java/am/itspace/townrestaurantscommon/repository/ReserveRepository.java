package am.itspace.townrestaurantscommon.repository;

import am.itspace.townrestaurantscommon.entity.Reserve;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReserveRepository extends JpaRepository<Reserve, Integer> {
    List<Reserve> findByRestaurantId(int id);

    Page<Reserve> findByUserId(int id, Pageable pageable);
}
