package am.itspace.townrestaurantsrest.controller;

import am.itspace.townrestaurantscommon.dto.FetchRequestDto;
import am.itspace.townrestaurantscommon.dto.user.ChangePasswordDto;
import am.itspace.townrestaurantscommon.dto.user.EditUserDto;
import am.itspace.townrestaurantscommon.dto.user.UserOverview;
import am.itspace.townrestaurantscommon.entity.User;
import am.itspace.townrestaurantsrest.api.UserApi;
import am.itspace.townrestaurantsrest.serviceRest.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.modelmapper.ModelMapper;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserEndpoint implements UserApi {

    private final ModelMapper modelMapper;
    private final UserService userService;

    @Override
    @GetMapping
    public ResponseEntity<List<UserOverview>> getAll() {
        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping("/pages")
    public ResponseEntity<List<UserOverview>> getAll(@Valid @RequestBody FetchRequestDto fetchRequestDto) {
        List<User> users = userService.getUsersList(fetchRequestDto);
        return ResponseEntity.ok(users.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList()));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<UserOverview> getById(@PathVariable("id") int id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @Override
    @PutMapping("/password/restore")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordDto changePasswordDto) {
        userService.changePassword(changePasswordDto);
        return ResponseEntity.ok().build();
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<UserOverview> update(@Valid @PathVariable("id") int id,
                                               @RequestBody EditUserDto editUserDto) {
        return ResponseEntity.ok(userService.update(id, editUserDto));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        userService.delete(id);
        return ResponseEntity.ok().build();
    }

    private UserOverview convertToDto(User user) {
        UserOverview userOverview = modelMapper.map(user, UserOverview.class);
        userOverview.setEmail(user.getEmail());
        return userOverview;
    }
}
