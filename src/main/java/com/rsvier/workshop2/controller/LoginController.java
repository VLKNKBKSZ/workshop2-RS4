package com.rsvier.workshop2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rsvier.workshop2.domain.Account;

@Controller
@RequestMapping("/login")
public class LoginController {
	
	@GetMapping
	public String login() {
		
		return "login";
	}
	
	@PostMapping
	public String doLogin( Account account,Model model ) {
		
		
	model.addAttribute("account", account);
		System.out.println(account.getEmail() + account.getPassword());
		
		return "adminMainMenu";
	}

}
