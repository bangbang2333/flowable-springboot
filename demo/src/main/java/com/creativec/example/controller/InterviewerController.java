package com.creativec.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.creativec.common.base.IgnoreAuth;
import com.creativec.common.base.PageResult;
import com.creativec.example.entity.Interviewer;
import com.creativec.example.service.InterviewerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author cc
 *
 *         2020年3月21日
 */
@RestController
@RequestMapping("/interviewer")
@Api(tags = { "面试者接口" })
public class InterviewerController {

    @Autowired
    private InterviewerService interviewerService;

    @ApiOperation(notes = "interviewer", value = "分页查询面试者")
    @GetMapping("/interviewer")
    public PageResult getInterviewerByPage(Interviewer interviewer) {
        return interviewerService.getInterviewerByPage(interviewer);
    }

    @ApiOperation(notes = "interviewer", value = "删除面试者")
    @DeleteMapping("/interviewer")
    public boolean removeInterviewer(String kid) {
        return interviewerService.removeInterviewer(kid);
    }

    @ApiOperation(notes = "interviewer", value = "修改面试者基本信息")
    @PutMapping("/interviewer")
    public Boolean updateInterviewer(Interviewer interviewer) {
        return interviewerService.updateInterviewer(interviewer);
    }

    @IgnoreAuth
    @ApiOperation(notes = "interviewer", value = "添加用户")
    @PostMapping("/interviewer")
    public String addInterviewer(Interviewer interviewer) {
        return interviewerService.addInterviewer(interviewer);
    }

}
