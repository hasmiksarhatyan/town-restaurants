package am.itspace.townrestaurantscommon.dto.product;

import am.itspace.townrestaurantscommon.dto.FileDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductRequestDto {

    private CreateProductDto createProductDto;
    private FileDto fileDto;
}

