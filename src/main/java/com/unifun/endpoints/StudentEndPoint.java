package com.unifun.endpoints;

import com.unifun.orm.Grupa;
import com.unifun.orm.Student;

import javax.persistence.PostUpdate;
import javax.transaction.Transactional;
import javax.ws.rs.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Path("api/student")
public class StudentEndPoint {

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
    @Path("add")
    @Transactional
    public String addStudent( @QueryParam("fname") String fName, @QueryParam("lname") String lName,
                             @QueryParam("sex") String sex, @QueryParam("grId") Long grId){

        Grupa grupa = Grupa.findById(grId);
        Student student = new Student(fName,lName,sex,grupa);
        student.persist();
        return student.isPersistent() ? "sa salvat" : "nu sa salvat";
    }
    @PUT
    @Path("update")
    @Transactional
    public String updateById(@QueryParam("id") Long id, @QueryParam("fname") String fName,
                             @QueryParam("lname") String lName, @QueryParam("sex") String sex){
        Student student = Student.findById(id);
        if (student != null){
            student.firstName = fName;
            student.lastName = lName;
            student.sex = sex;
            student.persist();
            return "update succes";
        }else{
            return "nu such student";
        }
    }

}
