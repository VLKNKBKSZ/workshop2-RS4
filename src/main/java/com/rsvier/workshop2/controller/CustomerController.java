package com.rsvier.workshop2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.rsvier.workshop2.domain.Address;
import com.rsvier.workshop2.domain.Person;
import com.rsvier.workshop2.repository.AddressRepository;
import com.rsvier.workshop2.repository.PersonRepository;

@Controller
@RequestMapping("/customer")
@SessionAttributes({"account","person"})
public class CustomerController {
	
	PersonRepository personRepository;
	AddressRepository addressRepository;
	
	@Autowired
	public CustomerController(PersonRepository personRepository,AddressRepository addressRepository) {
		this.personRepository = personRepository;
		this.addressRepository = addressRepository;
	}
	
	@GetMapping
	public String showCustomerMainMenu() {
		return"customerMainMenu";
	}
	
	
	@GetMapping("/personDetails")
	public String showPersondetails(Person person,Model model) {
		
		Address addressDB = addressRepository.findByPerson(person);
		model.addAttribute("address", addressDB);
		
		return "personDetails";
	}

}
