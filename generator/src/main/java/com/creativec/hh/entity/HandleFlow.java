package com.creativec.hh.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.creativec.common.base.BaseEntity;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * <p>
 * 流程处理表
 * </p>
 *
 * @author zsx
 * @since 2020-03-31
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="HandleFlow对象", description="流程处理表")
public class HandleFlow extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "流程步骤id")
    @TableField("woke_flow_id")
    private String wokeFlowId;

    @ApiModelProperty(value = "流程步骤")
    @TableField("step")
    private Integer step;

    @ApiModelProperty(value = "审批结果:0待审批,1通过,2拒绝")
    @TableField("result")
    private Integer result;

    @ApiModelProperty(value = "待审批人ID")
    @TableField("interviewer")
    private String interviewer;

    @ApiModelProperty(value = "流程执行者ID")
    @TableField("handler")
    private String handler;

    @ApiModelProperty(value = "意见描述")
    @TableField("description")
    private String description;

    @ApiModelProperty(value = "处理时间")
    @TableField("handle_time")
    private LocalDateTime handleTime;


}
