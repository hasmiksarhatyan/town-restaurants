package am.itspace.townrestaurantsrest.serviceRest.impl;

import am.itspace.townrestaurantscommon.dto.user.*;
import am.itspace.townrestaurantscommon.entity.Role;
import am.itspace.townrestaurantscommon.entity.User;
import am.itspace.townrestaurantscommon.mapper.UserMapper2;
import am.itspace.townrestaurantscommon.repository.UserRepository;
import am.itspace.townrestaurantsrest.exception.AuthenticationException;
import am.itspace.townrestaurantsrest.exception.EntityNotFoundException;
import am.itspace.townrestaurantsrest.exception.Error;
import am.itspace.townrestaurantsrest.exception.RegisterException;
import am.itspace.townrestaurantsrest.serviceRest.UserService;
import am.itspace.townrestaurantsrest.utilRest.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper2 userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    public UserOverview save(CreateUserDto createUserDto) {
        if (userRepository.existsByEmail(createUserDto.getEmail())) {
            log.info("User with that email already exists");
            throw new RegisterException(Error.USER_REGISTRATION_FAILED);
        }
        User user = userMapper.mapToEntity(createUserDto);
        user.setRole(Role.CUSTOMER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userMapper.mapToResponseDto(userRepository.save(user));
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
    public UserOverview update(int id, EditUserDto editUserDto) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Error.USER_NOT_FOUND));
        log.info("User with that id not found");
        if (editUserDto.getFirstName() != null) {
            user.setFirstName(editUserDto.getFirstName());
            userRepository.save(user);
        }
        log.info("The user was successfully stored in the database {}", user.getFirstName());
        return userMapper.mapToResponseDto(user);
    }

    @Override
    public void delete(int id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            log.info("The User has been successfully deleted");
        } else {
            log.info("User not found");
            throw new EntityNotFoundException(Error.USER_NOT_FOUND);
        }
    }
}


