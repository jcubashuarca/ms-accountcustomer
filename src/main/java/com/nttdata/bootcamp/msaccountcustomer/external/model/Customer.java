package com.nttdata.bootcamp.msaccountcustomer.external.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;

@Document(collection = "customer")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    private String id;
    private String documentNumber;
    private String name;
    private String lastname;
    private String  typeCustomerId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createdAt;
    
	
    
    

}
