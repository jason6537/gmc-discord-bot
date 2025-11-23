package com.project.ks.gmc_discord_bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GmcDiscordBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(GmcDiscordBotApplication.class, args);
	}

}
