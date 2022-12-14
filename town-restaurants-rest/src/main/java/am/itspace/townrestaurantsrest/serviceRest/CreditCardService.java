package am.itspace.townrestaurantsrest.serviceRest;

import am.itspace.townrestaurantscommon.dto.creditCard.CreditCardOverview;
import am.itspace.townrestaurantscommon.entity.CreditCard;

import java.util.List;

public interface CreditCardService {

    void save(CreditCard creditCard);

    List<CreditCardOverview> getAll(int pageNo, int pageSize, String sortBy, String sortDir);

    List<CreditCardOverview> getAllByUser(int pageNo, int pageSize, String sortBy, String sortDir);
}
