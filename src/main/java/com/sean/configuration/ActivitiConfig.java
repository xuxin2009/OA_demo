package com.sean.configuration;



import com.alibaba.druid.pool.DruidDataSource;
import org.activiti.engine.*;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;


/**
 * Created by sean on 2019/8/31.
 */
@Configuration
public class ActivitiConfig {

    @Bean
    public DataSource dataSource()
    {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/OA?characterEncoding=utf8");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        return dataSource;
    }

    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager()
    {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource());
        return dataSourceTransactionManager;
    }

    @Bean
    public ProcessEngine processEngine(DataSource dataSource, DataSourceTransactionManager transactionManager) throws IOException {
        SpringProcessEngineConfiguration configuration = new SpringProcessEngineConfiguration();
        //自动部署已有的流程文件
        Resource[] resources = new PathMatchingResourcePatternResolver().getResources(ResourceLoader.CLASSPATH_URL_PREFIX + "processes*//**//*.bpmn");
        configuration.setTransactionManager(transactionManager);
        configuration.setDataSource(dataSource);
        configuration.setDatabaseSchemaUpdate("true");
        configuration.setDeploymentResources(resources);
        configuration.setDbIdentityUsed(true);
        return configuration.buildProcessEngine();
    }




    @Bean
    public ProcessEngine processEngine() throws IOException {
        SpringProcessEngineConfiguration configuration = new SpringProcessEngineConfiguration();
        //自动部署已有的流程文件
        Resource[] resources = new PathMatchingResourcePatternResolver().getResources(ResourceLoader.CLASSPATH_URL_PREFIX + "processes*//*.bpmn");

        configuration.setDatabaseSchemaUpdate("true");
        configuration.setDeploymentResources(resources);
        configuration.setDbIdentityUsed(false);
        return configuration.buildProcessEngine();
    }

    @Bean
    public IdentityService identityService(ProcessEngine processEngine)
    {
        return processEngine.getIdentityService();
    }
    @Bean
    public RepositoryService repositoryService(ProcessEngine processEngine)
    {
        return processEngine.getRepositoryService();
    }

    @Bean
    public TaskService taskService(ProcessEngine processEngine)
    {
        return processEngine.getTaskService();
    }

    @Bean
    public RuntimeService runtimeService(ProcessEngine processEngine)
    {
        return processEngine.getRuntimeService();
    }
    @Bean
    public HistoryService historyService(ProcessEngine processEngine)
    {
        return processEngine.getHistoryService();
    }
    @Bean
    public ManagementService managementService(ProcessEngine processEngine)
    {
        return processEngine.getManagementService();
    }


}
