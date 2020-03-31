package com.creativec.example.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.creativec.common.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author ZSX
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("handle_flow")
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    @ApiModelProperty(value = "待审批人ID", hidden = true)
    @TableField("interviewer")
    private String interviewer;

    @ApiModelProperty(value = "流程执行者ID", hidden = true)
    @TableField("handler")
    private String handler;

    @ApiModelProperty(value = "意见描述")
    @TableField("description")
    private String description;


    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @ApiModelProperty(value = "处理时间")
    @TableField("handle_time")
    private LocalDateTime handleTime;


}
