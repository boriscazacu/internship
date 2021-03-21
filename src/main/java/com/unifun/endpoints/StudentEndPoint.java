package com.unifun.endpoints;

import com.unifun.orm.Grupa;
import com.unifun.orm.Student;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.json.Json;
import javax.json.JsonObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.StringReader;
import java.util.logging.Logger;

@Path("api/student")
public class StudentEndPoint {
    private static final Logger LOGGER = Logger.getLogger(String.valueOf(StudentEndPoint.class));
    @GET
    @Path("list")
    public String getListOfStudents() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(Student.listAll());
    }

    @DELETE
    @Path("deleteById")
    public String deleteById(@QueryParam("id") Long id){
        return Student.deleteByID(id);
    }

    @POST
    @Path("/addStudent")
    @Transactional
    public String addStudent(String body){
        JsonObject json = Json.createReader(new StringReader(body)).readObject();

		Student student = new Student(
            json.getString("firstName"),
            json.getString("lastName"),
            json.getString("sex"),
            Grupa.findById(Long.parseLong(json.getString("idGr")))
            );
        student.persist();
        LOGGER.info(student.toString());
        return student.isPersistent() ? "sa salvat" : "nu sa salvat";
    }
    @PUT
    @Path("/update")
    @Transactional
    public String updateById(String update){
        JsonObject json = Json.createReader(new StringReader(update)).readObject();

        Student student = Student.findById(Long.parseLong(json.getString("id")));
        if (student != null){
            if (student.firstName != json.getString("firstName")) {
                student.firstName = json.getString("firstName");
            }if (student.lastName != json.getString("lastName")) {
                student.lastName = json.getString("lastName");
            }if (student.sex != json.getString("sex")) {
                student.sex = json.getString("sex");
             }if (json.getString("idGr") != "0" ) {
                student.grupId = Grupa.findById(Long.parseLong(json.getString("idGr")));
            }
            return "update succes";
        }else{
            return "nu such student";
        }
    }

}
