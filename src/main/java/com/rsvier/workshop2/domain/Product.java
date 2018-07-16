package com.rsvier.workshop2.domain;

import java.math.BigDecimal;
import javax.persistence.*;
import javax.validation.constraints.Pattern;

import lombok.*;

@Data
@Entity
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long productId;
	
	@Pattern(regexp = "^[a-zA-Z]+$", message = "Gebruik alleen letters en laat dit veld niet leeg")
	private String name;
	
	@Pattern(regexp = "^[0-9]+$", message = "Gebruik alleen cijfers")
	private BigDecimal price;
	
	@Pattern(regexp = "^[0-9]+$", message = "Gebruik alleen cijfers")
	private int stock;

	
	public Product() {

	}
}
