package com.kob.botrunningsystem.controller;

import com.kob.botrunningsystem.service.BotRunningSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class BotRunningSystemServiceController {
    @Autowired
    private BotRunningSystemService botRunningSystemService;

    @PostMapping("/bot/add/")
    public String addBot(@RequestParam MultiValueMap<String, String> data){
        Integer userId = Integer.parseInt(Objects.requireNonNull(data.getFirst("user_id")));
        String botCode = data.getFirst("bot_code");
        String input = data.getFirst("input");
        return botRunningSystemService.addBot(userId, botCode, input);
    }
}
