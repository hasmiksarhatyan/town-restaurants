package am.itspace.townrestaurantsrest.controller;

import am.itspace.townrestaurantscommon.dto.FetchRequestDto;
import am.itspace.townrestaurantscommon.dto.productCategory.CreateProductCategoryDto;
import am.itspace.townrestaurantscommon.dto.productCategory.EditProductCategoryDto;
import am.itspace.townrestaurantscommon.dto.productCategory.ProductCategoryOverview;
import am.itspace.townrestaurantscommon.entity.ProductCategory;
import am.itspace.townrestaurantsrest.api.ProductCategoryApi;
import am.itspace.townrestaurantsrest.serviceRest.ProductCategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/productCategories")
public class ProductCategoryEndpoint implements ProductCategoryApi {

    private final ModelMapper modelMapper;
    private final ProductCategoryService productCategoryService;

    @Override
    @PostMapping
    public ResponseEntity<ProductCategoryOverview> create(@Valid @RequestBody CreateProductCategoryDto createProductCategoryDto) {
        return ResponseEntity.ok(productCategoryService.save(createProductCategoryDto));
    }

    @Override
    @GetMapping
    public ResponseEntity<List<ProductCategoryOverview>> getAll() {
        return ResponseEntity.ok(productCategoryService.getAll());
    }

    @Override
    @GetMapping("/pages")
    public ResponseEntity<List<ProductCategoryOverview>> getAll(@Valid @RequestBody FetchRequestDto fetchRequestDto) {
        List<ProductCategory> categories = productCategoryService.getCategoriesList(fetchRequestDto);
        return ResponseEntity.ok(categories.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList()));
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

    private ProductCategoryOverview convertToDto(ProductCategory productCategory) {
        ProductCategoryOverview productCategoryOverview = modelMapper.map(productCategory, ProductCategoryOverview.class);
        productCategoryOverview.setName(productCategory.getName());
        return productCategoryOverview;
    }
}

