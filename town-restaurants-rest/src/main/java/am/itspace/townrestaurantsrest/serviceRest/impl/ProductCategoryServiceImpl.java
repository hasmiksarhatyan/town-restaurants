package am.itspace.townrestaurantsrest.serviceRest.impl;

import am.itspace.townrestaurantscommon.dto.productCategory.CreateProductCategoryDto;
import am.itspace.townrestaurantscommon.dto.productCategory.EditProductCategoryDto;
import am.itspace.townrestaurantscommon.dto.productCategory.ProductCategoryOverview;
import am.itspace.townrestaurantscommon.entity.ProductCategory;
import am.itspace.townrestaurantscommon.mapper.ProductCategoryMapper;
import am.itspace.townrestaurantscommon.repository.ProductCategoryRepository;
import am.itspace.townrestaurantsrest.exception.EntityAlreadyExistsException;
import am.itspace.townrestaurantsrest.exception.EntityNotFoundException;
import am.itspace.townrestaurantsrest.exception.Error;
import am.itspace.townrestaurantsrest.serviceRest.ProductCategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ProductCategoryServiceImpl implements ProductCategoryService {

    private final ProductCategoryMapper productCategoryMapper;
    private final ProductCategoryRepository productCategoryRepository;

    @Override
    public ProductCategoryOverview save(CreateProductCategoryDto createProductCategoryDto) {
        if (productCategoryRepository.existsByName(createProductCategoryDto.getName())) {
            log.info("Category with that name already exists {}", createProductCategoryDto.getName());
            throw new EntityAlreadyExistsException(Error.PRODUCT_CATEGORY_ALREADY_EXISTS);
        }
        log.info("The category was successfully stored in the database {}", createProductCategoryDto.getName());
        return productCategoryMapper.mapToOverview(productCategoryRepository.save(productCategoryMapper.mapToEntity(createProductCategoryDto)));
    }

    @Override
    public List<ProductCategoryOverview> getAllCategories(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<ProductCategory> categories = productCategoryRepository.findAll(pageable);
        if (categories.isEmpty()) {
            log.info("Category not found");
            throw new EntityNotFoundException(Error.PRODUCT_CATEGORY_NOT_FOUND);
        }
        List<ProductCategory> listOfCategories = categories.getContent();
        log.info("Category successfully found");
        return new ArrayList<>(productCategoryMapper.mapToOverviewList(listOfCategories));
    }

    @Override
    public List<ProductCategoryOverview> getAll() {
        List<ProductCategory> categories = productCategoryRepository.findAll();
        if (categories.isEmpty()) {
            log.info("Category not found");
            throw new EntityNotFoundException(Error.PRODUCT_CATEGORY_NOT_FOUND);
        }
        log.info("Category successfully found");
        return productCategoryMapper.mapToOverviewList(categories);
    }

    @Override
    public ProductCategoryOverview getById(int id) {
        ProductCategory productCategory = productCategoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Error.PRODUCT_CATEGORY_NOT_FOUND));
        log.info("Category successfully found {}", productCategory.getName());
        return productCategoryMapper.mapToOverview(productCategory);
    }

    @Override
    public ProductCategoryOverview update(int id, EditProductCategoryDto editProductCategoryDto) {
        ProductCategory productCategory = productCategoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Error.PRODUCT_CATEGORY_NOT_FOUND));
        log.info("Category with that id not found");
        if (editProductCategoryDto.getName() != null) {
            productCategory.setName(editProductCategoryDto.getName());
            productCategoryRepository.save(productCategory);
        }
        log.info("The category was successfully stored in the database {}", productCategory.getName());
        return productCategoryMapper.mapToOverview(productCategory);
    }

    @Override
    public void delete(int id) {
        if (productCategoryRepository.existsById(id)) {
            productCategoryRepository.deleteById(id);
            log.info("The category has been successfully deleted");
        } else {
            log.info("Category not found");
            throw new EntityNotFoundException(Error.PRODUCT_CATEGORY_NOT_FOUND);
        }
    }
}

