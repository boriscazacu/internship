package com.unifun.endpoints;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unifun.orm.Grupa;
import com.unifun.orm.Student;

import javax.json.Json;
import javax.json.JsonObject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import java.io.StringReader;

@Path("api/grup")
public class GrupEndPoint {

    @GET
    @Path("list")
    public String getGrupList() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(Grupa.listAll());
    }

    @GET
    @Path("listById")
    public String getGrupListById(@QueryParam("id") Long id) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(Grupa.findById(id));
    }

    @POST
    @Path("/add")
    @Transactional
    public String addGrup(String body){
        JsonObject json = Json.createReader(new StringReader(body)).readObject();

        Grupa grupa = new Grupa(
                json.getString("grupName"),
                json.getString("grupMentor")
        );
        grupa.persist();
        return grupa.isPersistent() ? "sa salvat" : "nu sa salvat";
    }

    @PUT
    @Path("/update")
    @Transactional
    public String update(String update){
        JsonObject json = Json.createReader(new StringReader(update)).readObject();

        Grupa grupa = Grupa.findById(Long.parseLong(json.getString("grupId")));
        if (grupa != null){
            if (grupa.grupName != json.getString("grupName")) {
                grupa.grupName = json.getString("grupName");
            }if (grupa.grupMentor != json.getString("grupMentor")) {
                grupa.grupMentor = json.getString("grupMentor");
            }
            return "update succes";
        }else{
            return "nu such student";
        }
    }

    @DELETE
    @Path("deleteById")
    public String deleteById(@QueryParam("id") Long id){
        return Grupa.deleteByID(id);
    }

}
