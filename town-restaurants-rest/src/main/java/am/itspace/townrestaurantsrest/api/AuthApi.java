package am.itspace.townrestaurantsrest.api;

import am.itspace.townrestaurantscommon.dto.token.VerificationTokenDto;
import am.itspace.townrestaurantscommon.dto.user.CreateUserDto;
import am.itspace.townrestaurantscommon.dto.user.UserAuthDto;
import am.itspace.townrestaurantsrest.exception.ApiError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public interface AuthApi {

    @Operation(
            summary = "Register new user",
            description = "Possible error codes: 4006, 5001")
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
                                    schema = @Schema(implementation = ApiError.class))),
                    @ApiResponse(
                            responseCode = "5001",
                            description = "Failed to send an email",
                            content = @Content(
                                    mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ApiError.class)))})
    ResponseEntity<?> register(CreateUserDto createUserDto);

    @Operation(
            summary = "Authentication for user",
            description = "Possible error code: 4011")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User should be authenticated.",
                            content = @Content(mediaType = APPLICATION_JSON_VALUE)),
                    @ApiResponse(
                            responseCode = "4011",
                            description = "Wrong email or password, user not found.",
                            content =
                            @Content(
                                    mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ApiError.class)))
            })
    ResponseEntity<?> authentication(UserAuthDto userAuthDto);

    @Operation(
            summary = "Verification for user",
            description = "Possible error codes: 4046, 4049, 4092, 4093")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User should be verified.",
                            content = @Content(mediaType = APPLICATION_JSON_VALUE)),
                    @ApiResponse(
                            responseCode = "4046",
                            description = "User not found.",
                            content = @Content(
                                    mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ApiError.class))),
                    @ApiResponse(
                            responseCode = "4049",
                            description = "Token not found.",
                            content = @Content(
                                    mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ApiError.class))),
                    @ApiResponse(
                            responseCode = "4092",
                            description = "User already enabled.",
                            content = @Content(
                                    mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ApiError.class))),
                    @ApiResponse(
                            responseCode = "4093",
                            description = "Token has expired.",
                            content = @Content(
                                    mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ApiError.class)))})
    ResponseEntity<?> verifyToken(VerificationTokenDto verificationTokenDto);
}

