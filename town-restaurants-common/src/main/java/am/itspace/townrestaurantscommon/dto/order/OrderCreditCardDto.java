package am.itspace.townrestaurantscommon.dto.order;


import am.itspace.townrestaurantscommon.dto.creditCard.CreateCreditCardDto;
import am.itspace.townrestaurantscommon.dto.order.CreateOrderDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderCreditCardDto {

    private CreateOrderDto createOrderDto;
    private CreateCreditCardDto creditCardDto;
}
