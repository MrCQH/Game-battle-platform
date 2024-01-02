package com.kob.botrunningsystem.service.utils;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class BotPool extends Thread{
    private static final Queue<Bot> bots = new ArrayDeque<>();
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();

    public void addBot(Integer userId, String botCode, String input){
        lock.lock();
        try {
            bots.add(new Bot(userId, botCode, input));
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    private void consume(Bot bot){
        Consumer consumer = new Consumer();
        consumer.startTimeout(2000, bot);
    }

    @Override
    public void run() {
        while(true){
            lock.lock();
            if (bots.isEmpty()){
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                } finally {
                    lock.unlock();
                }
            } else {
                Bot bot = bots.remove();
                lock.unlock();
                consume(bot); // 这一步非常耗时, 所有需要先unlock,避免占用资源
            }
        }
    }
}
