package am.itspace.townrestaurantsrest.serviceRest;

import am.itspace.townrestaurantscommon.dto.creditCard.CreateCreditCardDto;
import am.itspace.townrestaurantscommon.dto.creditCard.CreditCardOverview;
import am.itspace.townrestaurantscommon.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CreditCardService {

    Page<CreditCardOverview> getCreditCards(Pageable pageable, User user);

    void addCreditCard(CreateCreditCardDto createCreditCardDto, User user);
}
