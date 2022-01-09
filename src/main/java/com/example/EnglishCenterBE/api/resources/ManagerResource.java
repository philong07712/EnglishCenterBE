package com.example.EnglishCenterBE.api.resources;

import java.util.ArrayList;
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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.example.EnglishCenterBE.data.ManagerService;
import com.example.EnglishCenterBE.models.Class;

@Path("/manager")
public class ManagerResource {
	
	@Path("/add")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addClass(String jo) {
		boolean isSuccess =new ManagerService().getInstance().addClass(jo);
		if(isSuccess) 
			return Response.ok().build();
		return Response.status(500, "Failed").build();
	}
	
	@Path("/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getClass(@PathParam("id") String id) throws Exception {
		Class lop = new ManagerService().getInstance().getClass(id);
		if (lop == null) return Response.status(422).build();
		return Response.ok().entity(lop.toJSONObject()).build();
	}
	
	@Path("/{id}/updateInfor")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateClass(@PathParam("id") String id, String jo) throws Exception {
		Boolean isSuccess = (new ManagerService()).getInstance().updateInforClass(id, jo);
		if (isSuccess) return Response.ok().build();
		return Response.status(500, "Failed").build();
	}
	
	@Path("/{id}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteClass(@PathParam("id") String id) throws Exception {
		Boolean isSuccess = (new ManagerService()).getInstance().deleteClass(id);
		if (isSuccess) return Response.ok().build();
		return Response.status(500, "Failed").build();
	}
	
	@Path("/{id}/addStudents")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addStudents(@PathParam("id") String id, String jo) throws Exception {
		Boolean isSuccess = (new ManagerService()).getInstance().addStudents(id, jo);
		if (isSuccess) return Response.ok().build();
		return Response.status(500, "Failed").build();
	}
	
	@Path("/{id}/deleteStudents")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteStudents(@PathParam("id") String id, String jo) throws Exception {
		Boolean isSuccess = (new ManagerService()).getInstance().deleteStudents(id, jo);
		if (isSuccess) return Response.ok().build();
		return Response.status(500, "Failed").build();
	}
	
	@Path("/search")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response searchClass() throws Exception {
		List<Class> classes;
		classes = (new ManagerService()).getInstance().getAllClass();
		
		if (classes == null) return Response.status(422).build();
		
		List<Map<String, Object>> list = new ArrayList<>();
        for (Class studentClass : classes) {
            list.add(studentClass.toJSONObject());
        }
        return Response.ok().entity(list).build();
	}
	@Path("/search/{para}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response searchClass(@PathParam("para") String para) throws Exception {
		List<Class> classes;
		
		classes = (new ManagerService()).getInstance().searchClassByName(para);
		
		if (classes == null) return Response.status(422).build();
		
		List<Map<String, Object>> list = new ArrayList<>();
        for (Class studentClass : classes) {
            list.add(studentClass.toJSONObject());
        }
        return Response.ok().entity(list).build();
	}
}
