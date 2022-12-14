package am.itspace.townrestaurantsrest.serviceRest;

import am.itspace.townrestaurantscommon.dto.product.EditProductDto;
import am.itspace.townrestaurantscommon.dto.product.ProductOverview;
import am.itspace.townrestaurantscommon.dto.product.ProductRequestDto;
import am.itspace.townrestaurantsrest.exception.EntityNotFoundException;

import java.util.List;

public interface ProductService {

    void delete(int id);

    byte[] getProductImage(String fileName);

    ProductOverview save(ProductRequestDto productRequestDto);

    ProductOverview update(int id, EditProductDto editProductDto);

    ProductOverview getById(int id) throws EntityNotFoundException;

    List<ProductOverview> getByOwner(int pageNo, int pageSize, String sortBy, String sortDir);

    List<ProductOverview> getAllByRole(int pageNo, int pageSize, String sortBy, String sortDir);

    List<ProductOverview> getAllProducts(int pageNo, int pageSize, String sortBy, String sortDir);

    List<ProductOverview> getByRestaurant(int id, int pageNo, int pageSize, String sortBy, String sortDir);
}

