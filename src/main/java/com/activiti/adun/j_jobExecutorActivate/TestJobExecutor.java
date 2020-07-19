package com.activiti.adun.j_jobExecutorActivate;

import com.activiti.adun.j_jobExecutorActivate.testTimer.ScheduleTimer;
import org.activiti.engine.*;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.jobexecutor.TimerStartEventJobHandler;
import org.activiti.engine.impl.persistence.entity.TimerEntity;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @Auther ADun
 * @Date 2020/7/15 17:30
 */
public class TestJobExecutor {


    private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();



    @Test
    public void testJobTask(){
        RepositoryService repositoryService = processEngine.getRepositoryService();
        TaskService taskService = processEngine.getTaskService();
        ManagementService managementService = processEngine.getManagementService();
        // 时间计算
        Date now = new Date();
        // delay为相较当前时间，延时的时间变量
        int delay=1;//分钟
        Date target = new Date(now.getTime() + delay * 60 * 1000);
        // 时间事件声明
        String taskId="2507";
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        TimerEntity timer = new TimerEntity();
        timer.setId("1");
        timer.setJobType(task.getName());
        timer.setDuedate(target);
        timer.setExclusive(true);



//        String processDefinitionId=task.getProcessDefinitionId();
//        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
//        String customProcessKey = processDefinition.getKey();
        String customProcessKey ="ParallelGateWayProcess";
        timer.setJobHandlerConfiguration(customProcessKey);// 这里存入需要启动的流程key
        timer.setJobHandlerType(TimerStartEventJobHandler.TYPE);

        // 保存作业事件
        Context.getCommandContext().getJobEntityManager().schedule(timer);
    }

    @Test
    public void getCurrentTime(){
        Date now = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
        System.out.println(now);
        System.out.println(sf.format(now));
        System.out.println(now.getTime());
        long endDate = now.getTime() + (2 * 60 * 1000);
        System.out.println(sf.format(endDate));

        LocalDateTime now1 = LocalDateTime.now();
        System.out.println(now1);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yy-MM-dd HH:mm:ss");
        String format = dateTimeFormatter.format(now1);
        System.out.println(format);
    }
    @Test
    public void getTwoAfter() throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        //定时两分钟
        Long endDateNum = date.getTime() + (2 * 60 * 1000);
        String format = sf.format(endDateNum);
        Date endDate = sf.parse(format);
        System.out.println(endDate);
    }

    public static void main(String[] args) throws ParseException {
        new ScheduleTimer().getTimer();
    }

    @Test
    public void testPrint(){
        System.out.println(0.0/0);
    }
}
