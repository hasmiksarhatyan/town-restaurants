package am.itspace.townrestaurantsweb.serviceWeb.impl;

import am.itspace.townrestaurantscommon.dto.creditCard.CreateCreditCardDto;
import am.itspace.townrestaurantscommon.dto.creditCard.CreditCardOverview;
import am.itspace.townrestaurantscommon.entity.CreditCard;
import am.itspace.townrestaurantscommon.entity.User;
import am.itspace.townrestaurantscommon.mapper.CreditCardMapper;
import am.itspace.townrestaurantscommon.mapper.UserMapper;
import am.itspace.townrestaurantscommon.repository.CreditCardRepository;
import am.itspace.townrestaurantsweb.serviceWeb.CreditCardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.String.format;
import static java.time.LocalDate.now;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreditCardServiceImpl implements CreditCardService {

    private final CreditCardRepository creditCardRepository;
    private final CreditCardMapper creditCardMapper;
    private final UserMapper userMapper;

    @Override
    public Page<CreditCardOverview> getCreditCards(Pageable pageable, User user) {
        List<CreditCard> creditCardByUser = creditCardRepository.findCreditCardByUserId(user.getId());
        if (creditCardByUser.isEmpty()) {
            throw new IllegalStateException("You don't have a basket");
        }
        List<CreditCardOverview> creditCardOverviews = creditCardMapper.mapToDto(creditCardByUser);
        return new PageImpl<>(creditCardOverviews);
    }

    @Override
    public void addCreditCard(CreateCreditCardDto cardDto, User user) {
        validateCreditCard(cardDto,user);
        cardDto.setUserOverview(userMapper.mapToResponseDto(user));
        CreditCard creditCard = creditCardMapper.mapToEntity(cardDto);
        creditCardRepository.save(creditCard);
    }

    private void validateCreditCard(CreateCreditCardDto creditCardDto, User user) {
        String cardHolder = creditCardDto.getCardHolder();
        String userName = format("%s %s",user.getFirstName(),user.getLastName());

        if(!cardHolder.equalsIgnoreCase(userName)){
            throw new IllegalStateException("Wrong Credit Card");
        }
        if(creditCardDto.getCardExpiresAt().isBefore(now())){
            throw new IllegalStateException("Expired Credit Card");
        }
    }
}
