package am.itspace.townrestaurantscommon.repository;

import am.itspace.townrestaurantscommon.dto.creditCard.CreditCardOverview;
import am.itspace.townrestaurantscommon.entity.CreditCard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CreditCardRepository extends JpaRepository<CreditCard, Integer> {

    boolean existsByCardNumber(String cardNumber);

    List<CreditCard> findCreditCardByUserId(int id);

    @Query("select cardNumber from CreditCard cardNumber where cardNumber=:cardNumber")
    Page<CreditCard> findByCardNumber(@Param("cardNumber") String cardNumber, Pageable pageReq);

    default Page<CreditCard> findByCardNumber(CreditCardOverview creditCardOverview, Pageable pageReq) {
        return findByCardNumber(creditCardOverview.getCardNumber(), pageReq);
    }
}
