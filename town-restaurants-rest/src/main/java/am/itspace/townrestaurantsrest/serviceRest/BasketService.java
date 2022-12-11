package am.itspace.townrestaurantsrest.serviceRest;

import am.itspace.townrestaurantscommon.dto.FetchRequestDto;
import am.itspace.townrestaurantscommon.dto.basket.BasketOverview;
import am.itspace.townrestaurantscommon.entity.Basket;

import java.util.List;

public interface BasketService {

    void delete(int id);

    double getTotalPrice();

    List<BasketOverview> getAll();

    void addProductToBasket(int id);

    List<Basket> getBasketsList(FetchRequestDto fetchRequestDto);
}
