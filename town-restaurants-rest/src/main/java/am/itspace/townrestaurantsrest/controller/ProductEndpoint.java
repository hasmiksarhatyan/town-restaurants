package am.itspace.townrestaurantsrest.controller;


import am.itspace.townrestaurantscommon.dto.product.CreateProductDto;
import am.itspace.townrestaurantscommon.dto.product.EditProductDto;
import am.itspace.townrestaurantscommon.dto.product.ProductOverview;
import am.itspace.townrestaurantsrest.api.ProductApi;
import am.itspace.townrestaurantsrest.serviceRest.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductEndpoint implements ProductApi {

    private final ProductService productService;

    @Override
    @PostMapping
    public ResponseEntity<ProductOverview> create(@Valid @RequestBody CreateProductDto createProductDto) {
        return ResponseEntity.ok(productService.save(createProductDto));
    }

    @Override
    @GetMapping
    public ResponseEntity<List<ProductOverview>> getAll(int userId) {
        return ResponseEntity.ok(productService.getAll(userId));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ProductOverview> getById(@PathVariable("id") int id) {
        return ResponseEntity.ok(productService.getById(id));
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<ProductOverview> update(@Valid @PathVariable("id") int id,
                                                  @RequestBody EditProductDto editProductDto) {
        return ResponseEntity.ok(productService.update(id, editProductDto));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        productService.delete(id);
        return ResponseEntity.ok().build();
    }
}

