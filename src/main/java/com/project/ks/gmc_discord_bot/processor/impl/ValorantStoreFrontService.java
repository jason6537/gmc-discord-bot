package com.project.ks.gmc_discord_bot.processor.impl;

import net.dv8tion.jda.api.EmbedBuilder;
import net.socketconnection.jva.ValorantAPI;
import net.socketconnection.jva.models.shop.Bundle;
import net.socketconnection.jva.models.shop.item.BundleItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class ValorantStoreFrontService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ValorantStoreFrontService.class);

    public List<EmbedBuilder> invokeValorantAPI() {
        try {
            ValorantAPI api = new ValorantAPI("HDEV-2fce41cf-ac50-46d7-adae-ae3f5e96448c");
            List<Bundle> storeBundles = api.getStoreBundles();
            BundleItem[] items = (BundleItem[]) storeBundles.get(0).getItems();

            return Arrays.stream(items)
                    .map(this::convertToEmbed)
                    .toList();
        } catch (Throwable e) {
            LOGGER.error("error found : ", e);
            return Collections.emptyList();
        } finally {
            LOGGER.info("API Called Done");
        }
    }

    private EmbedBuilder convertToEmbed(BundleItem item){

        if(item == null){
            return null;
        }

        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle(item.getName());
        eb.setImage(item.getImage());
        eb.setAuthor(item.getSkinId());
        eb.setDescription("RP : " + item.getBasePrice());

        return eb;
    }

}
