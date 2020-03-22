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
 * @date Created in 11:21 2020/3/22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wokes")
public class Wokes extends BaseEntity {

    @ApiModelProperty(value = "流程编码")
    @TableField("code")
    private String code;

    @ApiModelProperty(value = "流程名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "流程描述")
    @TableField("description")
    private String description;

}
