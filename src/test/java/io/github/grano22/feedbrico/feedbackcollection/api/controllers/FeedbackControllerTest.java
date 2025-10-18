package io.github.grano22.feedbrico.feedbackcollection.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.grano22.feedbrico.feedbackcollection.api.contract.FeedbackSubmissionRequest;
import io.github.grano22.feedbrico.feedbackcollection.api.contract.FeedbackSubmissionResponse;
import io.github.grano22.feedbrico.feedbackcollection.infrastructure.persistance.repository.WriteFeedbackRepository;
import io.github.grano22.feedbrico.feedbackcollection.infrastructure.readstore.repository.ReadFeedbackRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.assertj.MvcTestResult;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class FeedbackControllerTest {
    @Autowired
    MockMvcTester mockMvcTester;

    @Autowired
    private WriteFeedbackRepository writeFeedbackRepository;

    @PersistenceContext(unitName = "query")
    private EntityManager readEntityManager;

    @Autowired
    PlatformTransactionManager transactionManager;

    @Autowired
    private ReadFeedbackRepository readFeedbackRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        TransactionTemplate tx = new TransactionTemplate(transactionManager);
        tx.execute(status -> {
            readEntityManager.createQuery("DELETE FROM FeedbackProjection").executeUpdate();
            return null;
        });
        this.writeFeedbackRepository.deleteAll();
    }

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

    @ParameterizedTest(name="{0}")
    @MethodSource("provideInvalidFeedbackData")
    public void feedbackCannotBeSavedWhenProvidedDataIsInvalid(
        String _testTitle,
        String personName,
        String personEmail,
        String message
    ) throws Exception {
        // Arrange
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
        assertThat(result).hasStatus(HttpStatus.BAD_REQUEST);
        assertThat(readFeedbackRepository.count()).isEqualTo(0);
    }

    static Stream<Arguments> provideInvalidFeedbackData() {
        return Stream.of(
            Arguments.of("Person name is not passed", null, "test.person@exanple.com", "Test message"),
            Arguments.of("Person email is not passed", "Test Person", null, "Test message"),
            Arguments.of("Feedback message is not passed", "Test Person", "test.person@exanple.com", null),
            Arguments.of("Person name is too long", "Test Person", Strings.repeat("a", 65), "Test message"),
            Arguments.of("Person Email is too long", "Test Person", Strings.repeat("a", 60) + "test.person@exanple.com", "Test message"),
            Arguments.of("Person name is empty", "", "test.person@exanple.com", "Test message"),
            Arguments.of("Person email is empty", "Test Person", "", "Test message"),
            Arguments.of("Feedback message is empty", "Test Person", "test.person@exanple.com", ""),
            Arguments.of("Person email is invalid", "Test Person", "test.person%exanple.com", "Test message")
        );
    }
}
