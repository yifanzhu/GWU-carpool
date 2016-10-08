package com.gwu.carpool.core.model;


import java.util.ArrayList;

import java.util.HashMap;

public class User {

	private String id;

    private String email;
    private String username;
    private String gender;
    private String phone;
    private String password;
    private String reputation;
    
    //constructor
    public User(String email, String username, String gender,
			String phone, String password, String reputation) {
		super();

		this.email = email;
		this.username = username;
		this.gender = gender;
		this.phone = phone;
		this.password = password;
		this.reputation = reputation;

	}
    
    public User(){
    	super();
    }
    
    //override toString()
    public String toString() {
        return String.format("User(%s, %s, %s, %s, %s, %s, %s)", id, email, username, gender, phone, password, reputation);
    }
    
    //getters and setters

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getReputation() {
        return reputation;
    }

    public void setReputation(String reputation) {
        this.reputation = reputation;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {

        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
