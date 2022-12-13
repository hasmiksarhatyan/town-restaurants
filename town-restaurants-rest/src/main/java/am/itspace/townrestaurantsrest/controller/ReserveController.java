package am.itspace.townrestaurantsrest.controller;

import am.itspace.townrestaurantscommon.dto.reserve.CreateReserveDto;
import am.itspace.townrestaurantscommon.dto.reserve.EditReserveDto;
import am.itspace.townrestaurantscommon.dto.reserve.ReserveOverview;
import am.itspace.townrestaurantsrest.api.ReserveApi;
import am.itspace.townrestaurantsrest.serviceRest.ReserveService;
import am.itspace.townrestaurantsrest.utilRest.AppConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservations")
public class ReserveController implements ReserveApi {

    private final ReserveService reserveService;

    @Override
    @PostMapping
    public ResponseEntity<ReserveOverview> create(@Valid @RequestBody CreateReserveDto createReserveDto) {
        return ResponseEntity.ok(reserveService.save(createReserveDto));
    }

    @Override
    @GetMapping
    public ResponseEntity<List<ReserveOverview>> getAll() {
        return ResponseEntity.ok(reserveService.getAll());
    }

    @Override
    @GetMapping("/pages")
    public ResponseEntity<List<ReserveOverview>> getAll(@RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
                                                        @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                                        @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
                                                        @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {
        return ResponseEntity.ok(reserveService.getAllReserves(pageNo, pageSize, sortBy, sortDir));
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<ReserveOverview> update(@PathVariable("id") int id,
                                                  @Valid @RequestBody EditReserveDto editReserveDto) {
        return ResponseEntity.ok(reserveService.update(id, editReserveDto));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        reserveService.delete(id);
        return ResponseEntity.ok().build();
    }
}

