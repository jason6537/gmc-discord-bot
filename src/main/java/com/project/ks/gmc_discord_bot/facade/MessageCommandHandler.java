package com.project.ks.gmc_discord_bot.facade;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

/**
 * MessageCommandHandler
 */
public interface MessageCommandHandler {

    /**
     * Facade Pattern
     * Handling Command From Bot
     * @param messageReceivedEvent : Event
     */
    void handleMessage(MessageReceivedEvent messageReceivedEvent);

}
