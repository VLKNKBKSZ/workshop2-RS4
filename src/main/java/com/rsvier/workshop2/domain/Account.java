package com.rsvier.workshop2.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import lombok.*;

@Data
@Entity
public class Account {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long accountId;
	@Enumerated(EnumType.STRING)
	private AccountType accountType;
	@Column(unique=true)
	@NotBlank(message="Email is required")
	private String email;
	@NotBlank(message="Password is required")
	private String password;

	public enum AccountType {
		ADMIN, EMPLOYEE, CUSTOMER
	}

	public Account() {

	}

}
