package com.ccnet.admin.controller.api;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.ccnet.core.common.MemeberLevelType;
import com.ccnet.core.common.cache.InitSystemCache;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.SpringWebContextUtil;
import com.ccnet.core.common.utils.base.Const;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.common.utils.dataconvert.impl.BaseDto;
import com.ccnet.core.entity.TaskSchedule;
import com.ccnet.core.task.utils.ScheduleUtils;
import com.ccnet.core.task.utils.TaskLogUtil;
import com.ccnet.cps.dao.MemberInfoDao;
import com.ccnet.cps.entity.MemberInfo;
import com.ccnet.cps.entity.SbMoneyCount;
import com.ccnet.cps.entity.SbVisitMoney;
import com.ccnet.cps.service.SbMoneyCountService;
import com.ccnet.cps.service.SbVisitMoneyService;

/**
 * 会员等级
 * @author JackieWang
 *
 */
public class updateMemberLevelTask implements Job {
	
	/*日志对象*/
    private static final Logger LOG = Logger.getLogger(updateMemberLevelTask.class);
    
  	private MemberInfoDao memberInfoDao = (MemberInfoDao) SpringWebContextUtil.getBean("memberInfoDao"); 
  	private SbVisitMoneyService visitMoneyService = (SbVisitMoneyService) SpringWebContextUtil.getApplicationContext().getBean("sbVisitMoneyService"); 
	private SbMoneyCountService moneyCountService = (SbMoneyCountService) SpringWebContextUtil.getApplicationContext().getBean("sbMoneyCountService");  
  	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		TaskSchedule taskSchedule = (TaskSchedule)context.getMergedJobDataMap().get(ScheduleUtils.JOB_PARAM_KEY);  		   	     
        String jobName=taskSchedule.getJobName();
		String jobGroup=taskSchedule.getJobGroup();
		String jobClass=taskSchedule.getJobClass();
		LOG.info("任务[" + jobName + "]开始运行");
		try {
			//获取会员信息
			int start = 0;
    		int limit = 50;
			boolean temp = false;
			List<MemberInfo> memberList = null;
			while(true) {
					memberList = memberInfoDao.findMemberInfoList(start, limit);
					if (CollectionUtils.isEmpty(memberList)) {
	    				break;
	    			}
					double totalMoney = 0;
					MemeberLevelType levelType = MemeberLevelType.REGULAR;
					for (MemberInfo mb : memberList) {
						//当前会员等级
						MemeberLevelType _levelType = MemeberLevelType.getMemeberLevelType(mb.getMemberLevel());
						totalMoney = getYesterDayTotalMoney(mb);
						//判断昨日总收益
						if(totalMoney > 0){
							//处理升级
							if(totalMoney > MemeberLevelType.GOLDEN.getMoneyCount()){//黄金会员   前一天总 收益>50
								levelType = MemeberLevelType.GOLDEN;
							}else if(totalMoney > MemeberLevelType.PLATINUM.getMoneyCount()){//白金会员  前一天总 收益>100
								levelType = MemeberLevelType.PLATINUM;
							}else if(totalMoney > MemeberLevelType.DIAMOND.getMoneyCount()){//钻石会员  前一天总 收益>300
								levelType = MemeberLevelType.DIAMOND;
							}else{//上述条件均不满足 则降级
								
								//如果当前会员是钻石会员 降级为白金
								if(_levelType.equals(MemeberLevelType.DIAMOND)){
									levelType = MemeberLevelType.PLATINUM;
								}
								//如果当前会员是白金会员 降级为黄金
								if(_levelType.equals(MemeberLevelType.PLATINUM)){
									levelType = MemeberLevelType.GOLDEN;
								}
								//如果当前会员是黄金会员 降级为普通
								if(_levelType.equals(MemeberLevelType.GOLDEN)){
									levelType = MemeberLevelType.REGULAR;
								}
								//普通会员不用降级
							}
		                    //处理更新操作
		                    temp = memberInfoDao.updateMemberLevel(levelType.getType(), mb.getMemberId());
		                    if(temp){
		                    	InitSystemCache.updateCache(Const.CT_MEMBER_LIST);
		                    	HessianClientUtil.updateCache(Const.CT_MEMBER_LIST);
		                    	CPSUtil.xprint("更新会员【"+mb.getLoginAccount()+"】等级为【"+levelType.getName()+"】成功");
		                    }else{
		                    	CPSUtil.xprint("更新会员【"+mb.getLoginAccount()+"】等级为【"+levelType.getName()+"】失败");
		                    }
						}
					}
					
				 start += limit;
			}
			
			// 保存日志
			TaskLogUtil.saveTaskLog(jobGroup + ":" + jobName, jobClass,TaskLogUtil.NORMAL, "任务[" + jobName + "]正常结束运行");
		} catch (Exception e) {
			LOG.error("任务[" + jobName + "]异常",e);
			// 保存异常日志
			TaskLogUtil.saveTaskLog(jobGroup + ":" + jobName, jobClass,TaskLogUtil.EXCEPTION,e.toString());
		}finally{
			//更新会员缓存
			InitSystemCache.updateCache(Const.CT_MEMBER_LIST);
			CPSUtil.xprint("更新会员缓存到内存成功！");
		}
	}
	
	
	/**
	 * 获取用户昨日总收益
	 * @param memberInfo
	 * @return
	 */
	private Double getYesterDayTotalMoney(MemberInfo memberInfo) {
		
		Double yesterDayMoney = 0.0d;//昨日收益
		Dto paramDto = new BaseDto();
		String visitCode = memberInfo.getVisitCode();
		if(CPSUtil.isNotEmpty(visitCode)){
			//设置用户查询
			SbVisitMoney sbVisitMoney = new SbVisitMoney();
			SbMoneyCount sbMoneyCount = new SbMoneyCount();
			sbVisitMoney.setUserId(memberInfo.getMemberId());
			sbMoneyCount.setUserId(memberInfo.getMemberId());
			//设置查询日期
			String startDate = CPSUtil.getDateByUDay(-1);
			String overDate = CPSUtil.getDateByUDay(-1);
			paramDto.put("end_date", overDate);
			paramDto.put("start_date", startDate);
			//获取今日邀请奖励
			double totalVisitMoney = visitMoneyService.getCurrentUserVisitMoney(sbVisitMoney, paramDto);
			//获取今日文章/红包/提成收益
			double totalMoneyCount = moneyCountService.getCurrentUserMoneyCount(sbMoneyCount, paramDto);
			//总收益=文章收益+注册奖励
			yesterDayMoney = totalVisitMoney + totalMoneyCount;
		}
		
		CPSUtil.xprint("昨日收益总金额："+yesterDayMoney);
		return yesterDayMoney;
	}

}
