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
			SessionStatus sessionstatus) {

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

		return "redirect/customer";
	}

	
	
	@PostMapping("/editAddress")
	public String editAddress(Address address, Model model) {

		addressRepository.save(address);

		String addressEditSuccessful = "Het adres is aangepast.";
		model.addAttribute("editMessage", addressEditSuccessful);

		return "customerMainMenu";
	}
}
