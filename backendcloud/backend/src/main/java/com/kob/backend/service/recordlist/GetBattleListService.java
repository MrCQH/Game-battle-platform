package com.kob.backend.service.recordlist;

import com.alibaba.fastjson.JSONObject;

public interface GetBattleListService {
    public JSONObject getBattleList(Integer page);
}
