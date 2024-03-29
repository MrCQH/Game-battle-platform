package com.kob.botrunningsystem;

import com.kob.botrunningsystem.service.Impl.BotRunningSystemServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BotRunningSystemApplication {
    public static void main(String[] args) {
        BotRunningSystemServiceImpl.botpool.start();
        SpringApplication.run(BotRunningSystemApplication.class, args);
    }
}
