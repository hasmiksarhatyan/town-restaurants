package am.itspace.townrestaurantscommon.mapper;

import am.itspace.townrestaurantscommon.dto.user.CreateUserDto;
import am.itspace.townrestaurantscommon.dto.user.UserOverview;
import am.itspace.townrestaurantscommon.entity.User;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

//    @Mapping(target = "role", defaultValue = "CUSTOMER")
    User mapToEntity(CreateUserDto createUserDto);

    UserOverview mapToResponseDto(User user);

    List<UserOverview> mapToResponseDtoList(List<User> users);
}
