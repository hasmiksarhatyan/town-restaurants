package am.itspace.townrestaurantscommon.dto.order;

import am.itspace.townrestaurantscommon.dto.product.ProductOverview;
import am.itspace.townrestaurantscommon.dto.user.UserOverview;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderOverview {

    private Integer id;
    private String additionalAddress;
    private String additionalPhone;
    private LocalDateTime orderAt;
    private double totalPrice;
    private String status;
    private String paymentOption;
    private List<ProductOverview> productOverviews;
    private UserOverview userOverview;
    private boolean isPaid;
}
