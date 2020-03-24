/**
 * 
 */
package com.creativec.example.service;

import com.creativec.common.base.BaseService;
import com.creativec.common.base.PageResult;
import com.creativec.example.dto.InterviewerDto;
import com.creativec.example.dto.InterviewerSearchDto;
import com.creativec.example.entity.Interviewer;

/**
 * @author cc
 *
 *         2020年3月21日
 */
public interface InterviewerService extends BaseService<Interviewer> {

    /**
     * 分页查询面试者
     * 
     * @param interviewerSearchDto
     * @return
     */
    PageResult getInterviewerByPage(InterviewerSearchDto interviewerSearchDto);

    /**
     * 删除面试者
     * 
     * @param kid
     * @return
     */
    boolean removeInterviewer(String kid);

    /**
     * 修改面试者基本信息
     * 
     * @param interviewerDto
     * @return
     */
    Boolean updateInterviewer(InterviewerDto interviewerDto);

    /**
     * 新建面试者信息
     * 
     * @param interviewerDto
     * @return
     */
    String addInterviewer(InterviewerDto interviewerDto);

}
