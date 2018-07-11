package com.rsvier.workshop2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rsvier.workshop2.domain.Account;
import com.rsvier.workshop2.domain.Account.AccountType;
import com.rsvier.workshop2.domain.Address;
import com.rsvier.workshop2.domain.Person;
import com.rsvier.workshop2.repository.AccountRepository;
import com.rsvier.workshop2.repository.AddressRepository;
import com.rsvier.workshop2.repository.PersonRepository;

@Controller
@RequestMapping("/person")
public class PersonController {
	
	PersonRepository personRepository;
	AddressRepository addressRepository;
	AccountRepository accountRepository;
	
	@Autowired
	public PersonController(PersonRepository personRepository,AddressRepository addressRepository,AccountRepository accountRepository) {
		this.personRepository = personRepository;
		this.addressRepository = addressRepository;
		this.accountRepository = accountRepository;
	}
	
	@ModelAttribute("person")
	public Person person() {
		return new Person();
	}
	@ModelAttribute("address")
	public Address address() {
		return new Address();
	}
	
@GetMapping
	public String showPersonProfileForm() {
		
		return "person";
	}


@PostMapping
public String doCreatePersonAndAddress(Account account, Person person, Address address) {
	
	account.setAccountType(AccountType.CUSTOMER);
	Account accountDB = accountRepository.save(account);
	
	person.setAccount(accountDB);
	
	Person personDB = personRepository.save(person);
	
	address.setPerson(personDB);
	Address addressDB = addressRepository.save(address);
	
	return "";
}

}
