package com.ccnet.core.task.job;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.SpringWebContextUtil;
import com.ccnet.core.common.utils.sms.IPLocationUtil;
import com.ccnet.core.entity.TaskSchedule;
import com.ccnet.core.task.utils.ScheduleUtils;
import com.ccnet.core.task.utils.TaskLogUtil;
import com.ccnet.cps.dao.SbContentVisitLogDao;
import com.ccnet.cps.entity.SbContentVisitLog;
/**
 * 补充LoginLog, MemberLoginLog,OrderInfo表的ipLocation字段
 * @author Xiong Wei
 *
 */
public class LoadVisitLocation implements Job {
	
	/* 日志对象 */
    private static final Logger LOG = Logger.getLogger(LoadVisitLocation.class);
    
  	private SbContentVisitLogDao visitLogDao = (SbContentVisitLogDao) SpringWebContextUtil.getBean("sbContentVisitLogDao");  
    
	@Override
	public void execute(JobExecutionContext context){	
        TaskSchedule taskSchedule = (TaskSchedule)context.getMergedJobDataMap().get(ScheduleUtils.JOB_PARAM_KEY);  		   	     
        String jobName=taskSchedule.getJobName();
		String jobGroup=taskSchedule.getJobGroup();
		String jobClass=taskSchedule.getJobClass();
		LOG.info("任务[" + jobName + "]开始运行");
    	try {
    		Map<String, String> ipLocationCache = new HashMap<String, String>(0);
    		int start = 0;
    		int limit = 50;
    		String ip = null;
    		String location = null;
    		/**
    		 * 处理访问日志归属地
    		 */
    		List<SbContentVisitLog> visitLogs = null;
    		start = 0;
    		while(true) {
    			visitLogs = visitLogDao.findNotIpLocationVisitLog(start, limit);
    			if (CollectionUtils.isEmpty(visitLogs)) {
    				break;
    			}
    			for (SbContentVisitLog visitLog : visitLogs) {
					ip = visitLog.getRequestIp();
					ip = StringUtils.trimToEmpty(ip);
					//先在内存缓存中找
					location = ipLocationCache.get(ip);
					if (StringUtils.isBlank(location)) {
						//如果没有，则调用接口获取
						location = IPLocationUtil.getIpLocation(ip);
						ipLocationCache.put(ip, location);
					}
					String province = "";
					if (StringUtils.isNotBlank(location)) {
						visitLog.setIpLocation(location);
						//设置省份
						province = location.split(",")[0];
						CPSUtil.xprint("province="+province);
						if(CPSUtil.isNotEmpty(province)){
						   visitLog.setProvince(province);
						}
						visitLogDao.updateIpLocation(location,province,visitLog.getVisitId());
					}
					ip = null;
					location = null;
				}
    			visitLogs = null;
    			start += limit;
    		}
    		
			// 保存日志
			TaskLogUtil.saveTaskLog(jobGroup + ":" + jobName, jobClass,TaskLogUtil.NORMAL, "任务[" + jobName + "]正常结束运行");
		} catch (Exception e) {
			LOG.error("任务[" + jobName + "]异常",e);
			// 保存异常日志
			TaskLogUtil.saveTaskLog(jobGroup + ":" + jobName, jobClass,TaskLogUtil.EXCEPTION,e.toString());
		}
	}

	
}
