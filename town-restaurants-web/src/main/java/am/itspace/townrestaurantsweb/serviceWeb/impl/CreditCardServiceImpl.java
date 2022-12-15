package am.itspace.townrestaurantsweb.serviceWeb.impl;

import am.itspace.townrestaurantscommon.dto.creditCard.CreateCreditCardDto;
import am.itspace.townrestaurantscommon.dto.creditCard.CreditCardOverview;
import am.itspace.townrestaurantscommon.entity.CreditCard;
import am.itspace.townrestaurantscommon.entity.User;
import am.itspace.townrestaurantscommon.mapper.CreditCardMapper;
import am.itspace.townrestaurantscommon.repository.CreditCardRepository;
import am.itspace.townrestaurantsweb.serviceWeb.CreditCardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreditCardServiceImpl implements CreditCardService {

    private final CreditCardMapper creditCardMapper;
    private final CreditCardRepository creditCardRepository;

    @Override
    public Page<CreditCardOverview> getCreditCards(Pageable pageable, User user) {
        List<CreditCard> creditCardByUser = creditCardRepository.findCreditCardByUserId(user.getId());
        if (creditCardByUser.isEmpty()) {
            log.info("Credit card not found");
            throw new IllegalStateException("You don't have a credit card");
        }
        List<CreditCardOverview> creditCardOverviews = creditCardMapper.mapToDto(creditCardByUser);
        log.info("Credit card successfully found");
        return new PageImpl<>(creditCardOverviews);
    }

    @Override
    public void addCreditCard(CreateCreditCardDto cardDto, User user) {
        CreditCard creditCard = creditCardMapper.mapToEntity(cardDto);
        creditCard.setUser(user);
        creditCardRepository.save(creditCard);
        log.info("The credit card was successfully stored in the database {}", creditCard.getCardHolder());
    }

}
