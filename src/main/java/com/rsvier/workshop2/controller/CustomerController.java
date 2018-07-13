package com.rsvier.workshop2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.rsvier.workshop2.domain.Address;
import com.rsvier.workshop2.domain.Person;
import com.rsvier.workshop2.repository.AccountRepository;
import com.rsvier.workshop2.repository.AddressRepository;
import com.rsvier.workshop2.repository.PersonRepository;

@Controller
@RequestMapping("/customer")
@SessionAttributes({"account","person","address"})
public class CustomerController {
	
	AccountRepository accountRepository;
	PersonRepository personRepository;
	AddressRepository addressRepository;
	
	@Autowired
	public CustomerController(PersonRepository personRepository,AddressRepository addressRepository
			,AccountRepository accountRepository) {
		this.personRepository = personRepository;
		this.addressRepository = addressRepository;
		this.accountRepository = accountRepository;
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
	@PostMapping("/deleteProfile")
	public String deleteProfile(Person person) {
		
		Address addressDB = addressRepository.findByPerson(person);
		accountRepository.delete(person.getAccount());
		addressRepository.delete(addressDB);
		personRepository.delete(person);
		
		return "home";
	}

}
