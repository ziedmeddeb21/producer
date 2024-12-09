package com.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.quarkus.test.junit.QuarkusTest;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@QuarkusTest
public class ProducerServiceTest {



//    @InjectMocks
//    ProducerService producerService;
//
//    @Mock
//    Emitter<String> emitter;
//
//    @Mock
//    ObjectMapper objectMapper; // Mocked ObjectMapper for better control
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//    @Test
//    public void testSendJsonToKafka() throws Exception {
//        String json = "{\"name\":\"test\",\"value\":123}";
//        JsonNode payload = objectMapper.readTree(json);
//        String collectionName = "testCollection";
//
//        boolean result = producerService.sendJsonToKafka(payload, collectionName);
//
//        // Verify if the payload is sent
//        verify(emitter, times(1)).send(anyString());
//        assertTrue(result, "The result should be true");
//    }
//
//
//
//
//    @Test
//    public void testSendJsonToKafkaException() throws Exception {
//        JsonNode payload = mock(JsonNode.class);
//        String collectionName = "testCollection";
//
//        when(objectMapper.writeValueAsString(payload)).thenThrow(new JsonProcessingException("Test Exception") {});
//
//        boolean result = producerService.sendJsonToKafka(payload, collectionName);
//
//        assertFalse(result, "The result should be false");
//        verifyNoInteractions(emitter); // Ensure emitter is not called
//    }
//
//    @Test
//    public void testReceiveTransformedPayload() throws Exception {
//        // Arrange
//        String json = "{\"name\":\"test\",\"value\":123}";
//        JsonNode expectedPayload = mock(JsonNode.class); // Mock the expected payload
//
//        // Mock the ObjectMapper's behavior to return the expected JsonNode
//        ObjectMapper objectMapper = mock(ObjectMapper.class);
//        when(objectMapper.readTree(json)).thenReturn(expectedPayload);
//
//        // Mock the CompletableFuture so it completes immediately with the expected payload
//        CompletableFuture<Object> mockedFuture = mock(CompletableFuture.class);
//        when(mockedFuture.get()).thenReturn(expectedPayload);  // Mock future to return expected payload
//
//        // Mock the producerService and inject the mocked ObjectMapper and future
//        ProducerService producerService = mock(ProducerService.class);
//        producerService.transformedPayloadFuture = mockedFuture;  // Directly set the mocked future
//        producerService.objectMapper = objectMapper;  // Inject the mocked objectMapper
//
//        // Act: Call the receiveTransformedPayload method (which will use the mocked ObjectMapper)
//        producerService.receiveTransformedPayload(json);
//
//        // Assert: Verify that the result from getTransformedPayload() matches the expected payload
//        Object result = producerService.getTransformedPayload();
//        assertEquals(expectedPayload, result, "The payload should match the received payload");
//    }
//
//
//
//
//    @Test
//    public void testReceiveTransformedPayloadException() throws Exception {
//        String invalidJson = "invalid json";
//
//        when(objectMapper.readTree(invalidJson)).thenThrow(new JsonProcessingException("Test Exception") {});
//
//        producerService.receiveTransformedPayload(invalidJson);
//
//        Object result = producerService.getTransformedPayload();
//        assertEquals("Failed to process the mapping", result, "The result should indicate a mapping failure");
//    }
//
//    @Test
//    public void testGetTransformedPayloadException() throws Exception {
//        CompletableFuture<Object> future = mock(CompletableFuture.class);
//        producerService.transformedPayloadFuture = future; // Assuming direct assignment for test purposes
//
//        when(future.get()).thenThrow(new RuntimeException("Unexpected error"));
//
//        Object result = producerService.getTransformedPayload();
//
//        assertTrue(result instanceof String, "The result should be an error message");
//        assertEquals("Unexpected error", result, "The error message should match the exception");
//    }
}