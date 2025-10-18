package io.github.grano22.feedbrico.feedbackcollection.application;

import io.github.grano22.feedbrico.feedbackcollection.domain.CustomerFeedback;
import io.github.grano22.feedbrico.feedbackcollection.infrastructure.persistance.entity.FeedbackEntity;
import io.github.grano22.feedbrico.feedbackcollection.infrastructure.persistance.repository.WriteFeedbackRepository;
import io.github.grano22.feedbrico.shared.infrastructure.ObjectSerializer;
import io.github.grano22.feedbrico.shared.infrastructure.logging.LoggerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class FeedbackSubmitter {
    private static final Logger logger = LoggerFactory.getLogger(FeedbackSubmitter.class);

    private final WriteFeedbackRepository writeFeedbackRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final Clock clock;

    public FeedbackSubmitter(WriteFeedbackRepository writeFeedbackRepository, ApplicationEventPublisher eventPublisher, Clock clock) {
        this.writeFeedbackRepository = writeFeedbackRepository;
        this.eventPublisher = eventPublisher;
        this.clock = clock;
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
                    LocalDateTime.now(clock)
                )
            );

            LoggerContext.applicationService()
                .nextMessage("Feedback submitted")
                .nextMessage("Feedback id: " + feedbackEntity.getId())
                .nextMessage("Feedback title: " + feedback.name())
                .log(logger::info)
            ;
        } catch (IOException e) {
            LoggerContext.applicationService()
                .nextMessage("Failed to save feedback")
                .log(logger::error)
            ;

            throw new FailedToSaveFeedback("Failed to send feedback, please try again later", e);
        }
    }
}
