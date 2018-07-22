package com.rsvier.workshop2.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
@SessionAttributes({"person", "order", "orderLineList"})
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

    @GetMapping()
    public String showOrdersOfPerson(Person person, Model model) {

        List<Order> orderList = orderRepository.findOrdersByPerson(person);
        model.addAttribute("orderList", orderList);

        return "myOrders";
    }

    @GetMapping("/currentOrder")
    public String placeCurrentOrder(Person person, List<OrderLine> orderLineList,

                                    Model model, RedirectAttributes redirectAttributes,
                                    SessionStatus session) {


        Order order = new Order();
        // This loop is added so the orderlinetable can acces and save the order_id
        for (OrderLine orderLine : orderLineList
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
        redirectAttributes.addFlashAttribute("infoMessage", message);
        redirectAttributes.addFlashAttribute("person", person);

        return "redirect:/customer";
    }




    @PostMapping("/showOrderDetails")
    public String showOrderDetails(long orderId, Model model, Person person) {
        Order order = orderRepository.findOrderByOrderId(orderId);

        if (order == null) {
            String message = "U heeft een bestellingsnummer ingevoerd dat niet overeenkomt met uw bestellingen, probeer het nogmaals";
            List<Order> orderList = orderRepository.findOrdersByPerson(person);
            model.addAttribute("orderList", orderList);
            model.addAttribute("message", message);
            return "myOrders";
        }
        List<OrderLine> orderLineList = order.getListOfTotalOrderLines();
        model.addAttribute("orderLineList", orderLineList);
        model.addAttribute("order", order);

        return "showOrderDetails";


    }

    @PostMapping("/deleteOrder")
    public String deleteOrder(Order order, Model model) {
        /*
        This method is first checking if the orderstatus is closed. Closed orders can only be deleted by the employee.
        Then its looping true the orderLineList of the order and changing the stock back to its previous amount
        Then the order is being deleted with all the correspondending orderlines
         */
        if (order.getOrderStatus() == Order.OrderStatus.CLOSED) {
            String message = "De status van deze bestelling is Gesloten, u kunt deze bestelling niet meer aanpassen. Neem contact op met Nevvo Meubels";
            model.addAttribute("infoMessage", message);
            return "customerMainMenu";
        }

        calculatingStockWhenOrderIsDeleted(order);
        orderRepository.delete(order);
        String message = "Uw bestelling is succesvol verwijderd";
        model.addAttribute("infoMessage", message);
        return "customerMainMenu";
    }

    @PostMapping("/editOrderStatus")
    public String editOrderStatus(Order order, Model model) {
        /*
        This method is changing the order status to be closed so the order is definitve
        In real life it means he payed for the order and its going to be send to him
         */
        order.setOrderStatus(Order.OrderStatus.CLOSED);
        orderRepository.save(order);

        String message = "De status van de bestelling is nu gesloten, Uw bestelling word z.s.m opgestuurd";
        model.addAttribute("infoMessage", message);
        return "customerMainMenu";

    }

    public void calculatingStockWhenOrderIsDeleted(Order order) {
        for (OrderLine orderLine : order.getListOfTotalOrderLines()) {
            Product productDB = productRepository.findByName(orderLine.getProduct().getName());
            productDB.setStock(productDB.getStock() + orderLine.getNumberOfProducts());
            productRepository.save(productDB);
        }
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