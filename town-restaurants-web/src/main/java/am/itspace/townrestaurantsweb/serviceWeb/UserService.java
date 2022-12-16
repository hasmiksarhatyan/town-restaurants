package am.itspace.townrestaurantsweb.serviceWeb;

import am.itspace.townrestaurantscommon.dto.user.ChangePasswordDto;
import am.itspace.townrestaurantscommon.dto.user.CreateUserDto;
import am.itspace.townrestaurantscommon.dto.user.EditUserDto;
import am.itspace.townrestaurantscommon.dto.user.UserOverview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.mail.MessagingException;

public interface UserService {

    void delete(int id);

    void editUser(EditUserDto dto, int userId);

    void verifyUser(String token);

    Page<UserOverview> getUsers(Pageable pageable);

    void changePassword(Integer id, ChangePasswordDto dto);

    void saveUser(CreateUserDto dto) throws MessagingException;
}