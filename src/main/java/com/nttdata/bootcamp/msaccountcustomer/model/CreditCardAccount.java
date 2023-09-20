package com.nttdata.bootcamp.msaccountcustomer.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "creditcardaccount")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreditCardAccount {
	
	@Id
	private String id;
	private String numberCard;
	private String  productId;
	private String indPrimary;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createdAt;
}
