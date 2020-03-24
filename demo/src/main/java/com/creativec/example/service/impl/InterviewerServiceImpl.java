package com.creativec.example.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.excel.util.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.creativec.common.base.BaseServiceImpl;
import com.creativec.common.base.PageResult;
import com.creativec.common.base.SysLog;
import com.creativec.common.exception.BusinessException;
import com.creativec.example.dto.InterviewerDto;
import com.creativec.example.dto.InterviewerSearchDto;
import com.creativec.example.entity.HandleFlow;
import com.creativec.example.entity.Interviewer;
import com.creativec.example.entity.WokeFlow;
import com.creativec.example.mapper.InterviewerMapper;
import com.creativec.example.service.HandleFlowService;
import com.creativec.example.service.InterviewerService;
import com.creativec.example.service.WokeFlowService;
import com.github.pagehelper.PageHelper;

/**
 * @author cc
 */
@Service
public class InterviewerServiceImpl extends BaseServiceImpl<InterviewerMapper, Interviewer>
        implements InterviewerService {

    @Autowired
    private WokeFlowService wokeFlowService;

    @Autowired
    private HandleFlowService handleFlowService;

    @Override
    public PageResult getInterviewerByPage(InterviewerSearchDto interviewerSearchDto) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("user_name", interviewerSearchDto.getUserName());
        wrapper.orderByDesc("updated_at");
        PageHelper.startPage(interviewerSearchDto.getPageNum(), interviewerSearchDto.getPageSize());
        List list = super.list(wrapper);
        return PageResult.getPageResult(list);
    }

    @SysLog(logtype = "remove", logname = "删除面试者")
    @Override
    public boolean removeInterviewer(String kid) {
        return super.removeById(kid);
    }

    @Override
    public Boolean updateInterviewer(InterviewerDto interviewerDto) {
        Interviewer interviewer = new Interviewer();
        BeanUtils.copyProperties(interviewerDto, interviewer);
        return super.updateById(interviewer);
    }

    @Override
    public String addInterviewer(InterviewerDto interviewerDto) {
        List<WokeFlow> wokeFlows = wokeFlowService.queryWokeFlowListByWokesKid(interviewerDto.getWokeId());
        if (CollectionUtils.isEmpty(wokeFlows)) {
            throw new BusinessException("流程配置不能为空！");
        }
        Interviewer interviewer = new Interviewer();
        BeanUtils.copyProperties(interviewerDto, interviewer);
        String kid = super.insert(interviewer);
        WokeFlow firstWokeFlow = wokeFlows.get(0);
        HandleFlow handleFlow = HandleFlow.builder().wokeFlowId(firstWokeFlow.getKid())
                .handler(firstWokeFlow.getHandler()).interviewer(kid).step(firstWokeFlow.getStep()).build();
        handleFlowService.insert(handleFlow);
        return kid;
    }

}
