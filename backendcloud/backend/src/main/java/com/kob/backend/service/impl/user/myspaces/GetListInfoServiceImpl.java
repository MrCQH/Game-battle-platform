package com.kob.backend.service.impl.user.myspaces;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kob.backend.mapper.UserMapper;
import com.kob.backend.pojo.User;
import com.kob.backend.service.impl.utils.UserDetailsImpl;
import com.kob.backend.service.user.myspaces.GetListInfoService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class GetListInfoServiceImpl implements GetListInfoService {
    @Resource
    UserMapper mapper;

    @Override
    public List<User> getUserList() { // 随机查10个数据, 不足10,取最小
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        int count = Math.min(Math.toIntExact(mapper.selectCount(null)), 10);
        int offset = Math.max((int)(Math.random() * (count - 10)), 0);
        queryWrapper.orderByDesc("id");
        queryWrapper.last("limit " + offset + ", " + count);
        List<User> users = mapper.selectList(queryWrapper);
        for (User user : users){ // 保密
            user.setPassword("");
        }
        return users;
    }
}
