package com.creativec.common.base;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author ZSX
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_operation_log")
public class SysOperationLog extends BaseEntity{

    @TableField("log_type")
    private String logtype;

    @TableField("log_name")
    private String logname;

    @TableField("method")
    private String method;

    @TableField("params")
    private String params;

    @TableField("return_value")
    private String returnValue;
}
