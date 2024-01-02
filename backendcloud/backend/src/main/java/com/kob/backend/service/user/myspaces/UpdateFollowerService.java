package com.kob.backend.service.user.myspaces;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

public interface UpdateFollowerService {
    JSONObject addFollower(Integer targetId);
    JSONObject removeFollower(Integer targetId);

}
