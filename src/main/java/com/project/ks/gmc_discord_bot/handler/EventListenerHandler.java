package com.project.ks.gmc_discord_bot.handler;

import com.project.ks.gmc_discord_bot.BotConstants;
import com.project.ks.gmc_discord_bot.facade.MessageCommandHandler;
import com.project.ks.gmc_discord_bot.processor.impl.ValorantStoreFrontService;
import com.project.ks.gmc_discord_bot.template.ServiceCallback;
import com.project.ks.gmc_discord_bot.template.ServiceTemplate;
import lombok.Setter;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.restaction.MessageCreateAction;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@Setter
public class EventListenerHandler extends ListenerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventListenerHandler.class);

    private static final List<String> curseWords = new ArrayList<>();


    private static final List<String> whiteListCurseWordList = new ArrayList<>();

    static {
        curseWords.add("，我操你妈！");
        curseWords.add(", Pokai 你老母");
        curseWords.add(", 乌拉呀哈呀哈乌啦 ");
    }

    @Autowired
    private MessageCommandHandler messageCommandHandler;

    @Autowired
    private ValorantStoreFrontService valorantStoreFrontService;

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        ServiceTemplate.executeLogic(
                event,
                new ServiceCallback<MessageReceivedEvent>() {
                    @Override
                    public void handleEvent(MessageReceivedEvent event) {
                        LOGGER.info("Message Received ! : Content : {}", event.getMessage().getContentRaw());

                        String authorId = event.getAuthor().getId();

                        // YxY Message
                        if(whiteListCurseWordList.contains(authorId)){
                            String mention = "<@" + authorId + ">";
                            Random random = new Random();
                            MessageCreateAction messageCreateAction = event.getMessage()
                                    .getChannel()
                                    .sendMessage(mention + getCurseWords(random.nextInt(curseWords.size())));
                            messageCreateAction.queue();
                        }

                        //Yu Wei Feature
                        if(event.getMessage()
                                .getContentRaw()
                                .contains("<:yw:1400840577469386873>")){
                            String userId = "833334498241282060";
                            String mention = "<@" + userId + ">";
                            MessageCreateAction messageCreateAction = event.getMessage()
                                    .getChannel()
                                    .sendMessage(mention + ", 有人想你了!");
                            messageCreateAction.queue();
                        }

                        //Melon Feature
                        if(event.getMessage().getContentRaw().contains("<:melonsmile:1334398411369222154>")){
                            String userId = "524916028048932875";
                            String mention = "<@" + userId + ">";
                            MessageCreateAction messageCreateAction = event.getMessage()
                                    .getChannel()
                                    .sendMessage(mention + ", 你是傻逼!\n By yxy");
                            messageCreateAction.queue();
                        }

                        //Na Tie Feature
                        if(event.getMessage().getContentRaw().contains("<:NatieSleeping:1399751611273838695>")){
                            String userId = "754345471211995208";
                            String mention = "<@" + userId + ">";
                            MessageCreateAction messageCreateAction = event.getMessage()
                                    .getChannel()
                                    .sendMessage(mention + ", 你是我的小绵羊!\n By yxy");
                            messageCreateAction.queue();
                        }

                        //If Not Bot Command Do Nothing
                        if(!isBotCommand(event)){
                            return;
                        }

                        //Call Handler
                        messageCommandHandler.handleMessage(event);
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

    private String getCurseWords(int index){
        return curseWords.get(index);
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
