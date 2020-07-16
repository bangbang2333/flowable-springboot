package com.haiyang.flowablespringboot.wellsoft3;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.common.engine.impl.history.HistoryLevel;
import org.flowable.engine.*;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.image.ProcessDiagramGenerator;
import org.flowable.task.api.Task;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

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
                .setHistoryLevel(HistoryLevel.FULL)
                .setActivityFontName("宋体")
                .setLabelFontName("宋体")
                .setAnnotationFontName("宋体");
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
                .setHistoryLevel(HistoryLevel.FULL)
                .setActivityFontName("宋体")
                .setLabelFontName("宋体")
                .setAnnotationFontName("宋体");
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
        String a = "曾棒";
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

    @Test
    public void selectProcessImage() throws IOException {
        // 创建仓库服务对对象
        RepositoryService repositoryService = getProcessEngine().getRepositoryService();
        // 从仓库中找需要展示的文件
        String deploymentId = "1";
        List<String> names = repositoryService.getDeploymentResourceNames(deploymentId);
        String imageName = null;
        for (String name : names) {
            if (name.indexOf(".png") >= 0) {
                imageName = name;
            }
        }
        if (imageName != null) {
            log.info(imageName);
            File f = new File("C:/" + imageName);
            // 通过部署ID和文件名称得到文件的输入流
            InputStream in = repositoryService.getResourceAsStream(deploymentId, imageName);
            FileUtils.copyInputStreamToFile(in, f);
        }
    }

    @Test
    public void getActivityImage() throws IOException {

        Task task = getProcessEngine().getTaskService().createTaskQuery().taskId("7506").singleResult();
        //使用流程实例ID，查询正在执行的执行对象表，返回流程实例对象
        String InstanceId = task.getProcessInstanceId();
        List<String> activeActivityIds = getProcessEngine().getRuntimeService().getActiveActivityIds(InstanceId);
        List<String> flowList = new ArrayList<>();
        List<HistoricActivityInstance> historicActivityInstances = getProcessEngine().getHistoryService()
                .createHistoricActivityInstanceQuery()
                .processInstanceId(InstanceId)
                .orderByHistoricActivityInstanceStartTime().asc()
                .finished()
                .list();
        for (HistoricActivityInstance historicActivityInstance : historicActivityInstances) {
            if (historicActivityInstance.getActivityType().equals("sequenceFlow")) {
                flowList.add(historicActivityInstance.getActivityId());
            } else {
                activeActivityIds.add(historicActivityInstance.getActivityId());
            }
        }
        //获取流程图
        BpmnModel bpmnModel = getProcessEngine().getRepositoryService().getBpmnModel(task.getProcessDefinitionId());
        ProcessDiagramGenerator diagramGenerator = getProcessEngine().getProcessEngineConfiguration().getProcessDiagramGenerator();
        InputStream in = diagramGenerator.generateDiagram(bpmnModel, "jpg", activeActivityIds, flowList, "黑体", "黑体", "黑体", diagramGenerator.getClass().getClassLoader(), 10000.0, true);
        File f = new File("C:/" + "demo3.jpg");
        // 通过部署ID和文件名称得到文件的输入流
        FileUtils.copyInputStreamToFile(in, f);

    }
}



