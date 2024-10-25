package org.acme;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.jboss.logging.Logger;


@ApplicationScoped
public class MovieProducer {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Logger logger = Logger.getLogger(MovieProducer.class);
    @Inject
    @Channel("movies-out")
    Emitter<String> emitter;

    public void sendMovieToKafka(JsonNode movie) throws JsonProcessingException {
        String movieJson = objectMapper.writeValueAsString(movie);
        logger.infof("Json movie: %s", movieJson);
        emitter.send(movieJson);
    }
}