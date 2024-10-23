package org.demo.bloggingApp.service;

import com.google.inject.Inject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.demo.bloggingApp.domain.BlogEntity;
import org.demo.bloggingApp.dto.request.BlogRequest;
import org.demo.bloggingApp.error.AppException;
import org.demo.bloggingApp.error.ApplicationError;
import org.demo.bloggingApp.repository.BlogRepository;
import org.demo.bloggingApp.repository.UserRepository;

import java.util.List;

@Builder
@AllArgsConstructor(onConstructor_ = @Inject)
public class BlogService {

    private BlogRepository blogRepository;

    private UserRepository userRepository;

    public List<BlogEntity> getAllBlogs(final int userId) {
        return blogRepository.getAllBlogsForUserId(userId);
    }

    public void insertBlog(final BlogRequest blogDto, final int userId) {
        try {
            BlogEntity blog = BlogEntity.builder()
                    .userId(userId)
                    .title(blogDto.getTitle())
                    .content(blogDto.getContent())
                    .build();
            blogRepository.insertBlog(blog);
        } catch (Exception e) {
            throw new AppException(ApplicationError.INTERNAL_SERVER_ERROR);
        }
    }

}
