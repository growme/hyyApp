package com.ccnet.admin.controller.api;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import com.ccnet.core.entity.TaskSchedule;
import com.ccnet.core.task.utils.ScheduleUtils;
import com.ccnet.core.task.utils.TaskLogUtil;
/**
 * 自动检测域名任务
 * @author jackie wang
 *
 */
/* 案例 同步和不同步的区别  非同步需要加@DisallowConcurrentExecution */
public class AutoCheckDomain implements Job {
	
	   /* 日志对象 */
    private static final Logger LOG = Logger.getLogger(AutoCheckDomain.class);
    
	public void execute(JobExecutionContext context){	
        TaskSchedule taskSchedule = (TaskSchedule)context.getMergedJobDataMap().get(ScheduleUtils.JOB_PARAM_KEY);  		   	     
        String jobName=taskSchedule.getJobName();
		String jobGroup=taskSchedule.getJobGroup();
		String jobClass=taskSchedule.getJobClass();
		LOG.info("任务[" + jobName + "]开始运行");
    	try {
    		//开始处理
    		AutoCheckDomainHelper.autoCheckDomain();
			// 保存日志
			TaskLogUtil.saveTaskLog(jobGroup + ":" + jobName, jobClass,TaskLogUtil.NORMAL, "域名检测任务正常运行");
		} catch (Exception e) {
			LOG.error("任务[" + jobName + "]异常",e);
			// 保存异常日志
			TaskLogUtil.saveTaskLog(jobGroup + ":" + jobName, jobClass,TaskLogUtil.EXCEPTION,e.toString());
		}
	}

	
}
