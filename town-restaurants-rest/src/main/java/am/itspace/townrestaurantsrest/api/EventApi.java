package am.itspace.townrestaurantsrest.api;

import am.itspace.townrestaurantscommon.dto.FileDto;
import am.itspace.townrestaurantscommon.dto.event.CreateEventDto;
import am.itspace.townrestaurantscommon.dto.event.EditEventDto;
import am.itspace.townrestaurantscommon.dto.event.EventOverview;
import am.itspace.townrestaurantsrest.utilRest.AppConstants;
import am.itspace.townrestaurantsrest.exception.ApiError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public interface EventApi {

    @Operation(
            summary = "Add new event",
            description = "Possible error codes: 4004, 4010, 4050")
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
    ResponseEntity<EventOverview> create(CreateEventDto createEventDto, FileDto fileDto);

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
    ResponseEntity<List<EventOverview>> getAll();

    @Operation(
            summary = "Sort all events by restaurant",
            description = "Possible error codes: 4041, 4044")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Fetched sorted events from DB",
                            content = @Content(
                                    schema = @Schema(implementation = EventOverview.class),
                                    mediaType = APPLICATION_JSON_VALUE)),
                    @ApiResponse(
                            responseCode = "4041",
                            description = "Restaurant not found",
                            content = @Content(
                                    mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ApiError.class))),
                    @ApiResponse(
                            responseCode = "4044",
                            description = "Event not found",
                            content = @Content(
                                    mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ApiError.class)))
            })
    ResponseEntity<Map<Integer, List<EventOverview>>> sortAllByRestaurant();

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
    ResponseEntity<List<EventOverview>> getAll(int pageNo, int pageSize, String sortBy, String sortDir);

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
    ResponseEntity<EventOverview> getById(int id);

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
    ResponseEntity<EventOverview> update(int id, EditEventDto editEventDto);

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
    ResponseEntity<?> delete(int id);
}
