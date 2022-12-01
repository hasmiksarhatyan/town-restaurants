package am.itspace.townrestaurantscommon.repository;

import am.itspace.townrestaurantscommon.entity.CreditCard;
import am.itspace.townrestaurantscommon.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditCardRepository extends JpaRepository<CreditCard, Integer> {

    Page<CreditCard> findCreditCardByUser(User use, Pageable pageable);
}
