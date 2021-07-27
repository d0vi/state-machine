package com.imatia.statemachine.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Entry point of the application.
 */
@EntityScan(basePackages = {"com.imatia.statemachine.domain.model"})
@EnableJpaRepositories(basePackages={"com.imatia.statemachine.domain.repository"})
@SpringBootApplication(scanBasePackages={"com.imatia.statemachine"})
public class StateMachineApplication {

	public static void main(String[] args) {
		SpringApplication.run(StateMachineApplication.class, args);
	}

}
