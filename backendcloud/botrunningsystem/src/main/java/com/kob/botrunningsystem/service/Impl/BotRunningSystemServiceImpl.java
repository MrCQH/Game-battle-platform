package com.kob.botrunningsystem.service.Impl;

import com.kob.botrunningsystem.service.BotRunningSystemService;
import com.kob.botrunningsystem.service.utils.BotPool;
import org.springframework.stereotype.Service;

@Service
public class BotRunningSystemServiceImpl implements BotRunningSystemService {
    public final static BotPool botpool = new BotPool();

    @Override
    public String addBot(Integer userId, String botCode, String input) {
        System.out.println("add bot: " + userId + " " + botCode + " " + input);
        botpool.addBot(userId, botCode, input);
        return "add bot success!";
    }
}
