package com.unifun.endpoints;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unifun.orm.Results;
import com.fasterxml.jackson.databind.JsonNode;
import com.unifun.orm.SecretSanta;

import javax.json.Json;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import java.io.StringReader;

@Path("api/results")
public class ResultsEndpoint {
    ObjectMapper mapper = new ObjectMapper();

    @GET
    @Path("/list")
    public String getParticipants() throws JsonProcessingException {
        return mapper.writeValueAsString(Results.listAll());
    }

    @GET
    @Path("/getSanta")
    public String getBySanta(@QueryParam("name") String name) throws JsonProcessingException {
        return mapper.writeValueAsString(Results.find("secretSanta", name).firstResult());
    }

    @POST
    @Path("/add")
    @Transactional
    public String addParticipant(String body) throws JsonProcessingException {
        JsonNode json = mapper.readTree(body);
        Results result = new Results();
        result.secretSanta = json.get("secretSanta").asText();
        result.participant = json.get("participant").asText();
        result.persist();
        return mapper.writeValueAsString(result);
    }

    @DELETE
    @Transactional
    @Path("/delete")
    public boolean deleteResult(@QueryParam("id") long id) {
        Results result = Results.findById(id);
        resetFlags(result);
        result.delete();
        return !result.isPersistent();
    }

    @Transactional
    public void resetFlags(Results result) {
        SecretSanta santa = SecretSanta.getByName(result.secretSanta);
        santa.selected = false;
        SecretSanta participant = SecretSanta.getByName(result.participant);
        participant.isUsed = false;
    }

    @PUT
    @Path("/edit")
    @Transactional
    public String editParticipant(String body) throws JsonProcessingException {
        JsonNode json = mapper.readTree(body);
        long id = json.get("id").asLong();
        Results result = Results.findById(id);
        result.secretSanta = json.get("secretSanta").asText();
        result.participant = json.get("participant").asText();
        return mapper.writeValueAsString(result);
    }
}
