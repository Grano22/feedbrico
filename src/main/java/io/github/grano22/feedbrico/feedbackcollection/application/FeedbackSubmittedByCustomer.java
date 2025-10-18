package io.github.grano22.feedbrico.feedbackcollection.application;

import io.github.grano22.feedbrico.shared.application.AppEvent;

import java.time.LocalDateTime;
import java.util.UUID;

public record FeedbackSubmittedByCustomer(UUID id, UUID metadata, byte[] payload, LocalDateTime occurredAt) implements AppEvent<UUID, byte[]> {}
