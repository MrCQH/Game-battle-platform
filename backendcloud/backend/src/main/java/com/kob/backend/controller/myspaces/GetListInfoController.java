package com.kob.backend.controller.myspaces;


import com.kob.backend.pojo.User;
import com.kob.backend.service.user.myspaces.GetListInfoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class GetListInfoController {
    @Resource
    GetListInfoService getListInfoService;

    @GetMapping("/api/myspaces/usersList/")
    public List<User> getInfo(){
        return getListInfoService.getUserList();
    }
}
