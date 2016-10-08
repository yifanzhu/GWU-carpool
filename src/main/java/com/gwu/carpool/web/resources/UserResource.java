package com.gwu.carpool.web.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.stream.Stream;

import com.codahale.metrics.annotation.Timed;
import com.gwu.carpool.api.CarpoolApi;
import com.gwu.carpool.core.model.Event;
import com.gwu.carpool.core.model.User;
import com.gwu.carpool.web.json.EventJson;
import com.gwu.carpool.web.json.UserJson;

@Path("/api/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {
	
		private CarpoolApi api;
		
		//constructor
		public UserResource(CarpoolApi api) {
	        this.api = api;
	    }
		
		@GET
	    @Produces(MediaType.APPLICATION_JSON)
	    public Response getAllUsers() {
	        List<User> users = api.getAllUsers();
	        List<UserJson> jsons = new ArrayList<UserJson>();
	        for(User one : users){
	        	UserJson uj = new UserJson(one);
	        	jsons.add(uj);
	        }
	        //Stream<UserJson> jsons = users.parallelStream().map(u -> new UserJson(u));
	        //return Response.ok(jsons.toArray()).build();
	        return Response.ok(jsons).build();
	    }
		
		@GET
	    @Path("/{email}")
	    @Timed
	    public Response getUserByEmail(@PathParam("email") String email) {

	        Optional<User> result = api.getUserByEmail(email);
	        if (result.isPresent()) {
	            return Response.ok(new UserJson(result.get())).build();
//	        	return Response.ok("email got called").build();
	        } else {
	            return Response.status(Response.Status.NOT_FOUND).build();
	        }
	    }
		
		@GET
	    @Path("/{email}/events/pending")
	    @Timed
	    public Response getPendingByEmail(@PathParam("email") String email) {

	        List<Event> events = api.getEventsByUserIdAsPending(api.getUserByEmail(email).get().getId());
	        List<EventJson> jsons = new ArrayList<EventJson>();
	        for(Event one : events){
	        	EventJson uj = new EventJson(one);
	        	uj.setRole("rider");
	        	jsons.add(uj);
	        }
	        if (!events.isEmpty()) {
	            return Response.ok(jsons).build();
//	        	return Response.ok("email got called").build();
	        } else {
	            return Response.status(Response.Status.NOT_FOUND).build();
	        }
	    }
		
		@GET
	    @Path("/{email}/events/passenger")
	    @Timed
	    public Response getPassengerByEmail(@PathParam("email") String email) {

	        List<Event> events = api.getEventsByUserIdAsPassenger(api.getUserByEmail(email).get().getId());
	        List<EventJson> jsons = new ArrayList<EventJson>();
	        for(Event one : events){
	        	EventJson uj = new EventJson(one);
	        	uj.setRole("rider");
	        	jsons.add(uj);
	        }
	        if (events.size() != 0) {
	            return Response.ok(jsons).build();
//	        	return Response.ok("email got called").build();
	        } else {
	            return Response.status(Response.Status.NOT_FOUND).build();
	        }
	    }
		
		@GET
	    @Path("/{email}/events/driver")
	    @Timed
	    public Response getDriverByEmail(@PathParam("email") String email) {

	        List<Event> events = api.getEventsByUserIdAsDriver(api.getUserByEmail(email).get().getId());
	        List<EventJson> jsons = new ArrayList<EventJson>();
	        for(Event one : events){
	        	EventJson uj = new EventJson(one);
	        	uj.setRole("driver");
	        	jsons.add(uj);
	        }
	        if (events.size() != 0) {
	            return Response.ok(jsons).build();
	        } else {
	            return Response.status(Response.Status.NOT_FOUND).build();
	        }
	    }
		
		@GET
	    @Path("/{email}/events")
	    @Timed
	    public Response getAllEventsByEmail(@PathParam("email") String email) {

	        List<Event> eventsDriver = api.getEventsByUserIdAsDriver(api.getUserByEmail(email).get().getId());
	        List<Event> eventsPending = api.getEventsByUserIdAsPending(api.getUserByEmail(email).get().getId());
	        List<Event> eventsPassenger = api.getEventsByUserIdAsPassenger(api.getUserByEmail(email).get().getId());
	        
	        eventsDriver.addAll(eventsPassenger);
	        eventsDriver.addAll(eventsPending);
	        List<EventJson> jsons = new ArrayList<EventJson>();
	        for(Event one : eventsDriver){
	        	EventJson uj = new EventJson(one);
	        	jsons.add(uj);
	        }
	        if (eventsDriver.size() != 0) {
	            return Response.ok(jsons).build();
	        } else {
	            return Response.status(Response.Status.NOT_FOUND).build();
	        }
	    }


		
		
}
