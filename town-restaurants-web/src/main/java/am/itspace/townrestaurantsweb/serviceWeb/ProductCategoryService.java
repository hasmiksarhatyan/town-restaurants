package am.itspace.townrestaurantsweb.serviceWeb;

import am.itspace.townrestaurantscommon.dto.productCategory.CreateProductCategoryDto;
import am.itspace.townrestaurantscommon.dto.productCategory.EditProductCategoryDto;
import am.itspace.townrestaurantscommon.dto.productCategory.ProductCategoryOverview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductCategoryService {

    void deleteProductCategory(int id);

    List<ProductCategoryOverview> findAll();

    ProductCategoryOverview findById(int id);

    Page<ProductCategoryOverview> findAll(Pageable pageable);

    void addProductCategory(CreateProductCategoryDto dto);

    void editProductCategory(EditProductCategoryDto dto, int id);
}
