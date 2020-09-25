package com.ccnet.admin.controller.api;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.SpringWebContextUtil;
import com.ccnet.core.common.utils.base.Const;
import com.ccnet.core.common.utils.redis.JedisUtils;
import com.ccnet.core.entity.TaskSchedule;
import com.ccnet.core.task.utils.ScheduleUtils;
import com.ccnet.core.task.utils.TaskLogUtil;
import com.ccnet.cps.dao.SbContentVisitLogDao;
import com.ccnet.cps.entity.SbContentVisitLog;

/**
 * 更新文章相关参数
 * @author Jackie Wang
 *
 */
public class UpdateContentReadParamTask implements Job{
	
	   /* 日志对象 */
    private static final Logger LOG = Logger.getLogger(UpdateContentReadParamTask.class);
    private SbContentVisitLogDao sbContentVisitLogDao = SpringWebContextUtil.getBean("sbContentVisitLogDao",SbContentVisitLogDao.class); 
	
    @Override
	public void execute(JobExecutionContext context){	
        TaskSchedule taskSchedule = (TaskSchedule)context.getMergedJobDataMap().get(ScheduleUtils.JOB_PARAM_KEY);  		   	     
        String jobName=taskSchedule.getJobName();
		String jobGroup=taskSchedule.getJobGroup();
		String jobClass=taskSchedule.getJobClass();
		LOG.info("任务[" + jobName + "]开始运行");
    	try {
    		boolean temp = updateContentReadParamCache();
    		if(temp){
    			LOG.info("更新文章阅读参数任务成功...");
    		}else{
    			LOG.info("更新文章阅读参数任务失败...");
    		}
			// 保存日志
			TaskLogUtil.saveTaskLog(jobGroup + ":" + jobName, jobClass,TaskLogUtil.NORMAL, "任务[" + jobName + "]正常结束运行");
		} catch (Exception e) {
			LOG.error("任务[" + jobName + "]异常",e);
			// 保存异常日志
			TaskLogUtil.saveTaskLog(jobGroup + ":" + jobName, jobClass,TaskLogUtil.EXCEPTION,e.toString());
		}
	}
    
    
    /**
	 * 更新文章参数
	 * @return
	 */
	public boolean updateContentReadParamCache() {
		boolean temp = false;
		try{
			int start = 0;
    		int limit = 50;
			//从数据库中提起计费不成功的记录
    		List<SbContentVisitLog> visitList = null;
    		start = 0;
    		while(true) {
    			
    			visitList = sbContentVisitLogDao.findNotAccountVisitLog(start, limit);
    			if (CollectionUtils.isEmpty(visitList)) {
    				break;
    			}
    			
				String hdcs = "0";//滑动次数
				String zkcs = "0";//展开次数
				String ydcs = "0";//移动次数
				Date heartTime = null;
				String visitToken = null;
				Integer heartBeatCount = null;
				HashMap<String, String> readMap = null;
				for (SbContentVisitLog visitLog : visitList) {
					visitToken = visitLog.getVisitToken();
					heartTime = visitLog.getLastHeartBeatTime();
					heartBeatCount = visitLog.getHeartBeatCount();
					readMap = (HashMap)JedisUtils.getMap(Const.CT_CONTENT_READ_PARAM + ":" + visitToken);
					if(CPSUtil.isNotEmpty(readMap)){
						 hdcs = readMap.get("touch_count");
						 if(CPSUtil.isEmpty(hdcs)){
							 hdcs = "0";
						 }
						 zkcs = readMap.get("iaws_index");
						 if(CPSUtil.isEmpty(zkcs)){
							 zkcs = "0";
						 }
						 ydcs = readMap.get("iaws_coord_num");
						 if(CPSUtil.isEmpty(ydcs)){
							 ydcs = "0";
						 }
						 if(!"0".equals(hdcs) || !"0".equals(zkcs) || !"0".equals(ydcs)){
							 //只要有一个值不为0 则更新数据库
							 temp = sbContentVisitLogDao.updateContentReadParam(visitToken,hdcs,zkcs,ydcs);
							 CPSUtil.xprint("批量更新数据库阅读参数【"+visitToken+"】==>>"+(temp==true?"成功":"失败"));
							 if(temp){
								//从redis缓存中移除单个对象
								if(CPSUtil.isNotEmpty(heartTime) && CPSUtil.isNotEmptyForInteger(heartBeatCount)){
									CPSUtil.xprint("心跳数据已经存在，可以删除阅读指纹数据！");
									JedisUtils.removeFingerCacheValue(visitToken);
								 }
							 }
						 }
					}
					
					hdcs = "0";
    				zkcs = "0";
    				ydcs = "0";
				}
				visitList = null;
    			start += limit;
			}
    			
    		
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return temp;
  }

	
}
