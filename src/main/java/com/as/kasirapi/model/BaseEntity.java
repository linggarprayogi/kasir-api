package com.as.kasirapi.model;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class BaseEntity {
	@JsonIgnore
	public String    created_by;
	@JsonIgnore
	public Timestamp created_on;
	@JsonIgnore
	public String    updated_by;
	@JsonIgnore
	public Timestamp updated_on;
}
