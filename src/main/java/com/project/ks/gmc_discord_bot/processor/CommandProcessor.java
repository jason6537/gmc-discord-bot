package com.project.ks.gmc_discord_bot.processor;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.IOException;

public interface CommandProcessor {

    /**
     * Process the Business Logic for the Command
     * @param event : Message Event
     */
    void process(MessageReceivedEvent event) throws IOException;

    /**
     * Does the event need to process
     */
    boolean needProcess(MessageReceivedEvent event);
}
