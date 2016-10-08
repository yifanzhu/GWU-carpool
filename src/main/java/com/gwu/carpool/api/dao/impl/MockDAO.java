package com.gwu.carpool.api.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.codahale.metrics.health.HealthCheck.Result;
import com.gwu.carpool.api.dao.CarpoolDAO;
import com.gwu.carpool.core.model.Event;
import com.gwu.carpool.core.model.User;

public class MockDAO implements CarpoolDAO{

	@Override

	public void createUser(User user) {
	}
	
	public void createEvent(Event event) {

	}

	@Override
	public Optional<User> getUserByEmail(String email) {
		if(email.equals("liuqi627@gwu.edu")){

			User result = new User("liuqi627@gwu.edu", "liuqi627", "male",
					"4438086967", "liuqi627", "5");

			return Optional.of(result);
		}
		return Optional.empty();
	}

	@Override
	public List<User> getAllUsers() {

		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User updateUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}




	@Override
	public List<Event> getEventsByDepartureTime(Date date) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Event> getAllEvents() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Event> getEventsByUserIdAsDriver(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Event> getEventsByUserIdAsPending(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Event> getEventsByUserIdAsPassenger(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removePendingFromEvent(String evtId, User usr) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void changeEventStatusById(String evtId, String stat) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addPendingToEvent(String evtId, User usr) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addPassengerToEvent(String evtId, User usr) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteUserById(String userId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Event getEventByTitleAndPublishTimeAndDriverId(
			String title, Date publishtime, String driverId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Event updateEvent(Event event) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Event> getEventsById(String id) {
		// TODO Auto-generated method stub
		return null;
	}



}
