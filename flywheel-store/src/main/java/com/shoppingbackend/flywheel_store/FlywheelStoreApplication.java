package com.shoppingbackend.flywheel_store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Async;

@SpringBootApplication
@Async
public class FlywheelStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlywheelStoreApplication.class, args);
	}

}
