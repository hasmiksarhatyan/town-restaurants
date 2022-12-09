package am.itspace.townrestaurantsrest.serviceRest;

import am.itspace.townrestaurantscommon.dto.FetchRequestDto;
import am.itspace.townrestaurantscommon.dto.token.VerificationTokenDto;
import am.itspace.townrestaurantscommon.dto.user.*;
import am.itspace.townrestaurantscommon.entity.User;
import am.itspace.townrestaurantsrest.exception.EntityNotFoundException;

import java.util.List;

public interface UserService {

    void delete(int id);

    List<UserOverview> getAll();

    UserOverview update(int id, EditUserDto editUserDto);

    UserOverview verifyToken(VerificationTokenDto token);

    UserOverview getById(int id) throws EntityNotFoundException;

    UserAuthResponseDto authentication(UserAuthDto userAuthDto);

    void changePassword(ChangePasswordDto changePasswordDto);

    UserOverview save(CreateUserDto createUserDto);

    List<User> getUsersList(FetchRequestDto fetchRequestDto);
}
