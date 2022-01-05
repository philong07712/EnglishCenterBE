package com.example.EnglishCenterBE.api.resources;

import com.example.EnglishCenterBE.data.StudentService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

//@Path("/student")
//public class StudentResource {
//    public StudentResource() {
//
//    }
//
//    @Path("/{studentId}")
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response getStudent(@PathParam("studentId") String id) throws Exception {
//        Student student = StudentService.getInstance().getStudent(id);
//        if (student == null) return Response.status(422).build();
//        return Response.ok().entity(student.toJSONObject()).build();
//    }
//}
