package am.itspace.townrestaurantsrest.controller;

import am.itspace.townrestaurantscommon.dto.user.CreateUserDto;
import am.itspace.townrestaurantscommon.dto.user.UserAuthDto;
import am.itspace.townrestaurantsrest.serviceRest.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class AuthEndpoint {

    private final UserService userService;

    //    @Operation(
//            operationId = "postUser",
//            summary = "Registration",
//            description = "Registration for user"
//    )
//    @ApiResponse(
//            responseCode = "200",description = "Success",
//            content = @Contact(schema=@Schema(implementation = Activity.class)),
//            headers = @Header(name = TRACE_ID_HEADER_NAME,description = TRACE_ID_HEADER_DESCRIPTION,
//            schema = @Schema(type = "string"))
//    )
    @PostMapping
    public ResponseEntity<?> register(@Valid @RequestBody CreateUserDto createUserDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body((userService.save(createUserDto)));
    }

    @PostMapping("/auth")
    public ResponseEntity<?> auth(@Valid @RequestBody UserAuthDto userAuthDto) {
        return ResponseEntity.ok(userService.authentication(userAuthDto));
    }
}
