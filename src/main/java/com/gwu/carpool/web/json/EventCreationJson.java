package com.gwu.carpool.web.json;

import java.util.ArrayList;
import java.util.Date;

import com.gwu.carpool.core.model.Event;
import com.gwu.carpool.core.model.User;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EventCreationJson {
	
	private String title;
	private String departureTime;
	private String startAddress;
	private String endAddress;
	private String userEmail;
	private String capacity;
	private String description;


   

	public EventCreationJson() {}

    @JsonProperty("title")
	public String getTitle() {
		return title;
	}
    @JsonProperty("departureTime")
	public String getDepartureTime() {
		return departureTime;
	}
    @JsonProperty("startAddress")
	public String getStartAddress() {
		return startAddress;
	}
    @JsonProperty("endAddress")
	public String getEndAddress() {
		return endAddress;
	}
    @JsonProperty("userEmail")
	public String getuserEmail() {
		return userEmail;
	}
    @JsonProperty("capacity")
	public String getCapacity() {
		return capacity;
	}
    @JsonProperty("description")
	public String getDescription() {
		return description;
	}

    



    
 
}