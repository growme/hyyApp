package com.ccnet.core.task.job;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.ccnet.core.entity.TaskSchedule;
import com.ccnet.core.task.event.TaskScheduleInit;
import com.ccnet.core.task.utils.ScheduleUtils;
/* 案例 同步和不同步的区别  非同步需要加@DisallowConcurrentExecution */
public class JobFactory implements Job{
	
	   /* 日志对象 */
    private static final Logger LOG = Logger.getLogger(JobFactory.class);
    
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
			System.out.println("任务成功运行");
			LOG.info("任务成功运行");
	        TaskSchedule taskSchedule = (TaskSchedule)context.getMergedJobDataMap().get(ScheduleUtils.JOB_PARAM_KEY);  		   	
	    	LOG.info("任务名称 = [" + taskSchedule.getJobName() + "]");
	    	System.out.println("任务名称 = [" + taskSchedule.getJobName() + "]");
	}

	
}
