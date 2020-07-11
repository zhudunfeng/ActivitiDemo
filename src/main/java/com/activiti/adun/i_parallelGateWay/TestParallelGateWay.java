package com.activiti.adun.i_parallelGateWay;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;


/**
 * 并行网关
 * if else if  else if  else
 * @author LJH
 *
 */
public class TestParallelGateWay {

	private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

	/**
	 * 使用zip部署流程
	 *
	 */
	@Test
	public void deployProcess(){
		//得到流程部署的Service
		RepositoryService repositoryService = processEngine.getRepositoryService();
		InputStream inputStream = this.getClass().getResourceAsStream("/ParallelGateWayProcess.zip");
		ZipInputStream zipInputStream = new ZipInputStream(inputStream);
		Deployment deploy = repositoryService.createDeployment()
				.name("tao宝流程")
				.addZipInputStream(zipInputStream)
				.deploy();
		System.out.println("部署成功：流程部署ID："+deploy.getId());
	}

	/**
	 * 启动流程
	 * 主要会对act_ru_下的表进行修改
	 */
	@Test
	public void startProcess(){
		//获取运行时Service
		RuntimeService runtimeService = processEngine.getRuntimeService();
		String processDefinitionKey="ParallelGateWayProcess";
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey);
		//从act_ru_exection中查询
		System.out.println("流程启动成功：\n"+"执行实例id："+processInstance.getId()
				+"\n流程定义id："+processInstance.getProcessDefinitionId()
				+"\n流程实例id："+processInstance.getProcessInstanceId());

	}

	/**
	 * 查询我的个人任务 act_ru_task
	 */
	@Test
	public void queryMyTask(){
		TaskService taskService = processEngine.getTaskService();
//		String assignee="买家";
		String assignee="卖家";
		List<Task> taskList = taskService.createTaskQuery()
				//条件
				.taskAssignee(assignee)//根据任务办理人查询任务
				//排序
//				.orderByExecutionId()
				.orderByTaskCreateTime().desc()//倒序
				//结果集
				.list();
		if (null!=taskList&&taskList.size()>0) {
			taskList.forEach(task -> {
				System.out.println("任务执行实例id："+task.getExecutionId());
				System.out.println("任务id："+task.getId());
				System.out.println("父任务id："+task.getParentTaskId());
				System.out.println("任务流程实例id："+task.getProcessInstanceId());
				System.out.println("任务办理人："+task.getAssignee());
				System.out.println("任务名："+task.getName());
				System.out.println("任务创建时间："+task.getCreateTime());
				System.out.println("########################################");
			});
		}


	}


	/**
	 * 办理任务
	 */
	@Test
	public void completeTask(){
		TaskService taskService = this.processEngine.getTaskService();
//		Task task = taskService.createTaskQuery().taskName("收货").singleResult();
//		String taskId =task.getId();
//		String taskId = "2510";//（1）卖家发货
//		String taskId = "5002";//（1）买家收货
//		String taskId = "2507";//（1）买家付款
		String taskId = "10002";//（1）卖家收款

		// 根据任务ID去完成任务
		taskService.complete(taskId);
		// 根据任务ID去完成任务并指定流程变量
		System.out.println("任务完成");
	}
}
