package com.rsvier.workshop2.domain;

import javax.persistence.*;
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
