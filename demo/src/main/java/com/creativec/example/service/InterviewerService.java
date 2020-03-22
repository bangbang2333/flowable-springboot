/**
 * 
 */
package com.creativec.example.service;

import com.creativec.common.base.BaseService;
import com.creativec.common.base.PageResult;
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
     * @param interviewer
     * @return
     */
    PageResult getInterviewerByPage(Interviewer interviewer);

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
     * @param interviewer
     * @return
     */
    Boolean updateInterviewer(Interviewer interviewer);

    /**
     * 新建面试者信息
     * 
     * @param interviewer
     * @return
     */
    String addInterviewer(Interviewer interviewer);

}
