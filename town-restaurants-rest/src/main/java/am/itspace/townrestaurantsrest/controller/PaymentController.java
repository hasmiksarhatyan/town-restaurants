package am.itspace.townrestaurantsrest.controller;

import am.itspace.townrestaurantscommon.dto.payment.PaymentOverview;
import am.itspace.townrestaurantsrest.api.PaymentApi;
import am.itspace.townrestaurantsrest.serviceRest.PaymentService;
import am.itspace.townrestaurantsrest.utilRest.AppConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
public class PaymentController implements PaymentApi {

    private final PaymentService paymentService;

    @Override
    @GetMapping
    public ResponseEntity<List<PaymentOverview>> getAll() {
        return ResponseEntity.ok(paymentService.getAllByUser());
    }

    @Override
    @GetMapping("/pages")
    public ResponseEntity<List<PaymentOverview>> getAll(@RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
                                                        @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                                        @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
                                                        @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {
        return ResponseEntity.ok(paymentService.getAllPayments(pageNo, pageSize, sortBy, sortDir));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        paymentService.delete(id);
        return ResponseEntity.ok().build();
    }
}

