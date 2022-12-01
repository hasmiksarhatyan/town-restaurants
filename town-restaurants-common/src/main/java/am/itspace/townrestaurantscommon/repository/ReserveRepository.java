package am.itspace.townrestaurantscommon.repository;

import am.itspace.townrestaurantscommon.entity.Reserve;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReserveRepository extends JpaRepository<Reserve, Integer> {

}
