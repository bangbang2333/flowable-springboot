package com.creativec.example.service;

import com.creativec.common.base.BaseService;
import com.creativec.common.base.PageResult;
import com.creativec.example.entity.WokeFlow;

import java.util.List;

/**
 * @author llpei
 *
 */
public interface WokeFlowService extends BaseService<WokeFlow> {

    /**
     * 分页查询
     * 
     * @param wokeFlow
     * @return
     */
    PageResult getWokeFlowByPage(WokeFlow wokeFlow);

    /**
     * 修改流程步骤
     * 
     * @param wokeFlow
     */
    Boolean updateWokeFlow(WokeFlow wokeFlow);

    /**
     * 根据流程表kid查询流程配置列表
     * 
     * @param wokesKid
     * @return
     */
    List<WokeFlow> queryWokeFlowListByWokesKid(String wokesKid);

    /**
     * 添加流程步骤
     *
     * @param wokeFlow
     * @return
     * */
    String addWokeFlow(WokeFlow wokeFlow);

}
