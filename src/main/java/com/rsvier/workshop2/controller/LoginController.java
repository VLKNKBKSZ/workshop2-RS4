package com.rsvier.workshop2.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rsvier.workshop2.domain.Account;
import com.rsvier.workshop2.repository.AccountRepository;

@Controller
@RequestMapping("/login")
public class LoginController {

	AccountRepository accountRepository;

	@Autowired
	public LoginController(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	@GetMapping
	public String login() {

		return "login";
	}

	@PostMapping
	public String doLogin(@Valid Account account, Model model,Errors errors) {

		if (accountRepository.findByEmail(account.getEmail()) != null) {
			return "adminMainMenu";

		}
		String message = "Your informations are wrong. Please try to log in agian.";

		model.addAttribute("message", message);

		model.addAttribute("account", account);

		return "login";
	}

}
