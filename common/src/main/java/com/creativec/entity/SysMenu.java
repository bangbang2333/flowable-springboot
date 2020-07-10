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
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 菜单表
 * </p>
 *
 * @author zsx
 * @since 2020-07-08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(value = "SysMenu对象", description = "菜单表")
public class SysMenu implements Serializable {

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

    @NotBlank(message = "菜单名称不能为空")
    @ApiModelProperty(value = "菜单名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "菜单图标地址")
    @TableField("icon")
    private String icon;

    @ApiModelProperty(value = "url页面地址")
    @TableField("url")
    private String url;

    @ApiModelProperty(value = "uri接口地址")
    @TableField("uri")
    private String uri;

    @ApiModelProperty(value = "菜单排序号")
    @TableField("sequence")
    private Integer sequence;

    @ApiModelProperty(value = "菜单层级")
    @TableField("level")
    private Integer level;

    @ApiModelProperty(value = "是否叶子节点；0：否，1：是")
    @TableField("is_leaf")
    private Boolean isLeaf;

    @ApiModelProperty(hidden = true)
    @TableField(exist = false)
    private boolean checked;

    @ApiModelProperty(hidden = true)
    @TableField(exist = false)
    private List<SysMenu> child;
}
