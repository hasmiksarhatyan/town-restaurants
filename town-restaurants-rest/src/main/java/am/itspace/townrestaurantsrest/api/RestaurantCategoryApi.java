package am.itspace.townrestaurantsrest.api;

import am.itspace.townrestaurantscommon.dto.restaurantCategory.CreateRestaurantCategoryDto;
import am.itspace.townrestaurantscommon.dto.restaurantCategory.EditRestaurantCategoryDto;
import am.itspace.townrestaurantscommon.dto.restaurantCategory.RestaurantCategoryOverview;
import am.itspace.townrestaurantsrest.exception.ApiError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


public interface RestaurantCategoryApi {

    @Operation(
            summary = "Add new restaurant category",
            description = "Possible error codes: 4002")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Restaurant category has been added",
                            content = @Content(
                                    schema = @Schema(implementation = RestaurantCategoryOverview.class),
                                    mediaType = APPLICATION_JSON_VALUE)),
                    @ApiResponse(
                            responseCode = "4002",
                            description = "Restaurant category already exists",
                            content = @Content(
                                    mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ApiError.class)))
            })
    ResponseEntity<RestaurantCategoryOverview> create(CreateRestaurantCategoryDto createRestaurantCategoryDto);

    @Operation(
            summary = "Get all restaurant categories",
            description = "Possible error codes: 4042")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Fetched restaurant categories from DB",
                            content = @Content(
                                    schema = @Schema(implementation = RestaurantCategoryOverview.class),
                                    mediaType = APPLICATION_JSON_VALUE)),
                    @ApiResponse(
                            responseCode = "4042",
                            description = "Restaurant category not found",
                            content = @Content(
                                    mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ApiError.class)))})
    ResponseEntity<List<RestaurantCategoryOverview>> getAll();

    @Operation(
            summary = "Get all restaurant categories",
            description = "Possible error codes: 4042")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Fetched restaurant categories from DB",
                            content = @Content(
                                    schema = @Schema(implementation = RestaurantCategoryOverview.class),
                                    mediaType = APPLICATION_JSON_VALUE)),
                    @ApiResponse(
                            responseCode = "4042",
                            description = "Restaurant category not found",
                            content = @Content(
                                    mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ApiError.class)))})
    ResponseEntity<List<RestaurantCategoryOverview>> getAll(int pageNo, int pageSize, String sortBy, String sortDir);

    @Operation(
            summary = "Get restaurant category",
            description = "Possible error codes: 4042")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Fetched a restaurant category from DB",
                            content =
                            @Content(
                                    schema = @Schema(implementation = RestaurantCategoryOverview.class),
                                    mediaType = APPLICATION_JSON_VALUE)),
                    @ApiResponse(
                            responseCode = "4042",
                            description = "Restaurant category not found",
                            content =
                            @Content(
                                    schema = @Schema(implementation = ApiError.class),
                                    mediaType = APPLICATION_JSON_VALUE))
            })
    ResponseEntity<RestaurantCategoryOverview> getById(int id);

    @Operation(
            summary = "Update restaurant category",
            description = "Possible error codes: 4042")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Updated a restaurant category from DB",
                            content =
                            @Content(
                                    schema = @Schema(implementation = RestaurantCategoryOverview.class),
                                    mediaType = APPLICATION_JSON_VALUE)),
                    @ApiResponse(
                            responseCode = "4042",
                            description = "Restaurant category not found",
                            content =
                            @Content(
                                    schema = @Schema(implementation = ApiError.class),
                                    mediaType = APPLICATION_JSON_VALUE))
            })
    ResponseEntity<RestaurantCategoryOverview> update(int id, EditRestaurantCategoryDto editRestaurantCategoryDto);

    @Operation(
            summary = "Delete restaurant category",
            description = "Possible error codes: 4042")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Deleted a restaurant category from DB"),
                    @ApiResponse(
                            responseCode = "4042",
                            description = "Restaurant category not found",
                            content =
                            @Content(
                                    schema = @Schema(implementation = ApiError.class),
                                    mediaType = APPLICATION_JSON_VALUE))
            })
    ResponseEntity<?> delete(int id);
}

