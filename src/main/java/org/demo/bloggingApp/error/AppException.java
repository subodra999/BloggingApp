package org.demo.bloggingApp.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppException extends RuntimeException {

    private final ApplicationError error;

    public AppException(ApplicationError error) {
        super(error.getMessage());
        this.error = error;
    }

    public ApplicationError getError() {
        return error;
    }
}
