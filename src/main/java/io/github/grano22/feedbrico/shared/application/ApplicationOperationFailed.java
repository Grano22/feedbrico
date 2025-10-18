package io.github.grano22.feedbrico.shared.application;

public class ApplicationOperationFailed extends RuntimeException {
    public ApplicationOperationFailed(String message, Throwable cause) {
        super(message, cause);
    }
}
