package com.example.book_ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class BookMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookMsApplication.class, args);
	}

}
