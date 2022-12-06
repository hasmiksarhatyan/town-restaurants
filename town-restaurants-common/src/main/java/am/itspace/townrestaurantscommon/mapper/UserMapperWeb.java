package am.itspace.townrestaurantscommon.mapper;

import am.itspace.townrestaurantscommon.dto.user.CreateUserDto;
import am.itspace.townrestaurantscommon.dto.user.UserOverview;
import am.itspace.townrestaurantscommon.entity.Role;
import am.itspace.townrestaurantscommon.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserMapperWeb {

    private final PasswordEncoder passwordEncoder;

    public UserOverview mapToOverview(User user) {
        return UserOverview.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRole())
                .build();
    }

    public User mapToEntity(CreateUserDto dto) {
        return User.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(Role.CUSTOMER)
                .enabled(false)
                .build();
    }

    public Page<UserOverview> mapToOverviewPage(Page<User> users, Pageable pageable) {
        List<UserOverview> userOverviews = new ArrayList<>();
        for (User user : users) {
            userOverviews.add(mapToOverview(user));
        }
        return new PageImpl<>(userOverviews, pageable, userOverviews.size());
    }
}

