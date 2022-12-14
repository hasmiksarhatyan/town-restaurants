package am.itspace.townrestaurantsrest.serviceRest;

import am.itspace.townrestaurantscommon.dto.token.VerificationTokenDto;
import am.itspace.townrestaurantscommon.dto.user.*;
import am.itspace.townrestaurantsrest.exception.EntityNotFoundException;

import java.util.List;

public interface UserService {

    void delete(int id);

    UserOverview save(CreateUserDto createUserDto);

    UserOverview update(int id, EditUserDto editUserDto);

    UserOverview verifyToken(VerificationTokenDto token);

    void changePassword(ChangePasswordDto changePasswordDto);

    UserOverview getById(int id) throws EntityNotFoundException;

    UserAuthResponseDto authentication(UserAuthDto userAuthDto);

    List<UserOverview> getAllUsers(int pageNo, int pageSize, String sortBy, String sortDir);
}
