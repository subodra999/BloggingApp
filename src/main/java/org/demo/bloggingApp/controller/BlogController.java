package org.demo.bloggingApp.controller;

import com.google.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import lombok.AllArgsConstructor;
import org.demo.bloggingApp.dto.request.BlogRequest;
import org.demo.bloggingApp.service.BlogService;

@Path("/api/blog")
@AllArgsConstructor(onConstructor_ = @Inject)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BlogController {

    private final BlogService blogService;

    @Path("add")
    @POST
    public Object add(@HeaderParam("X-User-Id") int userId, final BlogRequest blogDto) {
        blogService.insertBlog(blogDto, userId);
        return "Blog added successfully";
    }

    @Path("getAll")
    @GET
    public Object getAll(@HeaderParam("X-User-Id") int userId) {
        return blogService.getAllBlogs(userId);
    }
}
