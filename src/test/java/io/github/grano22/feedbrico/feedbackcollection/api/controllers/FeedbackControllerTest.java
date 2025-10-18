package io.github.grano22.feedbrico.feedbackcollection.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.grano22.feedbrico.feedbackcollection.api.contract.FeedbackSubmissionRequest;
import io.github.grano22.feedbrico.feedbackcollection.api.contract.FeedbackSubmissionResponse;
import io.github.grano22.feedbrico.feedbackcollection.infrastructure.persistance.repository.WriteFeedbackRepository;
import io.github.grano22.feedbrico.feedbackcollection.infrastructure.readstore.repository.ReadFeedbackRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.assertj.MvcTestResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class FeedbackControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    MockMvcTester mockMvcTester;

    @Autowired
    private WriteFeedbackRepository writeFeedbackRepository;

    @Autowired
    private ReadFeedbackRepository readFeedbackRepository;

    @Autowired
    private ObjectMapper objectMapper;

//    @BeforeEach
//    public void setup() {
//        this.writeFeedbackRepository.deleteAll();
//    }

    @Test
    public void feedbackWasSavedAnd200tResponseWasGivenWhenProvidedDataWasValid() throws Exception {
        // Arrange
        String personName = "Test User";
        String personEmail = "test.user@example.com";
        String message = "Test message";
        FeedbackSubmissionRequest request = new FeedbackSubmissionRequest(personName, personEmail, message);
        String requestAsJson = objectMapper.writeValueAsString(request);

        // Act
        MvcTestResult result = mockMvcTester.post()
            .uri("/api/feedback/v1/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestAsJson)
            .exchange()
        ;

        // Assert
        assertThat(result)
            .hasStatus(HttpStatus.OK)
            .bodyJson().convertTo(FeedbackSubmissionResponse.class)
            .satisfies(response -> {
                assertThat(response.name()).isEqualTo(personName);
                assertThat(response.message()).isEqualTo(message);
            })
        ;
        assertThat(readFeedbackRepository.count()).isEqualTo(1);
    }
}
