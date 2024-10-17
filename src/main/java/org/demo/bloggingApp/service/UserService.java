package org.demo.bloggingApp.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.demo.bloggingApp.domain.UserEntity;
import org.demo.bloggingApp.dto.request.UserRequest;
import org.demo.bloggingApp.dto.response.LoginResponse;
import org.demo.bloggingApp.error.AppException;
import org.demo.bloggingApp.error.ApplicationError;
import org.demo.bloggingApp.repository.UserRepository;

import java.util.Optional;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;

    public Object register(final UserRequest userDto) {
        UserEntity user = UserEntity.builder().name(userDto.getName()).password(userDto.getPassword()).build();
        return userRepository
                .saveUser(user);
    }

    public LoginResponse login(final UserRequest userDto) {
        return userRepository
                .getUserByNameAndPassword(userDto.getName(), userDto.getPassword())
                .map(user -> LoginResponse.builder().isSuccess(true).welcomeNote("Welcome" + user.getName()).build())
                .orElseThrow(() -> new AppException(ApplicationError.USER_NOT_FOUND));
    }

}
