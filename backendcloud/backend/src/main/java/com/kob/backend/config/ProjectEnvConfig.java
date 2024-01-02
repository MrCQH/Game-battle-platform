package com.kob.backend.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProjectEnvConfig {
    private Logger logger = LoggerFactory.getLogger(ProjectEnvConfig.class);

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String dbUsername;

    @Value("${spring.datasource.password}")
    private String dbPassword; // 通过环境变量注入敏感信息

    @Value("${spring.datasource.driver-class-name}")
    private String driver; // 通过环境变量注入敏感信息

    @Bean
    public void PrintTest(){
        logger.info("env_config: \n" +
                " -datasource:\n" +
                "  -dbUrl: {}\n" +
                "  -dbUsername: {}\n" +
                "  -dbPassword: {}\n" +
                "  -driver: {}\n", dbUrl, dbUsername, dbPassword, driver);
    }
}
