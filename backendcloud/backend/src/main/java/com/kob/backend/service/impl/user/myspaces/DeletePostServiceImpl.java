package com.kob.backend.service.impl.user.myspaces;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kob.backend.mapper.PostMapper;
import com.kob.backend.pojo.Post;
import com.kob.backend.service.user.myspaces.DeletePostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DeletePostServiceImpl implements DeletePostService {
    @Autowired
    PostMapper postMapper;

    @Override
    public Map<String, String> deletePost(Integer postId) {
        QueryWrapper<Post> postQueryWrapper = new QueryWrapper<>();
        postQueryWrapper.eq("id", postId);
        Map<String, String> resp = new HashMap<>();
        if (postMapper.delete(postQueryWrapper) > 0){
            resp.put("result", "success");
        } else {
            resp.put("result", "error");
        }
        return resp;
    }
}
