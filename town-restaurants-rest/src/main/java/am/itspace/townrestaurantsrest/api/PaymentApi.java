package am.itspace.townrestaurantsrest.api;

import am.itspace.townrestaurantscommon.dto.FetchRequestDto;
import am.itspace.townrestaurantscommon.dto.payment.PaymentOverview;
import am.itspace.townrestaurantsrest.exception.ApiError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


public interface PaymentApi {

    @Operation(
            summary = "Get all payments",
            description = "Possible error code: 4052")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Fetched payments from DB.",
                            content = @Content(
                                    schema = @Schema(implementation = PaymentOverview.class),
                                    mediaType = APPLICATION_JSON_VALUE)),
                    @ApiResponse(
                            responseCode = "4052",
                            description = "Payment not found.",
                            content = @Content(
                                    mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ApiError.class)))})
    ResponseEntity<List<PaymentOverview>> getAll();

    @Operation(
            summary = "Get all payments",
            description = "Possible error codes: 4052, 4094")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Fetched payments from DB.",
                            content = @Content(
                                    schema = @Schema(implementation = PaymentOverview.class),
                                    mediaType = APPLICATION_JSON_VALUE)),
                    @ApiResponse(
                            responseCode = "4052",
                            description = "Payment not found.",
                            content = @Content(
                                    mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ApiError.class))),
                    @ApiResponse(
                            responseCode = "4094",
                            description = "Needs to authenticate.",
                            content = @Content(
                                    mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ApiError.class)))})
    ResponseEntity<List<PaymentOverview>> getAll(FetchRequestDto fetchRequestDto);

    @Operation(
            summary = "Delete payment",
            description = "Possible error code: 4042")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Deleted a payment from DB."),
                    @ApiResponse(
                            responseCode = "4052",
                            description = "Payment not found.",
                            content = @Content(
                                    mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ApiError.class)))})
    ResponseEntity<?> delete(int id);
}

