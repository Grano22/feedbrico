package io.github.grano22.feedbrico.feedbackcollection.infrastructure.readstore.repository;

import io.github.grano22.feedbrico.feedbackcollection.infrastructure.readstore.projection.FeedbackProjection;
import io.github.grano22.feedbrico.shared.infrastructure.readstore.repository.ReadRepository;

import java.util.UUID;

public interface ReadFeedbackRepository extends ReadRepository<FeedbackProjection, UUID> {
}
