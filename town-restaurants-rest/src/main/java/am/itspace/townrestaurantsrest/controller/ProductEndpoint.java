package am.itspace.townrestaurantsrest.controller;

import am.itspace.townrestaurantscommon.dto.FileDto;
import am.itspace.townrestaurantscommon.dto.FetchRequestDto;
import am.itspace.townrestaurantscommon.dto.product.CreateProductDto;
import am.itspace.townrestaurantscommon.dto.product.EditProductDto;
import am.itspace.townrestaurantscommon.dto.product.ProductOverview;
import am.itspace.townrestaurantscommon.entity.Product;
import am.itspace.townrestaurantsrest.api.ProductApi;
import am.itspace.townrestaurantsrest.serviceRest.ProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductEndpoint implements ProductApi {

    private final ModelMapper modelMapper;
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
    public ResponseEntity<List<ProductOverview>> getAll(@Valid @RequestBody FetchRequestDto fetchRequestDto) {
        List<Product> products = productService.getProductsList(fetchRequestDto);
        return ResponseEntity.ok(products.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList()));
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

    private ProductOverview convertToDto(Product product) {
        ProductOverview productOverview = modelMapper.map(product, ProductOverview.class);
        productOverview.setName(product.getName());
        return productOverview;
    }
}

