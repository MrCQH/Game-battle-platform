package com.kob.backend.controller.user.bot;

import com.kob.backend.service.user.bot.UpdataBotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UpdataBotcontroller {
    @Autowired
    private UpdataBotService updataBotService;

    @PostMapping("/api/user/bot/modify/")
    public Map<String, String> modify(@RequestParam Map<String, String> data){
        return updataBotService.updata(data);
    }

}
