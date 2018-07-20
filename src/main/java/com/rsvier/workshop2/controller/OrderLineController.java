package com.rsvier.workshop2.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
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

import com.rsvier.workshop2.domain.Order;
import com.rsvier.workshop2.domain.OrderLine;
import com.rsvier.workshop2.domain.Person;
import com.rsvier.workshop2.domain.Product;
import com.rsvier.workshop2.repository.OrderLineRepository;
import com.rsvier.workshop2.repository.OrderRepository;
import com.rsvier.workshop2.repository.ProductRepository;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@RequestMapping("/orderLine")
@SessionAttributes({"person","orderLineList"})
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
	public OrderLine orderLine() {
		return new OrderLine();
	}

	@ModelAttribute("orderLineList")
	public List<OrderLine> orderLineList() {
		return new ArrayList<>();
	}

	@GetMapping
	public String showNewOrderLine(Model model) {

		List<Product> productList = (List<Product>) productRepository.findAll();
		model.addAttribute(productList);

		return "createNewOrderLine";
	}

	@GetMapping("/showNewOrderLineForOrderLineList")
	public String showNewOrderLineForOrderLineList(Model model) {

		List<Product> productList = (List<Product>) productRepository.findAll();
		model.addAttribute(productList);

		return "createOrderLineForOrderLineList";
	}

	@PostMapping("/createNewOrderLine")
	public String createNewOrderLine(@Valid OrderLine orderLine, Errors errors, Person person, Model model) {

		if (errors.hasErrors()) {
			
			List<Product> productList = (List<Product>) productRepository.findAll();
			model.addAttribute(productList);
			
			return "createNewOrderLine";
		}
		
		
		
		Product productDB = productRepository.findByName(orderLine.getProduct().getName());
		
		
		if (productDB == null) {
			
			List<Product> productList = (List<Product>) productRepository.findAll();
			model.addAttribute(productList);
			String warningMessage = "Dit product bestaat niet. Probeer het opnieuw.";
			model.addAttribute("warningMessage", warningMessage);

			return "createNewOrderLine";
		}
		
		if (orderLine.getNumberOfProducts() > productDB.getStock()) {

			List<Product> productList = (List<Product>) productRepository.findAll();
			model.addAttribute(productList);
			String warningMessage = "Het ingevoerde aantal is te hoog. Voer aub een nieuw aantal in.";
			model.addAttribute("warningMessage", warningMessage);

			return "createNewOrderLine";
		}
		
		
		productDB.setStock(productDB.getStock() - orderLine.getNumberOfProducts());
		productRepository.save(productDB);
		
		
		orderLine.setProduct(productDB);
		List<OrderLine> orderLineList = new ArrayList<>();
		orderLineList.add(orderLine);
		model.addAttribute(orderLineList);

		BigDecimal totalPrice = getTotalPriceOfOrder(orderLineList);
		model.addAttribute("totalPrice", totalPrice);

		return "currentOrder";
	}

	
	
	
	@PostMapping("/addOrderLineToOrderLineList")
	public String addOrderLineToOrderLineList(List<OrderLine> orderLineList, @Valid OrderLine orderLine, Errors errors,
			Person person, Model model) {

		if (errors.hasErrors()) {
			List<Product> productList = (List<Product>) productRepository.findAll();
			model.addAttribute(productList);
			
			return "createOrderLineForOrderLineList";
		}

		Product productDB = productRepository.findByName(orderLine.getProduct().getName());
		
		if (productDB == null) {
			
			List<Product> productList = (List<Product>) productRepository.findAll();
			model.addAttribute(productList);
			String warningMessage = "Dit product bestaat niet. Probeer het opnieuw.";
			model.addAttribute("warningMessage", warningMessage);

			return "createOrderLineForOrderLineList";
		}

		if (orderLine.getNumberOfProducts() > productDB.getStock()) {

			List<Product> productList = (List<Product>) productRepository.findAll();
			model.addAttribute(productList);
			
			String warningMessage = "Het ingevoerde aantal is te hoog. Voer aub een nieuw aantal in.";
			model.addAttribute("warningMessage", warningMessage);

			return "createOrderLineForOrderLineList";
		}
		
		productDB.setStock(productDB.getStock() - orderLine.getNumberOfProducts());
		productRepository.save(productDB);
		
		orderLine.setProduct(productDB);
		orderLineList.add(orderLine);
		model.addAttribute(orderLineList);

		BigDecimal totalPrice = getTotalPriceOfOrder(orderLineList);
		model.addAttribute("totalPrice", totalPrice);

		return "currentOrder";
	}

	
	
	public BigDecimal getTotalPriceOfOrder(List<OrderLine> orderLineList) {

		BigDecimal totalPriceOfOrder = new BigDecimal(0);

		for (OrderLine orderLine : orderLineList) {

			BigDecimal totalPriceOfOrderLine = new BigDecimal(0);
			BigDecimal numberOfProductsInBigDecimal = (BigDecimal.valueOf(orderLine.getNumberOfProducts()));
			totalPriceOfOrderLine = (totalPriceOfOrderLine.add((orderLine.getProduct().getPrice()))
					.multiply(numberOfProductsInBigDecimal));
			totalPriceOfOrder = totalPriceOfOrder.add(totalPriceOfOrderLine);
		}

		return totalPriceOfOrder;
	}

}