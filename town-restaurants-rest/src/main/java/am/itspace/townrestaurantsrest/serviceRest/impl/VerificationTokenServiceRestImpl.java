package am.itspace.townrestaurantsrest.serviceRest.impl;

import am.itspace.townrestaurantscommon.entity.User;
import am.itspace.townrestaurantscommon.entity.VerificationToken;
import am.itspace.townrestaurantscommon.repository.VerificationTokenRepository;
import am.itspace.townrestaurantsrest.serviceRest.VerificationTokenServiceRest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;


@Slf4j
@Service
@RequiredArgsConstructor
public class VerificationTokenServiceRestImpl implements VerificationTokenServiceRest {

    private final VerificationTokenRepository tokenRepository;

    @Override
    public VerificationToken createToken(User user) {
        return tokenRepository.save(VerificationToken.builder()
                .plainToken(UUID.randomUUID().toString())
                .expiresAt(LocalDateTime.now().plus(12, ChronoUnit.HOURS))
                .user(user)
                .build());
    }

    @Override
    public VerificationToken findByPlainToken(String plainToken) {
        Optional<VerificationToken> tokenOptional = tokenRepository.findByPlainToken(plainToken);
        if(tokenOptional.isEmpty()){
            throw new IllegalStateException("Token doesn't exist");
        }
        VerificationToken token = tokenOptional.get();
        if (token.getExpiresAt().isBefore(LocalDateTime.now())){
            delete(token);
            throw new IllegalStateException("Token doesn't exist");
        }
        return token;
    }

    @Override
    public void delete(VerificationToken token) {
        tokenRepository.delete(token);
    }
}
