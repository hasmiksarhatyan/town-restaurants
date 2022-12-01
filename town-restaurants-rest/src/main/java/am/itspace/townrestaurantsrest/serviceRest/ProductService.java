package am.itspace.townrestaurantsrest.serviceRest;

import am.itspace.townrestaurantscommon.dto.product.CreateProductDto;
import am.itspace.townrestaurantscommon.dto.product.EditProductDto;
import am.itspace.townrestaurantscommon.dto.product.ProductOverview;
import am.itspace.townrestaurantsrest.exception.EntityNotFoundException;

import java.util.List;

public interface ProductService {

    ProductOverview save(CreateProductDto createProductDto);

    List<ProductOverview> getAll();

    ProductOverview getById(int id) throws EntityNotFoundException;

    ProductOverview update(int id, EditProductDto editProductDto);

    void delete(int id);
}

