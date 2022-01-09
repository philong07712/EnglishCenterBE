package com.example.EnglishCenterBE.api.resources;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.example.EnglishCenterBE.data.AccountService;
import com.example.EnglishCenterBE.data.FirebaseDBService;
import com.example.EnglishCenterBE.models.Account;
@Path("/account")
public class AccountResource {
	
	@Path("/add")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addAccount(String jo) {
		AccountService as = AccountService.getInstance();
		
		String isSuccess = as.AddAccountService(jo);
		if(isSuccess.equals("0")) {
			return Response.status(500, "Failed").build();
		} else {
			Map<String, Object> json = new HashMap<>();
		    json.put("start", isSuccess);
			return Response.ok().entity(json).build();
		}
	}
	
	@Path("/thong-ke")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getAllAcc() {
		FirebaseDBService sv = new FirebaseDBService();
		Map<String, Object> json = new HashMap<>();
		json = sv.getAllAccount();
		return Response.ok().entity(json).build();
	}
	
	@Path("/{UserName}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getAcc(@PathParam("UserName") String UserName) throws Exception {
		Account acc = AccountService.getInstance().getAccount(UserName);
        if (acc == null) return Response.status(422).build();
        return Response.ok().entity(acc.toJSONObject(true)).build();
	}
	
	@Path("/{UserName}")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getAcc(@PathParam("UserName") String UserName,String jo) throws Exception {
		Boolean isSuccess = AccountService.getInstance().updateAccount(jo);
		if (isSuccess) return Response.ok().build();
		return Response.status(500, "Failed").build();
	}
	
	@Path("/{UserName}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteAcc(@PathParam("UserName") String UserName) throws Exception {
		Boolean isSuccess = AccountService.getInstance().deleteAccount(UserName);
		if (isSuccess) return Response.ok().build();
		return Response.status(500, "Failed").build();
	}
	
}