package com.rsvier.workshop2.repository;

import com.rsvier.workshop2.domain.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.rsvier.workshop2.domain.Order;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long>{

    List<Order> findOrdersByPerson(Person person);

    Order findOrderByOrderId(long OrderId);
}
