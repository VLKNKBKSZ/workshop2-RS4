package com.rsvier.workshop2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping("/employee")
@SessionAttributes({"account"})
public class EmployeeController {
	
	AccountRepository accountRepository;

	
	@Autowired
	public EmployeeController(AccountRepository accountRepository) {
		
		this.accountRepository = accountRepository;
	}
	
	@GetMapping
	public String showEmplyeeMainMenu(Model model) {
		
	String productAdded = "Het product is aangemaakt";
	model.addAttribute("editMessage", productAdded);
	
	String deleteProductMessage = "Product is verwijderd.";
	model.addAttribute("deleteProductMessage", deleteProductMessage);
	
	String editProductMessage = "Het product is aangepast.";
	model.addAttribute("editProductMessage", editProductMessage);
	
		return"employeeMainMenu";
	}
	
	@GetMapping("/productPage")
	public String showProductMenu() {
		return "productPage";
	}
	
	@PostMapping("/changePassword")
	public String changePassword(Account account) {
		
		return "editAccount";
	}
	
}
