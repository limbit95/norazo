package edu.kh.norazo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import lombok.Getter;


@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class NorazoApplication {

	public static void main(String[] args) {
		SpringApplication.run(NorazoApplication.class, args);
	}

}
