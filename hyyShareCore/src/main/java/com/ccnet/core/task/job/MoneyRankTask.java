package com.ccnet.core.task.job;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.RandomNum;
import com.ccnet.core.common.utils.SpringWebContextUtil;
import com.ccnet.core.entity.TaskSchedule;
import com.ccnet.core.task.utils.ScheduleUtils;
import com.ccnet.core.task.utils.TaskLogUtil;
import com.ccnet.cps.dao.TodayMoneyRankDao;
import com.ccnet.cps.dao.TotalMoneyRankDao;
import com.ccnet.cps.entity.TodayMoneyRank;
import com.ccnet.cps.entity.TotalMoneyRank;

public class MoneyRankTask implements Job {
	
	/*日志对象*/
    private static final Logger LOG = Logger.getLogger(LoadLocation.class);
    //日收益排行
  	private TodayMoneyRankDao todayMoneyRankDao = (TodayMoneyRankDao) SpringWebContextUtil.getBean("todayMoneyRankDao"); 
    //总收益排行
  	private TotalMoneyRankDao totalMoneyRankDao = (TotalMoneyRankDao) SpringWebContextUtil.getBean("totalMoneyRankDao"); 
  	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		TaskSchedule taskSchedule = (TaskSchedule)context.getMergedJobDataMap().get(ScheduleUtils.JOB_PARAM_KEY);  		   	     
        String jobName=taskSchedule.getJobName();
		String jobGroup=taskSchedule.getJobGroup();
		String jobClass=taskSchedule.getJobClass();
		LOG.info("任务[" + jobName + "]开始运行");
    	try {
    		
    		//处理日收益递增逻辑
    		TodayMoneyRank todayMoneyRank = new TodayMoneyRank();
    		List<TodayMoneyRank> todayRankList = todayMoneyRankDao.findList(todayMoneyRank);
    		if(CPSUtil.listNotEmpty(todayRankList)){
    			for (TodayMoneyRank todayRank : todayRankList) {
					//随机生成一个金额
    				todayRank.setTodayMoney(Double.valueOf(RandomNum.getRandDoubleVal(100)));
    				todayRank.setUpdateTime(new Date());
    				todayMoneyRankDao.editTodayMoneyRank(todayRank);
				}
    		}
    		
    		//处理总收益递增逻辑
    		Double totalMoney = null;
    		TotalMoneyRank totalMoneyRank = new TotalMoneyRank();
    		List<TotalMoneyRank> totalRankList = totalMoneyRankDao.findList(totalMoneyRank);
    		if(CPSUtil.listNotEmpty(totalRankList)){
    			for (TotalMoneyRank totalRank : totalRankList) {
					//随机生成一个金额
    				totalMoney = totalRank.getTotalMoney()+Double.valueOf(RandomNum.getRandDoubleVal(100));
    				totalRank.setTotalMoney(totalMoney);
    				totalRank.setUpdateTime(new Date());
    				totalMoneyRankDao.editTotalMoneyRank(totalRank);
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
