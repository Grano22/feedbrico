package io.github.grano22.feedbrico.feedbackcollection.application;

import io.github.grano22.feedbrico.shared.application.ApplicationOperationFailed;

public class FailedToSaveFeedback extends ApplicationOperationFailed {
    public FailedToSaveFeedback(String message, Throwable cause) {
        super(message, cause);
    }
}
