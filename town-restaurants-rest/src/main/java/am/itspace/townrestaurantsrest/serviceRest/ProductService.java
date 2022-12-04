package am.itspace.townrestaurantsrest.serviceRest;

import am.itspace.townrestaurantscommon.dto.product.CreateProductDto;
import am.itspace.townrestaurantscommon.dto.product.EditProductDto;
import am.itspace.townrestaurantscommon.dto.product.ProductOverview;
import am.itspace.townrestaurantsrest.exception.EntityNotFoundException;

import java.util.List;

public interface ProductService {

    void delete(int id);

    List<ProductOverview> getAll();

    List<ProductOverview> findProductsByRestaurant(int id);

    ProductOverview save(CreateProductDto createProductDto);

    ProductOverview update(int id, EditProductDto editProductDto);

    ProductOverview getById(int id) throws EntityNotFoundException;
}

