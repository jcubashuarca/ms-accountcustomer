package com.nttdata.bootcamp.msaccountcustomer.application;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.bootcamp.msaccountcustomer.model.AccountCustomer;
import com.nttdata.bootcamp.msaccountcustomer.model.CreditCardAccount;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/creditcardaccount")
@RefreshScope
public class CreditCardAccountController {
	
	@Autowired
	CreditCardAccountService creditCardAccountService;
	
	@Value("${message.demo}")
	private String demoString;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Mono<CreditCardAccount> crear(@RequestBody CreditCardAccount creditCardAccount) {
		log.info(demoString);
		creditCardAccount.setCreatedAt(new Date());
		return creditCardAccountService.insertCreditCardAccount(Mono.just(creditCardAccount));
	}
	
	@GetMapping("/{id}")
	public Mono<ResponseEntity<CreditCardAccount>> findById(@PathVariable String id) {
		log.info(id);
		return creditCardAccountService.findById(id)
				.map(p -> ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(p))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/cuentadisponible/{id}")
	public Mono<ResponseEntity<AccountCustomer>> cuentadisponible(@PathVariable String id) {
		log.info(id);
		return creditCardAccountService.accountAvailablebalance(id)
				.map(p -> ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(p))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}
	
	
	
	
}
