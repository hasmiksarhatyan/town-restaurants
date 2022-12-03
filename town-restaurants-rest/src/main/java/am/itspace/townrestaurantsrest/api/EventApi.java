package am.itspace.townrestaurantsrest.api;

import am.itspace.townrestaurantscommon.dto.event.CreateEventDto;
import am.itspace.townrestaurantscommon.dto.event.EditEventDto;
import am.itspace.townrestaurantscommon.dto.event.EventOverview;
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

public interface EventApi {

    @Operation(
            summary = "Add new event",
            description = "Possible error codes: 4004")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Event has been added",
                            content = @Content(
                                    schema = @Schema(implementation = EventOverview.class),
                                    mediaType = APPLICATION_JSON_VALUE)),
                    @ApiResponse(
                            responseCode = "4004",
                            description = "Event already exists",
                            content = @Content(
                                    mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ApiError.class)))
            })
    @PostMapping
    ResponseEntity<EventOverview> create(@Valid @RequestBody CreateEventDto createEventDto);

    @Operation(
            summary = "Get all events",
            description = "Possible error codes: 4044")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Fetched events from DB",
                            content = @Content(
                                    schema = @Schema(implementation = EventOverview.class),
                                    mediaType = APPLICATION_JSON_VALUE)),
                    @ApiResponse(
                            responseCode = "4044",
                            description = "Event not found",
                            content = @Content(
                                    mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ApiError.class)))})
    @GetMapping
    ResponseEntity<List<EventOverview>> getAll();

    @Operation(
            summary = "Get event",
            description = "Possible error codes: 4044")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Fetched a event from DB",
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
    @GetMapping("/{id}")
    ResponseEntity<EventOverview> getById(@PathVariable("id") int id);

    @Operation(
            summary = "Update event",
            description = "Possible error codes: 4044")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Updated a event from DB",
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
    @PutMapping("/{id}")
    ResponseEntity<EventOverview> update(@Valid @PathVariable("id") int id, @RequestBody EditEventDto editEventDto);

    @Operation(
            summary = "Delete event",
            description = "Possible error codes: 4044")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Deleted event from DB"),
                    @ApiResponse(
                            responseCode = "4044",
                            description = "Event not found",
                            content =
                            @Content(
                                    schema = @Schema(implementation = ApiError.class),
                                    mediaType = APPLICATION_JSON_VALUE))
            })
    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable("id") int id);
}
