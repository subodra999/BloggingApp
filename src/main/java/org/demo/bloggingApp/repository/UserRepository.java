package org.demo.bloggingApp.repository;

import org.demo.bloggingApp.domain.UserEntity;

import java.util.Optional;

public interface UserRepository {

    Long saveUser(final UserEntity user);

    Optional<UserEntity> getUserByNameAndPassword(final String name, final String password);
}
