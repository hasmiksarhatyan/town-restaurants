package am.itspace.townrestaurantsrest.api.controller;

import am.itspace.townrestaurantscommon.dto.productCategory.CreateProductCategoryDto;
import am.itspace.townrestaurantscommon.dto.productCategory.EditProductCategoryDto;
import am.itspace.townrestaurantscommon.dto.productCategory.ProductCategoryOverview;
import am.itspace.townrestaurantsrest.api.ProductCategoryApi;
import am.itspace.townrestaurantsrest.serviceRest.ProductCategoryService;
import am.itspace.townrestaurantsrest.utilRest.AppConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/productCategories")
public class ProductCategoryController implements ProductCategoryApi {

    private final ProductCategoryService productCategoryService;

    @Override
    @PostMapping
    public ResponseEntity<ProductCategoryOverview> create(@Valid @RequestBody CreateProductCategoryDto createProductCategoryDto) {
        return ResponseEntity.ok(productCategoryService.save(createProductCategoryDto));
    }

    @Override
    @GetMapping
    public ResponseEntity<List<ProductCategoryOverview>> getAll(@RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
                                                                @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                                                @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
                                                                @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {
        return ResponseEntity.ok(productCategoryService.getAllCategories(pageNo, pageSize, sortBy, sortDir));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ProductCategoryOverview> getById(@PathVariable("id") int id) {
        return ResponseEntity.ok(productCategoryService.getById(id));
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<ProductCategoryOverview> update(@PathVariable("id") int id,
                                                          @Valid @RequestBody EditProductCategoryDto editProductCategoryDto) {
        return ResponseEntity.ok(productCategoryService.update(id, editProductCategoryDto));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        productCategoryService.delete(id);
        return ResponseEntity.ok().build();
    }
}

