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


@Path("/api/signup")
@Produces(MediaType.APPLICATION_JSON)
public class SignupResource {
	
		private CarpoolApi api;
		
		//constructor
		public SignupResource(CarpoolApi api) {
	        this.api = api;
	    }
		
		
		@GET
		@Path("/request")
	    @Timed
	    public Response createUser(@QueryParam("email") String email, @QueryParam("password") String password,
	    						   @QueryParam("gender") String gender, @QueryParam("phone") String phone,
	    						   @QueryParam("username") String username) {
			System.err.println("aaaaa" +  email + username + gender + "aaaaa");
			Optional<User> result = api.createUser(email, username, gender, phone, password, "");
			
	        if (result.isPresent()) {
	        	System.err.println("wwwwwwwwww");
	        	System.err.println(result.get().toString());
	            return Response.ok(new UserJson(result.get())).build();
//	        	return Response.ok("email got called").build();
	        } else {
	        	System.err.println("55555555555");
	            return Response.status(Response.Status.NOT_FOUND).entity("signup failed!").build();
	        	//return Response.ok("fuck u!").build();
	        }
	    }
		



		
		
}
