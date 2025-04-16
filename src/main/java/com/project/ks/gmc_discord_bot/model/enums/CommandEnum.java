package com.project.ks.gmc_discord_bot.model.enums;

import lombok.Getter;

@Getter
public enum CommandEnum {

    /**
     * Ping Command
     */
    PING("PING", "Ping on Bot");

    /**
     * Command Code
     */
    private final String code;

    /**
     * Description
     */
    private final String description;

    CommandEnum(String code, String description){
        this.code = code;
        this.description = description;
    }

    public static CommandEnum getByCode(String code){
        CommandEnum[] commands = values();

        for(CommandEnum command : commands){
            if(command.code.equals(code)){
                return command;
            }
        }
        return null;
    }

}
