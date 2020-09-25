package com.ccnet.core.task.job;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.SpringWebContextUtil;
import com.ccnet.core.entity.TaskSchedule;
import com.ccnet.core.task.utils.ScheduleUtils;
import com.ccnet.core.task.utils.TaskLogUtil;
import com.ccnet.cps.dao.SbSignInfoDao;
import com.ccnet.cps.entity.SbSignInfo;

/**
 * 重置用户签到数据
 * @author JackieWang
 *
 */
public class ResetMemberSignTask implements Job {

	/*日志对象*/
    private static final Logger LOG = Logger.getLogger(ResetMemberSignTask.class);
    //日收益排行
  	private SbSignInfoDao sbSignInfoDao = (SbSignInfoDao) SpringWebContextUtil.getBean("sbSignInfoDao"); 
  	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		TaskSchedule taskSchedule = (TaskSchedule)context.getMergedJobDataMap().get(ScheduleUtils.JOB_PARAM_KEY);  		   	     
        String jobName=taskSchedule.getJobName();
		String jobGroup=taskSchedule.getJobGroup();
		String jobClass=taskSchedule.getJobClass();
		LOG.info("任务[" + jobName + "]开始运行");
		try {
			//重置用户签到数据
			SbSignInfo signInfo = new SbSignInfo();
			List<SbSignInfo> signList = sbSignInfoDao.findList(signInfo);
			if(CPSUtil.listNotEmpty(signList)){
				for (SbSignInfo sign : signList) {
					sign.setSeriesCount(0);
					sign.setSignMoney(0.0);
					sign.setTotalCount(0);
					sign.setTotalMoney(0.0);
					sign.setLastSignTime(new Date());
					sbSignInfoDao.editSbSignInfo(sign);
				}
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
