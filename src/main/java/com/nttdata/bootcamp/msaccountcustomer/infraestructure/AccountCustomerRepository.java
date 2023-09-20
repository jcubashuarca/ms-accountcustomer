package com.nttdata.bootcamp.msaccountcustomer.infraestructure;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.nttdata.bootcamp.msaccountcustomer.model.AccountCustomer;

import reactor.core.publisher.Flux;

@Repository
public interface AccountCustomerRepository extends ReactiveMongoRepository<AccountCustomer, String> {
		
	Flux<AccountCustomer> findAllByProductIdIn(Iterable<String> string);
	
	Flux<AccountCustomer> findByCustomerId(String customerId);
}
