package am.itspace.townrestaurantscommon.repository;

import am.itspace.townrestaurantscommon.entity.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface CreditCardRepository extends JpaRepository<CreditCard, Integer>, PagingAndSortingRepository<CreditCard, Integer> {

    boolean existsByCardNumber(String cardNumber);

    List<CreditCard> findCreditCardByUserId(int id);
}
