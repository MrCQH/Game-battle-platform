package com.kob.backend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

public class FollowerRelationship {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String username;
    private String opponentUsername;
    private Boolean isFollowed;

    public FollowerRelationship() {
    }

    public FollowerRelationship(Integer id, String username, String opponentUsername, Boolean isFollowed) {
        this.id = id;
        this.username = username;
        this.opponentUsername = opponentUsername;
        this.isFollowed = isFollowed;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOpponentUsername() {
        return opponentUsername;
    }

    public void setOpponentUsername(String opponentUsername) {
        this.opponentUsername = opponentUsername;
    }

    public Boolean getFollowed() {
        return isFollowed;
    }

    public void setFollowed(Boolean followed) {
        isFollowed = followed;
    }

    @Override
    public String toString() {
        return "FollowerRelationship{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", opponentUsername='" + opponentUsername + '\'' +
                ", isFollowed=" + isFollowed +
                '}';
    }
}
