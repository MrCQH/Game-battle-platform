package com.mrchen.lottery.infrastructure.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mrchen.lottery.domain.activity.model.req.ActivityInfoLimitPageReq;
import com.mrchen.lottery.domain.activity.model.vo.AlterStateVO;
import com.mrchen.lottery.infrastructure.po.Activity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface IActivityDao extends BaseMapper<Activity> {
    @Update("update activity set state = #{afterState} where activity_id = #{activityId} and state = #{beforeState}")
    int alterState(AlterStateVO alterStateVO);

    /**
     * 扣减活动库存
     * @param activityId 活动ID
     * @return 更新数量
     */
    @Update("UPDATE activity SET stock_surplus_count = stock_surplus_count - 1 WHERE activity_id = #{activityId} AND stock_surplus_count > 0")
    int subtractionActivityStock(Long activityId);

    /**
     * 扫描待处理的活动列表，状态为：通过、活动中
     *
     * @param id ID
     * @return 待处理的活动集合
     */
    @Select("select activity_id, activity_name, begin_date_time, end_date_time, state, creator from activity where id >= #{id} and state in (4, 5)" +
            "order by id asc limit 10")
    List<Activity> scanToDoActivityList(Long id);

    /**
     * 更新用户领取活动后，活动库存
     *
     * @param activity  入参
     */
    @Update("UPDATE activity SET stock_surplus_count = #{stockSurplusCount} WHERE activity_id = #{activityId} AND stock_surplus_count > #{stockSurplusCount}")
    void updateActivityStock(Activity activity);

    /**
     * 查询活动分页数据数量
     *
     * @param req 入参
     * @return    结果
     */
    Long queryActivityInfoCount(ActivityInfoLimitPageReq req);

    /**
     * 查询活动分页数据列表
     *
     * @param req   入参
     * @return      结果
     */
    List<Activity> queryActivityInfoList(ActivityInfoLimitPageReq req);
}
