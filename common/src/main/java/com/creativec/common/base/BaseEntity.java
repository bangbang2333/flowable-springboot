package com.creativec.common.base;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 实体对象基类
 */
@Data
@ExcelIgnoreUnannotated
public class BaseEntity implements Serializable {

    @TableId(type = IdType.ASSIGN_UUID)
    @ApiModelProperty("主键")
    private String kid;

    /**
     * 仅支持 updateById(id) 与 update(entity, wrapper) 方法
     */
    @Version
    @TableField(value = "version", fill = FieldFill.INSERT)
    @ApiModelProperty("乐观锁版本")
    private Integer version;

    /**
     * 逻辑删除
     */
    @TableLogic
    @TableField(value = "is_deleted", fill = FieldFill.INSERT, select = false)
    @ApiModelProperty(hidden = true)
    @JsonIgnore
    private Integer deleted;

    @TableField(value = "created_by", fill = FieldFill.INSERT, select = false)
    @ApiModelProperty(hidden = true)
    @JsonIgnore
    private String createdBy;

    @TableField(value = "created_at", fill = FieldFill.INSERT)
    @ApiModelProperty(hidden = true)
    private Integer createdAt;

    @TableField(value = "created_from", fill = FieldFill.INSERT, select = false)
    @ApiModelProperty(hidden = true)
    @JsonIgnore
    private String createdFrom;

    @TableField(value = "created_ip", fill = FieldFill.INSERT, select = false)
    @ApiModelProperty(hidden = true)
    @JsonIgnore
    private String createdIp;

    @TableField(value = "updated_by", fill = FieldFill.INSERT_UPDATE, select = false)
    @ApiModelProperty(hidden = true)
    @JsonIgnore
    private String updatedBy;

    @TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE, select = false)
    @ApiModelProperty(hidden = true)
    @JsonIgnore
    private Integer updatedAt;

    @TableField(value = "updated_from", fill = FieldFill.INSERT_UPDATE, select = false)
    @ApiModelProperty(hidden = true)
    @JsonIgnore
    private String updatedFrom;

    @TableField(value = "updated_ip", fill = FieldFill.INSERT_UPDATE, select = false)
    @ApiModelProperty(hidden = true)
    @JsonIgnore
    private String updatedIp;

    @TableField(exist = false)
    @JsonIgnore
    private int pageNum = 1;

    @TableField(exist = false)
    @JsonIgnore
    private int pageSize = 20;

}
