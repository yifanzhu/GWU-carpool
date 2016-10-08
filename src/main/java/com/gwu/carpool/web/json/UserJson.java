package com.gwu.carpool.web.json;

import java.util.ArrayList;
import java.util.List;

import com.gwu.carpool.api.CarpoolApi;
import com.gwu.carpool.api.dao.impl.MongoDAO;
import com.gwu.carpool.core.model.Event;
import com.gwu.carpool.core.model.User;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserJson {
	

	private String id;
    private String email;
    private String username;
    private String gender;
    private String phone;
    private String password;
    private String reputation;
    private String asDriver;
	private String asPassengers;
    private String asPending;
    

    public UserJson() {}

    public UserJson(User u) {
    	
    	CarpoolApi api = new CarpoolApi(new MongoDAO("carpool"));
    	this.id = u.getId();
		this.email = u.getEmail();
		this.username = u.getUsername();
		this.gender = u.getGender();
		this.phone = u.getPhone();
		this.password = u.getPassword();
		this.reputation = u.getReputation();
		this.asDriver = "/api/users/" + this.email + "/events/driver";
		this.asPassengers = "/api/users/" + this.email + "/events/passenger";
		this.asPending = "/api/users/" + this.email + "/events/pending";
//		this.asDriver = api.getEventsByUserIdAsDriver(u.getId());
//		this.asPassengers = api.getEventsByUserIdAsPassenger(u.getId());
//		this.asPending = api.getEventsByUserIdAsPending(u.getId());
    }

    public User toUser() {
        return new User(email, username, gender, phone, password, reputation);
    }
    
    @JsonProperty("id")
	public String getId() {
		return id;
	}
    
    @JsonProperty("email")
	public String getEmail() {
		return email;
	}
    
    @JsonProperty("username")
	public String getUsername() {
		return username;
	}
    
    @JsonProperty("gender")
	public String getGender() {
		return gender;
	}
    
    @JsonProperty("phone")
	public String getPhone() {
		return phone;
	}
    
    @JsonProperty("password")
	public String getPassword() {
		return password;
	}
    
    @JsonProperty("reputation")
	public String getReputation() {
		return reputation;
	}
    
    @JsonProperty("asDriver_url")
    public String getAsDriver() {
		return asDriver;
	}

    @JsonProperty("asPassengers_url")
	public String getAsPassengers() {
		return asPassengers;
	}

    @JsonProperty("asPending_url")
	public String getAsPending() {
		return asPending;
	}



    
 
}