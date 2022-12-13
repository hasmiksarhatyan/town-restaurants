package am.itspace.townrestaurantsrest.controller;

import am.itspace.townrestaurantscommon.dto.creditCard.CreateCreditCardDto;
import am.itspace.townrestaurantscommon.dto.creditCard.CreditCardOverview;
import am.itspace.townrestaurantsrest.api.CreditCardApi;
import am.itspace.townrestaurantsrest.serviceRest.CreditCardService;
import am.itspace.townrestaurantsrest.utilRest.AppConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/creditCard")
public class CreditCardController implements CreditCardApi {

    private final CreditCardService creditCardService;

//    @Override
//    @PostMapping
//    public ResponseEntity<?> create(@Valid @RequestBody CreateCreditCardDto createCreditCardDto) {
//        creditCardService.save(createCreditCardDto);
//        return ResponseEntity.ok().build();
//    }

    @Override
    @GetMapping("/pages")
    public ResponseEntity<List<CreditCardOverview>> getAll(@RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
                                                           @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                                           @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
                                                           @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {
        return ResponseEntity.ok(creditCardService.getAllCreditCards(pageNo, pageSize, sortBy, sortDir));
    }

    @Override
    @GetMapping
    public ResponseEntity<List<CreditCardOverview>> getAllByUser() {
        return ResponseEntity.ok(creditCardService.getAllByUser());
    }
}

