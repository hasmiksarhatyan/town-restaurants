package am.itspace.townrestaurantsrest.serviceRest;

import am.itspace.townrestaurantscommon.dto.basket.BasketOverview;

import java.util.List;

public interface BasketService {

    void delete(int id);

    double getTotalPrice();

    List<BasketOverview> getAll();

    void addProductToBasket(int id);

    List<BasketOverview> getAllBaskets(int pageNo, int pageSize, String sortBy, String sortDir);
}
