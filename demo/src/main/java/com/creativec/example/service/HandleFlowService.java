package com.creativec.example.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.creativec.common.base.AuthDataHolder;
import com.creativec.common.base.BaseServiceImpl;
import com.creativec.common.base.GlobalResponse;
import com.creativec.common.exception.BusinessException;
import com.creativec.example.entity.HandleFlow;
import com.creativec.example.entity.Interviewer;
import com.creativec.example.entity.SysUser;
import com.creativec.example.entity.WokeFlow;
import com.creativec.example.mapper.HandleFlowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author ZSX
 */
@Service
public class HandleFlowService extends BaseServiceImpl<HandleFlowMapper, HandleFlow> {

    @Autowired SysUserService sysUserService;
    @Autowired InterviewerService interviewerService;
    @Autowired WokeFlowService wokeFlowService;

    @Transactional(rollbackFor = Exception.class)
    public GlobalResponse handleFlow(String kid, Integer result, String description) {
        if (result != 1 && result != 2) {
            return GlobalResponse.fail("无效的操作");
        }
        HandleFlow handleFlow = getById(kid);
        if (handleFlow == null) {
            return GlobalResponse.fail("流程不存在");
        }
        if (!handleFlow.getHandler().equals(AuthDataHolder.get().getKid())) {
            return GlobalResponse.fail("只有执行人才能执行该操作");
        }
        LambdaUpdateWrapper<HandleFlow> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(HandleFlow::getKid, kid)
                .eq(HandleFlow::getResult, 0)
                .set(HandleFlow::getDescription, description)
                .set(HandleFlow::getResult, result)
                .set(HandleFlow::getHandleTime, LocalDateTime.now());
        if (update(wrapper)) {
            boolean success = false;
            LambdaUpdateWrapper<Interviewer> updateWrapper = new LambdaUpdateWrapper<>();
            if (result == 2) {
                updateWrapper.eq(Interviewer::getKid, handleFlow.getInterviewer())
                        .eq(Interviewer::getResult, 0)
                        .set(Interviewer::getResult, 2);
                success = interviewerService.update(updateWrapper);
            } else if (result == 1) {
                WokeFlow wokeFlow = wokeFlowService.getById(handleFlow.getWokeFlowId());
                LambdaQueryWrapper<WokeFlow> queryWrapper = new LambdaQueryWrapper();
                queryWrapper.eq(WokeFlow::getWokeId, wokeFlow.getWokeId())
                        .eq(WokeFlow::getStep, handleFlow.getStep() + 1)
                        .last("limit 1");
                WokeFlow nextWork = wokeFlowService.getOne(queryWrapper);
                if (nextWork == null) {
                    updateWrapper.eq(Interviewer::getKid, handleFlow.getInterviewer())
                            .eq(Interviewer::getResult, 0)
                            .set(Interviewer::getResult, 1);
                    success = interviewerService.update(updateWrapper);
                } else {
                    HandleFlow nextFlow = HandleFlow.builder()
                            .wokeFlowId(nextWork.getKid())
                            .handler(nextWork.getHandler())
                            .interviewer(handleFlow.getInterviewer())
                            .step(nextWork.getStep())
                            .build();
                    success = save(nextFlow);
                }
            }
            if (success) {
                return GlobalResponse.success(null);
            } else {
                throw new BusinessException("操作失败");
            }
        }
        return GlobalResponse.fail();
    }

    public List<Map> getHandleFlow(String interviewerId) {
        List<HandleFlow> list = list(new LambdaQueryWrapper<HandleFlow>().eq(HandleFlow::getInterviewer, interviewerId).orderByAsc(HandleFlow::getStep));
        List<Map> result = new ArrayList(list.size());
        if (list.size() == 0) {
            return result;
        }
        List<String> ids = list.stream().map(HandleFlow::getHandler).collect(Collectors.toList());
        List<SysUser> sysUsers = sysUserService.getBaseMapper().selectBatchIds(ids);
        Map<String, String> userMap = sysUsers.stream().collect(Collectors.toMap(SysUser::getKid, SysUser::getRealName));
        for (HandleFlow flow : list) {
            Map map = new HashMap(6);
            map.put("kid", flow.getKid());
            map.put("step", flow.getStep());
            map.put("result", flow.getResult());
            map.put("handleTime", flow.getHandleTime());
            map.put("description", flow.getDescription());
            map.put("handler", userMap.get(flow.getHandler()));
            result.add(map);
        }
        return result;
    }
}
