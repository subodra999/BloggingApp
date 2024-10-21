package org.demo.bloggingApp.di;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import io.dropwizard.core.setup.Environment;
import lombok.AllArgsConstructor;
import org.demo.bloggingApp.resource.UserResource;
import org.demo.bloggingApp.utils.AppExceptionMapper;

@AllArgsConstructor
public class BloggingApplicationModule extends AbstractModule {

    private final Environment environment;

    private final Injector injector;

    @Override
    public void configure() {
        environment.jersey().register(injector.getInstance(UserResource.class));
        environment.jersey().register(injector.getInstance(AppExceptionMapper.class));
    }
}
