package am.itspace.townrestaurantsrest.serviceRest;


import am.itspace.townrestaurantscommon.dto.creditCard.CreateCreditCardDto;
import am.itspace.townrestaurantscommon.dto.creditCard.CreditCardOverview;
import am.itspace.townrestaurantscommon.entity.CreditCard;

import java.util.List;

public interface CreditCardService {

    List<CreditCardOverview> getAllByUser();

    void save(CreditCard creditCard);

    List<CreditCardOverview> getAllCreditCards(int pageNo, int pageSize, String sortBy, String sortDir);
}
