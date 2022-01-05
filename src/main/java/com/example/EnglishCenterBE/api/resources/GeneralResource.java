package com.example.EnglishCenterBE.api.resources;

import com.example.EnglishCenterBE.data.GeneralService;
import com.example.EnglishCenterBE.models.Account;
import com.example.EnglishCenterBE.utils.RoleUtils;
import com.example.EnglishCenterBE.utils.StringUtil;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

@Path("/general")
public class GeneralResource {
    public GeneralResource() {

    }

    @Path("login/{username}/{password}")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@PathParam("username") String username, @PathParam("password") String password) {
        String token = GeneralService.login(username, password);
        if (token == null) return Response.status(400).build();
        Map<String, String> map = new HashMap<>();
        map.put("Token", token);
        return Response.ok().entity(map).build();
    }

    @Path("change/{newPassword}")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response changePassword(@Context HttpHeaders httpHeaders, @PathParam("newPassword") String newPassword) {
        String token = httpHeaders.getHeaderString("Authorization");
        Account account = StringUtil.verifyUser(token);
        boolean isSuccess = GeneralService.changePassword(account.getUsername(), newPassword);
        if (!isSuccess) {
            return Response.status(400).build();
        }
        return Response.ok().build();
    }

    @Path("info")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getInfo(@Context HttpHeaders httpHeaders) throws Exception {
        String token = httpHeaders.getHeaderString("Authorization");
        Account account = StringUtil.verifyUser(token);
        Account info = GeneralService.getInformation(account.getUsername());
        if (info == null) {
            return Response.status(400).build();
        }
        return Response.ok().entity(info.toJSONObject()).build();
    }
}
