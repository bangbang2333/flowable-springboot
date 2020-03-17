package com.creativec.example.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.creativec.common.base.BaseEntity;
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
public class SysUser extends BaseEntity {

    private static final long serialVersionUID = 1L;

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

}
