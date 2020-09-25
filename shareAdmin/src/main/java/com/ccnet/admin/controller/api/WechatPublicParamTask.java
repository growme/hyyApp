package com.ccnet.admin.controller.api;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ccnet.admin.bh.entity.WechatPublicNumber;
import com.ccnet.admin.bh.service.WechatPublicNumberService;
import com.ccnet.core.common.utils.HttpUtil;
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
public class WechatPublicParamTask implements Job {

	/* 日志对象 */
	private static final Logger LOG = Logger.getLogger(WechatPublicNumberService.class);
	private WechatPublicNumberService wechatPublicNumberService = SpringWebContextUtil
			.getBean("wechatPublicNumberService", WechatPublicNumberService.class);

	@Override
	public void execute(JobExecutionContext context) {
		TaskSchedule taskSchedule = (TaskSchedule) context.getMergedJobDataMap().get(ScheduleUtils.JOB_PARAM_KEY);
		String jobName = taskSchedule.getJobName();
		String jobGroup = taskSchedule.getJobGroup();
		String jobClass = taskSchedule.getJobClass();
		LOG.info("任务[" + jobName + "]开始运行");
		try {
			wechatPublicNumberService.taskWechatPublicNumber();
			LOG.info("更新公众号APPSECRET任务成功...");
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
