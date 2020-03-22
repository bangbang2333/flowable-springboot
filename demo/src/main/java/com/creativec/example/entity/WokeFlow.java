package com.creativec.example.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.creativec.common.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author llpei
 * @date Created in 11:06 2020/3/22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("woke_flow")
public class WokeFlow extends BaseEntity {

    @ApiModelProperty(value = "流程id")
    @TableField("woke_id")
    private String wokeId;

    @ApiModelProperty(value = "流程步骤名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "流程步骤", hidden = true)
    @TableField("step")
    private Integer step;

    @ApiModelProperty(value = "流程执行者")
    @TableField("handler")
    private String handler;


}
