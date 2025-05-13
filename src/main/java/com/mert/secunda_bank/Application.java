package com.mert.secunda_bank;

import com.mert.secunda_bank.models.Transaction;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
	// bank's itself is not implemented
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
