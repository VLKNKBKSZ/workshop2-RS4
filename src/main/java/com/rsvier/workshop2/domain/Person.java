package com.rsvier.workshop2.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
@Entity
public class Person {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long personId;

	/*
	 * - Don't add cascade.ALL coz i want to save account seperately - cascade =
	 * CascadeType.REMOVE means If delete person--> account will be deleted -
	 * optional = false means account can't be null
	 */

	@OneToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
	@JoinColumn(name = "account_id")
	private Account account;

	@Pattern(regexp = "^[a-zA-Z]+$", message = "Gebruik alleen letters en laat dit veld niet leeg")
	private String name;

	@Pattern(regexp = "^[a-zA-Z]+$", message = "Gebruik alleen letters en laat dit veld niet leeg")
	private String lastName;

	private String middleName;

	@Pattern(regexp = "^[0-9]+$", message = "Gebruik alleen cijfers")
	private String phoneNumber;

	public Person() {

	}

}
