package com.haiyang.flowablespringboot.wellsoft3;

import lombok.extern.slf4j.Slf4j;
import org.flowable.common.engine.impl.history.HistoryLevel;
import org.flowable.engine.HistoryService;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.flowable.variable.api.history.HistoricVariableInstance;
import org.junit.Test;

import java.util.List;

@Slf4j
public class HistoryProcessTest {

    private ProcessEngine getProcessEngine() {
        ProcessEngineConfiguration cfg = new StandaloneProcessEngineConfiguration()
                .setJdbcUrl("jdbc:mysql://rm-m5eurfxs3558j21lino.mysql.rds.aliyuncs.com:3306/activity?useUnicode=true&characterEncoding=utf8")
                .setJdbcUsername("zengbang")
                .setJdbcPassword("12345678")
                .setJdbcDriver("com.mysql.jdbc.Driver")
                .setDatabaseSchemaUpdate(StandaloneProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE)
                .setHistoryLevel(HistoryLevel.FULL);

        return cfg.buildProcessEngine();
    }

    /**
     * @Description:查询历史流程实例
     * @Param: []
     * @return: void
     * @Author: Mr.zengbang
     * @Date: 2020/7/13
     */
    @Test
    public void selectHistoryInfo() {
        HistoryService historyService = getProcessEngine().getHistoryService();
        List<HistoricProcessInstance> historicProcessInstances = historyService.createHistoricProcessInstanceQuery()
                .list();
        for (HistoricProcessInstance hpi : historicProcessInstances) {
            log.info("历史流程实例ID:" + hpi.getId());
            log.info("流程定义ID:" + hpi.getProcessDefinitionId());
            log.info("历史流程实例的业务ID:" + hpi.getBusinessKey());
            log.info("流程部署ID:" + hpi.getDeploymentId());
            log.info("流程定义KEY:" + hpi.getProcessDefinitionKey());
            log.info("开始活动ID:" + hpi.getStartActivityId());
            log.info("结束活动ID:" + hpi.getEndActivityId());
            log.info("########################");
        }
    }

    /**
     * @Description:查询历史活动节点
     * @Param: []
     * @return: void
     * @Author: Mr.zengbang
     * @Date: 2020/7/13
     */
    @Test
    public void selectHistoryVariables() {

        HistoryService historyService = getProcessEngine().getHistoryService();
        List<HistoricActivityInstance> historicActivityInstances = historyService.createHistoricActivityInstanceQuery()
                .processInstanceId("5001")
                .finished()
                .list();
        for (HistoricActivityInstance historicActivityInstance : historicActivityInstances) {
            log.info("任务：" + historicActivityInstance.getActivityId() + " 消耗了 "
                    + historicActivityInstance.getDurationInMillis() + " 毫秒");

 /*           log.info("ID:"+historicActivityInstance.getId());
            log.info("流程定义ID:"+historicActivityInstance.getProcessDefinitionId());
            log.info("流程实例ID:"+historicActivityInstance.getProcessInstanceId());
            log.info("执行实例ID:"+historicActivityInstance.getExecutionId());
            log.info("活动ID:"+historicActivityInstance.getActivityId());
            log.info("任务ID:"+historicActivityInstance.getTaskId());
            log.info("活动名称:"+historicActivityInstance.getActivityName());
            log.info("活动类型:"+historicActivityInstance.getActivityType());
            log.info("任务办理人:"+historicActivityInstance.getAssignee());
            log.info("开始时间:"+historicActivityInstance.getStartTime());
            log.info("结束时间:"+historicActivityInstance.getEndTime());
            log.info("持续时间:"+historicActivityInstance.getDurationInMillis());
            log.info("#######################################");*/
        }
    }


    /**
     * @Description:查询历史任务
     * @Param: []
     * @return: void
     * @Author: Mr.zengbang
     * @Date: 2020/7/13
     */
    @Test
    public void selectHistoryTaskInfo() {
        HistoryService historyService = getProcessEngine().getHistoryService();
        List<HistoricTaskInstance> historicTaskInstances = historyService.createHistoricTaskInstanceQuery()
                .list();
        for (HistoricTaskInstance task : historicTaskInstances) {
            log.info("任务ID:" + task.getId());
            log.info("任务办理人:" + task.getAssignee());
            log.info("执行实例ID:" + task.getExecutionId());
            log.info("任务名称:" + task.getName());
            log.info("流程定义ID:" + task.getProcessDefinitionId());
            log.info("流程实例ID:" + task.getProcessInstanceId());
            log.info("任务创建时间:" + task.getCreateTime());
            log.info("任务结束时间:" + task.getEndTime());
            log.info("#######################################");
        }
    }


    /**
     * @Description:查询历史流程变量
     * @Param: []
     * @return: void
     * @Author: Mr.zengbang
     * @Date: 2020/7/13
     */
    @Test
    public void selectTaskInfo() {
        HistoryService historyService = getProcessEngine().getHistoryService();
/*        List<HistoricTaskInstance> historicTaskInstances = historyService.createHistoricTaskInstanceQuery()
                .finished()
                .taskAssignee("曾棒")
                .list();
        for (HistoricTaskInstance historicTaskInstance : historicTaskInstances) {
            log.info("id是：" + historicTaskInstance.getExecutionId());
        }*/

        List<HistoricVariableInstance> historicVariableInstances = historyService.createHistoricVariableInstanceQuery()
                .processInstanceId("5001")
                .orderByProcessInstanceId().desc()
                .list();
        for (HistoricVariableInstance hvs : historicVariableInstances) {
//            log.info("ID：" + hvs.getId());
            log.info("变量名：" + hvs.getVariableName());
            log.info("变量值：" + hvs.getValue());
//            log.info("变量类型：" + hvs.getVariableTypeName());
            log.info("创建时间：" + hvs.getCreateTime().toLocaleString());

            log.info("#####################");
        }

    }
}
