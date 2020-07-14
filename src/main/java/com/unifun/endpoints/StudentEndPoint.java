package com.unifun.endpoints;

import com.unifun.orm.Student;

import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

@Path("/student")
public class StudentEndPoint {

    @GET
    @Path("list")
    public String getListOfStudents(){
        return Student.listAll().toString();
    }

    @GET
    @Path("deleteById")
    public String deleteById(@QueryParam("id") Long id){
        return Student.deleteByID(id);
    }

    @GET
    @Path("add")
    @Transactional
    public String addStudent(@QueryParam("fname") String fName, @QueryParam("lname") String lName,
                             @QueryParam("sex") String sex, @QueryParam("grId") Long grId){

        Student student = new Student(fName,lName,sex,grId);
        student.persist();
        return student.isPersistent() ? "sa salvat" : "nu sa salvat";
    }
    @GET
    @Path("update")
    @Transactional
    public String updateById(@QueryParam("id") Long id, @QueryParam("fname") String fName){
        Student student = Student.findById(id);
        if (student != null){
            student.firstName = fName;
            return "update succes";
        }else{
            return "nu such student";
        }
    }

}
