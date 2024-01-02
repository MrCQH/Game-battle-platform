package com.kob.backend.controller.recordlist;

import com.alibaba.fastjson.JSONObject;
import com.kob.backend.service.recordlist.GetBattleListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GetBattleListController {
    @Autowired
    private GetBattleListService getBattleListService;

    @GetMapping("/api/record/getList/")
    public JSONObject getList(@RequestParam Map<String, String> data){
        Integer page = Integer.parseInt(data.get("page"));
        return getBattleListService.getBattleList(page);
    }
}
