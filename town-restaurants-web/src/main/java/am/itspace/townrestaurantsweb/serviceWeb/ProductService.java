package am.itspace.townrestaurantsweb.serviceWeb;
//
//import am.itspace.townrestaurantscommon.dto.product.CreateProductDto;
//import am.itspace.townrestaurantscommon.dto.product.EditProductDto;
//import am.itspace.townrestaurantscommon.dto.product.ProductOverview;
import am.itspace.townrestaurantscommon.dto.product.CreateProductDto;
import am.itspace.townrestaurantscommon.dto.product.EditProductDto;
import am.itspace.townrestaurantscommon.dto.product.ProductOverview;
import am.itspace.townrestaurantscommon.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    Page<ProductOverview> sortProduct(Pageable pageable, String sortProduct, Integer id);

    List<ProductOverview> findAll();

    ProductOverview findById(int id);

    void deleteProduct(int id, User user);

    List<ProductOverview> findAllById(int id);

    List<ProductOverview> findProductByUser(User user);

    List<ProductOverview> findProductsByRestaurant(int id);

    byte[] getProductImage(String fileName) throws IOException;

    void addProduct(CreateProductDto dto, MultipartFile[] files, User user) throws IOException;

    void editProduct(EditProductDto dto, int id, MultipartFile[] files) throws IOException;
}
