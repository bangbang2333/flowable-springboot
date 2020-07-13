package com.creativec.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author zhongshengxiang
 * @since 2020-03-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_user")
public class SysUser {

    private static final long serialVersionUID = 1L;


    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    @TableField("user_name")
    private String userName;

    /**
     * 真实姓名
     */
    @ApiModelProperty(value = "真实姓名")
    @TableField("real_name")
    private String realName;

    /**
     * 真实姓名
     */
    @ApiModelProperty(value = "密码")
    @TableField("password")
    private String password;


    @TableLogic
    @JsonIgnore
    @TableField(value = "is_deleted", fill = FieldFill.INSERT, select = false)
    private String deleted;
}
