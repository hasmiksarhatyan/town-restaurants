package am.itspace.townrestaurantsrest.api;

import am.itspace.townrestaurantscommon.dto.user.ChangePasswordDto;
import am.itspace.townrestaurantscommon.dto.user.EditUserDto;
import am.itspace.townrestaurantscommon.dto.user.UserOverview;
import am.itspace.townrestaurantsrest.exception.ApiError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public interface UserApi {

    @Operation(
            summary = "Get all users",
            description = "Possible error codes: 4046")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Fetched users from DB",
                            content = @Content(
                                    schema = @Schema(implementation = UserOverview.class),
                                    mediaType = APPLICATION_JSON_VALUE)),
                    @ApiResponse(
                            responseCode = "4046",
                            description = "User not found",
                            content = @Content(
                                    mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ApiError.class)))})
    ResponseEntity<List<UserOverview>> getAll();

    @Operation(
            summary = "Get all users",
            description = "Possible error codes: 4046")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Fetched users from DB",
                            content = @Content(
                                    schema = @Schema(implementation = UserOverview.class),
                                    mediaType = APPLICATION_JSON_VALUE)),
                    @ApiResponse(
                            responseCode = "4046",
                            description = "User not found",
                            content = @Content(
                                    mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ApiError.class)))})
    ResponseEntity<List<UserOverview>> getAll(int pageNo, int pageSize, String sortBy, String sortDir);

    @Operation(
            summary = "Get user",
            description = "Possible error codes: 4046")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Fetched a user from DB",
                            content = @Content(
                                    schema = @Schema(implementation = UserOverview.class),
                                    mediaType = APPLICATION_JSON_VALUE)),
                    @ApiResponse(
                            responseCode = "4046",
                            description = "User not found",
                            content = @Content(
                                    schema = @Schema(implementation = ApiError.class),
                                    mediaType = APPLICATION_JSON_VALUE))})
    ResponseEntity<UserOverview> getById(@PathVariable("id") int id);

    @Operation(
            summary = "Change password",
            description = "Possible error codes: 4046, 4094")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Change password for user",
                            content = @Content(
                                    schema = @Schema(implementation = UserOverview.class),
                                    mediaType = APPLICATION_JSON_VALUE)),
                    @ApiResponse(
                            responseCode = "4046",
                            description = "User not found",
                            content = @Content(
                                    schema = @Schema(implementation = ApiError.class),
                                    mediaType = APPLICATION_JSON_VALUE)),
                    @ApiResponse(
                            responseCode = "4094",
                            description = "Needs to authenticate",
                            content = @Content(
                                    schema = @Schema(implementation = ApiError.class),
                                    mediaType = APPLICATION_JSON_VALUE))})
    ResponseEntity<?> changePassword(ChangePasswordDto changePasswordDto);

    @Operation(
            summary = "Update user",
            description = "Possible error codes: 4046")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Updated a user from DB",
                            content = @Content(
                                    schema = @Schema(implementation = UserOverview.class),
                                    mediaType = APPLICATION_JSON_VALUE)),
                    @ApiResponse(
                            responseCode = "4046",
                            description = "User not found",
                            content = @Content(
                                    schema = @Schema(implementation = ApiError.class),
                                    mediaType = APPLICATION_JSON_VALUE))})
    ResponseEntity<UserOverview> update(int id, EditUserDto editUserDto);

    @Operation(
            summary = "Delete user",
            description = "Possible error codes: 4046")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Deleted a user from DB"),
                    @ApiResponse(
                            responseCode = "4046",
                            description = "User not found",
                            content = @Content(
                                    schema = @Schema(implementation = ApiError.class),
                                    mediaType = APPLICATION_JSON_VALUE))})
    ResponseEntity<?> delete(int id);
}
