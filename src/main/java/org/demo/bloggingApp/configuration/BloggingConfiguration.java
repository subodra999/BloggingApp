package org.demo.bloggingApp.configuration;

import io.dropwizard.core.Configuration;
import io.dropwizard.db.DataSourceFactory;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BloggingConfiguration extends Configuration {

    @Valid
    @NotNull
    private DataSourceFactory database;

}
