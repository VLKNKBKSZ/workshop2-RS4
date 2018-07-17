
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
import org.springframework.web.bind.annotation.SessionAttributes;

import com.rsvier.workshop2.domain.Account;
import com.rsvier.workshop2.domain.Account.AccountType;
import com.rsvier.workshop2.domain.Person;
import com.rsvier.workshop2.repository.AccountRepository;
import com.rsvier.workshop2.repository.PersonRepository;

@Controller
@RequestMapping("/login")
@SessionAttributes({"account","person"})
public class LoginController {

	AccountRepository accountRepository;
	PersonRepository personRepository;
	
	@Autowired
	public LoginController(AccountRepository accountRepository, PersonRepository personRepository) {
		this.accountRepository = accountRepository;
		this.personRepository = personRepository;
	}


	@ModelAttribute("account")
	public Account getAccount() {
		return new Account();
	}

	
	@ModelAttribute("person")
	public Person getPerson() {
		return new Person();
	}
	
	
	@GetMapping
	public String login() {

		return "login";
	}

	@PostMapping
	public String doLogin(@Valid Account account, Errors error, Model model) {

		if (error.hasErrors()) {
			return "login";
		}

		Account accountDB = accountRepository.findByEmail(account.getEmail());

		if (accountDB != null && accountDB.getEmail().equals(account.getEmail())
				&& accountDB.getPassword().equals(account.getPassword())) {
			
			Person person = personRepository.findByAccount(accountDB);
			model.addAttribute("person", person);
			
			if (accountDB.getAccountType() == AccountType.CUSTOMER) {
			
				return "redirect:/customer";
				}
			
			else if (accountDB.getAccountType() == AccountType.EMPLOYEE) {
				
				return "redirect:/employee";
			}
		}

		String message = "Het ingevoerde e-mailadres of wachtwoord klopt niet, probeer het nogmaals.";

		model.addAttribute("message", message);

		model.addAttribute("account", account);

		return "login";

	}
}