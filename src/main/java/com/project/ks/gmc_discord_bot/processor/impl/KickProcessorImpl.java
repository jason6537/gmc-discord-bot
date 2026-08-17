package com.project.ks.gmc_discord_bot.processor.impl;

import com.project.ks.gmc_discord_bot.processor.CommandProcessor;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.stereotype.Component;

@Component("KICK")
public class KickProcessorImpl implements CommandProcessor {

    @Override
    public void process(MessageReceivedEvent event) {
        User author = event.getAuthor();
        Guild guild = event.getGuild();
        guild.kick(author);
    }

    @Override
    public boolean needProcess(MessageReceivedEvent event) {
        return false;
    }
}
