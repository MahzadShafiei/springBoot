package com.mahzad.springBoot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.mahzad")
@EnableJpaRepositories(basePackages = {
        "com.mahzad.springBoot.repository",
        "com.mahzad.ipRegistery.repository"
})
@EntityScan(basePackages = {
        "com.mahzad.springBoot.model",
        "com.mahzad.ipRegistery.model"
})
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
