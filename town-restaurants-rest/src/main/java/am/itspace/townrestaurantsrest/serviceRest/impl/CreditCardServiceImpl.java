package am.itspace.townrestaurantsrest.serviceRest.impl;

import am.itspace.townrestaurantscommon.dto.creditCard.CreditCardOverview;
import am.itspace.townrestaurantscommon.entity.CreditCard;
import am.itspace.townrestaurantscommon.entity.User;
import am.itspace.townrestaurantscommon.mapper.CreditCardMapper;
import am.itspace.townrestaurantscommon.repository.CreditCardRepository;
import am.itspace.townrestaurantsrest.exception.AuthenticationException;
import am.itspace.townrestaurantsrest.exception.EntityNotFoundException;
import am.itspace.townrestaurantsrest.serviceRest.CreditCardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static am.itspace.townrestaurantsrest.exception.Error.*;
import static java.lang.String.format;
import static java.time.LocalDate.now;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CreditCardServiceImpl implements CreditCardService {

    private final CreditCardMapper creditCardMapper;
    private final CreditCardRepository creditCardRepository;
    private final SecurityContextServiceImpl securityContextService;

    @Override
    public void save(CreditCard creditCard) {
        try {
            User user = securityContextService.getUserDetails().getUser();
            validateCreditCard(creditCard, user);
            log.info("The credit card was successfully validated {}", creditCard.getCardHolder());
            creditCard.setUser(user);
            creditCardRepository.save(creditCard);
            log.info("The credit card was successfully stored in the database {}", creditCard.getCardHolder());
        } catch (ClassCastException e) {
            throw new AuthenticationException(NEEDS_AUTHENTICATION);
        }
    }

    private void validateCreditCard(CreditCard creditCard, User user) {
        String cardHolder = creditCard.getCardHolder();
        String userName = format("%s %s", user.getFirstName(), user.getLastName());
        if (!cardHolder.equalsIgnoreCase(userName)) {
            throw new EntityNotFoundException(WRONG_CREDIT_CARD_NUMBER);
        }
        if (creditCard.getCardExpiresAt().isBefore(now())) {
            throw new EntityNotFoundException(EXPIRED_CREDIT_CARD);
        }
    }

    @Override
    public List<CreditCardOverview> getAllCreditCards(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<CreditCard> cards = creditCardRepository.findAll(pageable);
        if (cards.isEmpty()) {
            log.info("Credit card not found");
            throw new EntityNotFoundException(CREDIT_CARD_NOT_FOUND);
        }
        List<CreditCard> listOfCards = cards.getContent();
        log.info("Credit card successfully found");
        return new ArrayList<>(creditCardMapper.mapToDto(listOfCards));
    }

    @Override
    public List<CreditCardOverview> getAllByUser() {
        try {
            User user = securityContextService.getUserDetails().getUser();
            List<CreditCard> creditCardByUser = creditCardRepository.findCreditCardByUserId(user.getId());
            if (creditCardByUser.isEmpty()) {
                log.info("Credit card not found");
                throw new EntityNotFoundException(CREDIT_CARD_NOT_FOUND);
            }
            log.info("Credit card successfully found");
            return creditCardMapper.mapToDto(creditCardByUser);
        } catch (ClassCastException e) {
            throw new AuthenticationException(NEEDS_AUTHENTICATION);
        }
    }
}
