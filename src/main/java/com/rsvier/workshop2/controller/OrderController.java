package com.rsvier.workshop2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.rsvier.workshop2.domain.Order;
import com.rsvier.workshop2.domain.OrderLine;
import com.rsvier.workshop2.domain.Person;
import com.rsvier.workshop2.domain.Product;
import com.rsvier.workshop2.repository.OrderRepository;
import com.rsvier.workshop2.repository.ProductRepository;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/order")
@SessionAttributes({"order", "orderLine"})
public class OrderController {

    private OrderRepository orderRepository;
    private ProductRepository productRepository;

    @Autowired
    public OrderController(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @ModelAttribute("order")
    public Order getOrder() {
        return new Order();
    }

    @ModelAttribute("product")
    public Product getProduct() {
        return new Product();
    }

    @GetMapping("/newOrder")
    public String createNewOrder(OrderLine orderLine, Person person, Model model) {

        Order order = new Order();
        order.setPerson(person);
        order.setOrderStatus(Order.OrderStatus.OPEN);
        order.getListOfTotalOrderLines().add(orderLine);
        System.out.println(order.getListOfTotalOrderLines().get(0).toString());
        model.addAttribute("orderLines", order.getListOfTotalOrderLines());
        return "currentOrder";
    }

    @GetMapping("/currentOrder")
    public String currentOrder(Order order, OrderLine orderLine, Model model) {

        order.getListOfTotalOrderLines().add(orderLine);
        model.addAttribute("orderLines", order.getListOfTotalOrderLines());
        return "currentOrder";
    }

    @GetMapping("/addOrderLineToOrder")
    public String addOrderLineToOrder() {
        return "redirect:/orderLine/showOrderLineForExistingOrder";
    }
}
