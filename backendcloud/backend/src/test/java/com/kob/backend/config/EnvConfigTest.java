package com.kob.backend.config;

import org.springframework.boot.test.context.SpringBootTest;


// websocket测试环境配置
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// @WebAppConfiguration
public class EnvConfigTest {
}
