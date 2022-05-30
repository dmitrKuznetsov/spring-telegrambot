package com.github.dmitrKuznetsov.stb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SpringJavaRushTelegramBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringJavaRushTelegramBotApplication.class, args);
	}

}
