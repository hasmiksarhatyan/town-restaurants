package am.itspace.townrestaurantsrest.serviceRest.impl;

import am.itspace.townrestaurantscommon.entity.VerificationToken;
import am.itspace.townrestaurantscommon.repository.VerificationTokenRepository;
import am.itspace.townrestaurantsrest.exception.TokenNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static am.itspace.townrestaurantsrest.parameters.MockData.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VerificationTokenServiceTest {

    @InjectMocks
    VerificationTokenServiceRestImpl tokenService;

    @Mock
    VerificationTokenRepository tokenRepository;

    @Test
    void shouldCreateToken() {
        //given
        var user = getUserForToken();
        var token = getVToken();
        //when
        doReturn(token).when(tokenRepository).save(any(VerificationToken.class));
        VerificationToken actual = tokenService.createToken(user);
        //then
        assertNotNull(actual);
    }

    @Test
    void shouldFindByPlainToken() {
        //given
        var token = getVToken();
        //when
        doReturn(Optional.of(token)).when(tokenRepository).findByPlainToken(token.getPlainToken());
        VerificationToken actual = tokenService.findByPlainToken(token.getPlainToken());
        //then
        assertNotNull(actual);
    }

    @Test
    void findByPlainTokenShouldThrowExceptionAsTokenHasExpired() {
        //given
        var token = getToken();
        //when
        doReturn(Optional.of(token)).when(tokenRepository).findByPlainToken(anyString());
        //then
        assertThrows(TokenNotFoundException.class, () -> tokenService.findByPlainToken(anyString()));
    }

    @Test
    void findByPlainTokenShouldThrowExceptionAsTokenIsEmpty() {
        //given
        Optional<VerificationToken> token = Optional.empty();
        //when
        doReturn(token).when(tokenRepository).findByPlainToken(anyString());
        //then
        assertThrows(TokenNotFoundException.class, () -> tokenService.findByPlainToken(anyString()));
    }

    @Test
    void deleteSuccess() {
        //give
        var token = getToken();
        //when
        tokenService.delete(token);
        //then
        verify(tokenRepository).delete(token);
    }
}
