package com.kob.backend.service.impl.battle;

import com.kob.backend.consumer.WebSocketServer;
import com.kob.backend.consumer.utils.Game;
import com.kob.backend.service.battle.ReceiveBotMoveService;
import org.springframework.stereotype.Service;

@Service
public class ReceiveBotMoveServiceImpl implements ReceiveBotMoveService {

    @Override
    public String botNextStep(Integer userId, Integer direction) {
        System.out.println("bot move: " + userId + " " + direction);
        if (WebSocketServer.users.get(userId) != null){
            Game game = WebSocketServer.users.get(userId).game;
            if (game != null){
                if (game.getPlayerA().getId().equals(userId)){
                    game.setStepNextA(direction);
                } else if (game.getPlayerB().getId().equals(userId)){
                    game.setStepNextB(direction);
                }
            }
        }
        return "receive bot move success!";
    }
}
