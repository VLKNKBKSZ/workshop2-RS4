package com.rsvier.workshop2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	public String doLogin( Account account,Model model ) {
		
		if(accountRepository.findByEmail(account.getEmail())!= null){
            return "adminMainMenu";
            
        }
		String message = "Your informations are wrong. Please try to log in agian.";
		
		model.addAttribute("message", message);
        	
	model.addAttribute("account", account);
		
		
		return "login";
	}

}
