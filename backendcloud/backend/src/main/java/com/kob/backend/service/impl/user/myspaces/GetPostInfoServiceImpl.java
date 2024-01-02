package com.kob.backend.service.impl.user.myspaces;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kob.backend.mapper.PostMapper;
import com.kob.backend.pojo.Post;
import com.kob.backend.service.user.myspaces.GetPostInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class GetPostInfoServiceImpl implements GetPostInfoService {

    @Resource
    PostMapper postMapper;

    @Override
    public JSONObject getPostInfo(Integer userId) {
        QueryWrapper<Post> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        if (postMapper.selectList(queryWrapper) == null) return new JSONObject();
        List<Post> posts = postMapper.selectList(queryWrapper);
        List<JSONObject> list = new ArrayList<>();
        for (Post item : posts){
            JSONObject json = new JSONObject();
            json.put("user_id", item.getUserId());
            json.put("id", item.getId());
            json.put("title", item.getTitle());
            json.put("content", item.getContent());
            json.put("createtime", item.getCreatetime());
            json.put("modifytime", item.getModifytime());
            list.add(json);
        }

        JSONObject resp = new JSONObject();
        resp.put("posts", list);
        resp.put("count", postMapper.selectCount(null));
        resp.put("result", "success");
        return resp;
    }
}
