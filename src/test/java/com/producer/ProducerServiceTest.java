package com.producer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.junit.QuarkusTest;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@QuarkusTest
public class ProducerServiceTest {

    @InjectMocks
    ProducerService producerService;

    @Mock
    Emitter<String> emitter;


    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSendJsonToKafka() throws Exception {
        String json = "{\"name\":\"test\",\"value\":123}";
        JsonNode payload = objectMapper.readTree(json);

        boolean result=producerService.sendJsonToKafka(payload);

        // Verify if the payload is sent
        verify(emitter, times(1)).send(json);
        assertNotNull(payload, "The payload should not be null");
        assertTrue(result,"The result should be true");


    }
}