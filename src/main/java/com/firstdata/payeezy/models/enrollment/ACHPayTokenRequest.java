package com.firstdata.payeezy.models.enrollment;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ACHPayTokenRequest {

	@JsonProperty("subscriber_id")
	String subscriberId;
	
	@JsonProperty("transaction_id")
	String pwmbTransactionId; 
	
	@JsonProperty("first_name")
	String firstName;
	
	@JsonProperty("last_name")
	String lastName;

	@JsonProperty("email")
	private String email;

	@JsonProperty("billing_address")
	private Address address;
	

	
	public String getSubscriberId() {
		return subscriberId;
	}
	public void setSubscriberId(String subscriberId) {
		this.subscriberId = subscriberId;
	}
	public String getPwmbTransactionId() {
		return pwmbTransactionId;
	}
	public void setPwmbTransactionId(String pwmbTransactionId) {
		this.pwmbTransactionId = pwmbTransactionId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
