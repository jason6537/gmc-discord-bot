package com.project.ks.gmc_discord_bot.facade.impl;

import com.project.ks.gmc_discord_bot.BotConstants;
import com.project.ks.gmc_discord_bot.facade.MessageCommandHandler;
import com.project.ks.gmc_discord_bot.model.enums.CommandEnum;
import com.project.ks.gmc_discord_bot.processor.CommandProcessor;
import lombok.AllArgsConstructor;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
public class MessageCommandHandlerImpl implements MessageCommandHandler{

    @Autowired
    private Map<String, CommandProcessor> processorMap;

    @Override
    public void handleMessage(MessageReceivedEvent messageReceivedEvent) {
        Message message = messageReceivedEvent.getMessage();
        List<String> args = parseArguments(message.getContentRaw());

        //Convert Into Enum
        CommandEnum commandEnum = CommandEnum.getByCode(args.get(0));

        //Assert Check
        Assert.notNull(commandEnum, "UNKNOWN Command");

        //For Each Command Get A Processor
        CommandProcessor processor = processorMap.get(commandEnum.name());

        //Handle Command
        processor.process(messageReceivedEvent);
    }

    /**
     * Parse Arguments
     * @param content :
     * @return
     */
    public List<String> parseArguments(String content){

        List<String> args = Arrays.stream(content.split(BotConstants.WHITESPACE))
                .toList();

        return args.subList(1, args.size());
    }

}
