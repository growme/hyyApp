package com.ccnet.admin.controller.api;

import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.SpringWebContextUtil;
import com.ccnet.core.common.utils.base.Const;
import com.ccnet.core.common.utils.redis.JedisUtils;
import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.core.entity.TaskSchedule;
import com.ccnet.core.task.utils.ScheduleUtils;
import com.ccnet.core.task.utils.TaskLogUtil;
import com.ccnet.cps.dao.SbContentVisitLogDao;
import com.ccnet.cps.dao.SbMoneyCountDao;
import com.ccnet.cps.dao.SbUserMoneyDao;
import com.ccnet.cps.entity.SbContentVisitLog;
import com.ccnet.cps.entity.SbMoneyCount;
import com.ccnet.cps.entity.SbUserMoney;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 更新文章相关参数
 * @author Jackie Wang
 *
 */
public class TransferGoldToMoneyTask implements Job{
	
	   /* 日志对象 */
    private static final Logger LOG = Logger.getLogger(TransferGoldToMoneyTask.class);
    private SbMoneyCountDao sbMoneyCountDao = SpringWebContextUtil.getBean("sbMoneyCountDao", SbMoneyCountDao.class);
    private SbUserMoneyDao sbUserMoneyDao = SpringWebContextUtil.getBean("sbUserMoneyDao", SbUserMoneyDao.class);

    @Override
	public void execute(JobExecutionContext context){	
        TaskSchedule taskSchedule = (TaskSchedule)context.getMergedJobDataMap().get(ScheduleUtils.JOB_PARAM_KEY);  		   	     
        String jobName=taskSchedule.getJobName();
		String jobGroup=taskSchedule.getJobGroup();
		String jobClass=taskSchedule.getJobClass();
		LOG.info("任务[" + jobName + "]开始运行");
    	try {
    		//获取前一天用户获得的金币
			BaseDao<Object> dto = new BaseDao<>();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate localDate = LocalDate.now().minusDays(1);
			String date = formatter.format(localDate);
			List<SbMoneyCount> list = sbMoneyCountDao.findTotalMoneyByTime(date,date);
			//转化金额
			for (SbMoneyCount moneyCount : list) {
				SbUserMoney userMoney = new SbUserMoney();
				userMoney.setUserId(moneyCount.getUserId());
				userMoney.setProfitsMoney(moneyCount.getUmoney());
				userMoney.setTmoney(moneyCount.getUmoney());
				userMoney.setUpdateTime(new Date());
				userMoney.setLastProDate(userMoney.getUpdateTime());
				sbUserMoneyDao.insertOrUpdate(userMoney);
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
