package com.creativec.example.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.creativec.common.base.PageResult;
import com.creativec.example.dto.InterviewerDto;
import com.creativec.example.dto.InterviewerSearchDto;
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
    public PageResult getInterviewerByPage(InterviewerSearchDto interviewerSearchDto) {
        return interviewerService.getInterviewerByPage(interviewerSearchDto);
    }

    @ApiOperation(notes = "interviewer", value = "删除面试者")
    @DeleteMapping("/interviewer")
    public boolean removeInterviewer(String kid) {
        return interviewerService.removeInterviewer(kid);
    }

    @ApiOperation(notes = "interviewer", value = "修改面试者基本信息")
    @PutMapping("/interviewer")
    public Boolean updateInterviewer(@Valid @RequestBody InterviewerDto interviewerDto) {
        return interviewerService.updateInterviewer(interviewerDto);
    }

    @ApiOperation(notes = "interviewer", value = "添加用户")
    @PostMapping("/interviewer")
    public String addInterviewer(@Valid @RequestBody InterviewerDto interviewerDto) {
        return interviewerService.addInterviewer(interviewerDto);
    }

}
