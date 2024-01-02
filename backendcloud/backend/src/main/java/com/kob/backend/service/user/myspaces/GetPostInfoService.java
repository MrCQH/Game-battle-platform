package com.kob.backend.service.user.myspaces;

import com.alibaba.fastjson.JSONObject;

public interface GetPostInfoService {
    JSONObject getPostInfo(Integer userId);
}
