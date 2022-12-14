package am.itspace.townrestaurantscommon.dto.product;

import am.itspace.townrestaurantscommon.dto.FileDto;
import lombok.Data;

@Data
public class ProductRequestDto {

    private CreateProductDto createProductDto;
    private FileDto fileDto;
}

