package am.itspace.townrestaurantsrest.api;

import am.itspace.townrestaurantscommon.dto.productCategory.CreateProductCategoryDto;
import am.itspace.townrestaurantscommon.dto.productCategory.EditProductCategoryDto;
import am.itspace.townrestaurantscommon.dto.productCategory.ProductCategoryOverview;
import am.itspace.townrestaurantsrest.exception.ApiError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


public interface ProductCategoryApi {

    @Operation(
            summary = "Add new product category",
            description = "Possible error codes: 4005")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Product category has been added",
                            content = @Content(
                                    schema = @Schema(implementation = ProductCategoryOverview.class),
                                    mediaType = APPLICATION_JSON_VALUE)),
                    @ApiResponse(
                            responseCode = "4005",
                            description = "Product category already exists",
                            content = @Content(
                                    mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ApiError.class)))
            })
    ResponseEntity<ProductCategoryOverview> create(CreateProductCategoryDto createProductCategoryDto);

    @Operation(
            summary = "Get all product categories",
            description = "Possible error codes: 4045")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Fetched product categories from DB",
                            content = @Content(
                                    schema = @Schema(implementation = ProductCategoryOverview.class),
                                    mediaType = APPLICATION_JSON_VALUE)),
                    @ApiResponse(
                            responseCode = "4045",
                            description = "Product category not found",
                            content = @Content(
                                    mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ApiError.class)))})
    ResponseEntity<List<ProductCategoryOverview>> getAll();

    @Operation(
            summary = "Get all product categories",
            description = "Possible error codes: 4045")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Fetched product categories from DB",
                            content = @Content(
                                    schema = @Schema(implementation = ProductCategoryOverview.class),
                                    mediaType = APPLICATION_JSON_VALUE)),
                    @ApiResponse(
                            responseCode = "4045",
                            description = "Product category not found",
                            content = @Content(
                                    mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ApiError.class)))})
    ResponseEntity<List<ProductCategoryOverview>> getAll(int pageNo, int pageSize, String sortBy, String sortDir);

    @Operation(
            summary = "Get product category",
            description = "Possible error codes: 4045")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Fetched a product category from DB",
                            content =
                            @Content(
                                    schema = @Schema(implementation = ProductCategoryOverview.class),
                                    mediaType = APPLICATION_JSON_VALUE)),
                    @ApiResponse(
                            responseCode = "4045",
                            description = "Product category not found",
                            content =
                            @Content(
                                    schema = @Schema(implementation = ApiError.class),
                                    mediaType = APPLICATION_JSON_VALUE))
            })
    ResponseEntity<ProductCategoryOverview> getById(int id);

    @Operation(
            summary = "Update product category",
            description = "Possible error codes: 4045")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Updated product category from DB",
                            content =
                            @Content(
                                    schema = @Schema(implementation = ProductCategoryOverview.class),
                                    mediaType = APPLICATION_JSON_VALUE)),
                    @ApiResponse(
                            responseCode = "4045",
                            description = "Product category not found",
                            content =
                            @Content(
                                    schema = @Schema(implementation = ApiError.class),
                                    mediaType = APPLICATION_JSON_VALUE))
            })
    ResponseEntity<ProductCategoryOverview> update(int id, EditProductCategoryDto editProductCategoryDto);

    @Operation(
            summary = "Delete product category",
            description = "Possible error codes: 4045")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Deleted product category from DB"),
                    @ApiResponse(
                            responseCode = "4045",
                            description = "Product category not found",
                            content =
                            @Content(
                                    schema = @Schema(implementation = ApiError.class),
                                    mediaType = APPLICATION_JSON_VALUE))
            })
    ResponseEntity<?> delete(int id);
}

