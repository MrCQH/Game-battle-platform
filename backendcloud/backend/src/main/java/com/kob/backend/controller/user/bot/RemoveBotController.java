package com.kob.backend.controller.user.bot;

import com.kob.backend.service.user.bot.RemoveBotservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class RemoveBotController{
    @Autowired
    private RemoveBotservice removeBotservice;

    @PostMapping("/api/user/bot/remove/")
    public Map<String, String> remove(@RequestParam Map<String, String> data){
        return removeBotservice.remove(data);
    }
}
