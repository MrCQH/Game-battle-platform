package com.kob.botrunningsystem.service.utils;

public class Bot {
    private Integer userId;
    private String botCode;
    private String input;

    public Bot() {
    }

    public Bot(Integer userId, String botCode, String input) {
        this.userId = userId;
        this.botCode = botCode;
        this.input = input;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getBotCode() {
        return botCode;
    }

    public void setBotCode(String botCode) {
        this.botCode = botCode;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    @Override
    public String toString() {
        return "Bot{" +
                "userId=" + userId +
                ", botCode='" + botCode + '\'' +
                ", input='" + input + '\'' +
                '}';
    }
}
