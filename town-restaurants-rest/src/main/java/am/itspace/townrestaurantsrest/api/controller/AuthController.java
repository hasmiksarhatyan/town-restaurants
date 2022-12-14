package am.itspace.townrestaurantsrest.api.controller;

import am.itspace.townrestaurantscommon.dto.token.VerificationTokenDto;
import am.itspace.townrestaurantscommon.dto.user.CreateUserDto;
import am.itspace.townrestaurantscommon.dto.user.UserAuthDto;
import am.itspace.townrestaurantsrest.api.AuthApi;
import am.itspace.townrestaurantsrest.serviceRest.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AuthController implements AuthApi {

    private final UserService userService;

    @Override
    @PostMapping("/registration")
    public ResponseEntity<?> register(@Valid @RequestBody CreateUserDto createUserDto) {
        return ResponseEntity.ok((userService.save(createUserDto)));
    }

    @Override
    @PostMapping("/activation")
    public ResponseEntity<?> authentication(@Valid @RequestBody UserAuthDto userAuthDto) {
        return ResponseEntity.ok(userService.authentication(userAuthDto));
    }

    @Override
    @PostMapping("/verification")
    public ResponseEntity<?> verifyToken(@Valid @RequestBody VerificationTokenDto verificationTokenDto) {
        return ResponseEntity.ok((userService.verifyToken(verificationTokenDto)));
    }
}
