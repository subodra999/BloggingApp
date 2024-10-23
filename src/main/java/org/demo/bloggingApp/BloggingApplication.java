package org.demo.bloggingApp;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Environment;
import org.demo.bloggingApp.configuration.BloggingConfiguration;
import org.demo.bloggingApp.di.BloggingApplicationModule;
import org.demo.bloggingApp.di.DIModule;

public class BloggingApplication extends Application<BloggingConfiguration> {

    public static void main(String[] args) throws Exception {
        new BloggingApplication().run(args);
    }

    @Override
    public void run(BloggingConfiguration bloggingConfiguration, Environment environment) {
        Injector injector = Guice.createInjector(new DIModule(environment, bloggingConfiguration));
        new BloggingApplicationModule(environment, injector).configure();
    }
}
