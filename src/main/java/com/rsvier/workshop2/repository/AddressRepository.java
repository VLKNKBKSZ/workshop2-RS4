package com.rsvier.workshop2.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rsvier.workshop2.domain.Address;
import com.rsvier.workshop2.domain.Person;

@Repository
public interface AddressRepository extends CrudRepository<Address,Long>{

	public Address findByPerson(Person person);
}
