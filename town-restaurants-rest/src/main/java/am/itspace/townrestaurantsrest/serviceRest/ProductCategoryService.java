package am.itspace.townrestaurantsrest.serviceRest;

import am.itspace.townrestaurantscommon.dto.productCategory.CreateProductCategoryDto;
import am.itspace.townrestaurantscommon.dto.productCategory.EditProductCategoryDto;
import am.itspace.townrestaurantscommon.dto.productCategory.ProductCategoryOverview;
import am.itspace.townrestaurantsrest.exception.EntityNotFoundException;

import java.util.List;

public interface ProductCategoryService {

    List<ProductCategoryOverview> getAll();

    ProductCategoryOverview getById(int id) throws EntityNotFoundException;

    ProductCategoryOverview save(CreateProductCategoryDto createProductCategoryDto);

    ProductCategoryOverview update(int id, EditProductCategoryDto editProductCategoryDto);

    void delete(int id);
}

