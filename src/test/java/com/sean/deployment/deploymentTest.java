package com.sean.deployment;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.identity.User;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by sean on 2019/9/3.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class deploymentTest
{

    /**
     * 输入流文件部署
     * @throws FileNotFoundException
     */

   @Test
    public void deploymentTest() throws FileNotFoundException {
       ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

       RepositoryService repositoryService = processEngine.getRepositoryService();

       DeploymentBuilder deploymentBuilder = repositoryService.createDeployment();

       InputStream inputStream = new FileInputStream(new File("E:/java/1.demo/12.activiti/OA/target/classes/test.jpg"));

       deploymentBuilder.addInputStream("inputA",inputStream);

       deploymentBuilder.deploy();
   }


    @Test
    public void userCandidateTest()
    {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        IdentityService identityService = processEngine.getIdentityService();

        Deployment deployment = repositoryService.createDeployment().addClasspathResource("test.jpg").deploy();

        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).singleResult();

        User user1 = identityService.createUserQuery().userEmail("sean_xin@126.com").singleResult();
        User user2 = identityService.createUserQuery().userEmail("zhangsan@123.com").singleResult();

        repositoryService.addCandidateStarterUser(processDefinition.getId(),user1.getId());
        repositoryService.addCandidateStarterUser(processDefinition.getId(),user2.getId());
    }
}
