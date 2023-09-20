package com.nttdata.bootcamp.msaccountcustomer.application;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.nttdata.bootcamp.msaccountcustomer.infraestructure.AccountCustomerRepository;
import com.nttdata.bootcamp.msaccountcustomer.infraestructure.CreditCardAccountRepository;
import com.nttdata.bootcamp.msaccountcustomer.model.AccountCustomer;
import com.nttdata.bootcamp.msaccountcustomer.model.CreditCardAccount;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class CreditCardAccountServiceImpl implements CreditCardAccountService {

	@Autowired
	CreditCardAccountRepository creditCardAccountRepository;
	@Autowired
	AccountCustomerRepository accountCustomerRepository;
	@Autowired
	private ReactiveMongoTemplate reactiveMongoTemplate;

	// fijarse de lo apuntado
	@Autowired
	ReactiveMongoTemplate mongoTemplate;

	@Override
	public Mono<CreditCardAccount> insertCreditCardAccount(Mono<CreditCardAccount> creditCardAccount) {

		return creditCardAccount.flatMap(creditCardAccountRepository::save);
	}

	@Override
	public Mono<CreditCardAccount> findById(String id) {
		return creditCardAccountRepository.findById(id);
	}

	@Override
	public Mono<AccountCustomer> accountAvailablebalance(String numberCard) {
		log.info("accountAvailablebalance INI");
			return this.findByNumberCard(numberCard).map(e->{
				return e.getProductId();
			}).collectList().flatMap(idAccounst->{
				 return this.findByProductId(idAccounst)
						 .filter(ac->ac.getAvailableBalance()>0d)
						 .collectList().flatMap(list->{
							 log.info("tamaño "+list.size());
							 return Mono.just(list.stream().findFirst().get());
				 });
						
			});
			
	}

	public Flux<CreditCardAccount> findByNumberCard(String numberCard) {
		Query query = new Query().with(Sort.by(Collections.singletonList(Sort.Order.asc("createdAt"))));
		query.addCriteria(Criteria.where("numberCard").is(numberCard));
		return reactiveMongoTemplate.find(query, CreditCardAccount.class);
	}

	

	public Flux<AccountCustomer> findByProductId(List<String> accountids) {
		Query query = new Query().with(Sort.by(Collections.singletonList(Sort.Order.asc("createdAt"))));
		query.addCriteria(Criteria.where("id").in(accountids));
		return reactiveMongoTemplate.find(query, AccountCustomer.class);
	}

}
