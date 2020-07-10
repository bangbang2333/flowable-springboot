package com.creativec.oa.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class AddMenu2RoleDto {

    @NotNull(message = "角色不能为空")
    private Integer roleId;

    @NotEmpty(message = "菜单不能为空")
    private List<Integer> menuIds;

}
