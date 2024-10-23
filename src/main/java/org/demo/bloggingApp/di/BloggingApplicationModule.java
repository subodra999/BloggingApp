package org.demo.bloggingApp.di;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import io.dropwizard.core.setup.Environment;
import lombok.AllArgsConstructor;
import org.demo.bloggingApp.controller.BlogController;
import org.demo.bloggingApp.filter.request.LoggingFilter;
import org.demo.bloggingApp.filter.request.UserIdFilter;
import org.demo.bloggingApp.controller.UserController;
import org.demo.bloggingApp.filter.response.ResponseHeaderFilter;
import org.demo.bloggingApp.utils.AppExceptionMapper;

@AllArgsConstructor
public class BloggingApplicationModule extends AbstractModule {

    private final Environment environment;

    private final Injector injector;

    @Override
    public void configure() {
       registerControllers();
       addFilters();
    }

    private void registerControllers() {
        environment.jersey().register(injector.getInstance(UserController.class));
        environment.jersey().register(injector.getInstance(BlogController.class));
        environment.jersey().register(injector.getInstance(AppExceptionMapper.class));
        environment.jersey().register(injector.getInstance(ResponseHeaderFilter.class));
    }

    private void addFilters() {

        environment.servlets()
                .addFilter("LoggingFilter", injector.getInstance(LoggingFilter.class))
                .addMappingForUrlPatterns(null, true, "/*");

        environment.servlets()
                .addFilter("UserIdFilter", injector.getInstance(UserIdFilter.class))
                .addMappingForUrlPatterns(null, true, "/api/*");
    }
}
