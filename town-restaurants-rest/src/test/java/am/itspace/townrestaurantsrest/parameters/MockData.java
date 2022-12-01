package am.itspace.townrestaurantsrest.parameters;

import am.itspace.townrestaurantscommon.dto.user.CreateUserDto;
import am.itspace.townrestaurantscommon.dto.user.UserAuthDto;
import am.itspace.townrestaurantscommon.dto.user.UserAuthResponseDto;
import am.itspace.townrestaurantscommon.dto.user.UserOverview;
import am.itspace.townrestaurantscommon.entity.Role;
import am.itspace.townrestaurantscommon.entity.User;

import java.time.LocalDateTime;

public class MockData {

    public static UserAuthDto getAuthDto() {
        return UserAuthDto.builder()
                .email("poxos@mail.com")
                .password("poxos")
                .build();
    }

    public static User getUser() {
        return User.builder()
                .id(1)
                .firstName("poxos")
                .lastName("poxosyan")
                .email("poxos@mail.com")
                .password("poxos")
                .createdAt(LocalDateTime.now())
                .role(Role.CUSTOMER)
                .enabled(true)
                .build();
    }

    public static UserAuthResponseDto getAuthResponseDto() {
        return UserAuthResponseDto.builder()
                .token("some token")
                .build();
    }

    public static CreateUserDto getCreateUserDto() {
        return CreateUserDto.builder()
                .firstName("poxos")
                .lastName("poxosyan")
                .email("poxos@mail.com")
                .password("poxos")
                .enabled(true)
                .build();
    }

    public static UserOverview getUserOverview() {
        return UserOverview.builder()
                .id(1)
                .firstName("poxos")
                .lastName("poxosyan")
                .email("poxos@mail.com")
                .role(Role.CUSTOMER)
                .build();
    }
}
