package com.sean;


import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.identity.Group;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.UUID;
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class OaApplicationTests {


	@Test
	public void contextLoads() {
	}

	@Test
	public void createActivitiTable()
	{

	}

	/**
	 * identityServiceTest
	 * 创建用户组
	 */
	@Test
	public void createGroupUser()
	{
		//创建默认的流程引擎
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		//得到身份服务组件实例
		IdentityService identityService = processEngine.getIdentityService();
		//生成UUID
		String genId = UUID.randomUUID().toString();
		//调用newGroup方法创建newGroup实例
		Group group = identityService.newGroup(genId);
		group.setName("经理组");
		group.setType("manager");
		//group.setId(null);//这样可以让activiti去生成主键id,避免主键重复，而在这里使用的UUID做主键
		//保存用户组到数据库
		identityService.saveGroup(group);

		//查询用户组
		Group data = identityService.createGroupQuery().groupId(genId).singleResult();
		log.info("Group id:{},data name:{}",data.getId(),data.getName());
	}

	/**
	 * updateGroupUser 更新用户组
	 */
	@Test
	public void updateGroupUser()
	{
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		IdentityService identityService = processEngine.getIdentityService();

		Group group = identityService.createGroupQuery().groupId("2501").singleResult();
		group.setName("经理1组");
		identityService.saveGroup(group);
	}

	/**
	 * 删除用户组 deleteGroupUser
	 *
	 */
	@Test
	public void deleteGroupUser()
	{
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		IdentityService identityService = processEngine.getIdentityService();

		Long count = identityService.createGroupQuery().count();
		System.out.println("删除之前用户组的数量:"+count);
		identityService.deleteGroup("2501");
		count = identityService.createGroupQuery().count();
		System.out.println("删除后用户组的数量："+count);
	}

	/**
	 * 查询userGroup用户组的API
	 */
	@Test
	public void queryGroupUser()
	{
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		IdentityService identityService = processEngine.getIdentityService();
		//list
		List<Group> groupList = identityService.createGroupQuery().list();
		for(Group group : groupList)
		{
			System.out.println("用户组ID："+group.getId());
			System.out.println("用户组name："+group.getName());
			System.out.println("用户组type："+group.getType());
			System.out.println("");
		}

		//排序
		groupList = identityService.createGroupQuery().orderByGroupId().desc().list();
		for(Group group : groupList)
		{
			System.out.println("用户组ID："+group.getId());
		}
	}

	//原生sql查询

	@Test
	public void queryGroupBySql()
	{
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		IdentityService identityService = processEngine.getIdentityService();


		Group group1 = identityService.newGroup(UUID.randomUUID().toString());
		group1.setName("groupA");
		group1.setType("typeA");
		identityService.saveGroup(group1);

		Group group2 = identityService.newGroup(UUID.randomUUID().toString());
		group2.setName("groupB");
		group2.setType("typeB");
		identityService.saveGroup(group2);

		Group group3 = identityService.newGroup(UUID.randomUUID().toString());
		group3.setName("groupC");
		group3.setType("typeC");
		identityService.saveGroup(group3);

		Group group4 = identityService.newGroup(UUID.randomUUID().toString());
		group4.setName("groupD");
		group4.setType("typeD");
		identityService.saveGroup(group4);

		Group group5 = identityService.newGroup(UUID.randomUUID().toString());
		group5.setName("groupE");
		group5.setType("typeE");
		identityService.saveGroup(group5);

		List<Group> groupList = identityService.createNativeGroupQuery()
				.sql("select * from act_id_group").list();

		for (Group group : groupList)
		{
			System.out.println("id:"+group.getName());
		}
	}

}
