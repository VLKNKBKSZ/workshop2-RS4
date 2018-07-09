package com.rsvier.workshop2.domain;

import java.math.BigDecimal;
import javax.persistence.*;
import lombok.*;

@Data
@Entity
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long productId;
	private String name;
	private BigDecimal price;
	private int stock;

	public Product() {

	}
}
