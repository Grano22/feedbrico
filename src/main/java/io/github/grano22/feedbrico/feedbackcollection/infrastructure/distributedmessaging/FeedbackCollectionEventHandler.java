package io.github.grano22.feedbrico.feedbackcollection.infrastructure.distributedmessaging;

import io.github.grano22.feedbrico.feedbackcollection.application.FeedbackSubmittedByCustomer;
import io.github.grano22.feedbrico.feedbackcollection.domain.CustomerFeedback;
import io.github.grano22.feedbrico.feedbackcollection.infrastructure.persistance.entity.FeedbackEntity;
import io.github.grano22.feedbrico.feedbackcollection.infrastructure.readstore.projection.FeedbackProjection;
import io.github.grano22.feedbrico.shared.infrastructure.ObjectSerializer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import java.io.IOException;

@Component
public class FeedbackCollectionEventHandler {
    @PersistenceContext(unitName = "query")
    private EntityManager readEntityManager;

    @EventListener
    @Async
    public void feedbackSubmitted(FeedbackSubmittedByCustomer event) {
        try {
            CustomerFeedback savedEntity = ObjectSerializer.deserializeFromBytes(event.payload(), CustomerFeedback.class);
            FeedbackProjection projectionToSync = new FeedbackProjection(savedEntity.id(), savedEntity.email(), savedEntity.name(), savedEntity.message());

            readEntityManager.persist(projectionToSync);
            readEntityManager.flush();
        } catch (IOException|ClassNotFoundException e) {

        }
    }
}
