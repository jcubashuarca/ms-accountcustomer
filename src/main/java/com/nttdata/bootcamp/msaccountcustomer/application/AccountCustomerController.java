package com.nttdata.bootcamp.msaccountcustomer.application;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.nttdata.bootcamp.msaccountcustomer.external.model.Customer;
import com.nttdata.bootcamp.msaccountcustomer.external.model.MyCustomException;
import com.nttdata.bootcamp.msaccountcustomer.model.AccountCustomer;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/accountcustomer")
@RefreshScope
public class AccountCustomerController {
	
	@Autowired
	WebClient.Builder webClientBuilder;
	
	@Autowired
	AccountCustomerService accountCustomerService;
	
	@PostMapping
	public Mono<AccountCustomer> insertAccountCustomer(@RequestBody AccountCustomer accountCustomer){
		accountCustomer.setCreatedAt(new Date());
		accountCustomer.setContableBalance(accountCustomer.getAvailableBalance());
		
	return getUserRating(accountCustomer.getCustomerId()).flatMap(c->{
		 return accountCustomerService.crearCuenta(Mono.just(accountCustomer));
	 });
		
	}
	
	private Mono<Customer> getUserRating(String userId) {
		  return webClientBuilder.build()
		    .get()
		    .uri("http://service-customer/customer/" + userId)
		    .retrieve()
		    .bodyToMono(Customer.class).onErrorResume(MyCustomException.class, ex->{
		    	 log.error(ex.getMessage());
		            return Mono.empty();
		    });
		}
	
	
	
	
	@GetMapping("/{id}")
	public Mono<ResponseEntity<AccountCustomer>> findById(@PathVariable String id) {
		log.info(id);
		return accountCustomerService.findById(id)
				.map(p -> ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(p))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}
	
	/**
	 * •	Permitir elaborar un resumen consolidado de un cliente con todos los productos que
	 *  pueda tener en el banco
	 * */
	@GetMapping("/customer/{customerId}")
	public Flux<AccountCustomer> findByCostumer(@PathVariable String customerId) {
		log.info(customerId);
		return accountCustomerService.findByCustomerId(customerId);
	}
	
	
	/**
	 * •	Consultar el saldo de la cuenta principal asociada a la tarjeta de débito. 
	 * 
	 * */
	@GetMapping("/numberCard/{numberCard}")
	public Mono<ResponseEntity<AccountCustomer>> findByNumberCard(@PathVariable String numberCard) {
		log.info(numberCard);
		return accountCustomerService.findByNumberCard(numberCard)
				.map(p -> ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(p))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}
	

}
