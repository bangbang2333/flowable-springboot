package com.creativec.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

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
@TableName("wx_user")
public class WxUser {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "openId")
    @TableField("open_id")
    private String openId;

    @ApiModelProperty(value = "subscribe")
    @TableField("subscribe")
    private Integer subscribe = 1;

    @ApiModelProperty(value = "取关时间")
    @TableField("quit_at")
    private LocalDateTime quitAt;


    @ApiModelProperty(value = "关注时间")
    @TableField(value = "create_at", fill = FieldFill.INSERT)
    private LocalDateTime createAt;

}
