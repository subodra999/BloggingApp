package org.demo.bloggingApp.service;

import static org.junit.jupiter.api.Assertions.*;

import org.demo.bloggingApp.domain.UserEntity;
import org.demo.bloggingApp.dto.request.UserRequest;
import org.demo.bloggingApp.dto.response.LoginResponse;
import org.demo.bloggingApp.error.AppException;
import org.demo.bloggingApp.repository.UserRepository;
import org.demo.bloggingApp.utils.PasswordUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void testRegister_whenUserDoesNotExist_shouldCreateUser() {

        UserRequest userDto = UserRequest.builder()
                .name("test")
                .password("password")
                .build();

        Mockito.doReturn(Optional.empty()).when(userRepository).getUserByName(any());
        Mockito.doReturn(1L).when(userRepository).saveUser(any());

        userService.register(userDto);

        Mockito.verify(userRepository, Mockito.times(1)).getUserByName(anyString());
        Mockito.verify(userRepository, Mockito.times(1)).saveUser(any(UserEntity.class));
    }

    @Test
    public void testRegister_whenUserAlreadyExist_shouldThrowException() {

        UserRequest userDto = UserRequest.builder()
                .name("test")
                .password("password")
                .build();

        UserEntity userEntity = UserEntity.builder()
                .name("test")
                .password("password")
                .build();

        Mockito.doReturn(Optional.of(userEntity)).when(userRepository).getUserByName(any());

        AppException exception = assertThrows(
                AppException.class,
                () -> userService.register(userDto),
                "no exception thrown"
        );

        assertEquals("User already exists", exception.getError().getMessage());
        assertEquals(400, exception.getError().getStatusCode());
        Mockito.verify(userRepository, Mockito.times(1)).getUserByName(anyString());
        Mockito.verify(userRepository, Mockito.never()).saveUser(any(UserEntity.class));
    }

    @Test
    public void testLogin_whenUserNameAndPasswordIsValid() {
        UserRequest userDto = UserRequest.builder()
                .name("test")
                .password("password")
                .build();

        UserEntity userEntity = UserEntity.builder()
                .name("test")
                .password(PasswordUtils.hashPassword("password"))
                .build();

        Mockito.doReturn(Optional.of(userEntity)).when(userRepository).getUserByName(anyString());

        LoginResponse response = userService.login(userDto);

        assertEquals("Welcome TEST, you are loggedIn now!", response.getWelcomeNote());
    }

    @Test
    public void testLogin_whenUserDoesNotExist_shouldThrowException() {
        UserRequest userDto = UserRequest.builder()
                .name("test")
                .password("password")
                .build();

        Mockito.doReturn(Optional.empty()).when(userRepository).getUserByName(anyString());

        AppException exception = assertThrows(
                AppException.class,
                () -> userService.login(userDto),
                "no exception thrown"
        );

        assertEquals("User not found", exception.getError().getMessage());
        assertEquals(400, exception.getError().getStatusCode());
        Mockito.verify(userRepository, Mockito.times(1)).getUserByName(anyString());
    }

    @Test
    public void testLogin_whenUserExistAndPasswordIsInvalid_shouldThrowException() {
        UserRequest userDto = UserRequest.builder()
                .name("test")
                .password("password")
                .build();

        UserEntity userEntity = UserEntity.builder()
                .name("test")
                .password(PasswordUtils.hashPassword("wrongPassword"))
                .build();

        Mockito.doReturn(Optional.of(userEntity)).when(userRepository).getUserByName(anyString());

        AppException exception = assertThrows(
                AppException.class,
                () -> userService.login(userDto),
                "no exception thrown"
        );

        assertEquals("Invalid password", exception.getError().getMessage());
        assertEquals(400, exception.getError().getStatusCode());
        Mockito.verify(userRepository, Mockito.times(1)).getUserByName(anyString());
    }
}
