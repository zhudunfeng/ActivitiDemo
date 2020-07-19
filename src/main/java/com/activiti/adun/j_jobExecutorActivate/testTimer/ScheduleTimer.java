package com.activiti.adun.j_jobExecutorActivate.testTimer;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @Auther ADun
 * @Date 2020/7/16 17:37
 */
public class ScheduleTimer {

    private  ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    public void getTimer() throws ParseException {
        Timer timer = new Timer();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        //定时两分钟
        Long endDateNum = date.getTime() + (1 * 60 * 1000);
        String format = sf.format(endDateNum);
        Date endDate = sf.parse(format);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                TaskService taskService = processEngine.getTaskService();
                Task task = taskService.createTaskQuery().taskId("10002").singleResult();
                taskService.complete(task.getId());//当前时间的两分钟后执行
                System.out.println("完成任务-"+task.getName());
            }
        },endDate);
    }


}
