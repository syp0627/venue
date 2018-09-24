package com.yupeng.venue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
public class VenueApplication {

	public static void main(String[] args) {
		SpringApplication.run(VenueApplication.class, args);
	}
}
