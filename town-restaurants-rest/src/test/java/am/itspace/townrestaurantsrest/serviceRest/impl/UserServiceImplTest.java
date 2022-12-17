package am.itspace.townrestaurantsrest.serviceRest.impl;

import am.itspace.townrestaurantscommon.dto.user.UserAuthResponseDto;
import am.itspace.townrestaurantscommon.dto.user.UserOverview;
import am.itspace.townrestaurantscommon.entity.User;
import am.itspace.townrestaurantscommon.entity.VerificationToken;
import am.itspace.townrestaurantscommon.mapper.UserMapper;
import am.itspace.townrestaurantscommon.repository.UserRepository;
import am.itspace.townrestaurantscommon.repository.VerificationTokenRepository;
import am.itspace.townrestaurantscommon.security.CurrentUser;
import am.itspace.townrestaurantscommon.service.MailService;
import am.itspace.townrestaurantsrest.exception.AuthenticationException;
import am.itspace.townrestaurantsrest.exception.EntityNotFoundException;
import am.itspace.townrestaurantsrest.exception.RegisterException;
import am.itspace.townrestaurantsrest.exception.VerificationException;
import am.itspace.townrestaurantsrest.serviceRest.VerificationTokenServiceRest;
import am.itspace.townrestaurantsrest.utilRest.JwtTokenUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.mail.MessagingException;
import java.util.List;
import java.util.Optional;

import static am.itspace.townrestaurantsrest.parameters.MockData.*;
import static org.hamcrest.MatcherAssert.assertThat;
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
    private MailService mailService;

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
    void successfulPasswordChanging() {
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
    void shouldSaveUser() throws MessagingException {
        //given
        var user = getUser();
        var expected = getUserOverview();
        var createUserDto = getCreateUserDto();
        var verificationToken = getVerificationToken();
        //when
        doReturn(false).when(userRepository).existsByEmail(anyString());
        doReturn(user).when(userMapper).mapToEntity(createUserDto);
        doReturn(user).when(userRepository).save(any(User.class));
        tokenService.createToken(user);
//        doNothing().when(mailService).sendEmail(user.getEmail(), "any", verificationToken.getPlainToken());
        doReturn(verificationToken).when(tokenRepository).save(any(VerificationToken.class));
        doReturn(expected).when(userMapper).mapToResponseDto(user);
        UserOverview actual = userService.save(createUserDto);
        //then
        assertNotNull(actual);
        assertEquals(expected, actual);

//        verify(userRepository, times(1)).save(user);
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

    @Test
    void tokenVerification() {
        //given
        var user = getUserForToken();
        var token = getVToken();
        var tokenDto = getVerificationTokenDto();
        //when
        doReturn(token).when(tokenService).findByPlainToken(anyString());
        tokenService.delete(token);
        tokenRepository.delete(token);
        doReturn(user).when(userRepository).save(any(User.class));
        userService.verifyToken(tokenDto);
        //then
        verify(tokenRepository, times(1)).delete(token);
    }

    @Test
    void tokenVerificationShouldThrowExceptionAsUserIsNull() {
        //given
        var token = getTokenWithoutUser();
        var tokenDto = getVerificationTokenDto();
        //when
        doReturn(token).when(tokenService).findByPlainToken(anyString());
        tokenService.delete(token);
        tokenRepository.delete(token);
        //then
        assertThrows(EntityNotFoundException.class, () -> userService.verifyToken(tokenDto));
    }

    @Test
    void tokenVerificationShouldThrowException() {
        //given
        var user = getUser();
        var token = getVerificationToken();
        var tokenDto = getVerificationTokenDto();
        //when
        doReturn(token).when(tokenService).findByPlainToken(anyString());
        tokenService.delete(token);
        assertThat(String.valueOf(user.isEnabled()), true);
        //then
        assertThrows(VerificationException.class, () -> userService.verifyToken(tokenDto));
    }

    @Test
    void shouldGetAllUsers() {
        //given
        var listOfPageableUsers = getPageUsers();
        var expected = List.of(getUserOverview(), getUserOverview());
        PageRequest pageable = PageRequest.of(1, 1, Sort.Direction.fromString("DESC"), "name");
        //when
        doReturn(listOfPageableUsers).when(userRepository).findAll(pageable);
        doReturn(expected).when(userMapper).mapToResponseDtoList(anyList());
        List<UserOverview> actual = userService.getAllUsers(1, 1, "name", "DESC");
        //then
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void getAllUsersShouldThrowException() {
        //given
        Page<User> empty = Page.empty();
        PageRequest pageable = PageRequest.of(1, 1, Sort.Direction.fromString("DESC"), "name");
        //when
        doReturn(empty).when(userRepository).findAll(pageable);
        //then
        assertThrows(EntityNotFoundException.class, () -> userService.getAllUsers(1, 1, "name", "DESC"));
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