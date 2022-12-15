package am.itspace.townrestaurantscommon.dto.order;

import am.itspace.townrestaurantscommon.dto.product.ProductOverview;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderDto {
    @NotBlank(message = "Address is mandatory")
    private String additionalAddress;
    private String additionalPhone;
    private double totalPrice;
    private String paymentOption;
    private List<ProductOverview> productOverviews;
}
