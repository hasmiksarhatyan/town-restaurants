package am.itspace.townrestaurantsweb.serviceWeb;

import am.itspace.townrestaurantscommon.dto.restaurant.CreateRestaurantDto;
import am.itspace.townrestaurantscommon.dto.restaurant.EditRestaurantDto;
import am.itspace.townrestaurantscommon.dto.restaurant.RestaurantOverview;
import am.itspace.townrestaurantscommon.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface RestaurantService {

    void deleteRestaurant(int id);

    List<RestaurantOverview> findAll();

    RestaurantOverview getRestaurant(int id);

    void editRestaurant(EditRestaurantDto dto, int id);

    byte[] getRestaurantImage(String fileName) throws IOException;

    Page<RestaurantOverview> findAllRestaurants(Pageable pageable);

    Page<RestaurantOverview> getRestaurantsByUser(User user, Pageable pageable);

    void addRestaurant(CreateRestaurantDto restaurantDto, MultipartFile[] files, User user) throws IOException;
}


