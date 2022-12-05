package am.itspace.townrestaurantsrest.serviceRest.impl;

import am.itspace.townrestaurantscommon.dto.token.VerificationTokenDto;
import am.itspace.townrestaurantscommon.dto.user.*;
import am.itspace.townrestaurantscommon.entity.Role;
import am.itspace.townrestaurantscommon.entity.User;
import am.itspace.townrestaurantscommon.entity.VerificationToken;
import am.itspace.townrestaurantscommon.mapper.UserMapper2;
import am.itspace.townrestaurantscommon.repository.UserRepository;
import am.itspace.townrestaurantsrest.exception.Error;
import am.itspace.townrestaurantsrest.exception.*;
import am.itspace.townrestaurantsrest.serviceRest.MailServiceRest;
import am.itspace.townrestaurantsrest.serviceRest.UserService;
import am.itspace.townrestaurantsrest.serviceRest.VerificationTokenServiceRest;
import am.itspace.townrestaurantsrest.utilRest.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.mail.MessagingException;
import java.util.List;

import static am.itspace.townrestaurantsrest.exception.Error.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper2 userMapper;
    private final JwtTokenUtil jwtTokenUtil;
    private final MailServiceRest mailService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenServiceRest tokenService;

    @Override
    public UserOverview save(CreateUserDto createUserDto) throws MessagingException {
        if (userRepository.existsByEmail(createUserDto.getEmail())) {
            log.info("User with that email already exists");
            throw new RegisterException(Error.USER_REGISTRATION_FAILED);
        }
        User user = userMapper.mapToEntity(createUserDto);
        user.setRole(Role.CUSTOMER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User save = userRepository.save(user);
        sendMail(user);
        return userMapper.mapToResponseDto(save);
    }

    private void sendMail(User user) {
        try {
            VerificationToken token = tokenService.createToken(user);
            mailService.sendEmail(user.getEmail(), "Welcome", "Hi, " + user.getFirstName() + user.getLastName() + "\n" +
                    "please, verify your account by sending this to /verify url  " + token.getPlainToken());
            log.info("Verification token was sent to email {}", user.getEmail());
        } catch (MessagingException | RuntimeException e) {
            log.info("Failed to send an email");
            throw new RegisterException(SEND_EMAIL_FAILED);
        }
    }

    @Override
    public UserOverview verifyToken(VerificationTokenDto verificationTokenDto) {
        String plainToken = verificationTokenDto.getPlainToken();
        VerificationToken token = tokenService.findByPlainToken(plainToken);
        User user = token.getUser();
        tokenService.delete(token);
        if (user == null) {
            log.info("User does not exists with email and token");
            throw new EntityNotFoundException(USER_NOT_FOUND);
        }
        if (user.isEnabled()) {
            log.info("User already enabled");
            throw new VerificationException(USER_ALREADY_ENABLED);
        }
        user.setEnabled(true);
        userRepository.save(user);
        log.info("User successfully saved");
        return userMapper.mapToResponseDto(user);
    }

    @Override
    public UserAuthResponseDto authentication(UserAuthDto userAuthDto) {
        User user = userRepository.findByEmail(userAuthDto.getEmail()).orElseThrow(() -> new AuthenticationException(Error.UNAUTHORIZED));
        log.info("User with that email not found");
        if (passwordEncoder.matches(userAuthDto.getPassword(), user.getPassword())) {
            log.info("User with username {} get auth token", user.getEmail());
            return UserAuthResponseDto.builder()
                    .token(jwtTokenUtil.generateToken(user.getEmail(), user))
                    .build();
        } else {
            throw new AuthenticationException(Error.UNAUTHORIZED);
        }
    }

    @Override
    public List<UserOverview> getAll() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            log.info("User not found");
            throw new EntityNotFoundException(Error.USER_NOT_FOUND);
        } else {
            log.info("User successfully detected");
            return userMapper.mapToResponseDtoList(users);
        }
    }

    @Override
    public UserOverview getById(int id) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Error.USER_NOT_FOUND));
        log.info("User successfully found {}", user.getFirstName());
        return userMapper.mapToResponseDto(user);
    }

    @Override
    public void changePassword(ChangePasswordDto changePasswordDto, int userId) {
        User currentUser = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(Error.USER_NOT_FOUND));
        String username = currentUser.getEmail();
        log.info("Request from user {} to change the password", username);
        User user = userRepository.findByEmail(username).orElseThrow(() -> new EntityNotFoundException(Error.USER_NOT_FOUND));
        boolean matches = passwordEncoder.matches(changePasswordDto.getOldPassword(), user.getPassword());
        if (!matches) {
            log.warn("User {} had provided wrong credentials to change the password", user.getEmail());
            throw new AuthenticationException(PROVIDED_WRONG_PASSWORD);
        }
        if (changePasswordDto.getOldPassword().equals(changePasswordDto.getNewPassword1())) {
            log.warn("User {} provided the same password to change password", user.getEmail());
            throw new AuthenticationException(PROVIDED_SAME_PASSWORD);
        }
        user.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword1()));
        userRepository.save(user);
        log.info("User {} password was successfully changed", user.getEmail());
    }

    @Override
    public UserOverview update(int id, EditUserDto editUserDto) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Error.USER_NOT_FOUND));
        log.info("User with that id not found");
        String firstName = editUserDto.getFirstName();
        String lastName = editUserDto.getLastName();
        if (StringUtils.hasText(firstName)) {
            user.setFirstName(firstName);
        }
        if (StringUtils.hasText(lastName)) {
            user.setLastName(lastName);
        }
        user.setFirstName(editUserDto.getFirstName());
        userRepository.save(user);
        log.info("The user was successfully stored in the database {}", user.getFirstName());
        return userMapper.mapToResponseDto(user);
    }

    @Override
    public void delete(int id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            log.info("User has been successfully deleted");
        } else {
            log.info("User not found");
            throw new EntityNotFoundException(Error.USER_NOT_FOUND);
        }
    }
}


