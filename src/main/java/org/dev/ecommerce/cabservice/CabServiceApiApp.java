package org.dev.ecommerce.cabservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={"org.dev.ecommerce.cabservice"})
public class CabServiceApiApp {

	public static void main(String[] args) {
		SpringApplication.run(CabServiceApiApp.class, args);
	}
}
