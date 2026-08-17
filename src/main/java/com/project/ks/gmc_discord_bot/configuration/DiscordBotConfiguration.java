package com.project.ks.gmc_discord_bot.configuration;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
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
                .enableIntents(GatewayIntent.GUILD_VOICE_STATES) // REQUIRED!
                .enableCache(CacheFlag.VOICE_STATE)             // REQUIRED!
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .addEventListeners(eventListenerHandler)
                .build();
    }

    @Bean
    public AudioPlayerManager audioPlayerManager() {
        AudioPlayerManager playerManager = new DefaultAudioPlayerManager();
        AudioSourceManagers.registerLocalSource(playerManager);
        AudioSourceManagers.registerRemoteSources(playerManager);
        return playerManager;
    }

}
