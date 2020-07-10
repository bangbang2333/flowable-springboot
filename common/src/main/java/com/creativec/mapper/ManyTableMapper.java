package com.creativec.mapper;

import com.creativec.entity.SysMenu;

import java.util.List;

/**
 * @author ZSX
 */
public interface ManyTableMapper {
    List<SysMenu> getMenuByUser(Integer userId);

    List<SysMenu> getMenuByRole(Integer roleId);

    List<String> getRoleByUser(Integer userId);

    List<Integer> getRoleByUri(String uri);
}
