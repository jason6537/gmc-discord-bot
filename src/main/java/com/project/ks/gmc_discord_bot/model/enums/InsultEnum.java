package com.project.ks.gmc_discord_bot.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum InsultEnum {

    MELON("","","");

    private String containsString;

    private String insultId;

    private String msg;
}
