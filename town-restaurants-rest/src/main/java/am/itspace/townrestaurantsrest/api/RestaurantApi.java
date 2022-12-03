package am.itspace.townrestaurantsrest.api;

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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public interface RestaurantApi {

    @Operation(
            summary = "Add new restaurant",
            description = "Possible error codes: 4001")
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
                                    schema = @Schema(implementation = ApiError.class)))
            })
    @PostMapping
    ResponseEntity<RestaurantOverview> create(@Valid @RequestBody CreateRestaurantDto createRestaurantDto);

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
    @GetMapping
    ResponseEntity<List<RestaurantOverview>> getAll();


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
    @GetMapping("/{id}")
    ResponseEntity<RestaurantOverview> getById(@PathVariable("id") int id);

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
    @PutMapping("/{id}")
    ResponseEntity<RestaurantOverview> update(@Valid @PathVariable("id") int id, @RequestBody EditRestaurantDto editRestaurantDto);

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
    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable("id") int id);
}
