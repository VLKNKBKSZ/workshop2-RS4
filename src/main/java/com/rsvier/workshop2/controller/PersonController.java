package com.rsvier.workshop2.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.rsvier.workshop2.domain.Account;
import com.rsvier.workshop2.domain.Person;

@Controller
@RequestMapping("/person")
@SessionAttributes({ "account", "person" }) // Use @SessionAttributes to store the object in the session in between
											// requests.
public class PersonController {

	@Autowired
	public PersonController() {

	}

	@ModelAttribute("person")
	public Person person() {
		return new Person();
	}

	@GetMapping
	public String showPersonProfileForm() {

		return "personForm";
	}

	@PostMapping
	public String doCreatePersonAndAddress(Account account, @Valid Person person, Errors errors) {

		if (errors.hasErrors()) {
			return "personForm";
		}

		person.setAccount(account);

		return "redirect:/address";
	}


}
