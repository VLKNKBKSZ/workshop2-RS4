package com.rsvier.workshop2.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.rsvier.workshop2.domain.OrderLine;

@Repository
public interface OrderLineRepository extends CrudRepository<OrderLine, Long>{

}
