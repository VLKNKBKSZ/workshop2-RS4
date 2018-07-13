package com.rsvier.workshop2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rsvier.workshop2.repository.PersonRepository;

@Controller
@RequestMapping("/customer")
public class CustomerController {
	
	PersonRepository personRepository;
	
	@Autowired
	public CustomerController(PersonRepository personRepository) {
		this.personRepository = personRepository;
	}
	
	@GetMapping
	public String showCustomerMainMenu() {
		return"customerMainMenu";
	}

}
