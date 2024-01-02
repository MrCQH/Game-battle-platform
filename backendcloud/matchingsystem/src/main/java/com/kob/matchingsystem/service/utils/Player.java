package com.kob.matchingsystem.service.utils;


public class Player {
    private Integer userId;
    private Integer rating;
    private Integer botId;
    private Integer waitingTime; // 等待时间

    public Player() {
    }

    public Player(Integer userId, Integer rating, Integer botId, Integer waitingTime) {
        this.userId = userId;
        this.rating = rating;
        this.botId = botId;
        this.waitingTime = waitingTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Integer getBotId() {
        return botId;
    }

    public void setBotId(Integer botId) {
        this.botId = botId;
    }

    public Integer getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(Integer waitingTime) {
        this.waitingTime = waitingTime;
    }

    @Override
    public String toString() {
        return "Player{" +
                "userId=" + userId +
                ", rating=" + rating +
                ", botId=" + botId +
                ", waitingTime=" + waitingTime +
                '}';
    }
}
