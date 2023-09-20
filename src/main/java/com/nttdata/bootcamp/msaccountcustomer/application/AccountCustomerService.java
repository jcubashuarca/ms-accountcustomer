package com.nttdata.bootcamp.msaccountcustomer.application;

import com.nttdata.bootcamp.msaccountcustomer.model.AccountCustomer;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountCustomerService {
	Mono<AccountCustomer> crearCuenta(Mono<AccountCustomer> countCustomer);
	public Mono<AccountCustomer> findById(String id) ;
	Flux<AccountCustomer> findByCustomerId(String customerId);
	
	Mono<AccountCustomer> findByNumberCard(String numberCard) ;
	
}
