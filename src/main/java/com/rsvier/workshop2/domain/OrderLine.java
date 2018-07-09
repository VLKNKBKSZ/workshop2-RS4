package com.rsvier.workshop2.domain;

import javax.persistence.*;
import lombok.*;

@Data
@Entity
public class OrderLine {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long orderLineId;
	@OneToOne(fetch = FetchType.LAZY)
	private Product product;
	private int numberOfProducts;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id")
	private Order order;

	public OrderLine() {

	}
}
