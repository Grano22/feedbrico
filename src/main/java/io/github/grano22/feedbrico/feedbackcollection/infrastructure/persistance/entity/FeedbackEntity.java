package io.github.grano22.feedbrico.feedbackcollection.infrastructure.persistance.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "feedbacks", schema = "core")
public class FeedbackEntity {
    @Id
    private UUID id;

    @Column(nullable = false)
    private String authorsEmail;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String message;

    public FeedbackEntity() {}

    public FeedbackEntity(UUID id, String authorsEmail, String name, String message) {
        this.id = id;
        this.authorsEmail = authorsEmail;
        this.name = name;
        this.message = message;
    }

    public UUID getId() {
        return id;
    }
}
