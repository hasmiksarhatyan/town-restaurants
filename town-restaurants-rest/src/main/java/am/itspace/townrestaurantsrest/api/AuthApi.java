package am.itspace.townrestaurantsrest.api;

import am.itspace.townrestaurantscommon.dto.user.CreateUserDto;
import am.itspace.townrestaurantscommon.dto.user.UserAuthDto;
import am.itspace.townrestaurantsrest.exception.ApiError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.mail.MessagingException;
import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public interface AuthApi {

    @Operation(
            summary = "Register new user",
            description = "Possible error code: 4006")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "New user should be registered.",
                            content =
                            @Content(
                                    mediaType = APPLICATION_JSON_VALUE)),
                    @ApiResponse(
                            responseCode = "4006",
                            description = "There already is an duplicate value for email",
                            content = @Content(
                                    mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ApiError.class)))})
    @PostMapping("/user")
    ResponseEntity<?> register(@Valid @RequestBody CreateUserDto createUserDto) throws MessagingException;

    @Operation(
            summary = "Authentication for user",
            description = "Possible error codes: 4011")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User should be authenticated.",
                            content = @Content(mediaType = APPLICATION_JSON_VALUE)),
                    @ApiResponse(
                            responseCode = "4011",
                            description = "Wrong email or password.",
                            content =
                            @Content(
                                    mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ApiError.class)))
            })
    @PostMapping("user/auth")
    ResponseEntity<?> auth(@Valid @RequestBody UserAuthDto userAuthDto);
}
