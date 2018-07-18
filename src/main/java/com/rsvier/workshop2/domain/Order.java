package com.rsvier.workshop2.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import javax.persistence.*;
import lombok.Data;

@Data
@Entity()
@Table(name="order_table")
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long orderId;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="order")
	private List<OrderLine> listOfTotalOrderLines = new ArrayList<>();
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="person_id")
	private Person person;
	
	private BigDecimal totalPrice;;
	
	private LocalDateTime orderDateTime;
	
	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;
	
	public enum OrderStatus{
		OPEN,CLOSED,SEND
	}
	
	public Order() {
		
	}

}
