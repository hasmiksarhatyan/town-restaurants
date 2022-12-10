package am.itspace.townrestaurantscommon.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FetchRequestDto {

    @NotBlank
    private Integer page;

    @NotBlank
    private Integer size;

    @NotBlank
    private String sortDir;

    @NotBlank
    private String sort;

    @NotBlank
    private String instance;
}
