package com.kob.backend.service.impl.user.myspaces;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kob.backend.mapper.FollowerRelationshipMapper;
import com.kob.backend.mapper.UserMapper;
import com.kob.backend.pojo.FollowerRelationship;
import com.kob.backend.pojo.User;
import com.kob.backend.service.impl.utils.UserDetailsImpl;
import com.kob.backend.service.user.myspaces.GetPostUserInfoService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class GetPostUserInfoServiceImpl implements GetPostUserInfoService {

    @Resource
    UserMapper userMapper;

    @Resource
    FollowerRelationshipMapper followerRelationshipMapper;

    @Override
    public JSONObject getPostUserInfo(Integer userId) {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();

        User my = loginUser.getUser();

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", userId);
        User you =  userMapper.selectOne(queryWrapper);
        JSONObject resp = new JSONObject();
        resp.put("id", you.getId());
        resp.put("username", you.getUsername());
        resp.put("photo", you.getPhoto());
        resp.put("rating", you.getRating());
        resp.put("followerCount", you.getFollowerCount());

        QueryWrapper<FollowerRelationship> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1 = queryWrapper1
                .eq("username", my.getUsername())
                .eq("opponent_username", you.getUsername());
        FollowerRelationship relationship = followerRelationshipMapper.selectOne(queryWrapper1);
        if (relationship == null){
            String myUsername = my.getUsername();
            String youUsername = you.getUsername();
            followerRelationshipMapper.insert(new FollowerRelationship(null, myUsername, youUsername, false));
            resp.put("is_followed", false);
        } else {
            Boolean isFollowed = relationship.getFollowed();
            resp.put("is_followed", isFollowed);
        }
        return resp;
    }
}
