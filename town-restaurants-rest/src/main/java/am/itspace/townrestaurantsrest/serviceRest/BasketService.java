package am.itspace.townrestaurantsrest.serviceRest;

import am.itspace.townrestaurantscommon.dto.basket.BasketOverview;

import java.util.List;

public interface BasketService {

    void delete(int id);

    double getTotalPrice();

    void addProductToBasket(int id);

    List<BasketOverview> getAll(int pageNo, int pageSize, String sortBy, String sortDir);

    List<BasketOverview> getAllByUser(int pageNo, int pageSize, String sortBy, String sortDir);
}
