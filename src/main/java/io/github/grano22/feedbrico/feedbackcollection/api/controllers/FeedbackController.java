package io.github.grano22.feedbrico.feedbackcollection.api.controllers;

import io.github.grano22.feedbrico.feedbackcollection.api.contract.FeedbackSubmissionRequest;
import io.github.grano22.feedbrico.feedbackcollection.api.contract.FeedbackSubmissionResponse;
import io.github.grano22.feedbrico.feedbackcollection.application.FeedbackSubmitter;
import io.github.grano22.feedbrico.feedbackcollection.domain.CustomerFeedback;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/feedback/v1/")
public class FeedbackController {
    private final FeedbackSubmitter feedbackSubmitter;

    public FeedbackController(FeedbackSubmitter feedbackSubmitter) {
        this.feedbackSubmitter = feedbackSubmitter;
    }

    @PostMapping
    public FeedbackSubmissionResponse submit(@RequestBody @Validated FeedbackSubmissionRequest request) {
        var feedback = new CustomerFeedback(UUID.randomUUID(), request.name(), request.email(), request.message());

        this.feedbackSubmitter.submit(feedback);

        return new FeedbackSubmissionResponse(feedback.id(), feedback.name(), feedback.message());
    }
}
