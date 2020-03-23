package com.creativec.example.service;

import com.creativec.common.base.BaseService;
import com.creativec.common.base.PageResult;
import com.creativec.example.entity.WokeFlow;
/**
 * @author llpei
 *
 * */
public interface WokeFlowService extends BaseService<WokeFlow> {

    /**
     * 分页查询
     * @param wokeFlow
     * @return
     * */
    PageResult getWokeFlowByPage(WokeFlow wokeFlow);

    /**
     * 修改流程步骤
     * @param wokeFlow
     * */
    Boolean updateWokeFlow(WokeFlow wokeFlow);

}
