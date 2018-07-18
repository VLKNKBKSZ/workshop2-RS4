package com.rsvier.workshop2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rsvier.workshop2.domain.Product;
import com.rsvier.workshop2.repository.ProductRepository;

@Controller
@RequestMapping("/orderLine")
public class OrderLineController {
	
	private ProductRepository productRepository;
	
	@Autowired
	public OrderLineController(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
	
	@GetMapping
	public String createNewOrderLine(Model model) {
		Iterable<Product> productList = productRepository.findAll();
		model.addAttribute("productList", productList);
		return "createNewOrderLine";
	}

}
