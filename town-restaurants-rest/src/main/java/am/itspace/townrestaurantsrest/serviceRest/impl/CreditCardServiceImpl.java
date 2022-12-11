package am.itspace.townrestaurantsrest.serviceRest.impl;

import am.itspace.townrestaurantscommon.dto.creditCard.CreateCreditCardDto;
import am.itspace.townrestaurantscommon.dto.creditCard.CreditCardOverview;
import am.itspace.townrestaurantscommon.entity.User;
import am.itspace.townrestaurantsrest.serviceRest.CreditCardService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class CreditCardServiceImpl implements CreditCardService {
    @Override
    public Page<CreditCardOverview> getCreditCards(Pageable pageable, User user) {
        return null;
    }

    @Override
    public void addCreditCard(CreateCreditCardDto createCreditCardDto, User user) {

    }
}
