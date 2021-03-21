package com.unifun.endpoints;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unifun.orm.SecretSanta;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Random;
import java.util.logging.Logger;

import javax.json.Json;
import javax.json.JsonObject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import java.io.StringReader;
import java.util.List;


@Path("api/santa")
public class SecretSantaEndpoint {
    ObjectMapper mapper = new ObjectMapper();
    Logger logger = Logger.getLogger(String.valueOf(SecretSantaEndpoint.class));

    @GET
    @Path("/list")
    public String getParticipants() throws JsonProcessingException {
        return mapper.writeValueAsString(SecretSanta.listAll());
    }

    @GET
    @Path("getParticipant")
    public String getParticipant(@QueryParam("name") String name) throws JsonProcessingException {
        return mapper.writeValueAsString(SecretSanta.getByName(name));
    }

    @POST
    @Path("/add")
    @Transactional
    public String addParticipant(String body) throws JsonProcessingException {
        JsonObject json = Json.createReader(new StringReader(body)).readObject();
        SecretSanta secretSanta = new SecretSanta();
        secretSanta.name = json.getString("name");
        secretSanta.password = json.getString("password");
        secretSanta.persist();
        return mapper.writeValueAsString(secretSanta);
    }

    @PUT
    @Path("/edit")
    @Transactional
    public String editParticipant(String body) throws JsonProcessingException {
        JsonNode json = mapper.readTree(body);
        long id = json.get("id").asLong();
        SecretSanta secretSanta = SecretSanta.findById(id);
        secretSanta.name = json.get("name").asText();
        return mapper.writeValueAsString(secretSanta);
    }

    @DELETE
    @Transactional
    @Path("/delete")
    public boolean deleteParticipant(@QueryParam("id") long id){
        SecretSanta secretSanta = SecretSanta.findById(id);
        secretSanta.delete();
        return !secretSanta.isPersistent();
    }

    @Transactional
    public void editFlag(long id) {
        SecretSanta secretSanta = SecretSanta.findById(id);
        secretSanta.isUsed = true;
    }
    @Transactional
    public boolean santaSelected(long id) {
        SecretSanta santa = SecretSanta.findById(id);
        if (!santa.selected) {
            santa.selected = true;
            return false;
        }
        return true;
    }

    @GET
    @Path("/generate")
    public String getSecretParticipant(@QueryParam("id") long id) throws JsonProcessingException {
        List<SecretSanta> secretSantaList = SecretSanta.list("isUsed = false and id != ?1", id);
//        List<SecretSanta> secretSantaList = SecretSanta.list("id != ?1", id);
        Random random = new Random();
        int cnt = secretSantaList.size();
        if (santaSelected(id) || cnt < 1) {
            return mapper.writeValueAsString(new SecretSanta());
        }
        int number = (int) (Math.random() * (cnt));
        int x = random.nextInt(cnt);
        logger.info("Number is " + number + " x is " + x + " cnt is " + cnt);
        editFlag(secretSantaList.get(number).id);
        return mapper.writeValueAsString(secretSantaList.get(number));
    }

    @GET
    @Path("/nameExist")
    public boolean nameExist(@QueryParam("name") String name) {
        List<SecretSanta> secretSantaList = SecretSanta.listAll();
        return secretSantaList.stream().anyMatch(santa -> santa.name.equals(name));
    }
}
