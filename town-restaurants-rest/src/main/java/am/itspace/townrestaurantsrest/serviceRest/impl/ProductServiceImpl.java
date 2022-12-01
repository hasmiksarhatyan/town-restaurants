package am.itspace.townrestaurantsrest.serviceRest.impl;

import am.itspace.townrestaurantscommon.dto.product.CreateProductDto;
import am.itspace.townrestaurantscommon.dto.product.EditProductDto;
import am.itspace.townrestaurantscommon.dto.product.ProductOverview;
import am.itspace.townrestaurantscommon.entity.Product;
import am.itspace.townrestaurantscommon.mapper.ProductMapper;
import am.itspace.townrestaurantscommon.repository.ProductRepository;
import am.itspace.townrestaurantsrest.exception.EntityNotFoundException;
import am.itspace.townrestaurantsrest.exception.Error;
import am.itspace.townrestaurantsrest.serviceRest.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductOverview save(CreateProductDto createProductDto) {
        if (productRepository.existsByName(createProductDto.getName())) {
            log.info("product with that name already exists {}", createProductDto.getName());
            throw new EntityNotFoundException(Error.PRODUCT_ALREADY_EXISTS);
        }
        log.info("The product was successfully stored in the database {}", createProductDto.getName());
        return productMapper.mapToResponseDto(productRepository.save(productMapper.mapToEntity(createProductDto)));
    }

    @Override
    public List<ProductOverview> getAll() {
        log.info("Product successfully detected");
        return productMapper.mapToOverviewList(productRepository.findAll());
    }

    @Override
    public ProductOverview getById(int id) {
       Product product = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Error.PRODUCT_NOT_FOUND));
        log.info("Product successfully found {}", product.getName());
        return productMapper.mapToResponseDto(product);
    }

    @Override
    public ProductOverview update(int id, EditProductDto editProductDto) {
        Product product = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Error.PRODUCT_NOT_FOUND));
        log.info("Product with that id not found");
        if (editProductDto.getName() != null) {
            product.setName(editProductDto.getName());
            productRepository.save(product);
        }
        log.info("The product was successfully stored in the database {}", product.getName());
        return productMapper.mapToResponseDto(product);
    }

    @Override
    public void delete(int id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            log.info("The product has been successfully deleted");
        }
        log.info("Product not found");
        throw new EntityNotFoundException(Error.PRODUCT_NOT_FOUND);
    }
}





//import am.itspace.townrestaurantscommon.dto.productCategory.CreateProductCategoryDto;
//        import am.itspace.townrestaurantscommon.dto.productCategory.EditProductCategoryDto;
//        import am.itspace.townrestaurantscommon.dto.productCategory.ProductCategoryOverview;
//        import am.itspace.townrestaurantscommon.entity.ProductCategory;
//        import am.itspace.townrestaurantscommon.entity.RestaurantProduct;
//        import am.itspace.townrestaurantscommon.mapper.ProductCategoryMapper;
//        import am.itspace.townrestaurantscommon.repository.ProductCategoryRepository;
//        import am.itspace.townrestaurantscommon.repository.productProductRepository;
//        import am.itspace.townrestaurantsrest.exception.EntityNotFoundException;
//        import am.itspace.townrestaurantsrest.exception.Error;
//        import am.itspace.townrestaurantsrest.service.ProductCategoryService;
//        import lombok.RequiredArgsConstructor;
//        import lombok.extern.slf4j.Slf4j;
//        import org.springframework.stereotype.Service;
//        import org.springframework.transaction.annotation.Transactional;
//
//        import java.util.List;

//@Slf4j
//@Service
//@Transactional
//@RequiredArgsConstructor
//public class ProductProductServiceImpl implements ProductCategoryService {
//
//    private final ProductCategoryRepository productCategoryRepository;
//    private final ProductCategoryMapper productCategoryMapper;
//
//    @Override
//    public ProductCategoryOverview save(CreateProductCategoryDto createProductCategoryDto) {
//        if (productCategoryRepository.existsByName(createRestaurantProductDto.getName())) {
//            log.info("Category with that name already exists {}", createRestaurantProductDto.getName());
//            throw new EntityNotFoundException(Error.Product_ALREADY_EXISTS);
//        }
//        log.info("The category was successfully stored in the database {}", createRestaurantProductDto.getName());
//        return productCategoryMapper.mapToResponseDto(productProductRepository.save(productProductMapper.mapToEntity(createRestaurantProductDto)));
//    }
//
//    @Override
//    public List<ProductCategoryOverview> getAll() {
//        log.info("Category successfully detected");
//        return productCategoryMapper.mapToResponseDtoList(productProductRepository.findAll());
//    }
//
//    @Override
//    public ProductCategoryOverview getById(int id) {
//        RestaurantProduct restaurantProduct = productProductRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Error.Product_NOT_FOUND));
//        log.info("Category successfully found {}", restaurantProduct.getName());
//        return productCategoryMapper.mapToResponseDto(restaurantProduct);
//    }
//
//    @Override
//    public ProductCategoryOverview update(int id, EditProductCategoryDto editProductCategoryDto) {
//        ProductCategory productCategory = productCategoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Error.Product_NOT_FOUND));
//        log.info("Restaurant with that id not found");
//        if (editProductCategoryDto.getName() != null) {
//            productCategory.setName(editProductCategoryDto.getName());
//            productCategoryRepository.save(productCategory);
//        }
//        log.info("The category was successfully stored in the database {}", productCategory.getName());
//        return productCategoryMapper.mapToResponseDto(productCategory);
//    }
//
//    @Override
//    public void delete(int id) {
//        if (productCategoryRepository.existsById(id)) {
//            productCategoryRepository.deleteById(id);
//            log.info("The category has been successfully deleted");
//        }
//        log.info("Category not found");
//        throw new EntityNotFoundException(Error.Product_NOT_FOUND);
//    }
//}

