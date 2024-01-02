package com.kob.backend.controller.user.account;

import com.kob.backend.common.EasyResult;
import com.kob.backend.model.user.account.AwardLimitPageReq;
import com.kob.backend.service.user.account.IGetAwardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("api/user/account")
public class GetAwardListController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private IGetAwardService getAwardService;

    @RequestMapping("queryAwardListPage")
    @ResponseBody
    public EasyResult getAwardList(String uId, String page, String rows){
        try{
            logger.info("获取用户列表 uId: {},  page: {}, rows {}", uId, page, rows);
            rows = rows == null ? "10" : rows;
            AwardLimitPageReq req = new AwardLimitPageReq(uId, page, rows);
            return getAwardService.getAwardListLimitPage(req);
        } catch (Exception e){
            logger.error("获取用户列表失败 page: {}, rows {}", page, rows, e);
            return EasyResult.buildEasyResultError(e);
        }

    }

}
