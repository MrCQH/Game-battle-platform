package com.kob.backend.consumer.utils;


import java.util.ArrayList;
import java.util.List;


public class Player {
    private Integer id;
    private Integer botId;
    private String botCode;
    private Integer sx;
    private Integer sy;
    private List<Integer> steps; // 记录后续蛇走的一系列操作

    public Player() {
    }

    public Player(Integer id, Integer botId, String botCode, Integer sx, Integer sy, List<Integer> steps) {
        this.id = id;
        this.botId = botId;
        this.botCode = botCode;
        this.sx = sx;
        this.sy = sy;
        this.steps = steps;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBotId() {
        return botId;
    }

    public void setBotId(Integer botId) {
        this.botId = botId;
    }

    public String getBotCode() {
        return botCode;
    }

    public void setBotCode(String botCode) {
        this.botCode = botCode;
    }

    public Integer getSx() {
        return sx;
    }

    public void setSx(Integer sx) {
        this.sx = sx;
    }

    public Integer getSy() {
        return sy;
    }

    public void setSy(Integer sy) {
        this.sy = sy;
    }

    public List<Integer> getSteps() {
        return steps;
    }

    public void setSteps(List<Integer> steps) {
        this.steps = steps;
    }

    private boolean checkTailIncreasing(Integer step){ // 蛇尾3回合增长一个单位长度
        if (step <= 10) return true;
        return step % 3 == 1;
    }

    public List<Cell> getCells(){
        List<Cell> res = new ArrayList<>();

        int[] dx = {-1, 0, 1, 0}, dy = {0, 1, 0, -1};
        int x = sx, y = sy;
        res.add(new Cell(x, y));
        int step = 0;
        for (Integer d: steps){
            x += dx[d];
            y += dy[d];
            res.add(new Cell(x, y));
            if (!checkTailIncreasing( ++ step)){
                res.remove(0);
            }
        }
        return res;
    }

    public String getStepString(){
        StringBuilder res = new StringBuilder();
        for (Integer step: steps){
            res.append(step);
        }
        return res.toString();
    }
}
