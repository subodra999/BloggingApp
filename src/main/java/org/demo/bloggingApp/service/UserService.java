package org.demo.bloggingApp.service;

import com.google.inject.Inject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.demo.bloggingApp.domain.UserEntity;
import org.demo.bloggingApp.dto.request.UserRequest;
import org.demo.bloggingApp.dto.response.LoginResponse;
import org.demo.bloggingApp.dto.response.RegistrationResponse;
import org.demo.bloggingApp.error.AppException;
import org.demo.bloggingApp.error.ApplicationError;
import org.demo.bloggingApp.repository.UserRepository;
import org.demo.bloggingApp.utils.PasswordUtils;

@Builder
@AllArgsConstructor(onConstructor_ = { @Inject})
public class UserService {

    private UserRepository userRepository;

    public Object register(final UserRequest userDto) {

        UserEntity user = UserEntity.builder()
                .name(userDto.getName())
                .password(PasswordUtils.hashPassword(userDto.getPassword()))
                .build();

        return userRepository
                .getUserByName(userDto.getName())
                .map(existingUser -> {
                    throw new AppException(ApplicationError.USER_ALREADY_EXISTS);
                })
                .orElseGet(() -> {
                    long id = userRepository.saveUser(user);
                    return RegistrationResponse.builder()
                            .UserId(String.valueOf(id))
                            .message("User registered successfully!")
                            .build();
                });
    }

    public LoginResponse login(final UserRequest userDto) {
        return userRepository
                .getUserByName(userDto.getName())
                .map(userEntity -> {
                    if (PasswordUtils.verifyPassword(userDto.getPassword(), userEntity.getPassword())) {
                        return LoginResponse.builder()
                                .welcomeNote("Welcome " + userEntity.getName().toUpperCase() + ", you are loggedIn now!")
                                .build();
                    } else {
                        throw new AppException(ApplicationError.INVALID_PASSWORD);
                    }
                })
                .orElseThrow(() -> new AppException(ApplicationError.USER_NOT_FOUND));
    }

}
