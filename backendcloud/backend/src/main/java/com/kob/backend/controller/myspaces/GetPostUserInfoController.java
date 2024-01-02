package com.kob.backend.controller.myspaces;

import com.alibaba.fastjson.JSONObject;
import com.kob.backend.service.user.myspaces.GetPostUserInfoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
public class GetPostUserInfoController {

    @Resource
    GetPostUserInfoService getPostUserInfoService;

    @GetMapping("/api/myspaces/getInfo/")
    @ResponseBody
    public JSONObject getInfo(@RequestParam Map<String, String> data){
        Integer userId = Integer.valueOf(data.get("userId"));
        return getPostUserInfoService.getPostUserInfo(userId);
    }
}
