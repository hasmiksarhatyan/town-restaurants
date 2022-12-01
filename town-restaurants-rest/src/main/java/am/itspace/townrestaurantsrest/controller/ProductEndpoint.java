package am.itspace.townrestaurantsrest.controller;


import am.itspace.townrestaurantscommon.dto.product.CreateProductDto;
import am.itspace.townrestaurantscommon.dto.product.EditProductDto;
import am.itspace.townrestaurantscommon.dto.product.ProductOverview;
import am.itspace.townrestaurantsrest.serviceRest.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductEndpoint {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductOverview> create(@Valid @RequestBody CreateProductDto createProductDto) {
        return ResponseEntity.ok(productService.save(createProductDto));
    }

    @GetMapping
    public ResponseEntity<List<ProductOverview>> getAll() {
        return ResponseEntity.ok(productService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductOverview> getById(@PathVariable("id") int id) {
        return ResponseEntity.ok(productService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductOverview> update(@Valid @PathVariable("id") int id,
                                                  @RequestBody EditProductDto editProductDto) {
        return ResponseEntity.ok(productService.update(id, editProductDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        productService.delete(id);
        return ResponseEntity.ok().build();
    }
}

