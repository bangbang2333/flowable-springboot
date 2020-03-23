package com.creativec.example.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.creativec.common.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author ZSX
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("handle_flow")
@Builder
public class HandleFlow extends BaseEntity {

    @ApiModelProperty(value = "流程步骤id")
    @TableField("woke_flow_id")
    @JsonIgnore
    private String wokeFlowId;

    @ApiModelProperty(value = "流程步骤", hidden = true)
    @TableField("step")
    private Integer step;

    @ApiModelProperty(value = "审批结果:0待审批,1通过,2拒绝")
    @TableField("result")
    private Integer result;

    @ApiModelProperty(value = "处理时间", hidden = true)
    @TableField("handle_time")
    private Integer handleTime;

    @ApiModelProperty(value = "待审批人ID", hidden = true)
    @TableField("interviewer")
    @JsonIgnore
    private String interviewer;

    @ApiModelProperty(value = "流程执行者ID", hidden = true)
    @TableField("handler")
    @JsonIgnore
    private String handler;

    @ApiModelProperty(value = "意见描述")
    @TableField("description")
    private String description;
}
