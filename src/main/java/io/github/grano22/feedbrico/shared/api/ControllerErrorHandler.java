package io.github.grano22.feedbrico.shared.api;

import io.github.grano22.feedbrico.shared.application.ApplicationOperationFailed;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerErrorHandler {
    public record SimpleJsonErrorMessage(String message) {}

    @ExceptionHandler(ApplicationOperationFailed.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public SimpleJsonErrorMessage handleApplicationOperationFailed(ApplicationOperationFailed ex) {
        return new SimpleJsonErrorMessage(ex.getMessage());
    }
}
