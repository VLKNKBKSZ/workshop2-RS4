package com.rsvier.workshop2.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.rsvier.workshop2.domain.Order;
import com.rsvier.workshop2.domain.Order.OrderStatus;
import com.rsvier.workshop2.domain.OrderLine;
import com.rsvier.workshop2.domain.Product;
import com.rsvier.workshop2.repository.OrderLineRepository;
import com.rsvier.workshop2.repository.OrderRepository;
import com.rsvier.workshop2.repository.ProductRepository;

@Controller
@RequestMapping("/orderLine")
@SessionAttributes("order")
public class OrderLineController {

	private ProductRepository productRepository;
	private OrderLineRepository orderLineRepository;
	private OrderRepository orderRepository;
	

	@Autowired
	public OrderLineController(ProductRepository productRepository, OrderLineRepository orderLineRepository, 
			OrderRepository orderRepository) {
		
		this.productRepository = productRepository;
		this.orderLineRepository = orderLineRepository;
		this.orderRepository = orderRepository;
	}
	
	
	@ModelAttribute("orderLine")
	public OrderLine getOrderLine() {
		return new OrderLine();
	}
	
	
	@ModelAttribute("order")
	public Order order() {
		return new Order();
	}

	@GetMapping
	public String showNewOrderLine(Model model) {
		
		List<Product> productList = (List<Product>) productRepository.findAll();
		
		model.addAttribute(productList);

		return "createNewOrderLine";
	}
	
	@PostMapping("/createNewOrderLine")
	public String createNewOrderLine(@Valid OrderLine orderLine, Errors errors) {
		
		if(errors.hasErrors()) {
			return "createNewOrderLine";
		}

		Product productDB = productRepository.findByName(orderLine.getProduct().getName());
		System.out.println(productDB.toString());
		if(orderLine.getNumberOfProducts() > productDB.getStock() ) {
			return "createNewOrderLine";
		}
		
		Order order = new Order();
		System.out.println(orderLine.toString());
		System.out.println(order.toString());
		order.getListOfTotalOrderLines().add(orderLine);
		order.setOrderStatus(OrderStatus.OPEN);
	
		//just for testing:
		orderLineRepository.save(orderLine);
		orderRepository.save(order);
		
		return "redirect:/";
	}

}
