package com.haiyang.flowablespringboot.wellsoft3;

import lombok.extern.slf4j.Slf4j;
import org.flowable.common.engine.impl.history.HistoryLevel;
import org.flowable.engine.*;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.task.api.Task;
import org.junit.Test;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class ProcessTess {

    /**
     * @Description:创建流程引擎
     * @Param: []
     * @return: void
     * @Author: Mr.zengbang
     * @Date: 2020/7/13
     */
    @Test
    public void getProcessEngineTest() {
        ProcessEngineConfiguration cfg = new StandaloneProcessEngineConfiguration()
                .setJdbcUrl("jdbc:mysql://rm-m5eurfxs3558j21lino.mysql.rds.aliyuncs.com:3306/activity?useUnicode=true&characterEncoding=utf8")
                .setJdbcUsername("zengbang")
                .setJdbcPassword("12345678")
                .setJdbcDriver("com.mysql.jdbc.Driver")
                .setDatabaseSchemaUpdate(StandaloneProcessEngineConfiguration.DB_SCHEMA_UPDATE_DROP_CREATE)
                .setHistoryLevel(HistoryLevel.FULL);
        cfg.buildProcessEngine();
        log.info("流程引擎启动成功");
    }

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
     * @Description:部署流程
     * @Param: []
     * @return: void
     * @Author: Mr.zengbang
     * @Date: 2020/7/13
     */
    @Test
    public void deployProcess() {
        ProcessEngine processEngine = getProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deployment = repositoryService.createDeployment()
                .name("运策量子请假流程3")
                .key("wellsoft_holiday3")
                .addClasspathResource("wellsoft_holiday3.bpmn20.xml")
                .deploy();
        log.info("部署成功,id是：{}", deployment.getId());
        log.info("部署成功,key是：{}", deployment.getKey());
        log.info("部署成功,name是：{}", deployment.getName());
        log.info("部署成功,引擎版本是：{}", deployment.getEngineVersion());
        log.info("部署成功,部署时间是：{}", deployment.getDeploymentTime().toLocaleString());
    }


    /**
     * @Description:查询已存在的流程
     * @Param: []
     * @return: void
     * @Author: Mr.zengbang
     * @Date: 2020/7/13
     */
    @Test
    public void getDeployProcess() {
        ProcessEngine processEngine = getProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        List<Deployment> deployments = repositoryService.createDeploymentQuery().list();
        for (Deployment deployment : deployments) {
            log.info("流程部署,id是:{}", deployment.getId());
            log.info("流程部署,key是:{}", deployment.getKey());
            log.info("流程部署,name是:{}", deployment.getName());
            log.info("流程部署,引擎版本是:{}", deployment.getEngineVersion());
            log.info("流程部署,部署时间是:{}", deployment.getDeploymentTime().toLocaleString());
        }

        List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery()
                .orderByProcessDefinitionVersion().asc()
                .list();
        Map<String, ProcessDefinition> processDefinitionMap = new HashMap();
        for (ProcessDefinition processDefinition : processDefinitions) {
            processDefinitionMap.put(processDefinition.getKey(), processDefinition);
        }
        Collection<ProcessDefinition> processDefinitionCollections = processDefinitionMap.values();
        for (ProcessDefinition processDefinition : processDefinitionCollections) {
            log.info("流程定义,id是:{}", processDefinition.getId());
            log.info("流程定义,key是:{}", processDefinition.getKey());
            log.info("流程定义,name是:{}", processDefinition.getName());
            log.info("流程定义,引擎版本是:{}", processDefinition.getEngineVersion());
            log.info("流程定义,定义版本是:{}", processDefinition.getVersion());
            log.info("流程定义,部署id是:{}", processDefinition.getDeploymentId());
        }
    }

    /**
     * @Description:启动一个流程
     * @Param: []
     * @return: void
     * @Author: Mr.zengbang
     * @Date: 2020/7/13
     */
    @Test
    public void startProcess() {
        RuntimeService runtimeService = getProcessEngine().getRuntimeService();
        String processKey = "process-holiday3";
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("reason", "生病了");
        objectMap.put("time", "请假一个星期");
        runtimeService.startProcessInstanceByKey(processKey, objectMap);
        log.info("流程启动成功");
    }

    /**
     * @Description:查询任务的数量
     * @Param: []
     * @return: void
     * @Author: Mr.zengbang
     * @Date: 2020/7/13
     */
    @Test
    public void selectTask() {
        TaskService taskService = getProcessEngine().getTaskService();
        String a = "曾棒,张三";
        Task task = taskService.createTaskQuery().taskAssignee(a).singleResult();
        log.info("任务的id是：{}", task.getId());
        Map<String, Object> processVariables = taskService.getVariables(task.getId());
        log.info("任务里面说了些啥？{}", processVariables);
    }

    /**
     * @Description:提交任务
     * @Param: []
     * @return: void
     * @Author: Mr.zengbang
     * @Date: 2020/7/13
     */
    @Test
    public void applyTask() {
        TaskService taskService = getProcessEngine().getTaskService();
        String a = "曾棒";
        Task task = taskService.createTaskQuery().taskAssignee(a).singleResult();
        log.info("任务的id是：{}", task.getId());
        Map<String, Object> variable = new HashMap<>();
        variable.put("apply", "yes");
        variable.put("message", "我确实要请假");
        taskService.complete(task.getId(), variable);
        log.info("申请者提交了任务");
    }

    @Test
    public void applyTask1() {
        TaskService taskService = getProcessEngine().getTaskService();
        String a = "刘军";
        Task task = taskService.createTaskQuery().taskAssignee(a).singleResult();
        log.info("任务的id是：{}", task.getId());
        Map<String, Object> variable = new HashMap<>();
        variable.put("managerapprove", "yes");
        variable.put("message", "同意");
//        variable.put("managerapprove", "no");
//        variable.put("message", "不同意");
        taskService.complete(task.getId(), variable);
        log.info("申请者提交了任务");
    }

    @Test
    public void applyTask2() {
        TaskService taskService = getProcessEngine().getTaskService();
        String a = "曹臣";
        Task task = taskService.createTaskQuery().taskAssignee(a).singleResult();
        log.info("任务的id是：{}", task.getId());
        Map<String, Object> variable = new HashMap<>();
        variable.put("directorapprove", "yes");
        variable.put("message", "同意");
//        variable.put("directorapprove", "no");
//        variable.put("message", "不同意");
        taskService.complete(task.getId(), variable);
        log.info("申请者提交了任务");
    }

    @Test
    public void applyTask3() {
        TaskService taskService = getProcessEngine().getTaskService();
        String a = "Tim";
        Task task = taskService.createTaskQuery().taskAssignee(a).singleResult();
        log.info("任务的id是：{}", task.getId());
        Map<String, Object> variable = new HashMap<>();
//        variable.put("employerapprove", "yes");
//        variable.put("message", "同意");
        variable.put("employerapprove", "no");
        variable.put("message", "不同意");
        taskService.complete(task.getId(), variable);
        log.info("申请者提交了任务");
    }

    /**
     * @Description:查询剩余任务
     * @Param: []
     * @return: void
     * @Author: Mr.zengbang
     * @Date: 2020/7/13
     */
    @Test
    public void selectTask1() {
        TaskService taskService = getProcessEngine().getTaskService();
        String a = "曾棒";
        List<Task> tasks = taskService.createTaskQuery().list();
        log.info("目前还有任务总数是：{}", tasks.size());
        for (Task task : tasks) {
            log.info("任务id:{}", task.getId());
            log.info("任务指派人:{}", task.getAssignee());
            log.info("任务创建时间:{}", task.getCreateTime());
            log.info("任务名字:{}", task.getName());
            log.info("任务拥有者:{}", task.getOwner());
            log.info("任务的流程定义id:{}", task.getTaskDefinitionId());
            log.info("任务的流程定义key:{}", task.getTaskLocalVariables());
        }
    }

}



