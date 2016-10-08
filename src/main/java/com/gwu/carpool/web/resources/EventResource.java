package com.gwu.carpool.web.resources;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import javassist.expr.NewArray;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.codahale.metrics.annotation.Timed;
import com.gwu.carpool.api.CarpoolApi;
import com.gwu.carpool.core.model.Event;
import com.gwu.carpool.core.model.User;
import com.gwu.carpool.web.json.EventJson;
import com.gwu.carpool.web.json.UserJson;

@Path("/api/events")
@Produces(MediaType.APPLICATION_JSON)
public class EventResource {
	
		private CarpoolApi api;
		
		//constructor
		public EventResource(CarpoolApi api) {
	        this.api = api;
	    }
		
		@GET
	    @Produces(MediaType.APPLICATION_JSON)
	    public Response getAllEvents() {
	        List<Event> events = api.getAllEvents();
	        List<EventJson> jsons = new ArrayList<EventJson>();
	        for(Event one : events){
	        	EventJson uj = new EventJson(one);
	        	jsons.add(uj);
	        }
	        //Stream<UserJson> jsons = users.parallelStream().map(u -> new UserJson(u));
	        //return Response.ok(jsons.toArray()).build();
	        return Response.ok(jsons).build();
	    }
		
		
		@GET
		@Path("/search")
	    @Timed
	    public Response searchEvent(@QueryParam("departureTime") String departureTime,
	    							@QueryParam("startAddress") String startAddress,
	    							@QueryParam("endAddress") String endAddress,
	    							@QueryParam("userEmail") String userEmail) {
			System.err.println("departureTime: " + departureTime);
			Long longTime = Long.parseLong(departureTime);
			System.err.println("longTime: " + longTime);
			Date time = new Date(longTime);
			System.err.println(time);
//			DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");  
//			try{
//				//time = df.parse(departureTime);
//			} catch(Exception e){
//				e.printStackTrace();
//			}
			
			List<Event> evts = api.getEventsByDepartureTime(time);
			//List<Event> evts = api.match(evts0, startAddress, endAddress);
			
			List<EventJson> jsons = new ArrayList<EventJson>();
			List<Event> evts0 = new ArrayList<Event>();
			
	        if (!evts.isEmpty()) {
	        	for(Event a : evts){
	        		boolean pending = true;
	        		boolean passenger = true;
	        		
	        		for(User b : a.getPending()){
	        			if(b.getEmail().equals(userEmail)){
	        				pending = false;
	        				break;
	        			}
	        		}
	        		for(User b : a.getPassengers()){
	        			if(b.getEmail().equals(userEmail)){
	        				passenger = false;
	        				break;
	        			}
	        		}
	        		if(!a.getDriver().getEmail().equals(userEmail) && pending && passenger) {
	        			evts0.add(a);
						EventJson evj = new EventJson(a);
						jsons.add(evj);
	        		}
				}
	        	
	        	List<Event> resultaa = api.match(evts0, startAddress, endAddress);
	        	for(Event evt: resultaa){
	        		EventJson ej = new EventJson(evt);
	        		jsons.add(ej);
	        	}
	        	return Response.ok(jsons).build();	
	        }else{
	        	return Response.status(Response.Status.NOT_FOUND).entity("get event failed!").build();
	        }
	    }
		
		@GET
		@Path("/join")
	    @Timed
	    public Response joinEvent(@QueryParam("eventId") String eventId, @QueryParam("userEmail") String userEmail) {
			
			api.addPendingToEvent(eventId, api.getUserByEmail(userEmail).get());
	        return Response.ok().entity("join success").build();
//	        	return Response.ok("email got called").build();
	    }
		
		@GET
		@Path("/accept")
	    @Timed
	    public Response acceptPassenger(@QueryParam("eventId") String eventId, @QueryParam("userEmail") String userEmail) {
			api.addPassengerToEvent(eventId, api.getUserByEmail(userEmail).get());
	        return Response.ok().build();
	    }

		@GET
		@Path("/decline")
		@Timed
		public Response acceptRider(@QueryParam("eventId") String eventId, @QueryParam("userEmail") String userEmail) {
			api.removePendingFromEvent(eventId, api.getUserByEmail(userEmail).get());
		        return Response.ok().build();
		}
		
		
		
		@GET
	    @Path("/{id}")
	    @Timed
	    public Response getEventById(@PathParam("id") String id) {
	        Optional<Event> result = api.getEventById(id);
	        if (result.isPresent()) {
	        	
	            return Response.ok(new EventJson(result.get())).build();
//	        	return Response.ok("email got called").build();
	        } else {
	            return Response.status(Response.Status.NOT_FOUND).build();
	        }
	    }
		
	
}
