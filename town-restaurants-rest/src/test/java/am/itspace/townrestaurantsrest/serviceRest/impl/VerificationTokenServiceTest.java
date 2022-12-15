package am.itspace.townrestaurantsrest.serviceRest.impl;

import am.itspace.townrestaurantscommon.repository.VerificationTokenRepository;
import am.itspace.townrestaurantsrest.exception.TokenNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static am.itspace.townrestaurantsrest.parameters.MockData.getToken;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VerificationTokenServiceTest {

    @InjectMocks
    VerificationTokenServiceRestImpl tokenService;

    @Mock
    VerificationTokenRepository tokenRepository;

    @Test
    void shouldFindByPlainToken() {
        //given
        var token = getToken();
        //when
        doReturn(Optional.of(token)).when(tokenRepository).findByPlainToken("");
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
