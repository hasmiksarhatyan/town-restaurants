package am.itspace.townrestaurantsrest.serviceRest;

import am.itspace.townrestaurantscommon.dto.productCategory.CreateProductCategoryDto;
import am.itspace.townrestaurantscommon.dto.productCategory.EditProductCategoryDto;
import am.itspace.townrestaurantscommon.dto.productCategory.ProductCategoryOverview;

import java.util.List;

public interface ProductCategoryService {

    void delete(int id);

    List<ProductCategoryOverview> getAll();

    ProductCategoryOverview getById(int id);

    ProductCategoryOverview save(CreateProductCategoryDto createProductCategoryDto);

    ProductCategoryOverview update(int id, EditProductCategoryDto editProductCategoryDto);

    List<ProductCategoryOverview> getAllCategories(int pageNo, int pageSize, String sortBy, String sortDir);
}

