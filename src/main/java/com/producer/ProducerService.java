package com.producer;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.jboss.logging.Logger;



@ApplicationScoped
public class ProducerService {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Logger logger = Logger.getLogger(ProducerService.class);
    @Inject
    @Channel("json-out")
    Emitter<String> emitter;

    public boolean sendJsonToKafka(JsonNode payload) throws JsonProcessingException {
        String payloadJson = objectMapper.writeValueAsString(payload);
        logger.infof("Json payload: %s", payloadJson);
        emitter.send(payloadJson);
        return true;
    }
}