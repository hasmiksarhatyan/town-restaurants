package am.itspace.townrestaurantsrest.controller;

import am.itspace.townrestaurantscommon.dto.FetchRequestDto;
import am.itspace.townrestaurantscommon.dto.creditCard.CreateCreditCardDto;
import am.itspace.townrestaurantscommon.dto.creditCard.CreditCardOverview;
import am.itspace.townrestaurantscommon.entity.CreditCard;
import am.itspace.townrestaurantsrest.api.CreditCardApi;
import am.itspace.townrestaurantsrest.serviceRest.CreditCardService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/creditCard")
public class CreditCardEndpoint implements CreditCardApi {

    private final ModelMapper modelMapper;
    private final CreditCardService creditCardService;

    @Override
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CreateCreditCardDto createCreditCardDto) {
        creditCardService.save(createCreditCardDto);
        return ResponseEntity.ok().build();
    }

    @Override
    @GetMapping
    public ResponseEntity<List<CreditCardOverview>> getAllByUser() {
        return ResponseEntity.ok(creditCardService.getAllByUser());
    }

    @Override
    @GetMapping("/pages")
    public ResponseEntity<List<CreditCardOverview>> getAll(@Valid @RequestBody FetchRequestDto fetchRequestDto) {
        List<CreditCard> cards = creditCardService.getCardsList(fetchRequestDto);
        return ResponseEntity.ok(cards.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList()));
    }

    private CreditCardOverview convertToDto(CreditCard creditCard) {
        CreditCardOverview creditCardOverview = modelMapper.map(creditCard, CreditCardOverview.class);
        creditCardOverview.setCardNumber(creditCard.getCardNumber());
        return creditCardOverview;
    }
}

