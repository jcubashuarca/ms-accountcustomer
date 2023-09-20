package com.nttdata.bootcamp.msaccountcustomer.application;

import com.nttdata.bootcamp.msaccountcustomer.model.AccountCustomer;
import com.nttdata.bootcamp.msaccountcustomer.model.CreditCardAccount;

import reactor.core.publisher.Mono;

public interface CreditCardAccountService {
	Mono<CreditCardAccount> insertCreditCardAccount(Mono<CreditCardAccount> countCustomer);
	
	Mono<CreditCardAccount> findById(String id);
	
	 Mono<AccountCustomer> accountAvailablebalance(String numberCard);
}
