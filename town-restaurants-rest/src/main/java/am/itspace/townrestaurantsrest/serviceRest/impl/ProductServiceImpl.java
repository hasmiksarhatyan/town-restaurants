package am.itspace.townrestaurantsrest.serviceRest.impl;

import am.itspace.townrestaurantscommon.dto.FetchRequestDto;
import am.itspace.townrestaurantscommon.dto.FileDto;
import am.itspace.townrestaurantscommon.dto.product.CreateProductDto;
import am.itspace.townrestaurantscommon.dto.product.EditProductDto;
import am.itspace.townrestaurantscommon.dto.product.ProductOverview;
import am.itspace.townrestaurantscommon.entity.Product;
import am.itspace.townrestaurantscommon.entity.Role;
import am.itspace.townrestaurantscommon.entity.User;
import am.itspace.townrestaurantscommon.mapper.ProductMapper;
import am.itspace.townrestaurantscommon.repository.ProductCategoryRepository;
import am.itspace.townrestaurantscommon.repository.ProductRepository;
import am.itspace.townrestaurantscommon.repository.RestaurantRepository;
import am.itspace.townrestaurantscommon.utilCommon.FileUtil;
import am.itspace.townrestaurantsrest.exception.Error;
import am.itspace.townrestaurantsrest.exception.*;
import am.itspace.townrestaurantsrest.serviceRest.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static am.itspace.townrestaurantsrest.exception.Error.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final FileUtil fileUtil;
    private final ProductMapper productMapper;
    private final ProductRepository productRepository;
    private final RestaurantRepository restaurantRepository;
    private final SecurityContextServiceImpl securityContextService;
    private final ProductCategoryRepository productCategoryRepository;

    @Override
    public ProductOverview save(CreateProductDto createProductDto, FileDto fileDto) {
        if (productRepository.existsByName(createProductDto.getName())) {
            log.info("Product with that name already exists {}", createProductDto.getName());
            throw new EntityAlreadyExistsException(Error.PRODUCT_ALREADY_EXISTS);
        }
        try {
            MultipartFile[] files = fileDto.getFiles();
            for (MultipartFile file : files) {
                if (!file.isEmpty() && file.getSize() > 0) {
                    if (file.getContentType() != null && !file.getContentType().contains("image")) {
                        log.info("File not found");
                        throw new MyFileNotFoundException(FILE_NOT_FOUND);
                    }
                }
            }
            createProductDto.setPictures(fileUtil.uploadImages(files));
        } catch (IOException e) {
            throw new FileStorageException(FILE_UPLOAD_FAILED);
        }
        log.info("The product was successfully stored in the database {}", createProductDto.getName());
        return productMapper.mapToResponseDto(productRepository.save(productMapper.mapToEntity(createProductDto)));
    }

    @Override
    public byte[] getProductImage(String fileName) {
        try {
            log.info("Images successfully found");
            return fileUtil.getImage(fileName);
        } catch (IOException e) {
            throw new MyFileNotFoundException(FILE_NOT_FOUND);
        }
    }

    @Override
    public List<ProductOverview> getAll() {
        try {
            User user = securityContextService.getUserDetails().getUser();
            List<Product> products = productRepository.findAll();
            if (products.isEmpty()) {
                log.info("Product not found");
                throw new EntityNotFoundException(Error.PRODUCT_NOT_FOUND);
            }
            if (user.getRole() == Role.MANAGER) {
                log.info("Products successfully found");
                return productMapper.mapToOverviewList(products);
            } else {
                List<Product> productsByUser = productRepository.findProductByUser(user);
                if (productsByUser.isEmpty()) {
                    log.info("Product not found");
                    throw new EntityNotFoundException(Error.PRODUCT_NOT_FOUND);
                } else {
                    log.info("Products successfully found");
                    return productMapper.mapToOverviewList(productsByUser);
                }
            }
        } catch (ClassCastException e) {
            throw new AuthenticationException(NEEDS_AUTHENTICATION);
        }
    }

    @Override
    public List<ProductOverview> findProductByUser() {
        try {
            User user = securityContextService.getUserDetails().getUser();
            if (user.getRole() != Role.RESTAURANT_OWNER) {
                log.info("The user is not the owner of the restaurant");
                throw new EntityNotFoundException(Error.PRODUCT_NOT_FOUND);
            }
            List<Product> productByUser = productRepository.findProductByUser(user);
            if (productByUser.isEmpty()) {
                log.info("Product not found");
                throw new EntityNotFoundException(Error.PRODUCT_NOT_FOUND);
            }
            log.info("Products successfully found");
            return productMapper.mapToOverviewList(productByUser);
        } catch (ClassCastException e) {
            throw new AuthenticationException(NEEDS_AUTHENTICATION);
        }
    }

    @Override
    public List<Product> getProductsList(FetchRequestDto dto) {
        PageRequest pageReq = PageRequest.of(dto.getPage(), dto.getSize(), Sort.Direction.fromString(dto.getSortDir()), dto.getSort());
        Page<Product> products = productRepository.findByProductName(dto.getInstance(), pageReq);
        if (products.isEmpty()) {
            log.info("Product not found");
            throw new EntityNotFoundException(Error.PRODUCT_NOT_FOUND);
        }
        return products.getContent();
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
