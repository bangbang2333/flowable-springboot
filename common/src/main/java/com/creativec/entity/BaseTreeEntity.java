package com.creativec.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 树形结构数据对象基类
 */
@Data
public abstract class BaseTreeEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "父节点")
    @TableField("parent_id")
    private Integer parentId;

    @ApiModelProperty(value = "父节点路径")
    @TableField("parent_id_path")
    private String parentIdPath;

    @ApiModelProperty(value = "菜单层级")
    @TableField("level")
    private Integer level;

    @ApiModelProperty(value = "是否叶子节点；0：否，1：是")
    @TableField("is_leaf")
    private Boolean isLeaf;


    public abstract void setChild(List child);
}
