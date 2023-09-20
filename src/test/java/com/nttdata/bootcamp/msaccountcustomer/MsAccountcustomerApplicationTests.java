package com.nttdata.bootcamp.msaccountcustomer;

import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.autoconfigure.RefreshAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.nttdata.bootcamp.msaccountcustomer.application.AccountCustomerController;
import com.nttdata.bootcamp.msaccountcustomer.application.AccountCustomerService;
import com.nttdata.bootcamp.msaccountcustomer.model.AccountCustomer;

import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@WebFluxTest(AccountCustomerController.class)
@ImportAutoConfiguration(RefreshAutoConfiguration.class)
class MsAccountcustomerApplicationTests {
	
	@Autowired
	private WebTestClient webTestClient;
	
	@MockBean
	AccountCustomerService accountCustomerService;

	@Test
	void saveAccount() {
		
		AccountCustomer object =  new AccountCustomer();
		object.setId("65656565");
		object.setProductId("ddddsdssd4343sd");
		object.setNumberAccount("1925555");
		object.setAvailableBalance(46d);
		object.setCustomerId("646466");
		object.setCreatedAt(new Date());
		object.setIndDel("0");
		object.setNumberInterbankAccount("65656565");
		
		Mono<AccountCustomer> accountCustomer = Mono.just(object);
			when(accountCustomerService.crearCuenta(accountCustomer)).thenReturn(accountCustomer);
			webTestClient.post().uri("/accountcustomer")
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
			.body(Mono.just(accountCustomer), AccountCustomer.class)
			.exchange()
			.expectStatus().isCreated();

	}

}
