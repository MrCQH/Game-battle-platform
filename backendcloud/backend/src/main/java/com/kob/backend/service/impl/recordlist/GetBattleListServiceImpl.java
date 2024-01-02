package com.kob.backend.service.impl.recordlist;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kob.backend.mapper.RecordMapper;
import com.kob.backend.mapper.UserMapper;
import com.kob.backend.pojo.Record;
import com.kob.backend.pojo.User;
import com.kob.backend.service.recordlist.GetBattleListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetBattleListServiceImpl implements GetBattleListService {
    @Autowired
    private RecordMapper recordMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public JSONObject getBattleList(Integer page) {
        IPage<Record> recordPage = new Page<>(page, 10); // 一个第i页,10行的表
        QueryWrapper<Record> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id"); // 条件查询器
        List<Record> records = recordMapper.selectPage(recordPage, queryWrapper).getRecords();
        List<JSONObject> items = new ArrayList<>();
        for (Record record : records){
            JSONObject item = new JSONObject();
            User userA = userMapper.selectById(record.getaId());
            User userB = userMapper.selectById(record.getbId());
            String aUsername = userA.getUsername();
            String bUsername = userB.getUsername();
            String aPhoto = userA.getPhoto();
            String bPhoto = userB.getPhoto();
            item.put("a_user_photo", aPhoto);
            item.put("a_username", aUsername);
            item.put("b_user_photo", bPhoto);
            item.put("b_username", bUsername);
            String result = "平局";

            if ("a".equals(record.getLoser())) result = bUsername + " 赢";
            else if("b".equals(record.getLoser())) result = aUsername + " 赢";

            item.put("result", result);
            item.put("record", record);
            items.add(item);
        }
        JSONObject resp = new JSONObject();
        resp.put("record_info", items);
        resp.put("total_page_count", recordMapper.selectCount(null));

        return resp;
    }
}
