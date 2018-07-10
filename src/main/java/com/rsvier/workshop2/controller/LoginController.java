package com.rsvier.workshop2.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rsvier.workshop2.domain.Account;
import com.rsvier.workshop2.repository.AccountRepository;

@Controller
@RequestMapping("/login")
public class LoginController {

	AccountRepository accountRepository;

	@ModelAttribute("account")
	public Account getAccount() {
		return new Account();
	}
	@Autowired
	public LoginController(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	@GetMapping
	public String login() {

		return "login";
	}

	@PostMapping
	public String doLogin(@Valid Account account,Errors error, Model model) {

		if(error.hasErrors()) {
			return "login";
		}
		
		if (accountRepository.findByEmail(account.getEmail()) != null) {
			return "adminMainMenu";

		}
		String message = "Het ingevoerde emailadres of wachtwoord klopt niet, probeer het nogmaals.";

		model.addAttribute("message", message);

		model.addAttribute("account", account);
		
		return "login";
	
	}

}
