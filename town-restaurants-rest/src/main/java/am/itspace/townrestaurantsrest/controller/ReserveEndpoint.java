package am.itspace.townrestaurantsrest.controller;

import am.itspace.townrestaurantscommon.dto.reserve.CreateReserveDto;
import am.itspace.townrestaurantscommon.dto.reserve.EditReserveDto;
import am.itspace.townrestaurantscommon.dto.reserve.ReserveOverview;
import am.itspace.townrestaurantsrest.api.ReserveApi;
import am.itspace.townrestaurantsrest.serviceRest.ReserveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservations")
public class ReserveEndpoint implements ReserveApi {

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
    @PutMapping("/{id}")
    public ResponseEntity<ReserveOverview> update(@Valid @PathVariable("id") int id,
                                                  @RequestBody EditReserveDto editReserveDto) {
        return ResponseEntity.ok(reserveService.update(id, editReserveDto));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        reserveService.delete(id);
        return ResponseEntity.ok().build();
    }
}

