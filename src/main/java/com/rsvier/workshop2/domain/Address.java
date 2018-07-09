package com.rsvier.workshop2.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Address {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long addressId;
	// Optional means that the person cannot be null @runtime
	@ManyToOne(optional=false)
	@JoinColumn(name="person_id")
	private Person person;
	@Enumerated(EnumType.STRING)
	private AddressType addressType;
	private String streetName;
	private int houseNumber;
	private String additionalHouseNumber;
	private String postalCode;
	private String city;
	private String country;

	public Address() {

	}
	
	public enum AddressType {MAIL, DELIVERY, INVOICE}

}
