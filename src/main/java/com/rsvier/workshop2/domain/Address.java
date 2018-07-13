package com.rsvier.workshop2.domain;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import lombok.*;

@Data
@Entity
public class Address {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long addressId;
	
	// Optional means that the person cannot be null @runtime
	@ManyToOne(optional=false, cascade = CascadeType.REMOVE)
	@JoinColumn(name="person_id")
	private Person person;
	
	
	@Enumerated(EnumType.STRING)
	private AddressType addressType;
	@Pattern(regexp = "^[a-zA-Z]+$", message = "Gebruik alleen letters en laat dit veld niet leeg")
	private String streetName;
	private int houseNumber;
	private String additionalHouseNumber;
	@Pattern(regexp="^[1-9][0-9]{3}\\s?[a-zA-Z]{2}$", message = "Voer a.u.b 4 cijfers en 2 letters in.")
	private String postalCode;
	@Pattern(regexp = "^[a-zA-Z]+$", message = "Gebruik alleen letters en laat dit veld niet leeg")
	private String city;
	@Pattern(regexp = "^[a-zA-Z]+$", message = "Gebruik alleen letters en laat dit veld niet leeg")
	private String country;
	public Address() {

	}
	
	public enum AddressType {MAIL, DELIVERY, INVOICE}

}
