package com.rsvier.workshop2.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
public class Account {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long accountId;

	@Enumerated(EnumType.STRING)
	private AccountType accountType;
	
	@Column(unique=true)
	@NotBlank
	//@Pattern(regexp=".+@.+\\.[a-z]+", message="Geen geldige e-mail invoer.")
	private String email;
	
	@NotBlank
	//@Pattern(regexp="^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", 
	//	message="Wachtwoord moet minimaal 8 karakters, een letter en een nummer")
	private String password;

	public enum AccountType {
		ADMIN, EMPLOYEE, CUSTOMER
	}

	public Account() {

	}

}
