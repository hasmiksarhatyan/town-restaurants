package am.itspace.townrestaurantsrest.controller;

import am.itspace.townrestaurantscommon.dto.FetchRequestDto;
import am.itspace.townrestaurantscommon.dto.reserve.CreateReserveDto;
import am.itspace.townrestaurantscommon.dto.reserve.EditReserveDto;
import am.itspace.townrestaurantscommon.dto.reserve.ReserveOverview;
import am.itspace.townrestaurantscommon.entity.Reserve;
import am.itspace.townrestaurantsrest.api.ReserveApi;
import am.itspace.townrestaurantsrest.serviceRest.ReserveService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservations")
public class ReserveEndpoint implements ReserveApi {

    private final ModelMapper modelMapper;
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
    public ResponseEntity<List<ReserveOverview>> getAll(@Valid @RequestBody FetchRequestDto fetchRequestDto) {
        List<Reserve> reserves = reserveService.getReservesList(fetchRequestDto);
        return ResponseEntity.ok(reserves.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList()));
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

    private ReserveOverview convertToDto(Reserve reserve) {
        ReserveOverview reserveOverview = modelMapper.map(reserve, ReserveOverview.class);
        reserveOverview.setPhoneNumber(reserve.getPhoneNumber());
        return reserveOverview;
    }
}

