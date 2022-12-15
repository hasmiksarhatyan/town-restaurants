package am.itspace.townrestaurantsrest.controller;

import am.itspace.townrestaurantscommon.dto.FileDto;
import am.itspace.townrestaurantscommon.dto.product.CreateProductDto;
import am.itspace.townrestaurantscommon.dto.product.EditProductDto;
import am.itspace.townrestaurantscommon.dto.product.ProductOverview;
import am.itspace.townrestaurantsrest.api.ProductApi;
import am.itspace.townrestaurantsrest.serviceRest.ProductService;
import am.itspace.townrestaurantsrest.utilRest.AppConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController implements ProductApi {

    private final ProductService productService;

    @Override
    @PostMapping
    public ResponseEntity<ProductOverview> create(@Valid @ModelAttribute CreateProductDto createProductDto,
                                                  @ModelAttribute FileDto fileDto) {
        return ResponseEntity.ok(productService.save(createProductDto, fileDto));
    }

    @Override
    @GetMapping(value = "/getImages", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImage(@RequestParam("fileName") String fileName) {
        return productService.getProductImage(fileName);
    }

    @Override
    @GetMapping
    public ResponseEntity<List<ProductOverview>> getAll() {
        return ResponseEntity.ok(productService.getAll());
    }

    @Override
    @GetMapping("/pages")
    public ResponseEntity<List<ProductOverview>> getAll(@RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
                                                        @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                                        @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
                                                        @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {
        return ResponseEntity.ok(productService.getAllProducts(pageNo, pageSize, sortBy, sortDir));
    }

    @Override
    @GetMapping("/user")
    public ResponseEntity<List<ProductOverview>> getByUser() {
        return ResponseEntity.ok(productService.findProductByUser());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ProductOverview> getById(@PathVariable("id") int id) {
        return ResponseEntity.ok(productService.getById(id));
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<ProductOverview> update(@PathVariable("id") int id,
                                                  @Valid @RequestBody EditProductDto editProductDto) {
        return ResponseEntity.ok(productService.update(id, editProductDto));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        productService.delete(id);
        return ResponseEntity.ok().build();
    }
}

