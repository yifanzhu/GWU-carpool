package com.gwu.carpool.web;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;

public class CarpoolConfiguration extends Configuration {
	@NotEmpty
    private String mongoDatabaseName;

	@JsonProperty
	public String getMongoDatabaseName() {
		return mongoDatabaseName;
	}
	
	@JsonProperty
	public void setMongoDatabaseName(String mongoDatabaseName) {
		this.mongoDatabaseName = mongoDatabaseName;
	}


}