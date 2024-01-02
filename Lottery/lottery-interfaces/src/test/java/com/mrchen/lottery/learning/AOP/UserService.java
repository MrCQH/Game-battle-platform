package com.mrchen.lottery.learning.AOP;

import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * @description:
 * @author：cqh
 * @date: 2023/7/21
 */
@Service
public class UserService implements IUserService {

    public String queryUserInfo() {
        try {
            Thread.sleep(new Random(1).nextInt(100));
            System.out.println("小傅哥，100001，深圳");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "小傅哥，100001，深圳";
    }

    public String register(String userName) {
        try {
            Thread.sleep(new Random(1).nextInt(100));
            System.out.println("小傅哥，100001，深圳");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "注册用户：" + userName + " success！";
    }
}
