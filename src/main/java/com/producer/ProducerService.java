package com.producer;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


@ApplicationScoped
public class ProducerService {
    ObjectMapper objectMapper = new ObjectMapper();
    private final Logger logger = Logger.getLogger(ProducerService.class);
    CompletableFuture<Object> transformedPayloadFuture = new CompletableFuture<>();
    @Inject
    @Channel("json-out")
    Emitter<String> emitter;


    public boolean sendJsonToKafka(JsonNode payload, String collectionName) {
        try {
            ObjectNode combinedPayload = objectMapper.createObjectNode();
            combinedPayload.set("content", payload);
            combinedPayload.put("collectionName", collectionName);

            String payloadJson = objectMapper.writeValueAsString(combinedPayload);
            logger.infof("Json payload: %s", payloadJson);
            emitter.send(payloadJson);

            return true;
        } catch (JsonProcessingException e) {
            logger.error("Failed to process JSON", e);
            return false;
        }
    }

    @Incoming("json-in-output")
    public void receiveTransformedPayload(String payload) {
        try {
            JsonNode payloadJson = objectMapper.readTree(payload);
            logger.infof("Received transformed payload: %s", payloadJson);
            transformedPayloadFuture.complete(payloadJson);
        } catch (Exception e) {
            logger.error("Failed to process received payload", e);
            transformedPayloadFuture.complete("Failed to process the mapping");
        } finally {

            transformedPayloadFuture = new CompletableFuture<>();
        }
    }

    public Object getTransformedPayload() {
        try {
            return transformedPayloadFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            logger.error("Failed to get transformed payload", e);
            return e.getMessage();
        }
    }
}