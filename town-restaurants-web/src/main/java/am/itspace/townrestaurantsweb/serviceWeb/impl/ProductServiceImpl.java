package am.itspace.townrestaurantsweb.serviceWeb.impl;

import am.itspace.townrestaurantscommon.dto.product.CreateProductDto;
import am.itspace.townrestaurantscommon.dto.product.EditProductDto;
import am.itspace.townrestaurantscommon.dto.product.ProductOverview;
import am.itspace.townrestaurantscommon.entity.Product;
import am.itspace.townrestaurantscommon.entity.Role;
import am.itspace.townrestaurantscommon.entity.User;
import am.itspace.townrestaurantscommon.mapper.ProductMapper;
import am.itspace.townrestaurantscommon.mapper.RestaurantMapper;
import am.itspace.townrestaurantscommon.mapper.UserMapper;
import am.itspace.townrestaurantscommon.repository.ProductCategoryRepository;
import am.itspace.townrestaurantscommon.repository.ProductRepository;
import am.itspace.townrestaurantscommon.repository.RestaurantRepository;
import am.itspace.townrestaurantsweb.serviceWeb.ProductService;
import am.itspace.townrestaurantsweb.utilWeb.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final FileUtil fileUtil;
    private final ProductMapper productMapper;
    private final UserMapper userMapper;
    private final RestaurantMapper restaurantMapper;
    private final ProductRepository productRepository;
    private final RestaurantRepository restaurantRepository;
    private final ProductCategoryRepository productCategoryRepository;

    @Override
    public Page<ProductOverview> sortProduct(Pageable pageable, String sort, Integer id) {
        Page<Product> products;
        if (id != null) {
            products = productRepository.findProductsByProductCategory_Id(id, pageable);
            if (products.isEmpty()) {
                throw new IllegalStateException();
            }
            return products.map(productMapper::mapToResponseDto);
        }
        switch (sort) {
            case "price_asc":
                products = productRepository.findByOrderByPriceAsc(pageable);
                break;
            case "price_desc":
                products = productRepository.findByOrderByPriceDesc(pageable);
                break;
            default:
                products = productRepository.findAll(pageable);
        }
        log.info("Products successfully sorted");
        return products.map(productMapper::mapToResponseDto);
    }

    @Override
    public List<ProductOverview> findAll() {
        log.info("Products successfully found");
        return productMapper.mapToOverviewList(productRepository.findAll());
    }

    @Override
    public List<ProductOverview> findAllById(int id) {
        List<Product> products = productRepository.findAllById(id);
        if (products.isEmpty()) {
            throw new IllegalStateException();
        }
        log.info("Products successfully detected");
        return productMapper.mapToOverviewList(products);
    }

    @Override
    public void addProduct(CreateProductDto dto, MultipartFile[] files, User user) throws IOException {
        if (StringUtils.hasText(dto.getName()) && dto.getPrice() >= 0) {
            dto.setUserOverview(userMapper.mapToResponseDto(user));
            Product product = productMapper.mapToEntity(dto);
            product.setPictures(fileUtil.uploadImages(files));
            productRepository.save(product);
            log.info("The product was successfully stored in the database {}", dto.getName());
        }
    }


    @Override
    public void editProduct(EditProductDto dto, int id, MultipartFile[] files) throws IOException {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isEmpty()) {
            log.info("Product not found");
            throw new IllegalStateException();
        }
        Product product = productOptional.get();
        String name = dto.getName();
        if (StringUtils.hasText(name)) {
            product.setName(name);
        }
        String description = dto.getDescription();
        if (StringUtils.hasText(description)) {
            product.setDescription(description);
        }
        Double price = dto.getPrice();
        if (price >= 0) {
            product.setPrice(price);
        }
        Integer restaurantId = dto.getRestaurantId();
        if (restaurantId != null) {
            product.setRestaurant(restaurantRepository.getReferenceById(restaurantId));
        }
        Integer productCategoryId = dto.getProductCategoryId();
        if (productCategoryId != null) {
            product.setProductCategory(productCategoryRepository.getReferenceById(productCategoryId));
        }
        List<String> pictures = dto.getPictures();
        if (pictures != null) {
            product.setPictures(fileUtil.uploadImages(files));
        }
        log.info("The product was successfully stored in the database {}", product.getName());
        productRepository.save(product);
    }

    @Override
    public byte[] getProductImage(String fileName) throws IOException {
        log.info("Image successfully found");
        return fileUtil.getImage(fileName);
    }

    @Override
    public void deleteProduct(int id, User user) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isEmpty()) {
            log.info("Product not found");
            throw new IllegalStateException();
        }
        if ((user.getRole() == Role.MANAGER) || (user.getId().equals(productOptional.get().getUser().getId()))) {
            productRepository.deleteById(id);
            log.info("The product has been successfully deleted");
        }
    }

    @Override
    public ProductOverview findById(int id) {
        Product product = productRepository.findById(id).orElseThrow(IllegalStateException::new);
        log.info("Product successfully found");
        return productMapper.mapToResponseDto(product);
    }

    @Override
    public List<ProductOverview> findProductByUser(User user) {
        log.info("Product successfully found");
        return productMapper.mapToOverviewList(productRepository.findProductByUser(user));
    }

    @Override
    public List<ProductOverview> findProductsByRestaurant(int id) {
        List<Product> products = productRepository.findProductsByRestaurant_Id(id);
//        if (products.isEmpty()) {
//            log.info("Product not found");
//            throw new IllegalStateException();
//        }
        log.info("Product successfully found");
        return productMapper.mapToOverviewList(products);
    }
}
