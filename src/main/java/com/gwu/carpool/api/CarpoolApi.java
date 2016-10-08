package com.gwu.carpool.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.TreeMap;

import com.gwu.carpool.api.dao.CarpoolDAO;
import com.gwu.carpool.api.dao.impl.MongoDAO;
import com.gwu.carpool.core.MatchEngine;
import com.gwu.carpool.core.model.Event;
import com.gwu.carpool.core.model.User;
import com.gwu.carpool.core.model.Event;

public class CarpoolApi{
	
	final CarpoolDAO dao;
	
	public CarpoolApi(CarpoolDAO dao){
		this.dao = dao;
	}
	
	
	public static void main(String[] args) {
		User user = new User("mao@gmail.com","gouuser","male","12312367","123445","");
		 CarpoolDAO dao = new MongoDAO("carpool");
		 dao.createUser(user);
	}
	
	//User
	public Optional<User> getUserByEmail(String email) {
        Optional<User> result = dao.getUserByEmail(email);
        return result;
    }
	
	public List<User> getAllUsers(){
		List<User> au = dao.getAllUsers();
		return au;
	}

	public Optional<User> createUser(String email, String username, String gender,
			String phone, String password, String reputation){
		Optional<User> r = this.getUserByEmail(email);
		
		if(r.isPresent()){
			return Optional.empty();
		}
		User user = new User(email, username, gender, phone, password, reputation);
		System.err.println(user.toString());
		dao.createUser(user);
		System.err.println("bbbbbbbbbbbb");
		Optional<User> result = dao.getUserByEmail(email);
		System.err.println("cccccccccccccc");
		
		return result;
	}
	
	public User updateUser(String email, String username, String gender,
			String phone, String password, String reputation){
		User user = new User(email, username, gender, phone, password, reputation);
		return dao.updateUser(user);
	}
	
	public void deleteUser(String email){
		Optional<User> user = getUserByEmail(email);
		dao.deleteUserById(user.get().getId());
	}
	
	public List<User> getAllUser(){
		return dao.getAllUsers();
	}
	//Event
	public Event createEvent(String title, Date departureTime, String startAddress, 
			String endAddress, User driver, ArrayList<User> passengers, ArrayList<User> pending,
			String capacity, String description, String status, Date publishTime){
		Event event = new Event(title, departureTime, startAddress, endAddress, driver, passengers, pending, 
				capacity, description, status, publishTime);
		dao.createEvent(event);
		return event;
	}

	public Event getEventByTitleAndPublishTimeAndDriverId(String title, Date publishtime, String driverId){
		return dao.getEventByTitleAndPublishTimeAndDriverId(title, publishtime, driverId);
	}
	
  	public List<Event> getEventsByDepartureTime(Date date){
  		return dao.getEventsByDepartureTime(date);
  	}
  	
  	public List<Event> getEventsByUserIdAsDriver(String userId){
  		return dao.getEventsByUserIdAsDriver(userId);
  	}
  	
	public List<Event> getEventsByUserIdAsPending(String userId){
		return dao.getEventsByUserIdAsPending(userId);
	}
	
	public List<Event> getEventsByUserIdAsPassenger(String userId){
		return dao.getEventsByUserIdAsPassenger(userId);
	}
	
  	public Event updateEvent(String title, Date departureTime, String startAddress, 
			String endAddress, User driver, ArrayList<User> passengers, ArrayList<User> pending,
			String capacity, String description, String status, Date publishTime){
  		Event event = new Event(title, departureTime, startAddress, endAddress, driver, passengers, pending, 
  				capacity, description, status, publishTime);
  		return dao.updateEvent(event);
  	}
  	
  	public void deleteEvent(String title, Date publishtime, String driverId){
  		Event event = dao.getEventByTitleAndPublishTimeAndDriverId(title, publishtime, driverId);
  		dao.changeEventStatusById(event.getId(), "Deleted");
  	}
  	
  	public List<Event> getAllEvents(){
  		return dao.getAllEvents();
  	}
  	//Pending
  	public void removePendingFromEvent(String evtId, User usr){
  		dao.removePendingFromEvent(evtId, usr);
  	}
  	
    public void addPendingToEvent(String evtId, User usr){
    	dao.addPendingToEvent(evtId, usr);
    }
    
    public void addPassengerToEvent(String evtId, User usr){
    	dao.addPassengerToEvent(evtId, usr);
    	dao.removePendingFromEvent(evtId, usr);
    }
    
    public void changeEventStatusById(String evtId, String stat){
    	//stat can be delete, 
    	dao.changeEventStatusById(evtId, stat);
    }


	
    public Optional<Event> getEventById(String id) {
    	return dao.getEventsById(id);
	}
    
    public List<Event> match(List<Event> list, String startAddress, String endAddress){
    	HashMap<Float, Event> map = new HashMap<Float, Event>();
    	for(Event evt : list){
    		Float result1 = MatchEngine.compare(startAddress, evt.getStartAddress());
    		Float result2 = MatchEngine.compare(endAddress, evt.getEndAddress());
    		Float result = result1 + result2;
    		map.put(result, evt);
    	}
    	ArrayList<Float> al = new ArrayList<Float>();
    	Iterator iter = map.entrySet().iterator(); 
    	while (iter.hasNext()) { 
    	    Map.Entry entry = (Map.Entry) iter.next(); 
    	    Float key = (Float)entry.getKey(); 
    	    al.add(key);
    	} 
    	Collections.sort(al);
    	ArrayList<Event> resultList = new ArrayList<Event>();
    	for(int i=0; i<al.size(); i++){
    		resultList.add(map.get(al.get(i)));
    	}
    	return resultList;
    }

}