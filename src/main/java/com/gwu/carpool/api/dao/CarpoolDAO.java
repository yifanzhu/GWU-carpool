package com.gwu.carpool.api.dao;

import com.gwu.carpool.core.model.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface CarpoolDAO {
	//create
	public void createUser(User user);
	public void createEvent(Event event);
	//read
	public Optional<User> getUserByEmail(String email);
	public List<Event> getEventsByDepartureTime(Date date);
	public List<Event> getAllEvents();
	public List<User> getAllUsers();
	public List<Event> getEventsByUserIdAsDriver(String userId);
	public List<Event> getEventsByUserIdAsPending(String userId);
	public List<Event> getEventsByUserIdAsPassenger(String userId);
	public Event getEventByTitleAndPublishTimeAndDriverId(String title, Date publishtime, String driverId);
	

	//update
    public User updateUser(User user);
    public Event updateEvent(Event event);
    public void removePendingFromEvent(String evtId, User usr);
    public void addPendingToEvent(String evtId, User usr);
    public void addPassengerToEvent(String evtId, User usr);
    public void changeEventStatusById(String evtId, String stat);

    //delete
    public void deleteUserById(String userId);
	public Optional<Event> getEventsById(String id);
	
    
    
}