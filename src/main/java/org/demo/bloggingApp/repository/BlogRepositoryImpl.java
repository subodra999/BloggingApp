package org.demo.bloggingApp.repository;

import com.google.inject.Inject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.demo.bloggingApp.dao.BlogDao;
import org.demo.bloggingApp.domain.BlogEntity;
import org.jdbi.v3.core.Jdbi;

import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor(onConstructor_ = @Inject)
public class BlogRepositoryImpl implements BlogRepository {

    private Jdbi jdbi;

    @Override
    public List<BlogEntity> getAllBlogsForUserId(final int id) {
        return this.jdbi.withExtension(BlogDao.class, dao -> dao.getAllBlogsForUserId(id));
    }

    @Override
    public void insertBlog(final BlogEntity blogEntity) {
        this.jdbi.useExtension(BlogDao.class, dao -> dao.insertBlog(blogEntity.getUserId(), blogEntity.getTitle(), blogEntity.getContent()));
    }
}
