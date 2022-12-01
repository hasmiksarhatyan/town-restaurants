package am.itspace.townrestaurantsrest.repository;

import am.itspace.townrestaurantscommon.entity.VerificationToken;
import am.itspace.townrestaurantscommon.repository.VerificationTokenRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class VerificationTokenRepositoryTest {

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Test
    void findByPlainToken() {
        VerificationToken token = VerificationToken.builder()
                .plainToken("123456")
                .build();
        verificationTokenRepository.save(token);
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByPlainToken("123456");
        assertNotNull(verificationToken);
    }
}