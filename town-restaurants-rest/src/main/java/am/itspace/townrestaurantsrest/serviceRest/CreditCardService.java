package am.itspace.townrestaurantsrest.serviceRest;


import am.itspace.townrestaurantscommon.dto.FetchRequestDto;
import am.itspace.townrestaurantscommon.dto.creditCard.CreateCreditCardDto;
import am.itspace.townrestaurantscommon.dto.creditCard.CreditCardOverview;
import am.itspace.townrestaurantscommon.entity.CreditCard;

import java.util.List;

public interface CreditCardService {

    List<CreditCardOverview> getAllByUser();

    void save(CreateCreditCardDto createCreditCardDto);

    List<CreditCard> getCardsList(FetchRequestDto fetchRequestDto);
}
