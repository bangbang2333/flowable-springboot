/**
 * 
 */
package com.creativec.example.dto;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author cc
 *
 *         2020年3月24日
 */
@Data
public class InterviewerDto {

    @ApiModelProperty(value = "主键")
    private String kid;

    @NotBlank
    @ApiModelProperty(value = "姓名")
    private String userName;

    @NotBlank
    @ApiModelProperty(value = "电话")
    private String mobile;

    @NotNull
    // @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "面试预约时间")
    private Date appointmentTime;

    @NotBlank
    @ApiModelProperty(value = "流程id(面试岗位)")
    private String wokeId;

}
