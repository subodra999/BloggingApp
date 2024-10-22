package org.demo.bloggingApp.di;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import io.dropwizard.core.setup.Environment;
import io.dropwizard.jdbi3.JdbiFactory;
import lombok.AllArgsConstructor;
import org.demo.bloggingApp.configuration.BloggingConfiguration;
import org.demo.bloggingApp.repository.UserRepository;
import org.demo.bloggingApp.repository.UserRepositoryImpl;
import org.jdbi.v3.core.Jdbi;

@AllArgsConstructor
public class DIModule extends AbstractModule {

    private final Environment environment;

    private final BloggingConfiguration configuration;

    @Override
    protected void configure() {
        super.configure();
        bind(UserRepository.class).to(UserRepositoryImpl.class).asEagerSingleton();
    }

    @Provides
    @Singleton
    public Jdbi provideJdbi() {
        JdbiFactory factory = new JdbiFactory();
        return factory.build(environment, configuration.getDatabase(), "mysql");
    }
}
