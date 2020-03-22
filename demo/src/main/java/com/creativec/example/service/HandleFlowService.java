package com.creativec.example.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.creativec.common.base.AuthDataHolder;
import com.creativec.common.base.BaseService;
import com.creativec.common.exception.BusinessException;
import com.creativec.common.tools.DateTime;
import com.creativec.example.entity.HandleFlow;
import com.creativec.example.entity.SysUser;
import com.creativec.example.mapper.HandleFlowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author ZSX
 */
@Service
public class HandleFlowService extends BaseService<HandleFlowMapper, HandleFlow> {

    @Autowired SysUserService sysUserService;

    @Transactional(rollbackFor = Exception.class)
    public boolean handleFlow(String kid, Integer result, String description) {
        HandleFlow handleFlow = getById(kid);
        if (handleFlow == null) {
            throw new BusinessException("流程不存在");
        }
        if (!handleFlow.getHandlerID().equals(AuthDataHolder.get().getKid())) {
            throw new BusinessException("只有执行人才能执行该操作");
        }
        LambdaUpdateWrapper<HandleFlow> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(HandleFlow::getKid, kid)
                .eq(HandleFlow::getResult, 0)
                .set(HandleFlow::getDescription, description)
                .set(HandleFlow::getResult, result);
        if (update(wrapper)) {
            if (result == 2) {
                // todo 拒绝后修改最终状态
            } else if (result == 1) {
                //todo 查找下一个流程并创建,如果没有直接修改最终状态
            } else {
                throw new BusinessException("无效的操作");
            }
        }
        return false;
    }

    public List<Map> getHandleFlow(String interviewerId) {
        List<HandleFlow> list = list(new LambdaQueryWrapper<HandleFlow>().eq(HandleFlow::getWokeFlowId, interviewerId));
        if (list.size() == 0) {
            return new ArrayList<>(0);
        }
        List<String> ids = list.stream().map(HandleFlow::getHandlerID).collect(Collectors.toList());
        List<SysUser> sysUsers = sysUserService.mapper.selectBatchIds(ids);
        Map<String, String> userMap = sysUsers.stream().collect(Collectors.toMap(SysUser::getKid, SysUser::getRealName));
        List result = new ArrayList(list.size());
        for (HandleFlow flow : list) {
            Map map = new HashMap(5);
            map.put("step", flow.getStep());
            map.put("result", flow.getResult());
            map.put("handlerDate", DateTime.converToStringDate(flow.getHandlerDate(), null));
            map.put("description", flow.getDescription());
            map.put("handler", userMap.get(flow.getKid()));
            result.add(map);
        }
        return result;
    }
}
