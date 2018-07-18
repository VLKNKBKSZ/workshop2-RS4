package com.rsvier.workshop2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.rsvier.workshop2.domain.Account;
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
	public String showCustomerMainMenu(@ModelAttribute("editMessage") String message, Model model) {
		
		model.addAttribute("transferedMessage", message);
		return"customerMainMenu";
	}
	
	
	@GetMapping("/personDetails")
	public String showPersondetails(Person person,Model model) {
		
		Address addressDB = addressRepository.findByPerson(person);
		model.addAttribute("address", addressDB);
		
		return "showPersonDetails";
	}
	
	@PostMapping("/deleteProfile")
	public String deleteProfile(Person person) {
		
		Address addressDB = addressRepository.findByPerson(person);
		addressRepository.delete(addressDB);
		personRepository.delete(person);
		return "home";
	}
	
	@PostMapping("/changeDetails")
	public String editProfile(Person person) {
		
		return "changeDetails";
	}
	
	
	@PostMapping("/changeAddress")
	public String changeAddress(Address address) {
		
		return "editAddress";
	}
	
	@PostMapping("/changePersonDetails")
	public String changePersonDetails(Person person) {
		
		return "editPersonForm";
	}
	
	@PostMapping("/changePassword")
	public String changePassword(Account account) {
		
		return "editAccount";
	}
	
}
