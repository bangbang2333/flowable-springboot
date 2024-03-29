package com.creativec.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author zsx
 * @since 2020-07-08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(value = "SysRole对象", description = "角色表")
public class SysRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @NotBlank(message = "角色名称不能为空")
    @ApiModelProperty(value = "角色名称")
    @TableField("name")
    private String name;

    @NotNull(message = "角色排序号不能为空")
    @ApiModelProperty(value = "角色排序号")
    @TableField("sequence")
    private Integer sequence;

    @ApiModelProperty(hidden = true)
    @TableField(exist = false)
    private boolean checked;

}
