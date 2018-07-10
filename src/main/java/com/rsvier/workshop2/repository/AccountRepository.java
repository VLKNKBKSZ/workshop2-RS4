package com.rsvier.workshop2.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.rsvier.workshop2.domain.Account;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long>{

	 public Account findByEmail(String email);
}
