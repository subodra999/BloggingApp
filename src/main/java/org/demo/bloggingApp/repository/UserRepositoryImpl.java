package org.demo.bloggingApp.repository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import org.demo.bloggingApp.dao.UserDao;
import org.demo.bloggingApp.domain.UserEntity;
import org.jdbi.v3.core.Jdbi;

import java.util.Optional;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserRepositoryImpl implements UserRepository {

    private Jdbi jdbi;

    public Long saveUser(final UserEntity user) {
        return this.jdbi.withExtension(UserDao.class, dao -> dao.insert(user));
    }

    public Optional<UserEntity> getUserByNameAndPassword(final String name, final String password) {
        return this.jdbi.withExtension(UserDao.class, dao -> dao.findByNameAndPassword(name, password));
    }
}
