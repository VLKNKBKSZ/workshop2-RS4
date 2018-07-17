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
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.rsvier.workshop2.domain.Account;
import com.rsvier.workshop2.domain.Account.AccountType;
import com.rsvier.workshop2.domain.Address;
import com.rsvier.workshop2.domain.Address.AddressType;
import com.rsvier.workshop2.domain.Person;
import com.rsvier.workshop2.repository.AccountRepository;
import com.rsvier.workshop2.repository.AddressRepository;
import com.rsvier.workshop2.repository.PersonRepository;

@Controller
@RequestMapping("/address")
@SessionAttributes({ "account", "person", "address" }) // Use @SessionAttributes to store the object in the session in
														// between requests.
public class AddressController {

	PersonRepository personRepository;
	AddressRepository addressRepository;
	AccountRepository accountRepository;

	@Autowired
	public AddressController(PersonRepository personRepository, AddressRepository addressRepository,
			AccountRepository accountRepository) {
		this.personRepository = personRepository;
		this.addressRepository = addressRepository;
		this.accountRepository = accountRepository;
	}

	@ModelAttribute("address")
	public Address address() {
		return new Address();
	}

	@GetMapping
	public String showAddressForm() {
		return "createNewAddress";
	}

	@PostMapping
	public String doCreateAddress(Account account, Person person, @Valid Address address, Errors errors,
			SessionStatus sessionstatus, RedirectAttributes redirectAttributes, Model model) {

		if (errors.hasErrors()) {
			return "createNewAddress";
		}

		account.setAccountType(AccountType.CUSTOMER);
		Account accountDB = accountRepository.save(account);

		person.setAccount(accountDB);

		Person personDB = personRepository.save(person);

		address.setPerson(personDB);
		address.setAddressType(AddressType.MAIL);
		addressRepository.save(address);

		sessionstatus.setComplete();
		
		/*
		 * I've added person and account also to the model and redirecting it. Because
		 * after you go back to the customerMainmenu and you want to edit person details
		 * or account details that was given a null pointer.
		 */
		
		String message = "Uw nieuwe account is succesvol aangemaakt.";
		model.addAttribute("editMessage", message);
		model.addAttribute("person", person);
		model.addAttribute("account", account);
		redirectAttributes.addFlashAttribute("editMessage", message);
		redirectAttributes.addFlashAttribute("person", person);
		redirectAttributes.addFlashAttribute("account", account);
		return "redirect:/customer";
	}

	@PostMapping("/editAddress")
	public String editAddress(Address address, RedirectAttributes redirectAttributes, Model model) {

		addressRepository.save(address);

		String message = "Uw adres is succesvol aangepast.";
		model.addAttribute("editMessage", message);
		redirectAttributes.addFlashAttribute("editMessage", message);

		return "redirect:/customer";
	}
}
