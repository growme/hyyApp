package com.ccnet.admin.controller.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.SpringWebContextUtil;
import com.ccnet.core.common.utils.base.Const;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.common.utils.redis.JedisUtils;
import com.ccnet.core.entity.TaskSchedule;
import com.ccnet.core.task.utils.ScheduleUtils;
import com.ccnet.core.task.utils.TaskLogUtil;
import com.ccnet.cps.service.SbContentVisitLogService;

/**
 * 更新文章心跳时间
 * 
 * @author Jackie Wang
 * 
 */
public class UpdateContentHeatBeatTask implements Job {

	/* 日志对象 */
	private static final Logger LOG = Logger.getLogger(UpdateContentHeatBeatTask.class);
	private SbContentVisitLogService contentVisitLogService = SpringWebContextUtil.getBean("sbContentVisitLogService",SbContentVisitLogService.class); 
	
	@Override
	public void execute(JobExecutionContext context) {
		TaskSchedule taskSchedule = (TaskSchedule) context.getMergedJobDataMap().get(ScheduleUtils.JOB_PARAM_KEY);
		String jobName = taskSchedule.getJobName();
		String jobGroup = taskSchedule.getJobGroup();
		String jobClass = taskSchedule.getJobClass();
		LOG.info("任务[" + jobName + "]开始运行");
		try {
			boolean temp = updateHeartBeatCache();
			if (temp) {
				LOG.info("更新文章心跳任务成功...");
			} else {
				LOG.info("更新文章心跳任务失败...");
			}
			// 保存日志
			TaskLogUtil.saveTaskLog(jobGroup + ":" + jobName, jobClass,TaskLogUtil.NORMAL, "任务[" + jobName + "]正常结束运行");
		} catch (Exception e) {
			LOG.error("任务[" + jobName + "]异常", e);
			// 保存异常日志
			TaskLogUtil.saveTaskLog(jobGroup + ":" + jobName, jobClass,TaskLogUtil.EXCEPTION, e.toString());
		}
	}

	/**
	 * 更新文章心跳
	 * 
	 * @return
	 */
	public boolean updateHeartBeatCache() {
		boolean temp = false;
		try {
			String timeCount = CPSUtil.getParamValue(Const.CT_HEART_BEAT_UPDATE_TIME);// 更新心跳时间间隔
			if (CPSUtil.isEmpty(timeCount)) {
				timeCount = "5";// 默认超过五分钟没有更新心跳则入库
			}
//			CPSUtil.xprint("开始更新，间隔时间==" + timeCount);
			int count = 1000;
			// 文章心跳日志
			ScanParams scanParams = new ScanParams().match(Const.CT_CONTENT_HEART_BEAT + ":*").count(count);
			String cursor = "0";
			do{
				ScanResult<String> result = JedisUtils.scan(cursor, scanParams);
				if (result == null) {
//					CPSUtil.xprint("更新心跳，同步结果为001null");
					break;
				}
				List<String> list = result.getResult();
				if (list == null) {
//					CPSUtil.xprint("更新心跳，同步结果为0021null");
					break;
				}
				saveToDb(list, timeCount);
				temp=true;
				cursor = result.getStringCursor();
			} while(!"0".equals(cursor));
//			CPSUtil.xprint("《《《《《《同步结束！》》》》》");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return temp;
	}
	
	private void saveToDb(List<String> keys, String timeCount) {

		String heartTime = null;
		String visitToken = null;
		for (String key : keys) {
//			CPSUtil.xprint("cachekey==" + key);
			Map<String, Object> objectMap = JedisUtils.getObjectMap(key);
			if (CPSUtil.isNotEmpty(objectMap)) {
//				CPSUtil.xprint("cacheDto==" + objectMap);
				heartTime = objectMap.containsKey("heartTime") ? String.valueOf(objectMap.get("heartTime")) : null;
				visitToken = objectMap.containsKey("visitToken") ? String.valueOf(objectMap.get("visitToken")) : null;
				if (CPSUtil.isNotEmpty(visitToken) && CPSUtil.isNotEmpty(heartTime)) {
					// 判断时间间隔是否超过5分钟没有更新心跳
					int heartBeatCount = CPSUtil.getSubSecond(heartTime,CPSUtil.getCurrentTime());
//					CPSUtil.xprint("距离最后心跳时间已经：【" + heartBeatCount+"】秒");
					if (heartBeatCount > Integer.valueOf(timeCount) * 60) {
						// 处理同步更新心跳
						contentVisitLogService.updateHeartBeatTime(visitToken, heartTime);
						// 处理同步更新 滑动 晃动 展开 等参数
						contentVisitLogService.updateContentReadParam(visitToken);
					}
				}
			}
		}
		
	}

}
