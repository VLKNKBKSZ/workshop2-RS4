package com.rsvier.workshop2.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	private String email;
	private String password;

	public enum AccountType {
		ADMIN, EMPLOYEE, CUSTOMER
	}

	public Account() {

	}

}
