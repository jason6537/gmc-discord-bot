package com.project.ks.gmc_discord_bot.processor.impl;

import com.project.ks.gmc_discord_bot.processor.CommandProcessor;
import lombok.Setter;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.requests.restaction.MessageCreateAction;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@Setter
public class CurseWordsProcessorImpl implements CommandProcessor {

    private static final List<String> curseWords = new ArrayList<>();

    private static final List<String> whiteListCurseWordList = new ArrayList<>();

    static {
        curseWords.add("，我操你妈！");
        curseWords.add(", Pokai 你老母");
        curseWords.add(", 乌拉呀哈呀哈乌啦 ");
    }

    @Override
    public void process(MessageReceivedEvent event) throws IOException {
        String authorId = event.getAuthor().getId();
        String mention = "<@" + authorId + ">";
        Random random = new Random();
        MessageCreateAction messageCreateAction = event.getMessage()
                .getChannel()
                .sendMessage(mention + getCurseWords(random.nextInt(curseWords.size())));
        messageCreateAction.queue();
    }

    @Override
    public boolean needProcess(MessageReceivedEvent event) {
        return whiteListCurseWordList.contains(event.getAuthor().getId());
    }

    private String getCurseWords(int index){
        return curseWords.get(index);
    }
}
