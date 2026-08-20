package com.project.ks.gmc_discord_bot.configuration;

import net.socketconnection.jva.ValorantAPI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class ValorantAPIConfiguration {

    @Value("${VALORANT_API_TOKEN}")
    private String TOKEN;

    @Bean
    public ValorantAPI valorantAPI() throws IOException {
        return new ValorantAPI(TOKEN);
    }

}
