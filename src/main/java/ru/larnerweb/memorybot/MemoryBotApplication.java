package ru.larnerweb.memorybot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MemoryBotApplication {
	public static void main(String[] args) {
		SpringApplication.run(MemoryBotApplication.class, args);
	}
}
