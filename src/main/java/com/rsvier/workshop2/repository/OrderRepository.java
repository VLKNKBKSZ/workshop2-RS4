package com.rsvier.workshop2.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.rsvier.workshop2.domain.Order;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long>{

}
