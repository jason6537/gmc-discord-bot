package com.project.ks.gmc_discord_bot.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @GetMapping("/healthCheck")
    public String simpleHealthCheck(){
        return "Healthy";
    }

}
