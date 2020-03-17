package com.creativec.example.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.creativec.common.base.BaseMyBatisMapper;
import com.creativec.example.entity.SysUser;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author zhongshengxiang
 * @since 2020-03-13
 */
@Repository
public interface SysUserMapper extends BaseMyBatisMapper<SysUser> {

    IPage<SysUser> getAll(IPage page);

}
