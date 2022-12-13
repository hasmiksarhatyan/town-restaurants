package am.itspace.townrestaurantscommon.mapper;

import am.itspace.townrestaurantscommon.dto.creditCard.CreateCreditCardDto;
import am.itspace.townrestaurantscommon.dto.creditCard.CreditCardOverview;
import am.itspace.townrestaurantscommon.entity.CreditCard;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CreditCardMapper {

    CreditCard mapToEntity(CreateCreditCardDto dto);

    @Mapping(source = "creditCard.user", target = "userOverview")
    CreditCardOverview mapToDto(CreditCard creditCard);

    List<CreditCardOverview> mapToDto(List<CreditCard> creditCard);
}

