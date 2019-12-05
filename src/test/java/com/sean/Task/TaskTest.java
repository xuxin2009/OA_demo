package com.sean.Task;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Created by sean on 2019/9/5.
 *
 *  关于流程任务相关的API测试
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class TaskTest {


    /**
     * Task的创建
     */
    @Test
    public void addTaskTest()
    {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();

        taskService.saveTask(taskService.newTask("task2"));

        taskService.saveTask(taskService.newTask("审核任务"));
    }

    /**
     * 删除task
     * 包括单个删除和批量删除多个任务，以及是否级联删除
     */
    @Test
    public void deleteTaskTest()
    {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();

        taskService.deleteTask("审核任务");//单个不级联删除
    }

    /**
     * 设置候选用户组
     */
    @Test
    public void addCandidateGroup()
    {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();

        IdentityService identityService = processEngine.getIdentityService();
        Group group = identityService.createGroupQuery().groupName("groupA").singleResult();//获取用户组

        //创建task
        Task task = taskService.newTask("task2");
        taskService.saveTask(task);

        //绑定task与group的关系
        taskService.addCandidateGroup(task.getId(),group.getId());
    }

    /**
     * 绑定用户与任务的关系，设置候选用户
     */
    @Test
    public  void addCandidateUser()
    {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        IdentityService identityService = processEngine.getIdentityService();

        TaskService taskService = processEngine.getTaskService();

        User user1 = identityService.createUserQuery().userEmail("sean_xin@126.com").singleResult();
        User user2 = identityService.createUserQuery().userEmail("zhangsan@123.com").singleResult();

        Task task = taskService.newTask();
        taskService.saveTask(task);

        taskService.addCandidateUser(task.getId(),user1.getId());
        taskService.addCandidateUser(task.getId(),user2.getId());
    }

    /**
     * 查询用户组下的所有任务
     */
    @Test
    public  void queryGroupTaskTest()
    {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        IdentityService identityService = processEngine.getIdentityService();
        TaskService taskService = processEngine.getTaskService();

        Group group = identityService.createGroupQuery().groupName("groupA").singleResult();
        List<Task> taskList = taskService.createTaskQuery().taskCandidateGroup(group.getId()).list();
        for (Task task : taskList)
        {
            log.info("用户组groupA的候选任务-->taskName:"+task.getName());
        }

        User user = identityService.createUserQuery().userEmail("sean_xin@126.com").singleResult();
        List<Task> userTaskList = taskService.createTaskQuery().taskCandidateUser(user.getId()).list();
        for (Task task : userTaskList)
        {
            log.info("用户userA的候选任务-->taskName:"+task.getName());
        }
    }

    @Test
    public void TaskOwnerTest()
    {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        IdentityService identityService = processEngine.getIdentityService();
        TaskService taskService = processEngine.getTaskService();

        User user = identityService.createUserQuery().userEmail("sean_xin@126.com").singleResult();
        Task task = taskService.newTask();
        task.setName("审核任务");
        taskService.saveTask(task);

       //设置任务持有人
        taskService.setOwner(task.getId(),user.getId());
        List<Task> taskList = taskService.createTaskQuery().taskOwner(user.getId()).list();
        for(Task task1 : taskList)
        {
            System.out.println("用户持有的任务名称为："+task1.getName());
        }
    }

}
