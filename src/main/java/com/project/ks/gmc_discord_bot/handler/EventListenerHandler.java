package com.project.ks.gmc_discord_bot.handler;

import com.project.ks.gmc_discord_bot.BotConstants;
import com.project.ks.gmc_discord_bot.context.DiscordOperationContextUtil;
import com.project.ks.gmc_discord_bot.facade.MessageCommandHandler;
import lombok.Setter;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.restaction.MessageCreateAction;
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

        //BlackList
        //if(BotConstants.backlist.contains(event.getAuthor().getId())){
           // MessageCreateAction messageCreateAction = event.getMessage().getChannel().sendMessage("操你妈， 我恨你");
          //  messageCreateAction.queue();
         //   return;
        //}

        //Yu Wei Feature
        if(event.getMessage().getContentRaw().contains("<:yw:1400840577469386873>")){
            String userId = "833334498241282060";
            String mention = "<@" + userId + ">";
            MessageCreateAction messageCreateAction = event.getMessage().getChannel().sendMessage(mention + ", 有人想你了!");
            messageCreateAction.queue();
        }


        //Yu Wei Feature
        if(event.getMessage().getContentRaw().contains("<:melonsmile:1334398411369222154>")){
            String userId = "524916028048932875";
            String mention = "<@" + userId + ">";
            MessageCreateAction messageCreateAction = event.getMessage().getChannel().sendMessage(mention + ", 你是傻逼!\n By yxy");
            messageCreateAction.queue();
        }


        //Na Tie Feature
        if(event.getMessage().getContentRaw().contains("<:NatieSleeping:1399751611273838695>")){
            String userId = "754345471211995208";
            String mention = "<@" + userId + ">";
            MessageCreateAction messageCreateAction = event.getMessage().getChannel().sendMessage(mention + ", 你是我的小绵羊!\n By yxy");
            messageCreateAction.queue();
        }

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
