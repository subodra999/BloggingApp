package org.demo.bloggingApp;

import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Environment;
import io.dropwizard.jdbi3.JdbiFactory;
import org.demo.bloggingApp.configuration.BloggingConfiguration;
import org.demo.bloggingApp.repository.UserRepositoryImpl;
import org.demo.bloggingApp.resource.UserResource;
import org.demo.bloggingApp.service.UserService;
import org.demo.bloggingApp.utils.AppExceptionMapper;
import org.jdbi.v3.core.Jdbi;

public class BloggingApplication extends Application<BloggingConfiguration> {

    public static void main(String[] args) throws Exception {
        new BloggingApplication().run(args);
    }

    @Override
    public void run(BloggingConfiguration bloggingConfiguration, Environment environment) {

        JdbiFactory factory = new JdbiFactory();
        Jdbi jdbi = factory.build(environment, bloggingConfiguration.getDatabase(), "mysql");

        environment.jersey().register(new UserResource(new UserService(new UserRepositoryImpl(jdbi))));
        environment.jersey().register(new AppExceptionMapper());
    }
}
