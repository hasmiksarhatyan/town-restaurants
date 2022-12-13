package am.itspace.townrestaurantscommon.repository;

import am.itspace.townrestaurantscommon.entity.Reserve;
import am.itspace.townrestaurantscommon.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ReserveRepository extends JpaRepository<Reserve, Integer>, PagingAndSortingRepository<Reserve, Integer> {

    List<Reserve> findByUser(User user);

    List<Reserve> findByRestaurantId(int id);

    boolean existsByPhoneNumber(String phoneNumber);

    Page<Reserve> findByUserId(int id, Pageable pageable);
}
