package com.kob.backend.service.user.account;

import com.kob.backend.common.EasyResult;
import com.kob.backend.model.user.account.AwardLimitPageReq;

public interface IGetAwardService {
    EasyResult getAwardListLimitPage(AwardLimitPageReq req);
}
