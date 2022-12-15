package am.itspace.townrestaurantsweb.serviceWeb.impl;

import am.itspace.townrestaurantscommon.dto.user.ChangePasswordDto;
import am.itspace.townrestaurantscommon.dto.user.CreateUserDto;
import am.itspace.townrestaurantscommon.dto.user.EditUserDto;
import am.itspace.townrestaurantscommon.dto.user.UserOverview;
import am.itspace.townrestaurantscommon.entity.Role;
import am.itspace.townrestaurantscommon.entity.User;
import am.itspace.townrestaurantscommon.entity.VerificationToken;
import am.itspace.townrestaurantscommon.mapper.UserMapper;
import am.itspace.townrestaurantscommon.repository.UserRepository;
import am.itspace.townrestaurantscommon.service.MailService;
import am.itspace.townrestaurantsweb.serviceWeb.UserService;
import am.itspace.townrestaurantsweb.serviceWeb.VerificationTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.mail.MessagingException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final MailService mailService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenService tokenService;

    @Override
    public Page<UserOverview> getUsers(Pageable pageable) {
        List<UserOverview> userOverviews = userMapper.mapToResponseDtoList(userRepository.findAll());
        return new PageImpl<>(userOverviews);
    }

    @Override
    public void saveUser(CreateUserDto dto) throws MessagingException {
        log.info("New request to get registered. Email {}", dto.getEmail());
        if (userRepository.existsByEmailIgnoreCase(dto.getEmail())) {
            log.info("There is already a user with email {}", dto.getEmail());
            throw new IllegalStateException("That email already in use");
        }
        User user = userMapper.mapToEntity(dto);
        user.setRole(Role.CUSTOMER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        log.info("User {} has successfully registered", user.getEmail());
        VerificationToken token = tokenService.createToken(user);
        mailService.sendEmail(dto.getEmail(), "Welcome", "Hi, " + dto.getFirstName() + dto.getLastName() + "\n" +
                "please, verify your account by clicking on this link <a href=\"http://localhost:8080/users/verify?token=" + token.getPlainToken() + "\">Active</a>");
        log.info("Verification token was sent to email {}", user.getEmail());
    }

    @Override
    public void verifyUser(String plainToken) {
        VerificationToken token = tokenService.findByPlainToken(plainToken);
        User user = token.getUser();
        tokenService.delete(token);
        if (user == null) {
            throw new IllegalStateException("User does not exists with email and token");
        }
        if (user.isEnabled()) {
            throw new IllegalStateException("User already enabled");
        }
        user.setEnabled(true);
        userRepository.save(user);
    }

    @Override
    public void delete(int id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalStateException("Something went wrong, try again!");
        }
        userRepository.deleteById(id);
    }

    @Override
    public void changePassword(Integer id, ChangePasswordDto dto) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new IllegalStateException("User doesn't exist");
        }
        User user = optionalUser.get();
        String oldPassword = dto.getOldPassword();
        String newPassword1 = dto.getNewPassword1();
        String newPassword2 = dto.getNewPassword2();

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new IllegalStateException("Provided wrong password");
        }

        if (!newPassword1.equals(newPassword2)) {
            throw new IllegalStateException("Given new passwords don't match");
        }
        if (passwordEncoder.matches(newPassword1, user.getPassword())) {
            throw new IllegalStateException("Provided old password in change password request");
        }
        user.setPassword(passwordEncoder.encode(newPassword1));
        userRepository.save(user);
    }

    @Override
    public void editUser(EditUserDto dto, int userId) {
        Optional<User> optional = userRepository.findById(userId);
        if (optional.isEmpty()) {
            throw new IllegalStateException("Something went wrong, try again!");
        }
        User user = optional.get();
        String firstName = dto.getFirstName();
        String lastName = dto.getLastName();

        if (StringUtils.hasText(firstName)) {
            user.setFirstName(firstName);
        }
        if (StringUtils.hasText(lastName)) {
            user.setLastName(lastName);
        }
        userRepository.save(user);
    }
}
