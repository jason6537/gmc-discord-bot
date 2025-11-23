package com.project.ks.gmc_discord_bot.scheduler;

import com.project.ks.gmc_discord_bot.processor.impl.ValorantStoreFrontService;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class DailyNotifyScheduler {

    private static final String ANNOUNCEMENT_CHANNEL_ID = "1438544130014842950";

    private static final Logger LOGGER = LoggerFactory.getLogger(DailyNotifyScheduler.class);

    @Autowired
    private JDA bot;

    @Autowired
    private ValorantStoreFrontService valorantStoreFrontService;

    @Scheduled(cron = "0 0 8 * * ?")
    public void notifyValorantStoreOpen(){
        TextChannel channelById = bot.getChannelById(TextChannel.class, ANNOUNCEMENT_CHANNEL_ID);

        if(Objects.isNull(channelById)){
            LOGGER.error("Channel Is Null from object channel: " + channelById);
            return;
        }

        channelById.sendMessage(" 商店已刷新 ！ 记得看下 Valorant 商店").queue();

        List<EmbedBuilder> embedBuilders = valorantStoreFrontService.invokeValorantAPI();
        embedBuilders
                .stream()
                .forEach(
                        e -> {
                            channelById.sendMessage("").setEmbeds(e.build()).queue();
                        }
                );
    }

}
