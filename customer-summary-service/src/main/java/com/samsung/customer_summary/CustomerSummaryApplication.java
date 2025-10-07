package com.samsung.customer_summary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class CustomerSummaryApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerSummaryApplication.class, args);
	}

}
