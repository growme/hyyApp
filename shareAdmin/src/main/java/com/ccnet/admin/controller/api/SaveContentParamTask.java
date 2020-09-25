package com.ccnet.admin.controller.api;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import com.ccnet.admin.bh.service.NewsService;
import com.ccnet.core.common.utils.SpringWebContextUtil;
import com.ccnet.core.entity.TaskSchedule;
import com.ccnet.core.task.utils.ScheduleUtils;
import com.ccnet.core.task.utils.TaskLogUtil;

/**
 * 抓取文章
 * 
 * @author Jackie Wang
 *
 */
public class SaveContentParamTask implements Job {

	/* 日志对象 */
	private static final Logger LOG = Logger.getLogger(SaveContentParamTask.class);
	private NewsService newsService = SpringWebContextUtil.getBean("newsService", NewsService.class);

	@Override
	public void execute(JobExecutionContext context) {
		TaskSchedule taskSchedule = (TaskSchedule) context.getMergedJobDataMap().get(ScheduleUtils.JOB_PARAM_KEY);
		String jobName = taskSchedule.getJobName();
		String jobGroup = taskSchedule.getJobGroup();
		String jobClass = taskSchedule.getJobClass();
		LOG.info("任务[" + jobName + "]开始运行");
		try {
			newsService.taskList();
			// 保存日志
			TaskLogUtil.saveTaskLog(jobGroup + ":" + jobName, jobClass, TaskLogUtil.NORMAL,
					"任务[" + jobName + "]正常结束运行");
		} catch (Exception e) {
			LOG.error("任务[" + jobName + "]异常", e);
			// 保存异常日志
			TaskLogUtil.saveTaskLog(jobGroup + ":" + jobName, jobClass, TaskLogUtil.EXCEPTION, e.toString());
		}
	}


}
