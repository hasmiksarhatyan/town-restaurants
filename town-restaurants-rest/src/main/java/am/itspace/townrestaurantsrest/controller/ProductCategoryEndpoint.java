package am.itspace.townrestaurantsrest.controller;


import am.itspace.townrestaurantscommon.dto.productCategory.CreateProductCategoryDto;
import am.itspace.townrestaurantscommon.dto.productCategory.EditProductCategoryDto;
import am.itspace.townrestaurantscommon.dto.productCategory.ProductCategoryOverview;
import am.itspace.townrestaurantsrest.serviceRest.ProductCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/productCategories")
public class ProductCategoryEndpoint {

    private final ProductCategoryService productCategoryService;

    @Operation(
            operationId = "postProductCategory",
            summary = "Creation a productCategory"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "SUCCESSFUL",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = EventEndpoint.class))}),
            @ApiResponse(responseCode = "4005", description = "Product category already exists",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<ProductCategoryOverview> create(@Valid @RequestBody CreateProductCategoryDto createProductCategoryDto) {
        return ResponseEntity.ok(productCategoryService.save(createProductCategoryDto));
    }

    @Operation(
            operationId = "getAllProductCategories",
            summary = "Get all productCategories"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "SUCCESSFUL",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = EventEndpoint.class))}),
            @ApiResponse(responseCode = "4045", description = "Product category not found",
                    content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<ProductCategoryOverview>> getAll() {
        return ResponseEntity.ok(productCategoryService.getAll());
    }

    @Operation(
            operationId = "getProductCategory",
            summary = "Get a product category by its id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "SUCCESSFUL",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = EventEndpoint.class))}),
            @ApiResponse(responseCode = "4045", description = "Product category not found",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductCategoryOverview> getById(@PathVariable("id") int id) {
        return ResponseEntity.ok(productCategoryService.getById(id));
    }

    @Operation(
            operationId = "putProductCategory",
            summary = "Update a product category by its id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "SUCCESSFUL",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = EventEndpoint.class))}),
            @ApiResponse(responseCode = "4045", description = "Product category not found",
                    content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProductCategoryOverview> update(@Valid @PathVariable("id") int id,
                                                          @RequestBody EditProductCategoryDto editProductCategoryDto) {
        return ResponseEntity.ok(productCategoryService.update(id, editProductCategoryDto));
    }

    @Operation(
            operationId = "deleteProductCategory",
            summary = "Delete a product category by its id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "SUCCESSFUL",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = EventEndpoint.class))}),
            @ApiResponse(responseCode = "4045", description = "Product category not found",
                    content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        productCategoryService.delete(id);
        return ResponseEntity.ok().build();
    }
}

