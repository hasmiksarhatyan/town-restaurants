package am.itspace.townrestaurantscommon.repository;

import am.itspace.townrestaurantscommon.entity.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CreditCardRepository extends JpaRepository<CreditCard, Integer> {

    List<CreditCard> findCreditCardByUserId(int id);

    boolean existsByCardNumber(String cardNumber);
}
