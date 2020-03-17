package com.creativec.example.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.creativec.common.base.*;
import com.creativec.common.exception.BusinessException;
import com.creativec.common.tools.AesHelper;
import com.creativec.common.tools.JsonHelper;
import com.creativec.common.tools.RedisHelper;
import com.creativec.example.entity.SysUser;
import com.creativec.example.mapper.SysUserMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author zhongshengxiang
 * @since 2020-03-13
 */
@Service
public class SysUserService extends BaseService<SysUserMapper, SysUser> {

    @Autowired private RedisHelper redisHelper;

    public PageResult getUserByPage(SysUser user) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("user_name", "zsx");
        PageHelper.startPage(user.getPageNum(), user.getPageSize());
        List list = list(wrapper);
        return PageResult.getPageResult(list);
    }


    public String addUser(SysUser user) {
        return insert(user);
    }

    public String login(String username, String password) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("user_name", username);
        SysUser one = getOne(wrapper);
        if (one == null) {
            throw new BusinessException("用户不存在");
        } else {
            String token = createToken(one.getKid());
            AuthDataHolder.User user1 = new AuthDataHolder.User();
            user1.setKid(one.getKid());
            user1.setUserName(one.getUserName());
            redisHelper.set(token, JsonHelper.toJson(user1), BaseConstant.tokenExpire);
            return token;
        }
    }

    String createToken(String userId) {
        return AesHelper.encrypt(userId, "demo" + System.currentTimeMillis());
    }

    public boolean updateUser(SysUser user) {
        return updateById(user);
    }

    @SysLog(logtype = "remove", logname = "删除用户")
    public boolean removeUser(String kid) {
        return removeById(kid);
    }
}
