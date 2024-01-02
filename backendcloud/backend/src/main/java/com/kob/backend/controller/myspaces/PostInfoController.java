package com.kob.backend.controller.myspaces;

import com.alibaba.fastjson.JSONObject;
import com.kob.backend.service.user.myspaces.DeletePostService;
import com.kob.backend.service.user.myspaces.GetPostInfoService;
import com.kob.backend.service.user.myspaces.WritePostContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
public class PostInfoController {

    @Resource
    GetPostInfoService getPostInfoService;

    @Autowired
    WritePostContentService writePostContentService;

    @GetMapping("/api/myspaces/post/")
    @ResponseBody
    public JSONObject getPost(@RequestParam Map<String, String> data){
        Integer userId = Integer.valueOf(data.get("userId"));
        return getPostInfoService.getPostInfo(userId);
    }

    @PostMapping("/api/myspaces/post/")
    public JSONObject writePost(@RequestParam Map<String, String> data){
        String content = data.get("content");
        return writePostContentService.writePost(content);
    }

    @Autowired
    DeletePostService deletePostService;

    @DeleteMapping("/api/myspace/post/")
    @ResponseBody
    public Map<String, String> deletePost(@RequestParam Map<String, String> data){
        Integer postId = Integer.valueOf(data.get("post_id"));
        return deletePostService.deletePost(postId);
    }
}
