package am.itspace.townrestaurantscommon.repository;

import am.itspace.townrestaurantscommon.dto.reserve.ReserveOverview;
import am.itspace.townrestaurantscommon.entity.Reserve;
import am.itspace.townrestaurantscommon.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReserveRepository extends JpaRepository<Reserve, Integer>, PagingAndSortingRepository<Reserve, Integer> {

    List<Reserve> findByUser(User user);

    List<Reserve> findByRestaurantId(int id);

    Page<Reserve> findByUserId(int id, Pageable pageable);

    @Query("select phoneNumber from Reserve phoneNumber where phoneNumber=:phoneNumber")
    Page<Reserve> findByReservePhoneNumber(@Param("phoneNumber") String phoneNumber, Pageable pageReq);

    default Page<Reserve> findByReservePhoneNumber(ReserveOverview reserveOverview, Pageable pageReq) {
        return findByReservePhoneNumber(reserveOverview.getPhoneNumber(), pageReq);
    }

    boolean existsByPhoneNumberAndReservedTimeAndReservedDate(String phoneNumber, String reservedTime, String reservedDate);
}
