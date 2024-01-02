package com.mrchen.lottery.domain.support.ids.policy;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.IdUtil;
import com.mrchen.lottery.domain.support.ids.IIdGenerator;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @description: hutool 工具包下的雪花算法，15位雪花算法推荐：https://github.com/yitter/idgenerator/blob/master/Java/source/src/main/java/com/github/yitter/core/SnowWorkerM1.java
 * @author：cqh
 * @date: 2023/6/19
 */
@Component
public class SnowFlake implements IIdGenerator {
    private Snowflake snowflake;

    // 在依赖注入完成后被自动调用
    @PostConstruct
    public void init(){
        long workerId;
        try{
            workerId = NetUtil.ipv4ToLong(NetUtil.getLocalhostStr());
        } catch (Exception e){
            workerId = NetUtil.getLocalhostStr().hashCode();
        }

        workerId = workerId >> 16 & 31;

        long dataCenterId = 1L;

        snowflake = IdUtil.createSnowflake(workerId, dataCenterId);
    }

    @Override
    public synchronized long nextId() {
        return snowflake.nextId();
    }
}
