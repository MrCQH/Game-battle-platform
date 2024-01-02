package com.kob.backend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Record {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer aId;
    private Integer aSx;
    private Integer aSy;
    private Integer bId;
    private Integer bSx;
    private Integer bSy;
    private String aSteps;
    private String bSteps;
    private String map;
    private String loser;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date createtime;

    public Record() {
    }

    public Record(Integer id, Integer aId, Integer aSx, Integer aSy, Integer bId, Integer bSx,
                  Integer bSy, String aSteps, String bSteps, String map, String loser, Date createtime) {
        this.id = id;
        this.aId = aId;
        this.aSx = aSx;
        this.aSy = aSy;
        this.bId = bId;
        this.bSx = bSx;
        this.bSy = bSy;
        this.aSteps = aSteps;
        this.bSteps = bSteps;
        this.map = map;
        this.loser = loser;
        this.createtime = createtime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getaId() {
        return aId;
    }

    public void setaId(Integer aId) {
        this.aId = aId;
    }

    public Integer getaSx() {
        return aSx;
    }

    public void setaSx(Integer aSx) {
        this.aSx = aSx;
    }

    public Integer getaSy() {
        return aSy;
    }

    public void setaSy(Integer aSy) {
        this.aSy = aSy;
    }

    public Integer getbId() {
        return bId;
    }

    public void setbId(Integer bId) {
        this.bId = bId;
    }

    public Integer getbSx() {
        return bSx;
    }

    public void setbSx(Integer bSx) {
        this.bSx = bSx;
    }

    public Integer getbSy() {
        return bSy;
    }

    public void setbSy(Integer bSy) {
        this.bSy = bSy;
    }

    public String getaSteps() {
        return aSteps;
    }

    public void setaSteps(String aSteps) {
        this.aSteps = aSteps;
    }

    public String getbSteps() {
        return bSteps;
    }

    public void setbSteps(String bSteps) {
        this.bSteps = bSteps;
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public String getLoser() {
        return loser;
    }

    public void setLoser(String loser) {
        this.loser = loser;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    @Override
    public String toString() {
        return "Record{" +
                "id=" + id +
                ", aId=" + aId +
                ", aSx=" + aSx +
                ", aSy=" + aSy +
                ", bId=" + bId +
                ", bSx=" + bSx +
                ", bSy=" + bSy +
                ", aSteps='" + aSteps + '\'' +
                ", bSteps='" + bSteps + '\'' +
                ", map='" + map + '\'' +
                ", loser='" + loser + '\'' +
                ", createtime=" + createtime +
                '}';
    }
}
