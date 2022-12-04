package am.itspace.townrestaurantsrest.serviceRest.impl;

import am.itspace.townrestaurantscommon.dto.product.CreateProductDto;
import am.itspace.townrestaurantscommon.dto.product.EditProductDto;
import am.itspace.townrestaurantscommon.dto.product.ProductOverview;
import am.itspace.townrestaurantscommon.entity.Event;
import am.itspace.townrestaurantscommon.entity.Product;
import am.itspace.townrestaurantscommon.entity.Role;
import am.itspace.townrestaurantscommon.mapper.ProductMapper;
import am.itspace.townrestaurantscommon.repository.ProductCategoryRepository;
import am.itspace.townrestaurantscommon.repository.ProductRepository;
import am.itspace.townrestaurantscommon.repository.RestaurantRepository;
import am.itspace.townrestaurantsrest.exception.EntityAlreadyExistsException;
import am.itspace.townrestaurantsrest.exception.EntityNotFoundException;
import am.itspace.townrestaurantsrest.exception.Error;
import am.itspace.townrestaurantsrest.serviceRest.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

import static am.itspace.townrestaurantsrest.controller.MyControllerAdvice.getUserDetails;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductMapper productMapper;
    private final ProductRepository productRepository;
    private final RestaurantRepository restaurantRepository;
    private final ProductCategoryRepository productCategoryRepository;

    @Override
    public ProductOverview save(CreateProductDto createProductDto) {
        if (productRepository.existsByName(createProductDto.getName())) {
            log.info("Product with that name already exists {}", createProductDto.getName());
            throw new EntityAlreadyExistsException(Error.PRODUCT_ALREADY_EXISTS);
        }
        log.info("The product was successfully stored in the database {}", createProductDto.getName());
        return productMapper.mapToResponseDto(productRepository.save(productMapper.mapToEntity(createProductDto)));
    }

    @Override
    public List<ProductOverview> getAll() {
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) {
            log.info("Product not found");
            throw new EntityNotFoundException(Error.PRODUCT_NOT_FOUND);
        }
        if (getUserDetails() != null) {
            if (getUserDetails().getRole() == Role.MANAGER) {
                log.info("Product successfully detected");
                return productMapper.mapToOverviewList(products);
            } else {
                List<Product> productByUser = productRepository.findProductByUser(getUserDetails());
                if (productByUser.isEmpty()) {
                    log.info("Product not found");
                    throw new EntityNotFoundException(Error.PRODUCT_NOT_FOUND);
                } else {
                    log.info("Product successfully detected");
                    return productMapper.mapToOverviewList(productByUser);
                }
            }
        }else {
            log.info("Product not found");
            throw new EntityNotFoundException(Error.PRODUCT_NOT_FOUND);
        }
    }

    @Override
    public ProductOverview getById(int id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Error.PRODUCT_NOT_FOUND));
        log.info("Product successfully found {}", product.getName());
        return productMapper.mapToResponseDto(product);
    }

    @Override
    public List<ProductOverview> findProductsByRestaurant(int id) {
        List<Product> products = productRepository.findProductsByRestaurant_Id(id);
        if (products.isEmpty()) {
            log.info("Product not found");
            throw new EntityNotFoundException(Error.PRODUCT_NOT_FOUND);
        }
        log.info("Product successfully detected");
        return productMapper.mapToOverviewList(products);
    }

    @Override
    public ProductOverview update(int id, EditProductDto editProductDto) {
        Product product = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Error.PRODUCT_NOT_FOUND));
        log.info("Product with that id not found");
        String name = editProductDto.getName();
        if (StringUtils.hasText(name)) {
            product.setName(name);
        }
        String description = editProductDto.getDescription();
        if (StringUtils.hasText(description)) {
            product.setDescription(description);
        }
        Double price = editProductDto.getPrice();
        if (price >= 0) {
            product.setPrice(price);
        }
        Integer restaurantId = editProductDto.getRestaurantId();
        if (restaurantId != null) {
            product.setRestaurant(restaurantRepository.getReferenceById(restaurantId));
        }
        Integer productCategoryId = editProductDto.getProductCategoryId();
        if (productCategoryId != null) {
            product.setProductCategory(productCategoryRepository.getReferenceById(productCategoryId));
        }
//        List<String> pictures =editProductDto.getPictures();
//        if (pictures != null) {
//            product.setPictures(fileUtil.uploadImages(files));
//        }
        productRepository.save(product);
        log.info("The product was successfully stored in the database {}", product.getName());
        return productMapper.mapToResponseDto(product);
    }

    @Override
    public void delete(int id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            log.info("The product has been successfully deleted");
        } else {
            log.info("Product not found");
            throw new EntityNotFoundException(Error.PRODUCT_NOT_FOUND);
        }
    }
}
