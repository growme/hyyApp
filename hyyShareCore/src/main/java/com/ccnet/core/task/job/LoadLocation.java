package com.ccnet.core.task.job;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import com.ccnet.core.common.utils.SpringWebContextUtil;
import com.ccnet.core.common.utils.sms.IPLocationUtil;
import com.ccnet.core.dao.impl.LoginLogDao;
import com.ccnet.core.entity.LoginLog;
import com.ccnet.core.entity.TaskSchedule;
import com.ccnet.core.task.utils.ScheduleUtils;
import com.ccnet.core.task.utils.TaskLogUtil;
import com.ccnet.cps.dao.MemberLoginLogDao;
import com.ccnet.cps.entity.MemberLoginLog;
/**
 * 补充LoginLog, MemberLoginLog,OrderInfo表的ipLocation字段
 * @author Xiong Wei
 *
 */
public class LoadLocation implements Job {
	
	/* 日志对象 */
    private static final Logger LOG = Logger.getLogger(LoadLocation.class);
    
  	private LoginLogDao loginLogDao = (LoginLogDao) SpringWebContextUtil.getBean("loginLogDao");  
  	private MemberLoginLogDao memberLoginLogDao = (MemberLoginLogDao) SpringWebContextUtil.getBean("memberLoginLogDao");  
    
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
    		 * 处理后台日志
    		 */
    		List<LoginLog> loginLogs = null;
    		start = 0;
    		while(true) {
    			loginLogs = loginLogDao.findNotIpLocationLoginLog(start, limit);
    			if (CollectionUtils.isEmpty(loginLogs)) {
    				break;
    			}
    			for (LoginLog loginLog : loginLogs) {
					ip = loginLog.getLoginIp();
					ip = StringUtils.trimToEmpty(ip);
					//先在内存缓存中找
					location = ipLocationCache.get(ip);
					if (StringUtils.isBlank(location)) {
						//如果没有，则调用接口获取
						location = IPLocationUtil.getIpLocation(ip);
						ipLocationCache.put(ip, location);
					}
					if (StringUtils.isNotBlank(location)) {
						loginLog.setIpLocation(location);
						loginLogDao.updateIpLocation(location, loginLog.getId());
					}
					ip = null;
					location = null;
				}
    			loginLogs = null;
    			start += limit;
    		}
    		loginLogs = null;
    		ip = null;
    		location = null;

    		//处理会员登录日志
    		List<MemberLoginLog> memberLoginLogs = null;
    		start = 0;
    		while(true) {
    			memberLoginLogs = memberLoginLogDao.findNotIpLocationMemberLoginLog(start, limit);
    			if (CollectionUtils.isEmpty(memberLoginLogs)) {
    				break;
    			}
    			for (MemberLoginLog loginLog : memberLoginLogs) {
					ip = loginLog.getLoginIp();
					ip = StringUtils.trimToEmpty(ip);
					//先在内存缓存中找
					location = ipLocationCache.get(ip);
					if (StringUtils.isBlank(location)) {
						//如果没有，则调用接口获取
						location = IPLocationUtil.getIpLocation(ip);
						ipLocationCache.put(ip, location);
					}
					if (StringUtils.isNotBlank(location)) {
						loginLog.setIpLocation(location);
						memberLoginLogDao.updateIpLocation(location, loginLog.getId());
					}
					ip = null;
					location = null;
				}
    			loginLogs = null;
    			start += limit;
    		}
    		memberLoginLogs = null;
    		ip = null;
    		location = null;
    		
			// 保存日志
			TaskLogUtil.saveTaskLog(jobGroup + ":" + jobName, jobClass,TaskLogUtil.NORMAL, "任务[" + jobName + "]正常结束运行");
		} catch (Exception e) {
			LOG.error("任务[" + jobName + "]异常",e);
			// 保存异常日志
			TaskLogUtil.saveTaskLog(jobGroup + ":" + jobName, jobClass,TaskLogUtil.EXCEPTION,e.toString());
		}
	}

	
}
