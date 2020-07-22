package com.haiyang.flowablespringboot.listener2;

import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.ExecutionListener;
import org.flowable.variable.api.persistence.entity.VariableInstance;

@Slf4j
public class EndListener implements ExecutionListener {
    @Override
    public void notify(DelegateExecution execution) {

        log.info("!!!!!!!!!!流程执行完毕！！！！！！！");
        for (String variableInstance : execution.getVariables().keySet()) {
            log.info("key是:{},value是:{}", variableInstance, execution.getVariables().get(variableInstance));
        }

    }
}
