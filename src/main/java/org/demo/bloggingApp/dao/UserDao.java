package org.demo.bloggingApp.dao;

import org.demo.bloggingApp.domain.UserEntity;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.Optional;

public interface UserDao {

    @GetGeneratedKeys
    @SqlUpdate(
            "INSERT INTO users (name, password) VALUES (:user.name, :user.password)"
    )
    long insert(@BindBean("user") UserEntity user);

    @SqlQuery(
            "SELECT * FROM users WHERE name = :name AND password = :password"
    )
    @RegisterBeanMapper(UserEntity.class)
    Optional<UserEntity> findByNameAndPassword(@Bind("name") String name, @Bind("password") String password);

    @SqlQuery(
            "SELECT * FROM users WHERE name = :name"
    )
    @RegisterBeanMapper(UserEntity.class)
    Optional<UserEntity> findByName(@Bind("name") String name);

    @SqlQuery(
            "SELECT * FROM users WHERE id = :id"
    )
    @RegisterBeanMapper(UserEntity.class)
    Optional<UserEntity> findById(@Bind("id") int id);
}
