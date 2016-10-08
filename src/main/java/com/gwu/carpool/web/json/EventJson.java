package com.gwu.carpool.web.json;

import java.util.ArrayList;
import java.util.Date;

import com.gwu.carpool.core.model.Event;
import com.gwu.carpool.core.model.User;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EventJson {
	

	private String id;
	private String title;
	private Date departureTime;
	private String startAddress;
	private String endAddress;
	private User driver;
	private ArrayList<User> passengers;
	private ArrayList<User> pending;
	private String capacity;
	private String description;
	private String status;
	private Date publishTime; 
	private String role;


   

	public EventJson() {}

    public EventJson(Event evt) {
    	this.id = evt.getId();
    	this.title = evt.getTitle();
		this.departureTime = evt.getDepartureTime();
		this.startAddress = evt.getStartAddress();
		this.endAddress = evt.getEndAddress();
		this.driver = evt.getDriver();
		this.passengers = evt.getPassengers();
		this.pending = evt.getPending();
		this.capacity = evt.getCapacity();
		this.description = evt.getDescription();
		this.status = evt.getStatus();
		this.publishTime = evt.getPublishTime();
		
    }
    
    public void setRole(String role) {
		this.role = role;
	}
    
    @JsonProperty("role")
    public String getRole() {
		return role;
	}

	@JsonProperty("id")
	public String getId() {
		return id;
	}
    @JsonProperty("title")
	public String getTitle() {
		return title;
	}
    @JsonProperty("departureTime")
	public Date getDepartureTime() {
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
    @JsonProperty("driver")
	public User getDriver() {
		return driver;
	}
    @JsonProperty("passengers")
	public ArrayList<User> getPassengers() {
		return passengers;
	}
    @JsonProperty("pending")
	public ArrayList<User> getPending() {
		return pending;
	}
    @JsonProperty("capacity")
	public String getCapacity() {
		return capacity;
	}
    @JsonProperty("description")
	public String getDescription() {
		return description;
	}
    @JsonProperty("status")
	public String getStatus() {
		return status;
	}
    @JsonProperty("publishTime")
	public Date getPublishTime() {
		return publishTime;
	}

    



    
 
}