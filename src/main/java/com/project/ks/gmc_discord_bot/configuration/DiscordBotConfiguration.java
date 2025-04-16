package com.project.ks.gmc_discord_bot.configuration;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class DiscordBotConfiguration {

    /**
     * Discord Token
     */
    @Value("${discord.api.token}")
    private String TOKEN;

    /**
     * PostgresDB Password
     */
    @Value("${db.postgres.password}")
    private String dbPassword;

    @Bean
    public JDA initBot(@Autowired ListenerAdapter eventListenerHandler){
        return JDABuilder
                .createLight(TOKEN,
                        GatewayIntent.MESSAGE_CONTENT,
                        GatewayIntent.GUILD_MESSAGES,
                        GatewayIntent.GUILD_PRESENCES,
                        GatewayIntent.GUILD_MEMBERS,
                        GatewayIntent.GUILD_MESSAGE_REACTIONS
                )
                .addEventListeners(eventListenerHandler)
                .build();
    }

}
