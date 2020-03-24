/**
 * 
 */
package com.creativec.example.dto;

import com.baomidou.mybatisplus.annotation.TableField;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author cc
 *
 *         2020年3月24日
 */
@Data
public class InterviewerSearchDto {

    @ApiModelProperty(value = "姓名")
    private String userName;

    @TableField(exist = false)
    private int pageNum = 1;

    @TableField(exist = false)
    private int pageSize = 20;

    /*
     * @ApiModelProperty(value = "电话") private String mobile;
     * 
     * @ApiModelProperty(value = "面试预约时间") private Date appointmentTime;
     * 
     * @ApiModelProperty(value = "流程id(面试岗位)") private String wokeId;
     */

}
