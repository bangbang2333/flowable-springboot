package com.creativec.example.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.creativec.common.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author cc
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("interviewer")
public class Interviewer extends BaseEntity {

    @ApiModelProperty(value = "姓名")
    @TableField("user_name")
    private String userName;

    @ApiModelProperty(value = "电话")
    @TableField("mobile")
    private String mobile;

    @ApiModelProperty(value = "面试预约时间")
    @TableField("appointment_time")
    private Date appointmentTime;

    @ApiModelProperty(value = "流程id(面试岗位)")
    @TableField("woke_id")
    @JsonIgnore
    private String wokeId;

    @ApiModelProperty(value = "最终面试结果:1通过,2不通过")
    @TableField("result")
    @JsonIgnore
    private Integer result;
}
