package com.ccnet.core.task.job;

import java.text.MessageFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import com.ccnet.core.common.utils.DateUtils;
import com.ccnet.core.common.utils.backup.DatabaseBackupHelper;
import com.ccnet.core.entity.TaskSchedule;
import com.ccnet.core.task.utils.ScheduleUtils;
import com.ccnet.core.task.utils.TaskLogUtil;
/* 案例 同步和不同步的区别  非同步需要加@DisallowConcurrentExecution */
public class BackDatabase implements Job{
	
	   /* 日志对象 */
    private static final Logger LOG = Logger.getLogger(BackDatabase.class);
    
	@Override
	public void execute(JobExecutionContext context){	
        TaskSchedule taskSchedule = (TaskSchedule)context.getMergedJobDataMap().get(ScheduleUtils.JOB_PARAM_KEY);  		   	     
        String jobName=taskSchedule.getJobName();
		String jobGroup=taskSchedule.getJobGroup();
		String jobClass=taskSchedule.getJobClass();
		LOG.info("任务[" + jobName + "]开始运行");
    	try {
    		String fileName = MessageFormat.format("DataBaseBack_{0}.sql", DateUtils.formatDate(new Date(), "yyyyMMddHHmmss"));
    		DatabaseBackupHelper.backup(fileName);
			// 保存日志
			TaskLogUtil.saveTaskLog(jobGroup + ":" + jobName, jobClass,TaskLogUtil.NORMAL, "备份数据库任务正常运行");
		} catch (Exception e) {
			LOG.error("任务[" + jobName + "]异常",e);
			// 保存异常日志
			TaskLogUtil.saveTaskLog(jobGroup + ":" + jobName, jobClass,TaskLogUtil.EXCEPTION,e.toString());
		}
	}

	
}
