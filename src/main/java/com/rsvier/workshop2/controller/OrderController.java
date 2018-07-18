package com.rsvier.workshop2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rsvier.workshop2.repository.OrderRepository;

@Controller
@RequestMapping("/order")
public class OrderController {

	private OrderRepository orderRepository;
	
	@Autowired
	public OrderController(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	@GetMapping
	public String createNewOrder() {
		return "createNewOrder";
	}
	
}
