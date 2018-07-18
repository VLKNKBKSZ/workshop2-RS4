package com.rsvier.workshop2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.rsvier.workshop2.domain.Account;
import com.rsvier.workshop2.domain.Order;
import com.rsvier.workshop2.domain.Product;
import com.rsvier.workshop2.repository.OrderRepository;
import com.rsvier.workshop2.repository.ProductRepository;

@Controller
@RequestMapping("/order")
@SessionAttributes({ "order", "product" })
public class OrderController {

	private OrderRepository orderRepository;
	private ProductRepository productRepository;

	@ModelAttribute("order")
	public Order getOrder() {
		return new Order();
	}

	@ModelAttribute("product")
	public Product getProduct() {
		return new Product();
	}

	@Autowired
	public OrderController(OrderRepository orderRepository, ProductRepository productRepository) {
		this.orderRepository = orderRepository;
		this.productRepository = productRepository;
	}

	@GetMapping
	public String createNewOrder() {
		return "home";
	}

}
