package io.github.grano22.feedbrico.feedbackcollection.domain;

import java.io.Serializable;
import java.util.UUID;

public record CustomerFeedback(UUID id, String name, String email, String message) implements Serializable {}
