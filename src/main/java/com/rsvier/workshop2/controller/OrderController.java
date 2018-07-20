package com.rsvier.workshop2.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
@SessionAttributes({"person", "order","orderLineList"})
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
    public String placeCurrentOrder(Person person, List<OrderLine> orderLineList,
    		 Model model, RedirectAttributes redirectAttributes,
    		SessionStatus session) {
    	
    	
    	Order order = new Order();
    	// This loop is added so the orderlinetable can acces and save the order_id
        for (OrderLine orderLine:orderLineList
             ) {
            orderLine.setOrder(order);

        }
    	order.setListOfTotalOrderLines(orderLineList);
        order.setTotalPrice(getTotalPriceOfOrder(order));
    	order.setPerson(person);
    	order.setOrderStatus(Order.OrderStatus.OPEN);
    	order.setOrderDateTime(LocalDateTime.now());
    	orderRepository.save(order);
    	session.setComplete();
    	
    	String message = "De bestelling is geplaatst.";
    	
        model.addAttribute("editMessage", message);
        redirectAttributes.addFlashAttribute("editMessage", message);
        redirectAttributes.addFlashAttribute("person", person);

        return "redirect:/customer";
    }

    @GetMapping()
    public String showOrdersOfPerson(Person person, Model model) {

        List<Order> orderList = orderRepository.findOrdersByPerson(person);
        model.addAttribute("orderList", orderList);

        return "myOrders";
    }


    public BigDecimal getTotalPriceOfOrder(Order order) {

        BigDecimal totalPriceOfOrder = new BigDecimal(0);

        for (OrderLine orderLine : order.getListOfTotalOrderLines()) {

            BigDecimal totalPriceOfOrderLine = new BigDecimal(0);
            BigDecimal numberOfProductsInBigDecimal = (BigDecimal.valueOf(orderLine.getNumberOfProducts()));
            totalPriceOfOrderLine = (totalPriceOfOrderLine.add((orderLine.getProduct().getPrice()))
                    .multiply(numberOfProductsInBigDecimal));
            totalPriceOfOrder = totalPriceOfOrder.add(totalPriceOfOrderLine);
        }

        return totalPriceOfOrder;
    }

}