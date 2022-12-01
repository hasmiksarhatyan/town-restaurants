package am.itspace.townrestaurantscommon.mapper;

import am.itspace.townrestaurantscommon.dto.creditCard.CreateCreditCardDto;
import am.itspace.townrestaurantscommon.dto.creditCard.CreditCardOverview;
import am.itspace.townrestaurantscommon.entity.CreditCard;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

import java.util.List;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CreditCardMapper {

    CreditCard mapToEntity(CreateCreditCardDto createCreditCardDto);

    CreditCardOverview mapToDto(CreditCard creditCard);

    List<CreditCardOverview> mapToDto(List<CreditCard> creditCard);

    List<CreditCardOverview> mapToDto(Page<CreditCard> CreditCard);
}

