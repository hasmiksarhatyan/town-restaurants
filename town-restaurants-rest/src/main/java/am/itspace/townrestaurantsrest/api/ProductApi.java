package am.itspace.townrestaurantsrest.api;

import am.itspace.townrestaurantscommon.dto.product.EditProductDto;
import am.itspace.townrestaurantscommon.dto.product.ProductOverview;
import am.itspace.townrestaurantscommon.dto.product.ProductRequestDto;
import am.itspace.townrestaurantsrest.exception.ApiError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


public interface ProductApi {

    @Operation(
            summary = "Add new product",
            description = "Possible error codes: 4003, 4010, 4050")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Product has been added",
                            content = @Content(
                                    schema = @Schema(implementation = ProductOverview.class),
                                    mediaType = APPLICATION_JSON_VALUE)),
                    @ApiResponse(
                            responseCode = "4003",
                            description = "Product already exists",
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
    ResponseEntity<ProductOverview> create(ProductRequestDto productRequestDto);

    @Operation(
            summary = "Get image",
            description = "Possible error code: 4050")
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
            summary = "Get all products",
            description = "Possible error codes: 4043, 4094")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Fetched products from DB",
                            content = @Content(
                                    schema = @Schema(implementation = ProductOverview.class),
                                    mediaType = APPLICATION_JSON_VALUE)),
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
    ResponseEntity<List<ProductOverview>> getByRole(int pageNo, int pageSize, String sortBy, String sortDir);

    @Operation(
            summary = "Get all products",
            description = "Possible error code: 4043")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Fetched products from DB",
                            content = @Content(
                                    schema = @Schema(implementation = ProductOverview.class),
                                    mediaType = APPLICATION_JSON_VALUE)),
                    @ApiResponse(
                            responseCode = "4043",
                            description = "Product not found",
                            content = @Content(
                                    mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ApiError.class)))})
    ResponseEntity<List<ProductOverview>> getAll(int pageNo, int pageSize, String sortBy, String sortDir);

    @Operation(
            summary = "Get products by restaurantId",
            description = "Possible error code: 4043")
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
    ResponseEntity<List<ProductOverview>> getByRestaurant(int id, int pageNo, int pageSize, String sortBy, String sortDir);

    @Operation(
            summary = "Get product",
            description = "Possible error codes: 4043, 4094")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Fetched a product from DB",
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
                                    mediaType = APPLICATION_JSON_VALUE)),
                    @ApiResponse(
                            responseCode = "4094",
                            description = "Needs to authenticate",
                            content = @Content(
                                    mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ApiError.class)))})
    ResponseEntity<List<ProductOverview>> getByOwner(int pageNo, int pageSize, String sortBy, String sortDir);

    @Operation(
            summary = "Get product",
            description = "Possible error code: 4043")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Fetched a product from DB",
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
    ResponseEntity<ProductOverview> getById(int id);

    @Operation(
            summary = "Update product",
            description = "Possible error code: 4043")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Updated product from DB",
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
    ResponseEntity<ProductOverview> update(int id, EditProductDto editProductDto);

    @Operation(
            summary = "Delete product",
            description = "Possible error code: 4043")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Deleted product from DB"),
                    @ApiResponse(
                            responseCode = "4043",
                            description = "Product not found",
                            content =
                            @Content(
                                    schema = @Schema(implementation = ApiError.class),
                                    mediaType = APPLICATION_JSON_VALUE))
            })
    ResponseEntity<?> delete(int id);
}

