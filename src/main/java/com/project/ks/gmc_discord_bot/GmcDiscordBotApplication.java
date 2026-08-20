package com.project.ks.gmc_discord_bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GmcDiscordBotApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(GmcDiscordBotApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(GmcDiscordBotApplication.class, args);
		LOGGER.info("Application Startup Successfully");
	}

}
