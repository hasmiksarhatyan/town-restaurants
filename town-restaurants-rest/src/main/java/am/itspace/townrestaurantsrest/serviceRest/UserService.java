package am.itspace.townrestaurantsrest.serviceRest;

import am.itspace.townrestaurantscommon.dto.VerificationTokenDto;
import am.itspace.townrestaurantscommon.dto.user.*;
import am.itspace.townrestaurantsrest.exception.EntityNotFoundException;

import java.util.List;

public interface UserService {

    UserOverview save(CreateUserDto createUserDto);

    UserAuthResponseDto authentication(UserAuthDto userAuthDto);

    List<UserOverview> getAll();

    UserOverview getById(int id) throws EntityNotFoundException;

    UserOverview update(int id, EditUserDto editUserDto);

    void delete(int id);

    void changePassword(ChangePasswordDto changePasswordDto);

    UserOverview verifyUser(VerificationTokenDto token) throws Exception;
}
