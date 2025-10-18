package io.github.grano22.feedbrico.feedbackcollection.api.contract;

import java.util.UUID;

public record FeedbackSubmissionResponse(UUID id, String name, String message) {}
