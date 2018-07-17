package com.rsvier.workshop2.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.rsvier.workshop2.domain.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long>{

	public Product findByName(String name);
}
