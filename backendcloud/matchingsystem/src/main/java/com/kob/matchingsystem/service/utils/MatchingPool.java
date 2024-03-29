package com.kob.matchingsystem.service.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class MatchingPool extends Thread{
    private static List<Player> players = new ArrayList<>();
    private Lock lock = new ReentrantLock();
    private static RestTemplate restTemplate;
    private static final String startGameUrl = "http://kobBackend:3000/battle/start/game/";

    @Autowired
    private void setRestTemplate(RestTemplate restTemplate){
        MatchingPool.restTemplate = restTemplate;
    }

    public void addPlayer(Integer userId, Integer rating, Integer botId){
        lock.lock();
        try {
            players.add(new Player(userId, rating, botId, 0));
        } finally {
            lock.unlock();
        }
    }

    public void removePlayer(Integer userId){
        lock.lock();
        try {
            List<Player> newPlayers = new ArrayList<>();
            for (Player player : players){
                if (!player.getUserId().equals(userId)){
                    newPlayers.add(player);
                }
            }
            players = newPlayers;
        } finally {
            lock.unlock();
        }
    }

    private void increaseWaitingTime(){
        for (Player player : players){
            player.setWaitingTime(player.getWaitingTime() + 1);
        }
    }

    private boolean checkMatched(Player a, Player b){ // 检查是否满足条件
        int ratingDelta = Math.abs(a.getRating() - b.getRating());
        // 即满足a的需求，又满足了b的需求
        int waiting = Math.min(a.getWaitingTime(), b.getWaitingTime());
        return ratingDelta <= waiting * 10;
    }

    private void sendResult(Player a, Player b){
        System.out.println("start game!");
        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("a_id", a.getUserId().toString());
        data.add("b_id", b.getUserId().toString());
        data.add("a_bot_id", a.getBotId().toString());
        data.add("b_bot_id", b.getBotId().toString());
        restTemplate.postForObject(startGameUrl, data, String.class);
    }

    private void startMatching(){ //尝试匹配
//        System.out.println("players: " + players.toString());
        List<Player> newPlayers = new ArrayList<>();
        boolean[] used = new boolean[players.size()];
        if (!players.isEmpty()){
            for (int i = 0; i < players.size(); i ++){
                if (used[i]) continue;
                for (int j = i + 1; j < players.size(); j ++){
                    if (used[j]) continue;
                    Player a = players.get(i), b = players.get(j);
                    if (checkMatched(a, b)){
                        used[i] = used[j] = true;
                        sendResult(a, b);
                    }
                }
            }
            for (int i = 0; i < players.size(); i ++){
                if (!used[i]){
                    newPlayers.add(players.get(i));
                }
            }
        }
        players = newPlayers;
    }

    @Override
    public void run() {
        while(true){
            try {
                Thread.sleep(1000);
                lock.lock();
                try{
                    increaseWaitingTime();
                    startMatching();
                } finally {
                    lock.unlock();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
