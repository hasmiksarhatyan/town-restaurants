package am.itspace.townrestaurantsrest.serviceRest.impl;

import am.itspace.townrestaurantscommon.dto.user.UserAuthResponseDto;
import am.itspace.townrestaurantscommon.dto.user.UserOverview;
import am.itspace.townrestaurantscommon.entity.User;
import am.itspace.townrestaurantscommon.mapper.UserMapper;
import am.itspace.townrestaurantscommon.repository.UserRepository;
import am.itspace.townrestaurantsrest.exception.AuthenticationException;
import am.itspace.townrestaurantsrest.exception.EntityNotFoundException;
import am.itspace.townrestaurantsrest.exception.RegisterException;
import am.itspace.townrestaurantsrest.utilRest.JwtTokenUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
    private UserMapper userMapper;

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

    @Test
    void successfulPasswordChanging() throws Exception {
        //given
        var authDto = getAuthDto();
        var user = getUser();
        //when
        doReturn(Optional.of(user)).when(userRepository).findByEmail(anyString());
        doReturn(true).when(passwordEncoder).matches(anyString(), anyString());
        UserAuthResponseDto actual = userService.authentication(authDto);
        //then
        assertNotNull(actual);
//        //given
//        var user = getUser();
//        var email = getUser().getEmail();
//        var changePassword = getChangePasswordDto();
//        //when
//        doReturn(user).when(any(User.class));
//        doReturn(Optional.of(user)).when(userRepository).findByEmail(email);
//        doReturn(true).when(passwordEncoder).matches(anyString(), anyString());
//        doReturn(user).when(userRepository).save(any(User.class));
//        userService.changePassword(changePassword);
//        //then
//        verify(userRepository, times(1)).save(user);
    }
/////nayiiii
    //save
//    @Test
//    void shouldSaveUser() {
//        //given
//        var createUserDto = getCreateUserDto();
//        var expected = getUserOverview();
//        var user = getUser();
//        //when
//        doReturn(false).when(userRepository).existsByEmail(anyString());
//        doReturn(user).when(userMapper).mapToEntity(createUserDto);
//        doReturn(expected).when(userMapper).mapToResponseDto(user);
//        doReturn(user).when(userRepository).save(any(User.class));
//        UserOverview actual = userService.save(createUserDto);
//        //then
//        assertNotNull(actual);
//        assertEquals(expected, actual);
//    }

    @Test
    void shouldThrowExceptionAsEmailAlreadyExists() {
        //given
        var createUserDto = getCreateUserDto();
        //when
        doReturn(true).when(userRepository).existsByEmail(anyString());
        //then
        assertThrows(RegisterException.class, () -> userService.save(createUserDto));
    }

    //getAll
    @Test
    void shouldGetAllUsers() {
        //given
        var users = List.of(getUser(), getUser(), getUser());
        var expected = List.of(getUserOverview(), getUserOverview(), getUserOverview());
        //when
        doReturn(users).when(userRepository).findAll();
        doReturn(expected).when(userMapper).mapToResponseDtoList(users);
        List<UserOverview> actual = userService.getAll();
        //then
        assertNotNull(actual);
        assertEquals(expected.size(), actual.size());
    }

    @Test
    void shouldThrowExceptionAsUserListIsEmpty() {
        //given
        List<User> empty = List.of();
        //when
        doReturn(empty).when(userRepository).findAll();
        //then
        assertThrows(EntityNotFoundException.class, () -> userService.getAll());
    }

    @Test
    void shouldEntityNotFoundExceptionAsUserNotFound() {
        //when
        doThrow(EntityNotFoundException.class).when(userRepository).findAll();
        //then
        assertThrows(EntityNotFoundException.class, () -> userService.getAll());
    }

    //getById
    @Test
    void shouldGetById() {
        //given
        var user = getUser();
        var expected = getUserOverview();
        //when
        doReturn(Optional.of(user)).when(userRepository).findById(anyInt());
        doReturn(expected).when(userMapper).mapToResponseDto(user);
        UserOverview actual = userService.getById(user.getId());
        //then
        assertNotNull(actual);
        assertEquals(expected.getId(), actual.getId());
    }

    //update
    @Test
    void shouldUpdateUser() {
        //given
        var user = getUser();
        var expected = getUserOverview();
        var editUser = getEditUserDto();
        //when
        doReturn(Optional.of(user)).when(userRepository).findById(anyInt());
        doReturn(expected).when(userMapper).mapToResponseDto(user);
        doReturn(user).when(userRepository).save(any(User.class));
        UserOverview actual = userService.update(user.getId(), editUser);
        //then
        verify(userRepository, times(1)).save(user);
        assertNotNull(actual);
        assertEquals(expected, actual);
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