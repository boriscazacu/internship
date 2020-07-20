package com.unifun.endpoints;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unifun.orm.Grupa;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

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
}
