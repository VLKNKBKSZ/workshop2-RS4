package com.rsvier.workshop2.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.rsvier.workshop2.domain.Account;
import com.rsvier.workshop2.repository.AccountRepository;

@Controller
@RequestMapping("/account")
@SessionAttributes("account")  // Use @SessionAttributes to store the object in the session in between requests.
public class AccountController {

	AccountRepository accountRepository;

	@Autowired
	public AccountController(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	@GetMapping
	public String showAccountForm() {

		return "register";

	}
	
	
	/* The @ModelAttribute is an annotation that binds a method parameter or method return value to a named model attribute
	 *  and then exposes it to a web view.
	 */
	@ModelAttribute("account")
    public Account getAccount() {
        return new Account();
    }


	@PostMapping
	public String doCreateAccount(@Valid Account account, Errors errors) {

		if (errors.hasErrors()) {
			return "account";
		}

	//	account.setAccountType(AccountType.CUSTOMER);

	//	Account accountDB = accountRepository.save(account);

		return "redirect:/person";
	}

}
