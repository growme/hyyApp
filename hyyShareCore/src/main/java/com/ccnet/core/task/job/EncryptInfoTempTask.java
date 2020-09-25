package com.ccnet.core.task.job;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import com.ccnet.core.common.utils.SpringWebContextUtil;
import com.ccnet.core.entity.TaskSchedule;
import com.ccnet.core.task.utils.ScheduleUtils;
import com.ccnet.core.task.utils.TaskLogUtil;
import com.ccnet.cps.dao.MemberInfoDao;
/**
 * 数据加密
 * @author Xiong Wei
 *
 */
public class EncryptInfoTempTask implements Job{
	
	   /* 日志对象 */
    private static final Logger LOG = Logger.getLogger(EncryptInfoTempTask.class);
  	private MemberInfoDao memberInfoDao = (MemberInfoDao) SpringWebContextUtil.getBean("memberInfoDao");  
  	
	@Override
	public void execute(JobExecutionContext context){	
        TaskSchedule taskSchedule = (TaskSchedule)context.getMergedJobDataMap().get(ScheduleUtils.JOB_PARAM_KEY);  		   	     
        String jobName=taskSchedule.getJobName();
		String jobGroup=taskSchedule.getJobGroup();
		String jobClass=taskSchedule.getJobClass();
		LOG.info("任务[" + jobName + "]开始运行");
    	try {
    		 memberInfoDao.temp();
			// 保存日志
			TaskLogUtil.saveTaskLog(jobGroup + ":" + jobName, jobClass,TaskLogUtil.NORMAL, "任务[" + jobName + "]正常结束运行");
		} catch (Exception e) {
			LOG.error("任务[" + jobName + "]异常",e);
			// 保存异常日志
			TaskLogUtil.saveTaskLog(jobGroup + ":" + jobName, jobClass,TaskLogUtil.EXCEPTION,e.toString());
		}
	}

	
}
