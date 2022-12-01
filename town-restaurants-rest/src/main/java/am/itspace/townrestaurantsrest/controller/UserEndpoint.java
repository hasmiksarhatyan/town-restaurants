package am.itspace.townrestaurantsrest.controller;

import am.itspace.townrestaurantscommon.dto.user.EditUserDto;
import am.itspace.townrestaurantscommon.dto.user.UserOverview;
import am.itspace.townrestaurantsrest.serviceRest.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserEndpoint {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserOverview>> getAll() {
        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserOverview> getById(@PathVariable("id") int id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserOverview> update(@Valid @PathVariable("id") int id,
                                               @RequestBody EditUserDto editUserDto) {
        return ResponseEntity.ok(userService.update(id, editUserDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        userService.delete(id);
        return ResponseEntity.ok().build();
    }
}
