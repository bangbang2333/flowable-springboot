/*
package com.haiyang.flowablespringboot;

import org.flowable.engine.*;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.engine.repository.Deployment;
import org.flowable.task.api.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.constraints.Null;
import java.util.List;

@RunWith(SpringRunner.class)
//@SpringBootTest
public class FlowableSpringbootApplicationTests {

//    @Autowired
//    ProcessEngine processEngine;

    @Test
    public void contextLoads() {
    }

    */
/**
     * @Description:部署一个流程文件
     * @Param: []
     * @return: void
     * @Author: Mr.zengbang
     * @Date: 2020/7/8
     *//*

    @Test
    public void test1() {

        ProcessEngine processEngine = getProcessEngine();

        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deployment = (Deployment) repositoryService.createDeployment()
                .name("申请流程")
                .addClasspathResource("demo.bpmn20.xml")
                .deploy();

        System.out.println("部署成功" + deployment.getId());
    }

    */
/**
     * @Description:启动一次流程
     * @Param: []
     * @return: void
     * @Author: Mr.zengbang
     * @Date: 2020/7/8
     *//*

    @Test
    public void test2() {

        ProcessEngine processEngine = getProcessEngine();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        String processName = "HelloWorld:1:4";
        String processKey = "helloworld";
        runtimeService.startProcessInstanceByKey(processKey);
        System.out.println("流程启动成功");
    }

    */
/**
     * @Description:查询一下某人的任务
     * @Param: []
     * @return: void
     * @Author: Mr.zengbang
     * @Date: 2020/7/8
     *//*

    @Test
    public void test3() {

        ProcessEngine processEngine = getProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        String assignName = "张三";
        List<Task> list = taskService.createTaskQuery().taskAssignee(assignName).list();
        for (Task task: list){
            System.out.println("任务id："+task.getId());
            System.out.println("流程实例id："+task.getProcessInstanceId());
            System.out.println("执行实例id："+task.getExecutionId());
            System.out.println("流程定义id："+task.getProcessDefinitionId());
            System.out.println("任务名称："+task.getName());
            System.out.println("任务办理人："+task.getAssignee());
            System.out.println("##########################");
        }
    }
    */
/**
     * @Description:完成一次任务并查询下一节点的任务数
     * @Param: []
     * @return: void
     * @Author: Mr.zengbang
     * @Date: 2020/7/8
     *//*

    @Test
    public void test4() {

        ProcessEngine processEngine = getProcessEngine();
        TaskService taskService = processEngine.getTaskService();
//        String taskid ="7506";
//        taskService.complete(taskid);
        System.out.println("任务完成");
        String assignName = "张三";
        List<Task> list = taskService.createTaskQuery().taskAssignee(assignName).list();
        for (Task task: list){
            System.out.println("任务id："+task.getId());
            System.out.println("流程实例id："+task.getProcessInstanceId());
            System.out.println("执行实例id："+task.getExecutionId());
            System.out.println("流程定义id："+task.getProcessDefinitionId());
            System.out.println("任务名称："+task.getName());
            System.out.println("任务办理人："+task.getAssignee());
            System.out.println("##########################");
        }

        String assignName1 = "李四";
        List<Task> list1 = taskService.createTaskQuery().taskAssignee(assignName1).list();
        for (Task task: list1){
            System.out.println("任务id："+task.getId());
            System.out.println("流程实例id："+task.getProcessInstanceId());
            System.out.println("执行实例id："+task.getExecutionId());
            System.out.println("流程定义id："+task.getProcessDefinitionId());
            System.out.println("任务名称："+task.getName());
            System.out.println("任务办理人："+task.getAssignee());
            System.out.println("##########################");
        }
    }


    @Test
    public void test5() {
        RuntimeService runtimeService = getProcessEngine().getRuntimeService();
        runtimeService.startProcessInstanceById("");
    }
    @Test
    public void getProcessEngine1() {
        ProcessEngineConfiguration cfg = new StandaloneProcessEngineConfiguration()
                .setJdbcUrl("jdbc:mysql://rm-m5eurfxs3558j21lino.mysql.rds.aliyuncs.com:3306/activity?useUnicode=true&characterEncoding=utf8")
                .setJdbcUsername("zengbang")
                .setJdbcPassword("12345678")
                .setJdbcDriver("com.mysql.jdbc.Driver")
                .setDatabaseSchemaUpdate(StandaloneProcessEngineConfiguration.DB_SCHEMA_UPDATE_DROP_CREATE);
        cfg.buildProcessEngine();
    }

    private ProcessEngine getProcessEngine() {
        ProcessEngineConfiguration cfg = new StandaloneProcessEngineConfiguration()
                .setJdbcUrl("jdbc:mysql://rm-m5eurfxs3558j21lino.mysql.rds.aliyuncs.com:3306/activity?useUnicode=true&characterEncoding=utf8")
                .setJdbcUsername("zengbang")
                .setJdbcPassword("12345678")
                .setJdbcDriver("com.mysql.jdbc.Driver")
                .setDatabaseSchemaUpdate(StandaloneProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);

        return cfg.buildProcessEngine();
    }

}

*/
