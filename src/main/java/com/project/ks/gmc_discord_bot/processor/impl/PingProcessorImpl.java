package com.project.ks.gmc_discord_bot.processor.impl;

import com.project.ks.gmc_discord_bot.context.DiscordOperationContextUtil;
import com.project.ks.gmc_discord_bot.processor.CommandProcessor;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.requests.restaction.MessageCreateAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Ping Processor, When user want's to ping the bot
 */
@Component("PING")
public class PingProcessorImpl implements CommandProcessor {

    private static final Logger LOGGER  = LoggerFactory.getLogger(PingProcessorImpl.class);

    @Override
    public void process(MessageReceivedEvent event) {

        LOGGER.info("Ping Action Received From {}", event.getAuthor().getName());

        Date startTime = DiscordOperationContextUtil.executionTime.get();
        Date endTime = new Date();
        long diff = endTime.getTime() - startTime.getTime();

        MessageChannel channel = event.getChannel();
        MessageCreateAction messageCreateAction = channel.sendMessage("PONG! with " + diff + "ms");
        messageCreateAction.queue();
    }
}
