package com.rsvier.workshop2.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.rsvier.workshop2.domain.Person;

@Repository
public interface PersonRepository extends CrudRepository<Person, Long>{
 
	public Person findPersonByAccountId(long id);
}
