package com.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.ExampleObject;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

@Path("/send-json")
@RolesAllowed("user")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProducerResource {

    @Inject
    ProducerService producer;

    @POST
    @Operation(summary = "Send a json to Kafka")
    @RequestBody(
            description = "JSON payload to send to Kafka",
            required = true,
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = {
                            @ExampleObject(
                                    name = "Example Payload",
                                    summary = "Sample request to Kafka",
                                    value = """
                    {
                       "information": "test_test",
                       "dateOperation": "16/09/2024",
                       "reference": 23159641,
                       "commentOperation": "test",
                       "messages": [
                          {
                             "objetMessage": "objet 1",
                             "destMessage": "destinataire 1",
                             "idAlerte": 12,
                             "textMessage": "text 1"
                          }
                       ],
                       "commande": {
                          "numCommande": "bechirtest1",
                          "numCommandeRemplace": "",
                          "optionsVeh": [
                             {
                                "libelleOption": "Peinture metallisee",
                                "numeroOption": 1,
                                "tauxTvaOption": 0,
                                "typeTvaOption": "HT",
                                "prixOptionHT": 0
                             }
                          ]
                       }
                    }
                    """
                            )
                    }
            )
    )
    @APIResponse(
            responseCode = "202",
            description = "JSON sent to Kafka successfully",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = {
                            @ExampleObject(
                                    name = "Success Response",
                                    summary = "Kafka acknowledged the message",
                                    value = """
                    {
                       "status": "success",
                       "message": "Data sent to Kafka topic successfully"
                    }
                    """
                            )
                    }
            )
    )
    @APIResponse(
            responseCode = "400",
            description = "Invalid JSON format",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = {
                            @ExampleObject(
                                    name = "Invalid JSON Error",
                                    value = """
                    Not able to deserialize data provided.
                    """
                            )
                    }
            )
    )
    @APIResponse(responseCode = "401", description = "Unauthorized access")
    @APIResponse(responseCode = "403", description = "Forbidden access")
    @APIResponse(responseCode = "500", description = "Internal server error")
    public Response send(JsonNode payload) throws JsonProcessingException {
        try{
            producer.sendJsonToKafka(payload);
            // Return a 202 - Accepted response.
            return Response.accepted(
                    """
               {
                  "status": "success",
                  "message": "Data sent to Kafka topic successfully"
               }
               """
            ).build();
        } catch (JsonProcessingException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Not able to deserialize data provided.").build();
        }
    }
}