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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.rsvier.workshop2.domain.Account;
import com.rsvier.workshop2.domain.Person;
import com.rsvier.workshop2.repository.PersonRepository;

@Controller
@RequestMapping("/person")
@SessionAttributes({ "account", "person" }) // Use @SessionAttributes to store the object in the session in between
											// requests.
public class PersonController {

	PersonRepository personRepository;
	
	@Autowired
	public PersonController(PersonRepository personRepository) {
		this.personRepository = personRepository;
	}

	
	@ModelAttribute("person")
	public Person person() {
		return new Person();
	}

	
	@GetMapping
	public String showPersonProfileForm() {

		return "createNewPerson";
	}

	@PostMapping
	public String doCreatePersonAndAddress(Account account, @Valid Person person, Errors errors) {

		if (errors.hasErrors()) {
			
			return "createNewPerson";
		}

		person.setAccount(account);

		return "redirect:/address";
	}

	
	@PostMapping("/editPerson")
	public String editPerson(Person person, RedirectAttributes redirectAttributes, Model model) {
			
		personRepository.save(person);
		String message = "Persoon is aangepast.";
     
        redirectAttributes.addFlashAttribute("infoMessage", message);
		
		return "redirect:/customer";
	}
	

}
