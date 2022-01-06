package com.example.EnglishCenterBE.api.resources;

import com.example.EnglishCenterBE.data.ClassService;
import com.example.EnglishCenterBE.data.ScoreService;
import com.example.EnglishCenterBE.models.Account;
import com.example.EnglishCenterBE.models.Class;
import com.example.EnglishCenterBE.models.Score;
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

@Path("/score")
public class ScoreResource {
    public ScoreResource() {

    }

    @Path("list/{username}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getScoreList(@Context HttpHeaders httpHeaders, @PathParam("username") String username) throws Exception {
        String token = httpHeaders.getHeaderString("Authorization");
        Account account = StringUtil.verifyUser(token);
        // if not that specific student then we will not show the data
        if (account.getRole().equals(Constants.Role.STUDENT)) {
            if (!account.getUsername().equals(username)) {
                return Response.status(400).build();
            }
        }

        List<Score> scores = ScoreService.getScoreList(username);
        if (scores == null) return Response.status(400).build();
        List<Map<String, Object>> list = new ArrayList<>();
        for (Score score : scores) {
            list.add(score.toJSONObject());
        }
        return Response.ok().entity(list).build();
    }
    @Path("{username}/{classId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getScoreDetail(@Context HttpHeaders httpHeaders,
                                   @PathParam("username") String username,
                                   @PathParam("classId") String classId) throws Exception {
        String token = httpHeaders.getHeaderString("Authorization");
        Account account = StringUtil.verifyUser(token);
        // if not that specific student then we will not show the data
        if (account.getRole().equals(Constants.Role.STUDENT)) {
            if (!account.getUsername().equals(username)) {
                return Response.status(400).build();
            }
        }
        Score score = ScoreService.getScoreDetail(username, classId);
        if (score == null) return Response.status(400).build();
        return Response.ok().entity(score.toJSONObject()).build();
    }
}
