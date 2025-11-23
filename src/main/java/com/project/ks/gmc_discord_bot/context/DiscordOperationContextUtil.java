package com.project.ks.gmc_discord_bot.context;

import java.util.Date;

public class DiscordOperationContextUtil {

    private static final ThreadLocal<Date> executionTime = new ThreadLocal<>();

    public static void initThreadLocal(){
        executionTime.set(new Date());
    }

    public static Date getExecutionTime(){
        return executionTime.get();
    }

    public static void clearContext(){
        executionTime.remove();
    }
}
