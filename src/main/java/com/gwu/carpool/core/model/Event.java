package com.gwu.carpool.core.model;

import java.util.ArrayList;
import java.util.Date;

import java.util.List;


public class Event {
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
	
	public Event(){
    	super();
    }

	public Event(
			String title, 
			Date departureTime, 
			String startAddress,
			String endAddress, 
			User driver, 
			ArrayList<User> passengers,
			ArrayList<User> pending, 
			String capacity, 
			String description,
			String status, 
			Date publishTime
			) {
		super();
		this.title = title;
		this.departureTime = departureTime;
		this.startAddress = startAddress;
		this.endAddress = endAddress;
		this.driver = driver;
		this.passengers = passengers;
		this.pending = pending;
		this.capacity = capacity;
		this.description = description;
		this.status = status;
		this.publishTime = publishTime;
	}
	
	public String toString(){
		return String.format("Event(%s,\n %s,\n %s,\n %s,\n %s,\n driver: %s,\n\n passengers: %s,\n\n pending: %s,\n\n %s,\n %s,\n %s,\n %s\n)", id, title, departureTime, startAddress, endAddress, driver.toString(), 
				toStringListOfUsers(passengers),toStringListOfUsers(pending), capacity, description, status, publishTime);
    }
	
	public String toStringListOfUsers(List<User> lu){
		String result = "";
		if(lu == null) return result;
		for(User usr : lu){
			result += usr.toString();
		}
		return result;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public Date getDepartureTime() {
		return departureTime;
	}

	public Date getPublishTime() {
		return publishTime;
	}

	public void setDepartureTime(Date departureTime) {
		this.departureTime = departureTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}


	public String getStartAddress() {
		return startAddress;
	}

	public void setStartAddress(String startAddress) {
		this.startAddress = startAddress;
	}

	public String getEndAddress() {
		return endAddress;
	}

	public void setEndAddress(String endAddress) {
		this.endAddress = endAddress;
	}

	public User getDriver() {
		return driver;
	}

	public ArrayList<User> getPassengers() {
		return passengers;
	}

	public ArrayList<User> getPending() {
		return pending;
	}


	public void setDriver(User driver) {
		this.driver = driver;
	}


	public void setPassengers(ArrayList<User> passengers) {
		this.passengers = passengers;
	}


	public void setPending(ArrayList<User> pending) {
		this.pending = pending;
	}


	public String getCapacity() {
		return capacity;
	}

	public void setCapacity(String capacity) {

		this.capacity = capacity;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}



	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}