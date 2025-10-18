package io.github.grano22.feedbrico.feedbackcollection.api.contract;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record FeedbackSubmissionRequest(
    @NotNull @NotEmpty @Size(max=64) String name,
    @NotNull @NotEmpty @Email @Size(max=64) String email,
    @NotNull @NotEmpty @Size(max=65535) String message
) {}