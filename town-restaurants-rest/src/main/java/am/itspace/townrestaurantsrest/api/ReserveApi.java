package am.itspace.townrestaurantsrest.api;

import am.itspace.townrestaurantscommon.dto.reserve.CreateReserveDto;
import am.itspace.townrestaurantscommon.dto.reserve.EditReserveDto;
import am.itspace.townrestaurantscommon.dto.reserve.ReserveOverview;
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

public interface ReserveApi {

    @Operation(
            summary = "Add new reserve",
            description = "Possible error codes: 4007")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Reserve has been added",
                            content = @Content(
                                    schema = @Schema(implementation = ReserveOverview.class),
                                    mediaType = APPLICATION_JSON_VALUE)),
                    @ApiResponse(
                            responseCode = "4007",
                            description = "Reserve already exists",
                            content = @Content(
                                    mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ApiError.class)))
            })
    @PostMapping
    ResponseEntity<ReserveOverview> create(@Valid @RequestBody CreateReserveDto createReserveDto);

    @Operation(
            summary = "Get all reserves",
            description = "Possible error codes: 4047")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Fetched reserves from DB",
                            content = @Content(
                                    schema = @Schema(implementation = ReserveOverview.class),
                                    mediaType = APPLICATION_JSON_VALUE)),
                    @ApiResponse(
                            responseCode = "4047",
                            description = "Reserve not found",
                            content = @Content(
                                    mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ApiError.class)))})
    @GetMapping
    ResponseEntity<List<ReserveOverview>> getAll(int userId);

    @Operation(
            summary = "Update reserve",
            description = "Possible error codes: 4047")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Updated a reserve from DB",
                            content =
                            @Content(
                                    schema = @Schema(implementation = ReserveOverview.class),
                                    mediaType = APPLICATION_JSON_VALUE)),
                    @ApiResponse(
                            responseCode = "4047",
                            description = "Reserve not found",
                            content =
                            @Content(
                                    schema = @Schema(implementation = ApiError.class),
                                    mediaType = APPLICATION_JSON_VALUE))
            })
    @PutMapping("/{id}")
    ResponseEntity<ReserveOverview> update(@Valid @PathVariable("id") int id, @RequestBody EditReserveDto editReserveDto);

    @Operation(
            summary = "Delete reserve",
            description = "Possible error codes: 4047")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Deleted a reserve from DB"),
                    @ApiResponse(
                            responseCode = "4047",
                            description = "Reserve not found",
                            content =
                            @Content(
                                    schema = @Schema(implementation = ApiError.class),
                                    mediaType = APPLICATION_JSON_VALUE))
            })
    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable("id") int id);
}
