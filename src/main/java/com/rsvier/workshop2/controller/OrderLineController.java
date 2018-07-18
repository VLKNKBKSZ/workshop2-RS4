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
import org.springframework.web.bind.support.SessionStatus;

import com.rsvier.workshop2.domain.OrderLine;
import com.rsvier.workshop2.domain.Product;
import com.rsvier.workshop2.repository.ProductRepository;

@Controller
@RequestMapping("/orderLine")
public class OrderLineController {

	private ProductRepository productRepository;

	@ModelAttribute("orderLine")
	public OrderLine getOrderLine() {
		return new OrderLine();
	}

	@Autowired
	public OrderLineController(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@GetMapping
	public String showNewOrderLine(Model model) {
		
		List<Product> productList = (List<Product>) productRepository.findAll();
		
		model.addAttribute(productList);

		return "createNewOrderLine";
	}
	
	@PostMapping("/createNewOrderLine")
	public String createNewOrderLine(@Valid OrderLine orderLine, Errors errors, 
			SessionStatus sessionStatus, Model model) {
		
		
		
		sessionStatus.isComplete();
		
		return "home";
	}

}
