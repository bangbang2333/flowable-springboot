package com.haiyang.flowablespringboot.listener;

import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.delegate.TaskListener;
import org.flowable.task.service.delegate.DelegateTask;

@Slf4j
public class EmployeeListener implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {
        log.info("任务的ID:{}" + delegateTask.getId());
        log.info("任务的名字:{}" + delegateTask.getName());
        log.info("任务的办理者:{}" + delegateTask.getAssignee());
        log.info("任务的执行id:{}" + delegateTask.getExecutionId());
        log.info("任务的流程定义id:{}" + delegateTask.getProcessDefinitionId());
        log.info("任务的流程实例id:{}" + delegateTask.getProcessInstanceId());
        log.info("任务的创建时间:{}" + delegateTask.getCreateTime().toLocaleString());
        log.info("任务的结束时间:{}" + delegateTask.getDueDate());
        delegateTask.setAssignee("曾棒");
    }
}
