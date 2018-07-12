package com.rsvier.workshop2.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import lombok.*;

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
