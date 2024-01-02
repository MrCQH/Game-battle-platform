package com.kob.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kob.backend.pojo.FollowerRelationship;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface FollowerRelationshipMapper extends BaseMapper<FollowerRelationship> {
//    @Insert("insert into follower_relationship(username, opponent_username, is_followed) " +
//            "values(#{username}, #{opponentName}, true)")
//    Integer insertRelationship(String username, String opponentName);

    @Update("update follower_relationship set is_followed = #{isFollowed} " +
            "where username = #{username} and opponent_username = #{opponentName}")
    Integer updateRelationship(String username, String opponentName, Boolean isFollowed);
}
