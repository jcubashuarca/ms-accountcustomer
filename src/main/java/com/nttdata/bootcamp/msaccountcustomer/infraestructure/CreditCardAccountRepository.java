package com.nttdata.bootcamp.msaccountcustomer.infraestructure;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.nttdata.bootcamp.msaccountcustomer.model.CreditCardAccount;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CreditCardAccountRepository extends ReactiveMongoRepository<CreditCardAccount, String> {
	
	Flux<CreditCardAccount> findByProductId(String productId);
	Mono<CreditCardAccount> findByNumberCardAndIndPrimary(String numberCard, String indPrimary);

}
