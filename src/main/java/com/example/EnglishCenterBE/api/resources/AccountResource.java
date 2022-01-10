package com.example.EnglishCenterBE.api.resources;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.example.EnglishCenterBE.data.AccountService;
import com.example.EnglishCenterBE.data.FirebaseDBService;
import com.example.EnglishCenterBE.models.Account;
import com.example.EnglishCenterBE.utils.Constants;
import com.example.EnglishCenterBE.utils.StringUtil;
@Path("/account")
public class AccountResource {
	
	@Path("/add")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addAccount(@Context HttpHeaders httpHeaders,String jo) {
		
		String token = httpHeaders.getHeaderString("Authorization");
        Account account = StringUtil.verifyUser(token);
        if (!account.getRole().equals(Constants.Role.ADMIN)) {
            return Response.status(400).build();
        }
        
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
	public Response getAllAcc(@Context HttpHeaders httpHeaders) {
		String token = httpHeaders.getHeaderString("Authorization");
        Account account = StringUtil.verifyUser(token);
        if (!account.getRole().equals(Constants.Role.ADMIN)) {
            return Response.status(400).build();
        }
		FirebaseDBService sv = new FirebaseDBService();
		Map<String, Object> json = new HashMap<>();
		json = sv.getAllAccount();
		return Response.ok().entity(json).build();
	}
	
	@Path("/{UserName}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getAcc(@Context HttpHeaders httpHeaders,@PathParam("UserName") String UserName) throws Exception {
		String token = httpHeaders.getHeaderString("Authorization");
        Account account = StringUtil.verifyUser(token);
        if (!account.getRole().equals(Constants.Role.ADMIN)) {
            return Response.status(400).build();
        }
		Account acc = AccountService.getInstance().getAccount(UserName);
        if (acc == null) return Response.status(422).build();
        return Response.ok().entity(acc.toJSONObject(true)).build();
	}
	
	@Path("/{UserName}")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getAcc(@Context HttpHeaders httpHeaders,@PathParam("UserName") String UserName,String jo) throws Exception {
		String token = httpHeaders.getHeaderString("Authorization");
        Account account = StringUtil.verifyUser(token);
        if (!account.getRole().equals(Constants.Role.ADMIN)) {
            return Response.status(400).build();
        }
		Boolean isSuccess = AccountService.getInstance().updateAccount(jo);
		if (isSuccess) return Response.ok().build();
		return Response.status(500, "Failed").build();
	}
	
	@Path("/{UserName}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteAcc(@Context HttpHeaders httpHeaders,@PathParam("UserName") String UserName) throws Exception {
		String token = httpHeaders.getHeaderString("Authorization");
        Account account = StringUtil.verifyUser(token);
        if (!account.getRole().equals(Constants.Role.ADMIN)) {
            return Response.status(400).build();
        }
		Boolean isSuccess = AccountService.getInstance().deleteAccount(UserName);
		if (isSuccess) return Response.ok().build();
		return Response.status(500, "Failed").build();
	}
	
}