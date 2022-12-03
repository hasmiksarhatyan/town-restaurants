package am.itspace.townrestaurantsrest.controller;

import am.itspace.townrestaurantscommon.dto.user.CreateUserDto;
import am.itspace.townrestaurantscommon.dto.user.UserAuthDto;
import am.itspace.townrestaurantsrest.api.AuthApi;
import am.itspace.townrestaurantsrest.serviceRest.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthEndpoint implements AuthApi {

    private final UserService userService;

    @Override
    @PostMapping("/user")
    public ResponseEntity<?> register(@Valid @RequestBody CreateUserDto createUserDto) {
        return ResponseEntity.ok((userService.save(createUserDto)));
    }

    @Override
    @PostMapping("/user/auth")
    public ResponseEntity<?> auth(@Valid @RequestBody UserAuthDto userAuthDto) {
        return ResponseEntity.ok(userService.authentication(userAuthDto));
    }
}
