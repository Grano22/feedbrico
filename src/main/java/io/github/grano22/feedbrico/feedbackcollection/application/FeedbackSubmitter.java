package io.github.grano22.feedbrico.feedbackcollection.application;

import io.github.grano22.feedbrico.feedbackcollection.domain.CustomerFeedback;
import io.github.grano22.feedbrico.feedbackcollection.infrastructure.persistance.entity.FeedbackEntity;
import io.github.grano22.feedbrico.feedbackcollection.infrastructure.persistance.repository.WriteFeedbackRepository;
import io.github.grano22.feedbrico.shared.infrastructure.ObjectSerializer;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class FeedbackSubmitter {
    private final WriteFeedbackRepository writeFeedbackRepository;
    private final ApplicationEventPublisher eventPublisher;

    public FeedbackSubmitter(WriteFeedbackRepository writeFeedbackRepository, ApplicationEventPublisher eventPublisher) {
        this.writeFeedbackRepository = writeFeedbackRepository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional(transactionManager = "commandTransactionManager")
    public void submit(@NonNull CustomerFeedback feedback) {
        try {
            FeedbackEntity feedbackEntity = new FeedbackEntity(
                feedback.id(),
                feedback.email(),
                feedback.name(),
                feedback.message()
            );

            writeFeedbackRepository.saveAndFlush(feedbackEntity);
            eventPublisher.publishEvent(
                new FeedbackSubmittedByCustomer(
                    UUID.randomUUID(),
                    feedbackEntity.getId(),
                    ObjectSerializer.serializeToBytes(feedback),
                    LocalDateTime.now()
                )
            );
        } catch (IOException e) {
            throw new FailedToSaveFeedback(e.getMessage(), e);
        }
    }
}
