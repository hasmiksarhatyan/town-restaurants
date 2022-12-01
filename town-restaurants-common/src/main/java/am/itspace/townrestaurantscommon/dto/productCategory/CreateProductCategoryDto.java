package am.itspace.townrestaurantscommon.dto.productCategory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductCategoryDto {

    @NotBlank(message = "Name is mandatory")
    private String name;
}
