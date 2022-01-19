package com.example.EnglishCenterBE.api.resources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import com.example.EnglishCenterBE.data.ManagerService;
import com.example.EnglishCenterBE.models.Account;
import com.example.EnglishCenterBE.models.Calendar;
import com.example.EnglishCenterBE.models.Class;
import com.example.EnglishCenterBE.utils.Constants;
import com.example.EnglishCenterBE.utils.StringUtil;

@Path("/manager")
public class ManagerResource {
	
	@Path("/add")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addClass(@Context HttpHeaders httpHeaders,String jo) {
		String token = httpHeaders.getHeaderString("Authorization");
        Account account = StringUtil.verifyUser(token);
        if (!account.getRole().equals(Constants.Role.MANAGER)) {
            return Response.status(400).build();
        }
		boolean isSuccess =new ManagerService().getInstance().addClass(jo);
		if(isSuccess) 
			return Response.ok().build();
		return Response.status(500, "Failed").build();
	}
	
	@Path("/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getClass(@Context HttpHeaders httpHeaders,@PathParam("id") String id) throws Exception {
		String token = httpHeaders.getHeaderString("Authorization");
        Account account = StringUtil.verifyUser(token);
        if (!account.getRole().equals(Constants.Role.MANAGER)) {
            return Response.status(400).build();
        }
		Class lop = new ManagerService().getInstance().getClass(id);
		if (lop == null) return Response.status(422).build();
		return Response.ok().entity(lop.toJSONObject()).build();
	}
	
	@Path("/{id}/updateInfor")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateClass(@Context HttpHeaders httpHeaders,@PathParam("id") String id, String jo) throws Exception {
		String token = httpHeaders.getHeaderString("Authorization");
        Account account = StringUtil.verifyUser(token);
        if (!account.getRole().equals(Constants.Role.MANAGER)) {
            return Response.status(400).build();
        }
		Boolean isSuccess = (new ManagerService()).getInstance().updateInforClass(id, jo);
		if (isSuccess) return Response.ok().build();
		return Response.status(500, "Failed").build();
	}
	
	@Path("/{id}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteClass(@Context HttpHeaders httpHeaders,@PathParam("id") String id) throws Exception {
		String token = httpHeaders.getHeaderString("Authorization");
        Account account = StringUtil.verifyUser(token);
        if (!account.getRole().equals(Constants.Role.MANAGER)) {
            return Response.status(400).build();
        }
		Boolean isSuccess = (new ManagerService()).getInstance().deleteClass(id);
		if (isSuccess) return Response.ok().build();
		return Response.status(500, "Failed").build();
	}
	
	@Path("/{id}/addStudents")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addStudents(@Context HttpHeaders httpHeaders,@PathParam("id") String id, String jo) throws Exception {
		String token = httpHeaders.getHeaderString("Authorization");
        Account account = StringUtil.verifyUser(token);
        if (!account.getRole().equals(Constants.Role.MANAGER)) {
            return Response.status(400).build();
        }
		Boolean isSuccess = (new ManagerService()).getInstance().addStudents(id, jo);
		if (isSuccess) return Response.ok().build();
		return Response.status(500, "Failed").build();
	}
	
	@Path("/{id}/deleteStudents")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteStudents(@Context HttpHeaders httpHeaders,@PathParam("id") String id, String jo) throws Exception {
		String token = httpHeaders.getHeaderString("Authorization");
        Account account = StringUtil.verifyUser(token);
        if (!account.getRole().equals(Constants.Role.MANAGER)) {
            return Response.status(400).build();
        }
		Boolean isSuccess = (new ManagerService()).getInstance().deleteStudents(id, jo);
		if (isSuccess) return Response.ok().build();
		return Response.status(500, "Failed").build();
	}
	
	@Path("/search")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response searchClass(@Context HttpHeaders httpHeaders) throws Exception {
		String token = httpHeaders.getHeaderString("Authorization");
        Account account = StringUtil.verifyUser(token);
        if (!account.getRole().equals(Constants.Role.MANAGER)) {
            return Response.status(400).build();
        }
		
		List<Class> classes;
		classes = (new ManagerService()).getInstance().getAllClass();
		
		if (classes == null) return Response.status(422).build();
		
		List<Map<String, Object>> list = new ArrayList<>();
        for (Class studentClass : classes) {
        	List<String> students = studentClass.getStudents();
        	Map<String, Object> names = new HashMap<String, Object>();
        	for(String id: students) {
        		Account acc = AccountService.getInstance().getAccount(id);
        		names.put(id, acc.getName());
        	}
        	Map<String, Object> lops = studentClass.toJSONObject();
        	lops.put("HocVien", names);
            list.add(lops);
            
        }
        
        return Response.ok().entity(list).build();
	}
	@Path("/search/{para}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response searchClass(@Context HttpHeaders httpHeaders,@PathParam("para") String para) throws Exception {
		String token = httpHeaders.getHeaderString("Authorization");
        Account account = StringUtil.verifyUser(token);
        if (!account.getRole().equals(Constants.Role.MANAGER)) {
            return Response.status(400).build();
        }
		
		List<Class> classes;
		
		classes = (new ManagerService()).getInstance().searchClassByName(para);
		
		if (classes == null) return Response.status(422).build();
		
		List<Map<String, Object>> list = new ArrayList<>();
		for (Class studentClass : classes) {
        	List<String> students = studentClass.getStudents();
        	Map<String, Object> names = new HashMap<String, Object>();
        	for(String id: students) {
        		Account acc = AccountService.getInstance().getAccount(id);
        		names.put(id, acc.getName());
        	}
        	Map<String, Object> lops = studentClass.toJSONObject();
        	lops.put("HocVien", names);
            list.add(lops);
            
        }
        return Response.ok().entity(list).build();
	}
	@Path("/getAllTeacher")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getAllTeacher(@Context HttpHeaders httpHeaders) throws Exception {
		String token = httpHeaders.getHeaderString("Authorization");
        Account account = StringUtil.verifyUser(token);
        if (!account.getRole().equals(Constants.Role.MANAGER)) {
            return Response.status(400).build();
        }
        List<Map<String, String>> list = (new ManagerService()).getInstance().getAllAccountByRole(Constants.Role.TEACHER);
		return Response.ok().entity(list).build();
	}
	
	@Path("/XemLich")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getAllTeacher(@Context HttpHeaders httpHeaders,String jo) throws Exception {
		String token = httpHeaders.getHeaderString("Authorization");
        Account account = StringUtil.verifyUser(token);
        if (!account.getRole().equals(Constants.Role.MANAGER)) {
            return Response.status(400).build();
        }
        Calendar ca = (new ManagerService()).getInstance().getCalender(jo);
		return Response.ok().entity(ca).build();
	}
}
