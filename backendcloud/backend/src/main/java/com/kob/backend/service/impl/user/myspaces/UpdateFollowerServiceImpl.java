package com.kob.backend.service.impl.user.myspaces;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kob.backend.mapper.FollowerRelationshipMapper;
import com.kob.backend.mapper.UserMapper;
import com.kob.backend.pojo.FollowerRelationship;
import com.kob.backend.pojo.User;
import com.kob.backend.service.impl.utils.UserDetailsImpl;
import com.kob.backend.service.user.myspaces.UpdateFollowerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.SQLDataException;

@Service
public class UpdateFollowerServiceImpl implements UpdateFollowerService {

    @Resource
    UserMapper userMapper;

    @Resource
    FollowerRelationshipMapper followerRelationshipMapper;

    @Override
    public JSONObject addFollower(Integer targetId) {
        JSONObject resp = new JSONObject();
        User opponent = userMapper.selectById(targetId);
        opponent.setFollowerCount(opponent.getFollowerCount() + 1);
        userMapper.updateById(opponent);
        User my = UserDetailsImpl.getUserInfo();
        QueryWrapper<FollowerRelationship> opponentQueryWrapper = new QueryWrapper<>();
        opponentQueryWrapper
                .eq("opponent_username", opponent.getUsername())
                .eq("username", my.getUsername());
        System.out.println(followerRelationshipMapper.selectOne(opponentQueryWrapper));
        if (followerRelationshipMapper.selectOne(opponentQueryWrapper) == null) {
            try {
                System.out.println(my.getUsername() + " " + opponent.getUsername());
                System.out.println(followerRelationshipMapper.insert(new FollowerRelationship(
                        null,
                        my.getUsername(),
                        opponent.getUsername(),
                        true
                )));
            } catch (Exception e){
                e.printStackTrace();
            }
        } else {
                followerRelationshipMapper.
                        updateRelationship(my.getUsername(), opponent.getUsername(), true);
        }
        resp.put("result", "success");
        return resp;
    }

    @Override
    public JSONObject removeFollower(Integer targetId) {
        JSONObject resp = new JSONObject();
        User opponent = userMapper.selectById(targetId);
        opponent.setFollowerCount(opponent.getFollowerCount() - 1);
        userMapper.updateById(opponent);
        User my = UserDetailsImpl.getUserInfo();
        QueryWrapper<FollowerRelationship> opponentQueryWrapper = new QueryWrapper<>();
        opponentQueryWrapper
                .eq("opponent_username", opponent.getUsername())
                .eq("username", my.getUsername());

        followerRelationshipMapper.updateRelationship(my.getUsername(), opponent.getUsername(), false);
        resp.put("result", "success");
        return resp;
    }
}
