package com.project.ks.gmc_discord_bot.template;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

/**
 * Discord Service
 * Template for the message handler for the commands
 */
public interface ServiceCallback<T> {

    /**
     * Handle Business Logic
     * @param event : Event
     */
    void handleEvent(T event);

    /**
     * Filter The Event Message
     * @param event : Event
     */
    boolean filterMessage(T event);

    /**
     * Get Digest Log From The Handler
     * @return digest log
     */
     String getDigestLog();
}
