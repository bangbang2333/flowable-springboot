package com.creativec.example.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.creativec.common.base.BaseServiceImpl;
import com.creativec.common.base.PageResult;
import com.creativec.common.base.SysLog;
import com.creativec.example.entity.Interviewer;
import com.creativec.example.mapper.InterviewerMapper;
import com.creativec.example.service.InterviewerService;
import com.github.pagehelper.PageHelper;

/**
 * @author cc
 */
@Service
public class InterviewerServiceImpl extends BaseServiceImpl<InterviewerMapper, Interviewer>
        implements InterviewerService {

    @Override
    public PageResult getInterviewerByPage(Interviewer interviewer) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("user_name", interviewer.getUserName());
        wrapper.orderByDesc("updated_at");
        PageHelper.startPage(interviewer.getPageNum(), interviewer.getPageSize());
        List list = super.list(wrapper);
        return PageResult.getPageResult(list);
    }

    @SysLog(logtype = "remove", logname = "删除面试者")
    @Override
    public boolean removeInterviewer(String kid) {
        return super.removeById(kid);
    }

    @Override
    public Boolean updateInterviewer(Interviewer interviewer) {
        return super.updateById(interviewer);
    }

    @Override
    public String addInterviewer(Interviewer interviewer) {
        String kid = super.insert(interviewer);
        // TODO 获取wokeflow
        // TODO 新建handleflow
        return kid;
    }

}
