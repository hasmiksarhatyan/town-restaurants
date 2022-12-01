package am.itspace.townrestaurantscommon.dto.productCategory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductCategoryOverview {

    private Integer id;
    private String name;
}