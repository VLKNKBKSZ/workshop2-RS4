package com.rsvier.workshop2.domain;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
@Entity
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long productId;
	
	@Pattern(regexp = "^[a-zA-Z]+$", message = "Gebruik alleen letters en laat dit veld niet leeg")
	private String name;
	@Min(value = 0L, message = "The value must be positive")
	private BigDecimal price;
	@Min(value = 0L, message = "The value must be positive")
	private int stock;
	
	public Product() {

	}
}
