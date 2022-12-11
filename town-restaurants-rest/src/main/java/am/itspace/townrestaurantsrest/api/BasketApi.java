package am.itspace.townrestaurantsrest.api;

import am.itspace.townrestaurantscommon.dto.FetchRequestDto;
import am.itspace.townrestaurantscommon.dto.basket.BasketOverview;
import am.itspace.townrestaurantsrest.exception.ApiError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public interface BasketApi {

    @Operation(
            summary = "Add product to basket",
            description = "Possible error codes: 4043, 4094")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Product has been added"),
                    @ApiResponse(
                            responseCode = "4043",
                            description = "Product not found",
                            content = @Content(
                                    mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ApiError.class))),
                    @ApiResponse(
                            responseCode = "4094",
                            description = "Needs to authenticate",
                            content = @Content(
                                    mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ApiError.class)))})
    ResponseEntity<?> create(int productId);

    @Operation(
            summary = "Get all baskets",
            description = "Possible error codes: 4048")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Fetched baskets from DB",
                            content = @Content(
                                    schema = @Schema(implementation = BasketOverview.class),
                                    mediaType = APPLICATION_JSON_VALUE)),
                    @ApiResponse(
                            responseCode = "4048",
                            description = "Basket not found",
                            content = @Content(
                                    mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ApiError.class)))})
    ResponseEntity<List<BasketOverview>> getAll();

    @Operation(
            summary = "Get all baskets",
            description = "Possible error codes: 4048, 4094")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Fetched baskets from DB",
                            content = @Content(
                                    schema = @Schema(implementation = BasketOverview.class),
                                    mediaType = APPLICATION_JSON_VALUE)),
                    @ApiResponse(
                            responseCode = "4048",
                            description = "Basket not found",
                            content = @Content(
                                    mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ApiError.class))),
                    @ApiResponse(
                            responseCode = "4094",
                            description = "Needs to authenticate",
                            content = @Content(
                                    mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ApiError.class)))})
    ResponseEntity<List<BasketOverview>> getAll(FetchRequestDto fetchRequestDto);

    @Operation(
            summary = "Get total price of products",
            description = "Possible error codes: 4048, 4094")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Fetched baskets from DB"),
                    @ApiResponse(
                            responseCode = "4048",
                            description = "Basket not found",
                            content = @Content(
                                    mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ApiError.class))),
                    @ApiResponse(
                            responseCode = "4094",
                            description = "Needs to authenticate",
                            content = @Content(
                                    mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ApiError.class)))})
    ResponseEntity<Double> getTotalPrice();

    @Operation(
            summary = "Remove product from basket",
            description = "Possible error codes: 4048, 4094")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Removed product from DB"),
                    @ApiResponse(
                            responseCode = "4048",
                            description = "Basket not found",
                            content = @Content(
                                    mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ApiError.class))),
                    @ApiResponse(
                            responseCode = "4094",
                            description = "Needs to authenticate",
                            content = @Content(
                                    mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ApiError.class)))})
    ResponseEntity<?> delete(int id);
}
