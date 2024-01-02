package com.kob.botrunningsystem.service.utils;

import org.joor.Reflect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class Consumer extends Thread{
    private Bot bot;
    private static RestTemplate restTemplate;
    private final static String nextStepUrl = "http://kobBackend:3000/receive/bot/move/";
    private Logger logger = LoggerFactory.getLogger(Consumer.class);

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate){
        Consumer.restTemplate = restTemplate;
    }

    public void startTimeout(long timeout, Bot bot){ // 代码最多运行timeout秒
        this.bot = bot;
        this.start();

        try {
            this.join(timeout); // 最多等timeout秒之后继续向后执行
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            this.interrupt(); // 将该线程打断
        }
    }

    public String addUid(String code, String uid){
        int k = code.indexOf(" implements java.util.function.Supplier<Integer>");
        return code.substring(0, k) + uid + code.substring(k);
    }

    public String getCodeOfClassName(String sourceCode){
        // 定义匹配Java类名的正则表达式
        String regex = "class\\s+([A-Z][A-Za-z0-9_]*)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(sourceCode);

        // 查找第一个匹配的类名
        if (matcher.find()) {
            return matcher.group(1);
        }
        // 如果没有找到匹配的类名，则返回null或者抛出异常
        throw new NoClassDefFoundError("not find matching class name");
    }


    private void sendResult(Integer direction){
        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("direction", direction.toString());
        data.add("user_id", bot.getUserId().toString());
        restTemplate.postForObject(nextStepUrl, data, String.class);
    }

    @Override
    public void run() {
        UUID uuid = UUID.randomUUID();
        String uid = uuid.toString().substring(0, 8); // 随机生成一个8位不重复的数字

        logger.info("包名是： \n{}", "com.kob.botrunningsystem.utils." + getCodeOfClassName(bot.getBotCode()) + uid);
        logger.info("修改后的源代码是： \n{}", addUid(bot.getBotCode(), uid));

        // 动态编译,在运行时编译  也可以用字节码增强
        Supplier<Integer> botInterface = Reflect.compile(
                "com.kob.botrunningsystem.utils." + getCodeOfClassName(bot.getBotCode()) + uid, // 包名
                        addUid(bot.getBotCode(), uid) // 源码的内容
        )
                .create() // 构造
                .get(); // Get the wrapped object

        File file = new File("input.txt");
        try(PrintWriter fout = new PrintWriter(file)){
            // 地图
            fout.println(bot.getInput());
            fout.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Integer direction = botInterface.get();

        sendResult(direction);

        logger.info("move direction: {} {}", bot.getUserId(), direction);
    }

    // @Override
    // public void run() {
    //     UUID uuid = UUID.randomUUID();
    //     String uid = uuid.toString().substring(0, 8); // 随机生成一个8位不重复的数字
    //     String sourceCode = bot.getBotCode();
    //     ClassPool classPool = ClassPool.getDefault();
    //
    //     File file = new File("input.txt");
    //     try (PrintWriter fout = new PrintWriter(file)){
    //         fout.println(bot.getInput());
    //         fout.flush();
    //
    //         CtClass ctClass = classPool.get("com.kob.botrunningsystem.utils." + getCodeOfClassName(sourceCode));
    //         String name = ctClass.getName();
    //         ctClass.setName(name + uid);
    //
    //         logger.info("修改后的className是: {}", ctClass.getName());
    //         Class<?> clazz = ctClass.toClass();
    //         Supplier<Integer> botInterface = (Supplier<Integer>) clazz.newInstance();
    //         Integer direction = botInterface.get();
    //         logger.info("方向是: {}", direction);
    //         sendResult(direction);
    //     } catch (NotFoundException | CannotCompileException | InstantiationException | IllegalAccessException | FileNotFoundException e) {
    //         e.printStackTrace();
    //     }
    //
    // }
}
