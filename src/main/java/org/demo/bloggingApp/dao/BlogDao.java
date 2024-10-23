package org.demo.bloggingApp.dao;

import org.demo.bloggingApp.domain.BlogEntity;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

public interface BlogDao {

    @SqlQuery(
            "SELECT * FROM blogs WHERE user_id = :id"
    )
    @RegisterBeanMapper(BlogEntity.class)
    List<BlogEntity> getAllBlogsForUserId(@Bind("id") int id);

    @SqlUpdate(
            "INSERT INTO blogs (user_id, title, content) VALUES (:userId, :title, :content)"
    )
    void insertBlog(@Bind("userId") int userId, @Bind("title") String title, @Bind("content") String content);
}
