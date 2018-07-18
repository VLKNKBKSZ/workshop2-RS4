package com.rsvier.workshop2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.rsvier.workshop2.domain.Account;
import com.rsvier.workshop2.repository.AccountRepository;

@Controller
@RequestMapping("/employee")
@SessionAttributes({ "account", "model" })
public class EmployeeController {

	AccountRepository accountRepository;

	@Autowired
	public EmployeeController(AccountRepository accountRepository) {

		this.accountRepository = accountRepository;
	}

	
	@GetMapping

	public String showEmplyeeMainMenu(@ModelAttribute("productMessage") String message, Model model) {
		model.addAttribute("transferedMessage", message);
		return "employeeMainMenu";

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
