package com.josuecamelo.marsweather;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MarsWeatherApplication {

	public static void main(String[] args) {
		SpringApplication.run(MarsWeatherApplication.class, args);
	}
}
