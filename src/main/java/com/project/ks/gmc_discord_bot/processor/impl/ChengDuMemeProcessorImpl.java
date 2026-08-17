package com.project.ks.gmc_discord_bot.processor.impl;

import com.project.ks.gmc_discord_bot.audioService.AudioService;
import com.project.ks.gmc_discord_bot.audioService.AudioTrackListener;
import com.project.ks.gmc_discord_bot.processor.CommandProcessor;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class ChengDuMemeProcessorImpl implements CommandProcessor {

    public static final String YXY_AUTHOR_ID = "604674402591834153";

    public static final String CHENGDUTEST = "chengdutest";

    public static final String CHENGDU_CHANNEL_ID = "1354432520275497030";

    @Autowired
    private AudioService audioService;

    @Override
    public void process(MessageReceivedEvent event) throws IOException {

        VoiceChannel targetChannel = event.getGuild().getVoiceChannelById(CHENGDU_CHANNEL_ID);

        List<Member> members = event.getMessage().getMentions().getMembers();

        members.forEach(e -> {
            event.getGuild().moveVoiceMember(e, targetChannel).queue();
        });

        //audioService.playMp3(event.getGuild(), targetChannel, "audio/gay.mp3", new AudioTrackListener(event.getGuild()));
    }

    @Override
    public boolean needProcess(MessageReceivedEvent event) {
        return event.getMessage().getContentRaw().contains(CHENGDUTEST) && !event.getMessage().getAuthor().getId().equals(YXY_AUTHOR_ID);
    }
}
