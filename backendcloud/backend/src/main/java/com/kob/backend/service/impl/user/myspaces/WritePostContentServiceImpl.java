package com.kob.backend.service.impl.user.myspaces;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kob.backend.mapper.PostMapper;
import com.kob.backend.pojo.Post;
import com.kob.backend.pojo.User;
import com.kob.backend.service.impl.utils.UserDetailsImpl;
import com.kob.backend.service.user.myspaces.WritePostContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.util.Date;


@Service
public class WritePostContentServiceImpl implements WritePostContentService {
    @Autowired
    PostMapper postMapper;

    @Override
    public JSONObject writePost(String content) {
        User my = UserDetailsImpl.getUserInfo();

        Date now = new Date();
        postMapper.insert(new Post(
                null,
                my.getId(),
                null,
                content,
                now,
                now
        ));
        JSONObject resp = new JSONObject();
        resp.put("result", "success");
        return resp;
    }
}
