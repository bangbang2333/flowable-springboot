package com.creativec.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.creativec.common.base.BaseServiceImpl;
import com.creativec.common.base.PageResult;
import com.creativec.example.entity.WokeFlow;
import com.creativec.example.mapper.WokeFlowMapper;
import com.creativec.example.service.WokeFlowService;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author llpei
 * @Description:
 * @date Created in 19:39 2020/3/22
 */
@Service
public class WokeFlowServiceImpl extends BaseServiceImpl<WokeFlowMapper, WokeFlow> implements WokeFlowService {
    @Override
    public PageResult getWokeFlowByPage(WokeFlow wokeFlow) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("name",wokeFlow.getName());
        PageHelper.startPage(wokeFlow.getPageNum(),wokeFlow.getPageSize());
        List list = super.list(wrapper);
        return PageResult.getPageResult(list);
    }

    @Override
    public Boolean updateWokeFlow(WokeFlow wokeFlow) {
        return super.updateById(wokeFlow);
    }
}
