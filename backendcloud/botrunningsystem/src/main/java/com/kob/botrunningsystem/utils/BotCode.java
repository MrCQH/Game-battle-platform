package com.kob.botrunningsystem.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class BotCode implements java.util.function.Supplier<Integer>{
    static class Cell{
        public int x, y;
        public Cell(int x, int y){
            this.x = x;
            this.y = y;
        }
    }
    private boolean checkTailIncreasing(Integer step){
        if (step <= 10) return true;
        return step % 3 == 1;
    }
    public List<Cell> getCells(int sx, int sy, String steps){
        List<Cell> res = new ArrayList<>();
        int[] dx = {-1, 0, 1, 0}, dy = {0, 1, 0, -1};
        int x = sx, y = sy;
        res.add(new Cell(x, y));
        int step = 0;
        for (int i = 0; i < steps.length(); i ++){
            int d = steps.charAt(i) - '0';
            x += dx[d];
            y += dy[d];
            res.add(new Cell(x, y));
            if (!checkTailIncreasing( ++ step)){
                res.remove(0);
            }
        }
        return res;
    }
    public Integer nextMove(String input) {
        String[] strs = input.split("#");
        int[][] g = new int[13][14];
        for (int i = 0, k = 0; i < 13; i ++){
            for (int j = 0; j < 14; k ++, j ++){
                if (strs[0].charAt(k) == '1'){
                    g[i][j] = 1;
                }
            }
        }
        int aSx = Integer.parseInt(strs[1]), aSy = Integer.parseInt(strs[2]);
        int bSx = Integer.parseInt(strs[4]), bSy = Integer.parseInt(strs[5]);
        String stepsA = strs[3].substring(1, strs[3].length() - 1);
        String stepsB = strs[6].substring(1, strs[6].length() - 1);

        List<Cell> cellA = getCells(aSx, aSy, stepsA);
        List<Cell> cellB = getCells(bSx, bSy, stepsB);

        for (Cell c : cellA) g[c.x][c.y] = 1;
        for (Cell c : cellB) g[c.x][c.y] = 1;

        int[] dx = {-1, 0, 1, 0}, dy = {0, 1, 0, -1};
        Random random = new Random();
        for (int i = 0; i < 1000; i ++){
            int d = random.nextInt(4);
            int x = cellA.get(cellA.size() - 1).x + dx[d];
            int y = cellA.get(cellA.size() - 1).y + dy[d];
            if (x >= 0 && x < 13 && y >= 0 && y < 14 && g[x][y] == 0){
                return d;
            }
        }
        return 0;
    }
    @Override
    public Integer get() {
        File file = new File("input.txt");
        try {
            Scanner sc = new Scanner(file);
            return nextMove(sc.next());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
