package com.activiti.adun.a_init;

import java.util.List;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * 初始化数据
 * 
 * @author LJH
 *
 */
public class InitTable {

	@Test
	public void initTables() {
		// 创建数据源
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/activiti6ui");
		dataSource.setUsername("root");
		dataSource.setPassword("root");

		// 创建流程引擎的配置
		ProcessEngineConfiguration configuration = ProcessEngineConfiguration
				.createStandaloneProcessEngineConfiguration();
//		configuration.setJdbcDriver("com.mysql.jdbc.Driver");
//		configuration.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/activiti6ui");
//		configuration.setJdbcUsername("root");
//		configuration.setJdbcPassword("root");
		configuration.setDataSource(dataSource);
		/**
		 * ProcessEngineConfiguration.DB_SCHEMA_UPDATE_FALSE 如果数据库里面没有activit的表，也不会创建
		 * ProcessEngineConfiguration.DB_SCHEMA_UPDATE_CREATE_DROP 创建表，使用完之后删除
		 * ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE 如果数据库里面没有表，就创建
		 * 
		 * dorp-create 代表如果数据库里面有表，那么先删除再创建
		 * 
		 */
		// 配置表的初始化的方式
		configuration.setDatabaseSchemaUpdate("drop-create");

		// 得到流程引擎
		ProcessEngine processEngine = configuration.buildProcessEngine();
		System.out.println(processEngine);
	}

	@Test
	public void intiTables2() {
		ProcessEngineConfiguration configuration = ProcessEngineConfiguration
				.createProcessEngineConfigurationFromResource("/activiti.cfg.xml");
		// 得到流程引擎
		ProcessEngine processEngine = configuration.buildProcessEngine();
		System.out.println(processEngine);

	}
	
	@Test
	public void intiTables3() {
		//必须创建activiti.cfg.xml  并配置好数据库的信息
		ProcessEngine processEngine=ProcessEngines.getDefaultProcessEngine();
		System.out.println(processEngine);
		//流程图的部署  修改  删除的服务器 act_ge_bytearray, act_re_deployment, act_re_model, act_re_procdef
		RepositoryService repositoryService = processEngine.getRepositoryService();
		//流程的运行 act_ru_event_subscr, act_ru_execution, act_ru_identitylink, act_ru_job, act_ru_task, act_ru_variable
		RuntimeService runtimeService = processEngine.getRuntimeService();
		TaskService taskService = processEngine.getTaskService();
		//查询历史记录的服务器act_hi_actinst, act_hi_attachment, act_hi_comment, act_hi_detail, act_hi_identitylink, act_hi_procinst, act_hi_taskinst, act_hi_varinst
		HistoryService historyService = processEngine.getHistoryService();
		//页面表单的服务器[了解]
		FormService formService = processEngine.getFormService();
		//对工作流的用户管理的表的操作act_id_group, act_id_info, act_id_membership, act_id_user
		IdentityService identityService = processEngine.getIdentityService();
		//管理器
		ManagementService managementService = processEngine.getManagementService();
	}

}
