package com.rsvier.workshop2.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.rsvier.workshop2.domain.Order;
import com.rsvier.workshop2.domain.OrderLine;
import com.rsvier.workshop2.domain.Person;
import com.rsvier.workshop2.domain.Product;
import com.rsvier.workshop2.repository.OrderRepository;
import com.rsvier.workshop2.repository.ProductRepository;

@Controller
@RequestMapping("/order")
@SessionAttributes({"person", "order", "orderLine", "orderLineList"})
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
    
    @ModelAttribute("orderLineList")
	public List<OrderLine> orderLineList() {
		return new ArrayList<>();
	}

    @Autowired
    public OrderController(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @GetMapping("/currentOrder")
    public String placeCurrentOrder(OrderLine orderLine, Person person, List<OrderLine> orderLineList, 
    		@ModelAttribute("totalPrice")BigDecimal totalPrice, Model model, RedirectAttributes redirectAttributes,
    		SessionStatus session) {
    	
    	
    	Order order = new Order();
    	
    	order.setListOfTotalOrderLines(orderLineList);
    	order.setPerson(person);
    	order.setTotalPrice(totalPrice);
    	
    	orderRepository.save(order);
    	session.isComplete();
    	
    	String message = "De bestelling is geplaatst.";
    	
        model.addAttribute("editMessage", message);
        redirectAttributes.addFlashAttribute("editMessage", message);
        
        return "redirect: customerMainMenu";
    }

}