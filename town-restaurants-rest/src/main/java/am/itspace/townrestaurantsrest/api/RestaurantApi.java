package am.itspace.townrestaurantsrest.api;

import am.itspace.townrestaurantscommon.dto.FileDto;
import am.itspace.townrestaurantscommon.dto.event.EventOverview;
import am.itspace.townrestaurantscommon.dto.FetchRequestDto;
import am.itspace.townrestaurantscommon.dto.product.ProductOverview;
import am.itspace.townrestaurantscommon.dto.restaurant.CreateRestaurantDto;
import am.itspace.townrestaurantscommon.dto.restaurant.EditRestaurantDto;
import am.itspace.townrestaurantscommon.dto.restaurant.RestaurantOverview;
import am.itspace.townrestaurantsrest.exception.ApiError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public interface RestaurantApi {

    @Operation(
            summary = "Add new restaurant",
            description = "Possible error codes: 4001, 4010, 4050")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Restaurant has been added",
                            content = @Content(
                                    schema = @Schema(implementation = RestaurantOverview.class),
                                    mediaType = APPLICATION_JSON_VALUE)),
                    @ApiResponse(
                            responseCode = "4001",
                            description = "Restaurant already exists",
                            content = @Content(
                                    mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ApiError.class))),
                    @ApiResponse(
                            responseCode = "4010",
                            description = "Error occurred while uploading multipart file.",
                            content = @Content(
                                    mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ApiError.class))),
                    @ApiResponse(
                            responseCode = "4050",
                            description = "File not found",
                            content = @Content(
                                    mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ApiError.class)))})
    ResponseEntity<RestaurantOverview> create(CreateRestaurantDto createRestaurantDto, FileDto fileDto);

    @Operation(
            summary = "Get image",
            description = "Possible error codes: 4050")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Fetched image from DB"),
                    @ApiResponse(
                            responseCode = "4050",
                            description = "File not found",
                            content = @Content(
                                    mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ApiError.class)))})
    byte[] getImage(String fileName);

    @Operation(
            summary = "Get all restaurants",
            description = "Possible error codes: 4041")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Fetched restaurants from DB",
                            content = @Content(
                                    schema = @Schema(implementation = RestaurantOverview.class),
                                    mediaType = APPLICATION_JSON_VALUE)),
                    @ApiResponse(
                            responseCode = "4041",
                            description = "Restaurant not found",
                            content = @Content(
                                    mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ApiError.class)))})
    ResponseEntity<List<RestaurantOverview>> getAll();

    @Operation(
            summary = "Get all restaurants",
            description = "Possible error codes: 4041")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Fetched restaurants from DB",
                            content = @Content(
                                    schema = @Schema(implementation = RestaurantOverview.class),
                                    mediaType = APPLICATION_JSON_VALUE)),
                    @ApiResponse(
                            responseCode = "4041",
                            description = "Restaurant not found",
                            content = @Content(
                                    mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ApiError.class)))})
    ResponseEntity<List<RestaurantOverview>> getAll(FetchRequestDto fetchRequestDto);

    @Operation(
            summary = "Get all restaurants by user",
            description = "Possible error codes: 4041, 4094")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Fetched restaurants from DB",
                            content = @Content(
                                    schema = @Schema(implementation = RestaurantOverview.class),
                                    mediaType = APPLICATION_JSON_VALUE)),
                    @ApiResponse(
                            responseCode = "4041",
                            description = "Restaurant not found",
                            content = @Content(
                                    mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ApiError.class))),
                    @ApiResponse(
                            responseCode = "4094",
                            description = "Needs to authenticate",
                            content = @Content(
                                    mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ApiError.class)))})
    ResponseEntity<List<RestaurantOverview>> getAllByUser(@Valid @RequestBody FetchRequestDto fetchRequestDto);

    @Operation(
            summary = "Get restaurant",
            description = "Possible error codes: 4041")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Fetched the restaurant from DB",
                            content =
                            @Content(
                                    schema = @Schema(implementation = RestaurantOverview.class),
                                    mediaType = APPLICATION_JSON_VALUE)),
                    @ApiResponse(
                            responseCode = "4041",
                            description = "Restaurant not found",
                            content =
                            @Content(
                                    schema = @Schema(implementation = ApiError.class),
                                    mediaType = APPLICATION_JSON_VALUE))
            })
    ResponseEntity<RestaurantOverview> getById(int id);

    @Operation(
            summary = "Get events by restaurantId",
            description = "Possible error codes: 4044")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Fetched events from DB",
                            content =
                            @Content(
                                    schema = @Schema(implementation = EventOverview.class),
                                    mediaType = APPLICATION_JSON_VALUE)),
                    @ApiResponse(
                            responseCode = "4044",
                            description = "Event not found",
                            content =
                            @Content(
                                    schema = @Schema(implementation = ApiError.class),
                                    mediaType = APPLICATION_JSON_VALUE))
            })
    ResponseEntity<List<EventOverview>> findEventsByRestaurantId(int id);

    @Operation(
            summary = "Get products by restaurantId",
            description = "Possible error codes: 4043")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Fetched products from DB",
                            content =
                            @Content(
                                    schema = @Schema(implementation = ProductOverview.class),
                                    mediaType = APPLICATION_JSON_VALUE)),
                    @ApiResponse(
                            responseCode = "4043",
                            description = "Product not found",
                            content =
                            @Content(
                                    schema = @Schema(implementation = ApiError.class),
                                    mediaType = APPLICATION_JSON_VALUE))
            })
    ResponseEntity<List<ProductOverview>> findProductsByRestaurantId(int id);

    @Operation(
            summary = "Update restaurant",
            description = "Possible error codes: 4041")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Updated a restaurant from DB",
                            content =
                            @Content(
                                    schema = @Schema(implementation = RestaurantOverview.class),
                                    mediaType = APPLICATION_JSON_VALUE)),
                    @ApiResponse(
                            responseCode = "4041",
                            description = "Restaurant not found",
                            content =
                            @Content(
                                    schema = @Schema(implementation = ApiError.class),
                                    mediaType = APPLICATION_JSON_VALUE))
            })
    ResponseEntity<RestaurantOverview> update(int id, EditRestaurantDto editRestaurantDto);

    @Operation(
            summary = "Delete restaurant",
            description = "Possible error codes: 4041")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Deleted a restaurant from DB"),
                    @ApiResponse(
                            responseCode = "4041",
                            description = "Restaurant not found",
                            content =
                            @Content(
                                    schema = @Schema(implementation = ApiError.class),
                                    mediaType = APPLICATION_JSON_VALUE))
            })
    ResponseEntity<?> delete(int id);
}
