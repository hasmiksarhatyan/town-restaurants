package am.itspace.townrestaurantsweb.serviceWeb.impl;

import am.itspace.townrestaurantscommon.dto.productCategory.CreateProductCategoryDto;
import am.itspace.townrestaurantscommon.dto.productCategory.EditProductCategoryDto;
import am.itspace.townrestaurantscommon.dto.productCategory.ProductCategoryOverview;
import am.itspace.townrestaurantscommon.entity.ProductCategory;
import am.itspace.townrestaurantscommon.mapper.ProductCategoryMapper;
import am.itspace.townrestaurantscommon.repository.ProductCategoryRepository;
import am.itspace.townrestaurantsweb.serviceWeb.ProductCategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductCategoryServiceImpl implements ProductCategoryService {

    private final ProductCategoryMapper productCategoryMapper;
    private final ProductCategoryRepository productCategoryRepository;

    @Override
    public Page<ProductCategoryOverview> findAll(Pageable pageable) {
        log.info("Category successfully found");
        return productCategoryRepository.findAll(pageable).map(productCategoryMapper::mapToOverview);
    }

    @Override
    public List<ProductCategoryOverview> findAll() {
        List<ProductCategory> categories = productCategoryRepository.findAll();
        if (categories.isEmpty()) {
            log.info("Category not found");
            throw new IllegalStateException();
        }
        log.info("Category successfully found");
        return productCategoryMapper.mapToOverviewList(categories);
    }

    @Override
    public void addProductCategory(CreateProductCategoryDto dto) {
        if (StringUtils.hasText(dto.getName())) {
            log.info("The category was successfully stored in the database {}", dto.getName());
            productCategoryRepository.save(productCategoryMapper.mapToEntity(dto));
        }
    }

    @Override
    public void editProductCategory(EditProductCategoryDto dto, int id) {
        Optional<ProductCategory> productCategoryOptional = productCategoryRepository.findById(id);
        if (productCategoryOptional.isEmpty()) {
            log.info("Category not found");
            throw new IllegalStateException();
        }
        ProductCategory productCategory = productCategoryOptional.get();
        String name = dto.getName();
        if (StringUtils.hasText(name)) {
            productCategory.setName(name);
        }
        log.info("The category was successfully stored in the database {}", dto.getName());
        productCategoryRepository.save(productCategory);
    }

    @Override
    public void deleteProductCategory(int id) {
        if (productCategoryRepository.existsById(id)) {
            productCategoryRepository.deleteById(id);
            log.info("The category has been successfully deleted");
        } else {
            log.info("Category not found");
            throw new IllegalStateException();
        }
    }

    @Override
    public ProductCategoryOverview findById(int id) {
        ProductCategory productCategory = productCategoryRepository.findById(id).orElseThrow(IllegalStateException::new);
        log.info("Category successfully found");
        return productCategoryMapper.mapToOverview(productCategory);
    }
}
