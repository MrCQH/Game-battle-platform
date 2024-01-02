package com.mrchen.lottery.domain.activity.service.stateflow;

import com.mrchen.lottery.common.Constants;
import com.mrchen.lottery.domain.activity.service.stateflow.event.*;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description: 状态刘庄配置
 * @author：cqh
 * @date: 2023/6/14
 */
public class StateConfig {
    @Resource
    private ArraignmentState arraignmentState;

    @Resource
    private CloseState closeState;

    @Resource
    private DoingState doingState;

    @Resource
    private EditingState editingState;

    @Resource
    private OpenState openState;

    @Resource
    private PassState passState;

    @Resource
    private RefuseState refuseState;

    protected Map<Enum<Constants.ActivityState>, AbstractState> stateGroup = new ConcurrentHashMap<>();

    @PostConstruct
    public void init(){
        stateGroup.put(Constants.ActivityState.ARRAIGNMENT, arraignmentState);
        stateGroup.put(Constants.ActivityState.CLOSE, closeState);
        stateGroup.put(Constants.ActivityState.DOING, doingState);
        stateGroup.put(Constants.ActivityState.EDIT, editingState);
        stateGroup.put(Constants.ActivityState.OPEN, openState);
        stateGroup.put(Constants.ActivityState.PASS, passState);
        stateGroup.put(Constants.ActivityState.REFUSE, refuseState);
    }

}
