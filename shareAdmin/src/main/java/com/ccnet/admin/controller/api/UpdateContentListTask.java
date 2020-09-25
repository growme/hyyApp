package com.ccnet.admin.controller.api;

import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import com.alibaba.fastjson.JSON;
import com.ccnet.admin.bh.dao.ApiSbContentInfoDao;
import com.ccnet.core.common.WeightType;
import com.ccnet.core.common.utils.SpringWebContextUtil;
import com.ccnet.core.common.utils.base.Const;
import com.ccnet.core.common.utils.redis.JedisUtils;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.entity.TaskSchedule;
import com.ccnet.core.task.utils.ScheduleUtils;
import com.ccnet.core.task.utils.TaskLogUtil;
import com.ccnet.cps.entity.SbColumnInfo;
import com.ccnet.cps.entity.SbContentInfo;
import com.ccnet.cps.service.SbColumnInfoService;

/**
 * 抓取文章
 * 
 * @author Jackie Wang
 *
 */
public class UpdateContentListTask implements Job {

	/* 日志对象 */
	private static final Logger LOG = Logger.getLogger(UpdateContentListTask.class);
	private ApiSbContentInfoDao apiSbContentInfoDao = SpringWebContextUtil.getBean("apiSbContentInfoDao",
			ApiSbContentInfoDao.class);
	private SbColumnInfoService sbColumnInfoService = SpringWebContextUtil.getBean("sbColumnInfoService",
			SbColumnInfoService.class);

	@Override
	public void execute(JobExecutionContext context) {
		TaskSchedule taskSchedule = (TaskSchedule) context.getMergedJobDataMap().get(ScheduleUtils.JOB_PARAM_KEY);
		String jobName = taskSchedule.getJobName();
		String jobGroup = taskSchedule.getJobGroup();
		String jobClass = taskSchedule.getJobClass();
		LOG.info("任务[" + jobName + "]开始运行");
		try {
			SbColumnInfo arg0 = new SbColumnInfo();
			arg0.setEnabled(1);
			List<SbColumnInfo> columns = sbColumnInfoService.getColumnInfoList(arg0);
			for (int i = 0; i < columns.size(); i++) {
				SbContentInfo sbContentInfo = new SbContentInfo();
				sbContentInfo.setContentType(columns.get(i).getColumnType());
				sbContentInfo.setColumnId(columns.get(i).getColumnId());
				if (columns.get(i).getColumnType() == 0) {
					sbContentInfo.setHomeToped(WeightType.TOP.getType());
					TopArticle(sbContentInfo);
					sbContentInfo.setHomeToped(WeightType.HITHPRICE.getType());
					TopArticle(sbContentInfo);
				}
				sbContentInfo.setHomeToped(WeightType.COMMON.getType());
				pulldownArticle(sbContentInfo);
			}
			// 保存日志
			TaskLogUtil.saveTaskLog(jobGroup + ":" + jobName, jobClass, TaskLogUtil.NORMAL,
					"任务[" + jobName + "]正常结束运行");
		} catch (Exception e) {
			LOG.error("任务[" + jobName + "]异常", e);
			// 保存异常日志
			TaskLogUtil.saveTaskLog(jobGroup + ":" + jobName, jobClass, TaskLogUtil.EXCEPTION, e.toString());
		}
	}

	public void pulldownArticle(SbContentInfo sbContentInfo) {
		Page<SbContentInfo> contents = new Page<SbContentInfo>();
		contents.setPageSize(10);
		sbContentInfo.setCheckState(1);
		List<SbContentInfo> con = apiSbContentInfoDao.getSbContentList(sbContentInfo, contents);
		JedisUtils.set("ARTICLE_LIST" + ":TYPE" + sbContentInfo.getContentType() + "_ColumnId_"
				+ sbContentInfo.getColumnId() + "_WeightType_" + sbContentInfo.getHomeToped(), JSON.toJSONString(con),
				Const.REDIS_TIME);
	}

	public void TopArticle(SbContentInfo sbContentInfo) {
		Page<SbContentInfo> contents = new Page<SbContentInfo>();
		sbContentInfo.setCheckState(1);
		List<SbContentInfo> con = apiSbContentInfoDao.findSbContentInfoByPage(sbContentInfo, contents);
		JedisUtils.set("ARTICLE_LIST" + ":TYPE" + sbContentInfo.getContentType() + "_ColumnId_"
				+ sbContentInfo.getColumnId() + "_WeightType_" + sbContentInfo.getHomeToped(), JSON.toJSONString(con),
				Const.REDIS_TIME);
	}
}
