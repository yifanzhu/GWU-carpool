package com.gwu.carpool.web.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.stream.Stream;

import com.codahale.metrics.annotation.Timed;
import com.gwu.carpool.api.CarpoolApi;
import com.gwu.carpool.core.model.User;
import com.gwu.carpool.web.json.UserJson;


@Path("/api/login")
@Produces(MediaType.APPLICATION_JSON)
public class LoginResource {
	
		private CarpoolApi api;
		
		//constructor
		public LoginResource(CarpoolApi api) {
	        this.api = api;
	    }
		
		@GET
		public Response sayHello(){
			return Response.ok().entity("hello!").build();
		}
		
		@GET
		@Path("/request")
	    @Timed
	    public Response getUserByEmail(@QueryParam("email") String email, @QueryParam("password") String password) {
			System.err.println(email);
			System.err.println(password);
	        Optional<User> result = api.getUserByEmail(email);
	        if (result.isPresent() && result.get().getPassword().equals(password)) {
	            return Response.ok(new UserJson(result.get())).build();
//	        	return Response.ok("email got called").build();
	        } else {
	            return Response.status(Response.Status.NOT_FOUND).entity("login failed!").build();
	        	//return Response.ok("fuck u!").build();
	        }
	    }
		



		
		
}
