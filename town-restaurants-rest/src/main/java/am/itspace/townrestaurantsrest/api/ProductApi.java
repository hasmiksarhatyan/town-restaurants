package am.itspace.townrestaurantsrest.api;


import am.itspace.townrestaurantscommon.dto.product.CreateProductDto;
import am.itspace.townrestaurantscommon.dto.product.EditProductDto;
import am.itspace.townrestaurantscommon.dto.product.ProductOverview;
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


public interface ProductApi {

    @Operation(
            summary = "Add new product",
            description = "Possible error codes: 4003")
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
                                    schema = @Schema(implementation = ApiError.class)))
            })
    @PostMapping
    ResponseEntity<ProductOverview> create(@Valid @RequestBody CreateProductDto createProductDto);

    @Operation(
            summary = "Get all products",
            description = "Possible error codes: 4043")
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
    @GetMapping
    ResponseEntity<List<ProductOverview>> getAll(int userId);

    @Operation(
            summary = "Get product",
            description = "Possible error codes: 4043")
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
    @GetMapping("/{id}")
    ResponseEntity<ProductOverview> getById(@PathVariable("id") int id);

    @Operation(
            summary = "Update product",
            description = "Possible error codes: 4043")
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
    @PutMapping("/{id}")
    ResponseEntity<ProductOverview> update(@Valid @PathVariable("id") int id, @RequestBody EditProductDto editProductDto);

    @Operation(
            summary = "Delete product",
            description = "Possible error codes: 4043")
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
    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable("id") int id);
}

