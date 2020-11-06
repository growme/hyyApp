package com.ccnet.admin.controller.api;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import com.ccnet.core.common.cache.InitSystemCache;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.RandomNum;
import com.ccnet.core.common.utils.SpringWebContextUtil;
import com.ccnet.core.common.utils.UniqueID;
import com.ccnet.core.common.utils.base.Const;
import com.ccnet.core.dao.impl.SystemParamDao;
import com.ccnet.core.entity.SystemParams;
import com.ccnet.core.entity.TaskSchedule;
import com.ccnet.core.task.utils.ScheduleUtils;
import com.ccnet.core.task.utils.TaskLogUtil;
/**
 * 更新已发放奖金
 * @author Jackie Wang
 *
 */
public class UpdateDefaulBounsTask implements Job{
	
	   /* 日志对象 */
    private static final Logger LOG = Logger.getLogger(UpdateDefaulBounsTask.class);
    private SystemParamDao systemParamDao = (SystemParamDao) SpringWebContextUtil.getBean("systemParamDao"); 
	@Override
	public void execute(JobExecutionContext context){	
        TaskSchedule taskSchedule = (TaskSchedule)context.getMergedJobDataMap().get(ScheduleUtils.JOB_PARAM_KEY);  		   	     
        String jobName=taskSchedule.getJobName();
		String jobGroup=taskSchedule.getJobGroup();
		String jobClass=taskSchedule.getJobClass();
		LOG.info("任务[" + jobName + "]开始运行");
    	try {
    		
    		//获取全局奖金默认值
    		Integer _bouns = 0;
    		String bouns = CPSUtil.getParamValue(Const.SYS_DEFAULT_BOUNS);
    		if(CPSUtil.isNotEmpty(bouns)){
    			//处理资金累计
    			_bouns = Integer.valueOf(bouns) + RandomNum.getRandomIntVal(100);
    		}else{
    			_bouns = Integer.valueOf(UniqueID.getUniqueID(6, 0));//如果没有设置默认给6位数的值
    		}
    		
    		//处理更新
    		SystemParams params = new SystemParams();
    		params.setParamKey(Const.SYS_DEFAULT_BOUNS);
    		params.setParamValue(_bouns+"");
    		if(systemParamDao.editSystemParam(params)){
    			//更新缓存
    			InitSystemCache.updateCache(Const.CT_PARAM_LIST);
//    			HessianClientUtil.updateCache(Const.CT_PARAM_LIST);
    			CPSUtil.xprint("全局奖金更新为【"+_bouns+"】成功！");
    		}else{
    			CPSUtil.xprint("全局奖金更新为【"+_bouns+"】失败！");
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
