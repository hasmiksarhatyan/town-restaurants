package am.itspace.townrestaurantscommon.mapper;

import am.itspace.townrestaurantscommon.dto.creditCard.CreateCreditCardDto;
import am.itspace.townrestaurantscommon.dto.creditCard.CreditCardOverview;
import am.itspace.townrestaurantscommon.entity.CreditCard;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.4.1 (Amazon.com Inc.)"
)
@Component
public class CreditCardMapperImpl implements CreditCardMapper {

    @Override
    public CreditCard mapToEntity(CreateCreditCardDto createCreditCardDto) {
        if ( createCreditCardDto == null ) {
            return null;
        }

        CreditCard.CreditCardBuilder creditCard = CreditCard.builder();

        creditCard.cardNumber( createCreditCardDto.getCardNumber() );
        creditCard.cardHolder( createCreditCardDto.getCardHolder() );
        creditCard.cardExpiresAt( createCreditCardDto.getCardExpiresAt() );
        creditCard.cvv( createCreditCardDto.getCvv() );

        return creditCard.build();
    }

    @Override
    public CreditCardOverview mapToDto(CreditCard creditCard) {
        if ( creditCard == null ) {
            return null;
        }

        CreditCardOverview.CreditCardOverviewBuilder creditCardOverview = CreditCardOverview.builder();

        creditCardOverview.id( creditCard.getId() );
        if ( creditCard.getCardNumber() != null ) {
            creditCardOverview.cardNumber( Integer.parseInt( creditCard.getCardNumber() ) );
        }
        creditCardOverview.cardHolder( creditCard.getCardHolder() );
        creditCardOverview.cardExpiresAt( creditCard.getCardExpiresAt() );
        if ( creditCard.getCvv() != null ) {
            creditCardOverview.cvv( Integer.parseInt( creditCard.getCvv() ) );
        }
        creditCardOverview.user( creditCard.getUser() );

        return creditCardOverview.build();
    }

    @Override
    public List<CreditCardOverview> mapToDto(List<CreditCard> creditCard) {
        if ( creditCard == null ) {
            return null;
        }

        List<CreditCardOverview> list = new ArrayList<CreditCardOverview>( creditCard.size() );
        for ( CreditCard creditCard1 : creditCard ) {
            list.add( mapToDto( creditCard1 ) );
        }

        return list;
    }

    @Override
    public List<CreditCardOverview> mapToDto(Page<CreditCard> CreditCard) {
        if ( CreditCard == null ) {
            return null;
        }

        List<CreditCardOverview> list = new ArrayList<CreditCardOverview>();
        for ( CreditCard creditCard : CreditCard ) {
            list.add( mapToDto( creditCard ) );
        }

        return list;
    }
}
