package com.kob.backend.consumer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kob.backend.consumer.utils.Game;
import com.kob.backend.consumer.utils.JwtAuthentication;
import com.kob.backend.mapper.BotMapper;
import com.kob.backend.mapper.RecordMapper;
import com.kob.backend.mapper.UserMapper;
import com.kob.backend.pojo.Bot;
import com.kob.backend.pojo.User;
import com.mrchen.lottery.rpc.activity.booth.ILotteryActivityBooth;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ServerEndpoint("/websocket/{token}")  // 注意不要以'/'结尾
public class WebSocketServer {

    private Session session = null;
    private User user; // 维护这个链接的User信息
    public static final ConcurrentHashMap<Integer, WebSocketServer> users = new ConcurrentHashMap<>(); // 线程安全的HashMap
    public Game game = null;
    private static final String addPlayerUrl = "http://botMatchingSystem:3001/player/add/";
    private static final String removePlayerUrl = "http://botMatchingSystem:3001/player/remove/";

    public static UserMapper userMapper;
    public static RecordMapper recordMapper;
    public static RestTemplate restTemplate;
    private static BotMapper botMapper;
    public static ILotteryActivityBooth lotteryActivityBooth;

    @DubboReference(version = "1.0.0", timeout = 5000)
    public void setLotteryActivityBooth(ILotteryActivityBooth lotteryActivityBooth){
        WebSocketServer.lotteryActivityBooth = lotteryActivityBooth;
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper){
        WebSocketServer.userMapper = userMapper;
    }
    @Autowired
    public void setRecordMapper(RecordMapper recordMapper){
        WebSocketServer.recordMapper = recordMapper;
    }
    @Autowired
    public void setRestTemplate(RestTemplate restTemplate){
        WebSocketServer.restTemplate = restTemplate;
    }
    @Autowired
    public void setBotMapper(BotMapper botMapper){
        WebSocketServer.botMapper = botMapper;
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("token") String token) throws IOException {
        this.session = session;
        System.out.println("connected!");
        Integer userId = JwtAuthentication.getUserId(token);
        this.user = userMapper.selectById(userId);

        if (this.user != null){
            users.put(userId, this);
        } else {
            this.session.close();
        }
    }

    @OnClose
    public void onClose() {
        System.out.println("disconnected!");
        if (this.user != null){
            users.remove(this.user.getId());
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) { // 当路由接受前端信息
        System.out.println("receive message!");
        JSONObject data = JSON.parseObject(message);
        String event = data.getString("event");
        if ("start-matching".equals(event)){
            startMatching(data.getInteger("bot_id"));
        } else if ("stop-matching".equals(event)) {
            stopMatching();
        } else if ("move".equals(event)){
            move(data.getInteger("direction"));
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    public void sendMessage(String message){ // server发送消息给client
        synchronized (this.session){
            try{
                this.session.getBasicRemote().sendText(message);
            } catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    public static void startGame(Integer aId, Integer aBotId, Integer bId, Integer bBotId){
        User a = userMapper.selectById(aId), b = userMapper.selectById(bId);
        Bot botA = botMapper.selectById(aBotId), botB = botMapper.selectById(bBotId);

        Game game = new Game(
                13,
                14,
                60,
                a.getId(),
                botA,
                b.getId(),
                botB
        );
        game.createMap();

        if (users.get(a.getId()) != null)
            users.get(a.getId()).game = game;
        if (users.get(b.getId()) != null)
            users.get(b.getId()).game = game;
        game.start(); // Game中的run函数开始执行

        JSONObject repGame = new JSONObject();
        repGame.put("a_id", game.getPlayerA().getId());
        repGame.put("a_sx", game.getPlayerA().getSx());
        repGame.put("a_sy", game.getPlayerA().getSy());
        repGame.put("b_id", game.getPlayerB().getId());
        repGame.put("b_sx", game.getPlayerB().getSx());
        repGame.put("b_sy", game.getPlayerB().getSy());
        repGame.put("map", game.getG());

        JSONObject repA = new JSONObject();
        repA.put("event", "start-matching");
        repA.put("opponent_photo", b.getPhoto());
        repA.put("opponent_username", b.getUsername());
        repA.put("game", repGame);
        if (users.get(a.getId()) != null)
            users.get(a.getId()).sendMessage(repA.toJSONString());

        JSONObject repB = new JSONObject();
        repB.put("event", "start-matching");
        repB.put("opponent_photo", a.getPhoto());
        repB.put("opponent_username", a.getUsername());
        repB.put("game", repGame);
        if (users.get(b.getId()) != null)
            users.get(b.getId()).sendMessage(repB.toJSONString());
    }

    private void startMatching(Integer botId){
        System.out.println("start_matching!");
        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("user_id", this.user.getId().toString());
        data.add("rating", this.user.getRating().toString());
        data.add("bot_id", botId.toString());
        restTemplate.postForObject(addPlayerUrl, data, String.class);
    }

    private void stopMatching(){
        System.out.println("stop_matching!");
        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("user_id", this.user.getId().toString());
        restTemplate.postForObject(removePlayerUrl, data, String.class);
    }

    public void move(Integer direction){
        if (game.getPlayerA().getId().equals(user.getId())){ // 防止用户断线， 出现错误
            if (game.getPlayerA().getBotId().equals(-1)) // 亲自出马
                game.setStepNextA(direction);
        } else if (game.getPlayerB().getId().equals(user.getId())){ // 防止用户断线， 出现错误
            if (game.getPlayerB().getBotId().equals(-1))  // 亲自出马
                game.setStepNextB(direction);
        }
    }
}
