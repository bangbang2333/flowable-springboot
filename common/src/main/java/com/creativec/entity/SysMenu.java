package com.creativec.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
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
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "SysMenu对象", description = "菜单表")
public class SysMenu extends BaseTreeEntity {

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

    @ApiModelProperty(hidden = true)
    @TableField(exist = false)
    private boolean checked;

    @ApiModelProperty(hidden = true)
    @TableField(exist = false)
    private List<SysMenu> child;

    @Override
    public void setChild(List child) {
        this.child = child;
    }
}
