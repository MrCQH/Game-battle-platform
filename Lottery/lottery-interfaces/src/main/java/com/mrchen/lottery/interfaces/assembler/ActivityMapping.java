package com.mrchen.lottery.interfaces.assembler;

import com.mrchen.lottery.domain.activity.model.vo.ActivityVO;
import com.mrchen.lottery.rpc.activity.deploy.dto.ActivityDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * @description: 活动对象转换配置
 * @author: 小傅哥，微信：fustack
 * @date: 2021/12/11
 * @github: https://github.com/fuzhengwei
 * @Copyright: 公众号：bugstack虫洞栈 | 博客：https://bugstack.cn - 沉淀、分享、成长，让自己和他人都能有所收获！
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ActivityMapping extends IMapping<ActivityVO, ActivityDTO>{

    @Override
    List<ActivityDTO> sourceToTarget(List<ActivityVO> var1);

}
