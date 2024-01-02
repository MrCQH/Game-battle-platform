package com.mrchen.lottery.test;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class Fibonacci {
    @Test
    public void test(){
        long val = (1L << 32) - (long) ((1L << 32) * (Math.sqrt(5) - 1))/2;
        System.out.println(Long.toHexString(val));
    }
}
