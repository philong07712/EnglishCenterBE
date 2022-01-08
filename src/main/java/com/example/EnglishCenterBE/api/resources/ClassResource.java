package com.example.EnglishCenterBE.api.resources;

import com.example.EnglishCenterBE.data.ClassService;
import com.example.EnglishCenterBE.models.Account;
import com.example.EnglishCenterBE.models.Class;
import com.example.EnglishCenterBE.utils.Constants;
import com.example.EnglishCenterBE.utils.StringUtil;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Path("/class")
public class ClassResource {
    public ClassResource() {

    }

    @Path("list/{username}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getClassList(@Context HttpHeaders httpHeaders, @PathParam("username") String username) throws Exception {
        String token = httpHeaders.getHeaderString("Authorization");
        Account account = StringUtil.verifyUser(token);
        // if not that specific student then we will not show the data
        if (account.getRole().equals(Constants.Role.STUDENT)) {
            if (!account.getUsername().equals(username)) {
                return Response.status(400).build();
            }
        }
        List<Class> classes = ClassService.getClassList(username);
        if (classes == null) return Response.status(400).build();
        List<Map<String, Object>> list = new ArrayList<>();
        for (Class studentClass : classes) {
            list.add(studentClass.toJSONObject());
        }
        return Response.ok().entity(list).build();
    }

    @Path("list/teacherClass/{teacherId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getClassListTeacher(@Context HttpHeaders httpHeaders, @PathParam("teacherId") String teacherId)
            throws Exception {
        String token = httpHeaders.getHeaderString("Authorization");
        Account account = StringUtil.verifyUser(token);
        // if not that specific student then we will not show the data
        if (account.getRole().equals(Constants.Role.STUDENT)) {
            return Response.status(400).build();
        }
        List<Class> classes = ClassService.getClassListWithTeacher(teacherId);
        if (classes == null) return Response.status(400).build();
        List<Map<String, Object>> list = new ArrayList<>();
        for (Class studentClass : classes) {
            list.add(studentClass.toJSONObject());
        }
        return Response.ok().entity(list).build();
    }


    @Path("{classId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getClassDetail(@Context HttpHeaders httpHeaders, @PathParam("classId") String classId) {
        String token = httpHeaders.getHeaderString("Authorization");
        Account account = StringUtil.verifyUser(token);
        // TODO: only admin can get info of all class
        Map<String, Object> map = ClassService.getClassDetail(classId);
        if (map == null) {
            return Response.status(400).build();
        }
        return Response.ok().entity(map).build();
    }
}
