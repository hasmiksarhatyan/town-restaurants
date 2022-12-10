package am.itspace.townrestaurantsrest.serviceRest.impl;

import am.itspace.townrestaurantscommon.dto.user.UserAuthResponseDto;
import am.itspace.townrestaurantscommon.dto.user.UserOverview;
import am.itspace.townrestaurantscommon.entity.User;
import am.itspace.townrestaurantscommon.mapper.UserMapper;
import am.itspace.townrestaurantscommon.repository.UserRepository;
import am.itspace.townrestaurantscommon.repository.VerificationTokenRepository;
import am.itspace.townrestaurantscommon.security.CurrentUser;
import am.itspace.townrestaurantsrest.exception.AuthenticationException;
import am.itspace.townrestaurantsrest.exception.EntityNotFoundException;
import am.itspace.townrestaurantsrest.exception.RegisterException;
import am.itspace.townrestaurantsrest.serviceRest.VerificationTokenServiceRest;
import am.itspace.townrestaurantsrest.utilRest.JwtTokenUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static am.itspace.townrestaurantsrest.parameters.MockData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserMapper userMapper;

    @Mock
    private JwtTokenUtil tokenUtil;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    VerificationTokenRepository tokenRepository;

    @Mock
    private VerificationTokenServiceRest tokenService;

    @Mock
    SecurityContextServiceImpl securityContextService;

    @Test
    void shouldAuthenticateUserAndCreateResponseDto() {
        //given
        var user = getUser();
        var authDto = getAuthDto();
        var expected = getAuthResponseDto();
        //when
        doReturn(Optional.of(user)).when(userRepository).findByEmail(anyString());
        doReturn(true).when(passwordEncoder).matches(anyString(), anyString());
        doReturn(expected.getToken()).when(tokenUtil).generateToken(any(User.class));
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
    void authenticationShouldThrowExceptionAsPasswordsDoesNotMatch() {
        //given
        var user = getUser();
        var authDto = getAuthDto();
        var email = getUser().getEmail();
        CurrentUser currentUser = new CurrentUser(getUser());
        //when
        doReturn(Optional.of(user)).when(userRepository).findByEmail(email);
        doReturn(false).when(passwordEncoder).matches(anyString(), anyString());
        //then
        assertThrows(AuthenticationException.class, () -> userService.authentication(authDto));
    }

    @Test
    void successfulPasswordChanging() throws Exception {
        //given
        var user = getUser();
        var email = getUser().getEmail();
        CurrentUser currentUser = new CurrentUser(getUser());
        var changePassword = getChangePasswordDto();
        //when
        doReturn(currentUser).when(securityContextService).getUserDetails();
        doReturn(Optional.of(user)).when(userRepository).findByEmail(email);
        doReturn(true).when(passwordEncoder).matches(anyString(), anyString());
        doReturn(user).when(userRepository).save(any(User.class));
        userService.changePassword(changePassword);
        //then
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void passwordChangingShouldTrowException() throws Exception {
        //given
        var user = getUser();
        var email = getUser().getEmail();
        CurrentUser currentUser = new CurrentUser(getUser());
        var changePassword = getChangePasswordDto();
        //when
        doReturn(currentUser).when(securityContextService).getUserDetails();
        doReturn(Optional.of(user)).when(userRepository).findByEmail(email);
        doReturn(false).when(passwordEncoder).matches(anyString(), anyString());
        //then
        assertThrows(AuthenticationException.class, () -> userService.changePassword(changePassword));
    }

    @Test
    void shouldSaveUser() {
        //given
        var user = getUser();
        var expected = getUserOverview();
        var createUserDto = getCreateUserDto();
        //when
        doReturn(false).when(userRepository).existsByEmail(anyString());
        doReturn(user).when(userMapper).mapToEntity(createUserDto);
        doReturn(expected).when(userMapper).mapToResponseDto(user);
        doReturn(user).when(userRepository).save(any(User.class));
        UserOverview actual = userService.save(createUserDto);
        //then
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void VerificationToken() {
        //given
        var user = getUser();
        var token = getVerificationToken();
        var tokenDto = getVerificationTokenDto();
        //when
        doReturn(token).when(tokenService).findByPlainToken(anyString());
        tokenService.delete(token);
        doReturn(user).when(userRepository).save(any(User.class));
        UserOverview actual = userService.verifyToken(tokenDto);
        //then
        assertNotNull(actual);
        verify(tokenRepository, times(1)).delete(token);
    }

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

    @Test
    void shouldGetUsersList() {
        //given
        var listOfUsers = getPageRestaurants();
        var fetchRequest = getFetchRequestDto();
        PageRequest pageReq = PageRequest.of(1, 1, Sort.Direction.fromString("desc"), "1");
        //when
        doReturn(listOfUsers).when(userRepository).findByEmail("1", pageReq);
        List<User> actual = userService.getUsersList(fetchRequest);
        //then
        assertNotNull(actual);
    }

    @Test
    void shouldGetUsersListThrowException() {
        //given
        var fetchRequest = getFetchRequestDto();
        var getNullPageUsers = getNullPageRestaurants();
        PageRequest pageReq = PageRequest.of(1, 1, Sort.Direction.fromString("desc"), "1");
        //when
        doReturn(getNullPageUsers).when(userRepository).findByEmail("1", pageReq);
        //then
        assertThrows(EntityNotFoundException.class, () -> userService.getUsersList(fetchRequest));
    }

    @Test
    void shouldUpdateUser() {
        //given
        var user = getUser();
        var editUser = getEditUserDto();
        var expected = getUserOverview();
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