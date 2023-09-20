package com.nttdata.bootcamp.msaccountcustomer.application;

import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.bootcamp.msaccountcustomer.infraestructure.AccountCustomerRepository;
import com.nttdata.bootcamp.msaccountcustomer.infraestructure.CreditCardAccountRepository;
import com.nttdata.bootcamp.msaccountcustomer.model.AccountCustomer;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AccountCustomerServiceImpl implements AccountCustomerService {

	@Autowired
	AccountCustomerRepository accountCustomerRepository;

	@Autowired
	CreditCardAccountRepository creditCardAccountRepository;

	@Override
	public Mono<AccountCustomer> crearCuenta(Mono<AccountCustomer> countCustomer) {
		return countCustomer.flatMap(account -> {
			if (account.getCreatedAt() == null) {
				account.setCreatedAt(new Date());
			}

			account.setNumberAccount(generateNumberAccount(192));
			account.setNumberInterbankAccount("001" + account.getNumberAccount() + "91");
			account.setIndDel("0");

			return countCustomer.flatMap(accountCustomerRepository::insert);
		});
	}

	public static String generateNumberAccount(int prefix) {
		Random rand = new Random();
		long x = (long) (rand.nextDouble() * 100000000000000L);
		return String.valueOf(prefix) + String.format("%014d", x);
	}

	@Override
	public Mono<AccountCustomer> findById(String id) {
		return accountCustomerRepository.findById(id);
	}

	@Override
	public Flux<AccountCustomer> findByCustomerId(String customerId) {
		return accountCustomerRepository.findByCustomerId(customerId);
	}

	@Override
	public Mono<AccountCustomer> findByNumberCard(String numberCard) {
		return creditCardAccountRepository.findByNumberCardAndIndPrimary(numberCard, "1").flatMap(cc -> {
			return accountCustomerRepository.findById(cc.getProductId());
		});
	}

}
