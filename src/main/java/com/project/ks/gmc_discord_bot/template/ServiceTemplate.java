package com.project.ks.gmc_discord_bot.template;

import com.project.ks.gmc_discord_bot.context.DiscordOperationContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceTemplate {

    /**
     * Logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceTemplate.class);

    public static <T> void executeLogic(T payload, ServiceCallback<T> callback){

        //Init Thread Local
        DiscordOperationContextUtil.initThreadLocal();

        try {
            //Event Triggered on The Discord Handler
            LOGGER.info("Event toggled on the discord handler");

            //Execute Discord Message Filtering
            if(callback.filterMessage(payload)){
                LOGGER.info("Message does not need to be executed payload : ", payload);
                return;
            }

            //Execute Logic
            callback.handleEvent(payload);

        } catch (Exception e) {

            LOGGER.info("Unknown Exception occurred on the service : ", e);

        } finally {

            //Clear the discord context every single actions from the event
            DiscordOperationContextUtil.clearContext();
        }
    }

}
