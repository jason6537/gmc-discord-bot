package com.project.ks.gmc_discord_bot.processor;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public interface CommandProcessor {

    /**
     * Process the Business Logic for the Command
     * @param event : Message Event
     */
    void process(MessageReceivedEvent event);
}
