package com.sean;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.identity.Authentication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.UUID;

/**
 * Created by sean on 2019/9/3.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserTest {

    @Test
    public void addUser()
    {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        IdentityService identityService = processEngine.getIdentityService();

        String uuid = UUID.randomUUID().toString();
        User user = identityService.newUser(uuid);
        user.setPassword("123546");
        user.setEmail("xiaoliu@126.com");
        user.setFirstName("张");
        user.setLastName("小刘");

        identityService.saveUser(user);

         uuid = UUID.randomUUID().toString();
         user = identityService.newUser(uuid);
        user.setPassword("123546");
        user.setEmail("bangzhuren@126.com");
        user.setFirstName("张");
        user.setLastName("小张");

        identityService.saveUser(user);

       /* User user1 = identityService.createUserQuery().userEmail("sean_xin@126.com").singleResult();
        System.out.println("username:"+user1.getLastName()+" "+user1.getFirstName());*/
    }

    @Test
    public void checkPasswordTest()
    {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        IdentityService identityService = processEngine.getIdentityService();

        System.out.println("密码123456校验结果："+identityService.checkPassword("ed2466e3-9e21-413a-9ef4-44448b177ce5","123546"));
        System.out.println("密码123校验结果："+identityService.checkPassword("ed2466e3-9e21-413a-9ef4-44448b177ce5","123"));
    }

    @Test
    public void  authenticatedUserId()
    {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        IdentityService identityService = processEngine.getIdentityService();

        identityService.setAuthenticatedUserId("1");
        System.out.println("当前线程UserId:"+ Authentication.getAuthenticatedUserId());
    }

    @Test
    public void UserInfoTest()
    {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        IdentityService identityService = processEngine.getIdentityService();

        String userId = UUID.randomUUID().toString();
        User user = identityService.newUser(userId);
        user.setLastName("zhang");
        user.setFirstName("san");
        user.setEmail("zhangsan@123.com");
        user.setPassword("12345");

        identityService.saveUser(user);

        identityService.setUserInfo(userId,"age","30");
        identityService.setUserInfo(userId,"weight","70Kg");
    }

    /**
     * 用户与用户组的绑定
     */
    @Test
    public void MemberShipTest()
    {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        IdentityService identityService = processEngine.getIdentityService();

        User user = identityService.createUserQuery().userEmail("zhangsan@123.com").singleResult();
        Group group = identityService.createGroupQuery().groupName("groupB").singleResult();

        identityService.createMembership(user.getId(),group.getId());
    }

    /**
     * 查询用户组下的用户
     */


    @Test
    public void queryMembershipTest()
    {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        IdentityService identityService = processEngine.getIdentityService();

        //获取到groupId
        Group group = identityService.createGroupQuery().groupName("groupA").singleResult();

       List<User> userList = identityService.createUserQuery().memberOfGroup(group.getId()).list();
        for (User user : userList)
        {
            System.out.println("userName:"+user.getFirstName());
        }
     }

    /**
     * 查询用户所在的用户组
     */
    @Test
    public void queryUserGroup()
    {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        IdentityService identityService = processEngine.getIdentityService();

        User user = identityService.createUserQuery().userEmail("zhangsan@123.com").singleResult();
        List<Group> groupList = identityService.createGroupQuery().groupMember(user.getId()).list();

        for(Group group : groupList)
        {
            System.out.println("group:"+group.getName());
        }
    }











































}
