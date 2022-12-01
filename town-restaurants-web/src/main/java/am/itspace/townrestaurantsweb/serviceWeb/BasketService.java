package am.itspace.townrestaurantsweb.serviceWeb;

import am.itspace.townrestaurantscommon.dto.basket.BasketOverview;
import am.itspace.townrestaurantscommon.entity.User;
import am.itspace.townrestaurantscommon.security.CurrentUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface BasketService {

    Page<BasketOverview> getBaskets(Pageable pageable, User user);

    void addBasket(int id, CurrentUser currentUser);

    void delete(int id);
}


