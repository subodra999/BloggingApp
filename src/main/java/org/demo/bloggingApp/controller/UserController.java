package org.demo.bloggingApp.controller;

import com.google.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import lombok.AllArgsConstructor;
import org.demo.bloggingApp.dto.request.UserRequest;
import org.demo.bloggingApp.service.UserService;

@Path("/user")
@AllArgsConstructor(onConstructor_ = { @Inject})
@Consumes (MediaType.APPLICATION_JSON)
@Produces (MediaType.APPLICATION_JSON)
public class UserController {

    private UserService userService;

    @Path("register")
    @POST
    public Object register(final UserRequest userDto) {
        return userService.register(userDto);
    }

    @Path("login")
    @POST
    public Object login(final UserRequest userDto) {
        return userService.login(userDto);
    }
}
