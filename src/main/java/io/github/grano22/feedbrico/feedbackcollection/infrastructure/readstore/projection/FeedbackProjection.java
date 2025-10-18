package io.github.grano22.feedbrico.feedbackcollection.infrastructure.readstore.projection;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.Immutable;

import java.util.UUID;

@Entity
@Immutable
@Table(name = "feedbacks", schema = "core_projections")
public class FeedbackProjection {
    @Id
    private UUID id;

    @Column(nullable = false)
    private String authorsEmail;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String message;

    public FeedbackProjection() {}

    public FeedbackProjection(UUID id, String authorsEmail, String name, String message) {
        this.id = id;
        this.name = name;
        this.message = message;
        this.authorsEmail = authorsEmail;
    }

    public UUID id() {
        return id;
    }

    public String authorsEmail() {
        return authorsEmail;
    }

    public String name() {
        return name;
    }

    public String message() {
        return message;
    }
}
