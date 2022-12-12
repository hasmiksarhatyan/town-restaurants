package am.itspace.townrestaurantsweb.serviceWeb;

import am.itspace.townrestaurantscommon.dto.basket.BasketOverview;
import am.itspace.townrestaurantscommon.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface BasketService {

    double totalPrice(User user);

    void delete(int id, User user);

    List<BasketOverview> getBaskets(User user);

    void addProductToBasket(int id, User user);

    Page<BasketOverview> getBaskets(Pageable pageable, User user);
}


