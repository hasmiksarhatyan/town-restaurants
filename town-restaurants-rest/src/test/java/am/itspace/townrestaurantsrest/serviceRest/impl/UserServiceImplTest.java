package am.itspace.townrestaurantsrest.serviceRest.impl;

import am.itspace.townrestaurantscommon.dto.user.UserAuthDto;
import am.itspace.townrestaurantscommon.dto.user.UserAuthResponseDto;
import am.itspace.townrestaurantscommon.dto.user.UserOverview;
import am.itspace.townrestaurantscommon.entity.Role;
import am.itspace.townrestaurantscommon.entity.User;
import am.itspace.townrestaurantscommon.mapper.UserMapper2;
import am.itspace.townrestaurantscommon.repository.UserRepository;
import am.itspace.townrestaurantsrest.exception.AuthenticationException;
import am.itspace.townrestaurantsrest.exception.EntityNotFoundException;
import am.itspace.townrestaurantsrest.exception.Error;
import am.itspace.townrestaurantsrest.exception.RegisterException;
import am.itspace.townrestaurantsrest.utilRest.JwtTokenUtil;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static am.itspace.townrestaurantsrest.parameters.MockData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenUtil tokenUtil;

    @Mock
    private UserMapper2 userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    //auth
    @Test
    void shouldAuthenticateUserAndCreateResponseDto() {
        //given
        var authDto = getAuthDto();
        var user = getUser();
        var expected = getAuthResponseDto();

        //when
        doReturn(Optional.of(user)).when(userRepository).findByEmail(anyString());
        doReturn(true).when(passwordEncoder).matches(anyString(), anyString());
        doReturn(expected.getToken()).when(tokenUtil).generateToken(anyString(), any(User.class));
        UserAuthResponseDto actual = userService.authentication(authDto);

        //then
        assertNotNull(actual);
        assertEquals(expected.getToken(), actual.getToken());
    }

    @Test
    void shouldThrowExceptionAsUserNotFoundWhileAuthentication() {
        //given
        var authDto = getAuthDto();

        //when
        doThrow(AuthenticationException.class).when(userRepository).findByEmail(anyString());

        //then
        assertThrows(AuthenticationException.class, () -> userService.authentication(authDto));
    }

    @Test
    void shouldReturnNullAsPasswordsDoesNotMatch() {
        //given
        var authDto = getAuthDto();
        var user = getUser();

        //when
        doReturn(Optional.of(user)).when(userRepository).findByEmail(anyString());
        doReturn(false).when(passwordEncoder).matches(anyString(), anyString());
        UserAuthResponseDto actual = userService.authentication(authDto);

        //then
        assertNull(actual);
    }

    //save

    @Test
    void shouldSaveCreateUserDto() {
        //given
        var createUserDto = getCreateUserDto();
        var userOverview = getUserOverview();
        var user = getUser();
        //when

        doReturn(false).when(userRepository.existsByEmail(anyString()));
        doReturn(user).when(userMapper.mapToEntity(createUserDto));
//        user.setRole(Role.CUSTOMER);
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
        doReturn(true).when(userRepository.save(user));
//        doReturn(userOverview).when(userMapper.mapToResponseDto(user));
        UserOverview actual = userService.save(createUserDto);
        //then

        assertNotNull(actual);
//        assertEquals(actual,userOverview);
    }

    @Test
    void saveUser() {
        //given
        var user = getUser();
        //when
        userRepository.save(user);
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());
        User value = userArgumentCaptor.getValue();
        //then
        assertEquals(user, value);
    }

    @Test
    void shouldRegisterExceptionAsUserEmailIsFound() {
        //given
        var createUserDto = getCreateUserDto();

        //when
        doThrow(RegisterException.class).when(userRepository).existsByEmail(anyString());

        //then
        assertThrows(RegisterException.class, () -> userService.save(createUserDto));
    }

    @Test
    void addUserFailTesSaveNull() {
        //given
        var user = getUser();
        //then
        userRepository.save(null);
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());
        User value = userArgumentCaptor.getValue();
        assertNotEquals(user, value);
    }

    @Test
    void role() {
        //given
        var user = getUser();
        //then
        assertTrue(CoreMatchers.is(user.getRole()).matches(Role.CUSTOMER));
    }


    //getAll
    @Test
    void shouldEntityNotFoundExceptionAsUserNotFound() {
        //when
        doThrow(EntityNotFoundException.class).when(userRepository).findAll();

        //then
        assertThrows(EntityNotFoundException.class, () -> userService.getAll());
    }

    //    @Override
//    public UserOverview getById(int id) {
//        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Error.USER_NOT_FOUND));
//        log.info("User successfully found {}", user.getFirstName());
//        return userMapper.mapToResponseDto(user);
//    }
    //getById

    @Test
    void shouldGetById() {

        //given
        var user = getUser();
        var expected = getUserOverview();

        //when
        doReturn(Optional.of(user)).when(userRepository).findById(anyInt());
        doReturn(user).when(userMapper.mapToResponseDto(user));
        UserOverview actual = userService.getById(user.getId());

        //then
        assertNotNull(actual);
        assertEquals(expected.getId(), actual.getId());
    }


    //delete
    @Test
    void deleteUserSuccess() {
        //given
        int userId = 1;

        //when
        when(userRepository.existsById(userId)).thenReturn(true);
        userService.delete(userId);

        //then
        verify(userRepository).deleteById(userId);
    }

    @Test
    void shouldThrowExceptionAsUserNotFound() {
        //given
        int userId = 1;

        //when
        when(userRepository.existsById(userId)).thenReturn(false);

        //then
        assertThrows(EntityNotFoundException.class, () -> userService.delete(userId));
    }
}