package com.haiyang.flowablespringboot.listener;

import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.ExecutionListener;

@Slf4j
public class ExecEmployeeListener implements ExecutionListener {
    @Override
    public void notify(DelegateExecution execution) {
        log.info("执行的ID:{}" + execution.getId());
        log.info("执行的名字:{}" + execution.getEventName());
        log.info("执行的父亲的名字:{}" + execution.getParent().getEventName());
        log.info("执行的流程变量:{}" + execution.getVariables());
        log.info("执行的流程定义id:{}" + execution.getExecutions());
    }
}
