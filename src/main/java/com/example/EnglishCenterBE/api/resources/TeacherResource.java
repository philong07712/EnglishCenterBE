package com.example.EnglishCenterBE.api.resources;

import com.example.EnglishCenterBE.data.ClassService;
import com.example.EnglishCenterBE.data.GeneralService;
import com.example.EnglishCenterBE.data.ScoreService;
import com.example.EnglishCenterBE.data.TeacherService;
import com.example.EnglishCenterBE.models.Account;
import com.example.EnglishCenterBE.models.Class;
import com.example.EnglishCenterBE.models.Score;
import com.example.EnglishCenterBE.utils.Constants;
import com.example.EnglishCenterBE.utils.StringUtil;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/teacher")
public class TeacherResource {
    public TeacherResource() {

    }

    @Path("/class/{classId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getClassList(@Context HttpHeaders httpHeaders,
                                 @PathParam("classId") String classId) throws Exception {
        String token = httpHeaders.getHeaderString("Authorization");
        Account account = StringUtil.verifyUser(token);
        // if not that specific student then we will not show the data
        if (account.getRole().equals(Constants.Role.STUDENT)) {
            return Response.status(400).build();
        }
        Map<String, Object> students = TeacherService.getClassDetail(classId);
        if (students == null) return Response.status(400).build();
        return Response.ok().entity(students).build();
    }

    @Path("info/{username}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getInfo(@Context HttpHeaders httpHeaders, @PathParam("username") String username) throws Exception {
        String token = httpHeaders.getHeaderString("Authorization");
        Account account = StringUtil.verifyUser(token);
        if (account.getRole().equals(Constants.Role.STUDENT)) {
            return Response.status(400).build();
        }
        Account info = GeneralService.getInformation(username);
        if (info == null) {
            return Response.status(400).build();
        }
        return Response.ok().entity(info.toJSONObject(true)).build();
    }

    @Path("search/ma_lop/{classId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchClass(@Context HttpHeaders httpHeaders, @PathParam("classId") String classId) throws Exception {
        String token = httpHeaders.getHeaderString("Authorization");
        Account account = StringUtil.verifyUser(token);
        if (account.getRole().equals(Constants.Role.STUDENT)) {
            return Response.status(400).build();
        }
        if (classId.equals("null")) {
            classId = "";
        }
        List<Class> classes = TeacherService.searchByClassId(classId);
        List<Map<String, Object>> list = new ArrayList<>();
        for (Class studentClass : classes) {
            list.add(studentClass.toJSONObject());
        }
        return Response.ok().entity(list).build();
    }

    @Path("search/ma_hv/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchStudentById(@Context HttpHeaders httpHeaders, @PathParam("id") String studentId) throws Exception {
        String token = httpHeaders.getHeaderString("Authorization");
        Account account = StringUtil.verifyUser(token);
        if (account.getRole().equals(Constants.Role.STUDENT)) {
            return Response.status(400).build();
        }
        if (studentId.equals("null")) {
            studentId = "";
        }
        List<Account> accounts = TeacherService.searchByStudentId(studentId);
        List<Map<String, Object>> list = new ArrayList<>();
        for (Account studentAccount : accounts) {
            list.add(studentAccount.toJSONObject(true));
        }
        return Response.ok().entity(list).build();
    }

    @Path("search/ten_hv/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchStudentByName(@Context HttpHeaders httpHeaders, @PathParam("id") String studentId) throws Exception {
        String token = httpHeaders.getHeaderString("Authorization");
        Account account = StringUtil.verifyUser(token);
        if (account.getRole().equals(Constants.Role.STUDENT)) {
            return Response.status(400).build();
        }
        if (studentId.equals("null")) {
            studentId = "";
        }
        List<Account> accounts = TeacherService.searchByStudentName(studentId);
        List<Map<String, Object>> list = new ArrayList<>();
        for (Account studentAccount : accounts) {
            list.add(studentAccount.toJSONObject(true));
        }
        return Response.ok().entity(list).build();
    }

    @Path("/score")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)

    public Response addScores(@Context HttpHeaders httpHeaders, String json) {
        String token = httpHeaders.getHeaderString("Authorization");
        Account account = StringUtil.verifyUser(token);
        // if not that specific student then we will not show the data
        if (account.getRole().equals(Constants.Role.STUDENT)) {
            return Response.status(400).build();
        }
        Map<String, Object> map = parseScoreFormJSON(json);
        if (map == null) {
            return Response.status(400).build();
        }
        String classId = (String) map.get("MaLop");
        String className = (String) map.get("TenLop");
        List<Score> scores = (List<Score>) map.get("HocVien");
        boolean isSuccess = TeacherService.addScores(classId, className, scores);
        if (!isSuccess) {
            return Response.status(400).build();
        }
        return Response.ok().build();
    }

    private Map<String, Object> parseScoreFormJSON(String json) {
        Map<String, Object> map = new HashMap<>();
        try {
            JSONObject obj = (JSONObject) new JSONParser().parse(json);
            String classId = (String) obj.get("MaLop");
            String className = (String) obj.get("TenLop");

            JSONArray joScores = (JSONArray) obj.get("HocVien");
            List<Score> scoreList = new ArrayList<>();
            for (Object scoreObj : joScores) {
                Score score = Score.JSONParser((JSONObject) scoreObj);
                scoreList.add(score);
            }
            map.put("HocVien", scoreList);
            map.put("MaLop", classId);
            map.put("TenLop", className);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
