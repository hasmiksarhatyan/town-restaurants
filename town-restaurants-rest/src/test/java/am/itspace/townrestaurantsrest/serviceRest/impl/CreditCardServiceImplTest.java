package am.itspace.townrestaurantsrest.serviceRest.impl;

import am.itspace.townrestaurantscommon.dto.creditCard.CreditCardOverview;
import am.itspace.townrestaurantscommon.entity.CreditCard;
import am.itspace.townrestaurantscommon.mapper.CreditCardMapper;
import am.itspace.townrestaurantscommon.repository.CreditCardRepository;
import am.itspace.townrestaurantscommon.security.CurrentUser;
import am.itspace.townrestaurantsrest.exception.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

import static am.itspace.townrestaurantsrest.parameters.MockData.*;
import static java.lang.String.format;
import static java.time.LocalDate.now;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreditCardServiceImplTest {

    @InjectMocks
    CreditCardServiceImpl creditCardService;

    @Mock
    CreditCardMapper creditCardMapper;

    @Mock
    CreditCardRepository creditCardRepository;

    @Mock
    SecurityContextServiceImpl securityContextService;

    @Test
    void shouldSaveCreditCard() {
        //given
        var user = getUser();
        var creditCard = getCreditCard();
        CurrentUser currentUser = new CurrentUser(getUser());
        var creatCreditCard = getCreateCreditCardDto();
        String userName = format("%s %s", user.getFirstName(), user.getLastName());
        //when
        doReturn(currentUser).when(securityContextService).getUserDetails();
        creditCard.setUser(currentUser.getUser());
        assertThat(creatCreditCard.getCardHolder().equalsIgnoreCase(userName), Matchers.is(true));
        assertThat(creatCreditCard.getCardExpiresAt().isBefore(now()), Matchers.is(true));
        doReturn(creditCard).when(creditCardRepository).save(any(CreditCard.class));
        creditCardService.save(creditCard);
        //then
        verify(creditCardRepository, times(1)).save(creditCard);
    }

    @Test
    void shouldGetAll() {
        //given
        var listOfPageableCreditCards = getPageCreditCards();
        var expected = List.of(getCreditCardOverview(), getCreditCardOverview());
        PageRequest pageable = PageRequest.of(1, 1, Sort.Direction.fromString("DESC"), "name");
        //when
        doReturn(listOfPageableCreditCards).when(creditCardRepository).findAll(pageable);
        doReturn(expected).when(creditCardMapper).mapToDto(anyList());
        List<CreditCardOverview> actual = creditCardService.getAll(1, 1, "name", "DESC");
        //then
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void getAllShouldThrowException() {
        //given
        Page<CreditCard> empty = Page.empty();
        PageRequest pageable = PageRequest.of(1, 1, Sort.Direction.fromString("DESC"), "name");
        //when
        doReturn(empty).when(creditCardRepository).findAll(pageable);
        //then
        assertThrows(EntityNotFoundException.class, () -> creditCardService.getAll(1, 1, "name", "DESC"));
    }

    @Test
    void shouldGetAllByUser() {
        //given
        CurrentUser currentUser = new CurrentUser(getUser());
        var listOfPageableCreditCards = getPageCreditCards();
        var expected = List.of(getCreditCardOverview(), getCreditCardOverview());
        PageRequest pageable = PageRequest.of(1, 1, Sort.Direction.fromString("DESC"), "name");
        //when
        doReturn(currentUser).when(securityContextService).getUserDetails();
        doReturn(listOfPageableCreditCards).when(creditCardRepository).findAllByUser(currentUser.getUser(), pageable);
        doReturn(expected).when(creditCardMapper).mapToDto(anyList());
        List<CreditCardOverview> actual = creditCardService.getAllByUser(1, 1, "name", "DESC");
        //then
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void getAllByUserShouldThrowException() {
        //given
        Page<CreditCard> empty = Page.empty();
        CurrentUser currentUser = new CurrentUser(getUser());
        PageRequest pageable = PageRequest.of(1, 1, Sort.Direction.fromString("DESC"), "name");
        //when
        doReturn(currentUser).when(securityContextService).getUserDetails();
        doReturn(empty).when(creditCardRepository).findAllByUser(currentUser.getUser(), pageable);
        //then
        assertThrows(EntityNotFoundException.class, () -> creditCardService.getAllByUser(1, 1, "name", "DESC"));
    }
}