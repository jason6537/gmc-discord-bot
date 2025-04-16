package com.project.ks.gmc_discord_bot.handler;

import com.project.ks.gmc_discord_bot.BotConstants;
import com.project.ks.gmc_discord_bot.context.DiscordOperationContextUtil;
import com.project.ks.gmc_discord_bot.facade.MessageCommandHandler;
import lombok.Setter;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Setter
public class EventListenerHandler extends ListenerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventListenerHandler.class);

    @Autowired
    private MessageCommandHandler messageCommandHandler;

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        DiscordOperationContextUtil.initThreadLocal();

        LOGGER.info("Message Received ! : Content : {}", event.getMessage().getContentRaw());

        //If Not Bot Command Do Nothing
        if(!isBotCommand(event)){
            return;
        }

        //Call Handler
        messageCommandHandler.handleMessage(event);

        DiscordOperationContextUtil.clearContext();
    }

    @Override
    public void onMessageReactionAdd(@NotNull MessageReactionAddEvent event) {

        LOGGER.info("Reacted Some Cool Msg : {}", event.toString());
    }

    /**
     * Check If is bot command
     * @param event : Message Received Event
     * @return True is Bot Command Else False
     */
    private boolean isBotCommand(MessageReceivedEvent event){

        //Avoid Do Anything if the Sender is bot
        if(event.getAuthor().isBot()){
            return false;
        }

        //Obtain Message
        Message message = event.getMessage();
        String content = message.getContentRaw();

        //Validate String
        return content.split(BotConstants.WHITESPACE)[0].equals(BotConstants.ALIAS);
    }
}
