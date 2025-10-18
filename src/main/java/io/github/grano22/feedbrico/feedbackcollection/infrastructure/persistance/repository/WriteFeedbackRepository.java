package io.github.grano22.feedbrico.feedbackcollection.infrastructure.persistance.repository;

import io.github.grano22.feedbrico.feedbackcollection.infrastructure.persistance.entity.FeedbackEntity;
import io.github.grano22.feedbrico.shared.infrastructure.persistance.repository.WriteRepository;

import java.util.UUID;

public interface WriteFeedbackRepository extends WriteRepository<FeedbackEntity, UUID> {
}
