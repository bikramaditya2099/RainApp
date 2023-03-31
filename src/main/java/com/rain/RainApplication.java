package com.rain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

import com.rain.utils.InvokeAllDefaultDBOperations;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class RainApplication {
public static void main(String[] args) {

		
		InvokeAllDefaultDBOperations.invokeAllDefaultTask();
		
		SpringApplication.run(RainApplication.class, args);
	}
}
