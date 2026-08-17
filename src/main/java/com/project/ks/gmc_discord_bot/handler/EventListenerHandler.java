package com.project.ks.gmc_discord_bot.handler;

import com.project.ks.gmc_discord_bot.BotConstants;
import com.project.ks.gmc_discord_bot.facade.MessageCommandHandler;
import com.project.ks.gmc_discord_bot.processor.CommandProcessor;
import com.project.ks.gmc_discord_bot.template.ServiceCallback;
import com.project.ks.gmc_discord_bot.template.ServiceTemplate;
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

import java.io.IOException;
import java.util.List;

@Component
@Setter
public class EventListenerHandler extends ListenerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventListenerHandler.class);

    @Autowired
    private MessageCommandHandler messageCommandHandler;

    @Autowired
    private List<CommandProcessor> processors;

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        ServiceTemplate.executeLogic(
                event,
                new ServiceCallback<MessageReceivedEvent>() {
                    @Override
                    public void handleEvent(MessageReceivedEvent event) throws IOException {
                        LOGGER.info(String.valueOf(event.getResponseNumber()));
                        LOGGER.info("Message Received ! : Content : {}", event.getMessage().getContentRaw());

                        //Filter Executable Processors
                        processors.stream()
                                .filter(e -> e.needProcess(event))
                                .forEach(processor -> {
                                    try {
                                        processor.process(event);
                                    } catch(Exception e) {
                                        LOGGER.error("error occured" , e);
                                    }  finally {
                                        LOGGER.debug("done handle");
                                    }
                                });
                    }

                    @Override
                    public boolean filterMessage(MessageReceivedEvent event) {
                        return false;
                    }

                    @Override
                    public String getDigestLog() {
                        return "";
                    }
                }
        );

    }

    @Override
    public void onMessageReactionAdd(@NotNull MessageReactionAddEvent event) {
        LOGGER.info("Reacted Some Cool Msg : {}", event.toString());
        LOGGER.info(event.getMessageAuthorId());
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
