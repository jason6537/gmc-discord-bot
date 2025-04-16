package com.project.ks.gmc_discord_bot.context;

import java.util.Date;

public class DiscordOperationContextUtil {

    public static final ThreadLocal<Date> executionTime = new ThreadLocal<>();

    public static void initThreadLocal(){
        executionTime.set(new Date());
    }

    public static void clearContext(){
        executionTime.remove();
    }
}
