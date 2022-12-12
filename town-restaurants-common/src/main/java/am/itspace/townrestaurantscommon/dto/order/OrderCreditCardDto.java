package am.itspace.townrestaurantscommon.dto.order;


import am.itspace.townrestaurantscommon.dto.creditCard.CreateCreditCardDto;
import am.itspace.townrestaurantscommon.dto.order.CreateOrderDto;
import lombok.Data;

@Data
public class OrderCreditCardDto {

    private CreateOrderDto createOrderDto;
    private CreateCreditCardDto creditCardDto;
}
