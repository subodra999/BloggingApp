package org.demo.bloggingApp.repository;

import org.demo.bloggingApp.domain.BlogEntity;

import java.util.List;

public interface BlogRepository {

    List<BlogEntity> getAllBlogsForUserId(int id);

    void insertBlog(final BlogEntity blogEntity);
}
