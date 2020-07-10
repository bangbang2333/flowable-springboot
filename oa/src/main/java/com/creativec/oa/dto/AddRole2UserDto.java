package com.creativec.oa.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class AddRole2UserDto {

    @NotNull(message = "用户不能为空")
    private Integer userId;

    @NotEmpty(message = "角色不能为空")
    private List<Integer> roleIds;

}
