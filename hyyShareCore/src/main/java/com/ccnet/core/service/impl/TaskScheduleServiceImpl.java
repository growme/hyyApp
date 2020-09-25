package com.ccnet.core.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.dao.impl.TaskScheduleDao;
import com.ccnet.core.entity.TaskSchedule;
import com.ccnet.core.service.TaskScheduleService;
import com.ccnet.core.task.utils.ScheduleUtils;

@Service("taskScheduleService")
public class TaskScheduleServiceImpl extends BaseServiceImpl<TaskSchedule> implements TaskScheduleService {

	/** 调度工厂Bean */
	@Autowired
	private Scheduler scheduler;
	
	@Autowired
	private TaskScheduleDao taskScheduleDao;

	@Override
	public void initTaskSchedule() {
		//查找启用的任务
		TaskSchedule aj=new TaskSchedule();
		aj.setJobStatus(0);	
		List<TaskSchedule> taskScheduleList = taskScheduleDao.findList(aj);	
		if (CollectionUtils.isNotEmpty(taskScheduleList)) {
			for (TaskSchedule taskSchedule : taskScheduleList) {
				CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, taskSchedule.getJobName(),taskSchedule.getJobGroup());
				try {
					if (cronTrigger == null) {
						// 不存在，创建一个
						ScheduleUtils.createTaskSchedule(scheduler, taskSchedule);	
					} else {
						// 已存在，那么更新相应的定时设置
						ScheduleUtils.createTaskSchedule(scheduler, taskSchedule);
					}
				} catch (Exception e) {
					logger.error("创建定时任务失败",e);
				}
			}
		}
	}

	
	public Page<TaskSchedule> findTaskScheduleByPage(TaskSchedule taskSchedule,
			Page<TaskSchedule> page, Dto pdDto) {
		return this.taskScheduleDao.findTaskScheduleByPage(taskSchedule, page, pdDto);
	}
	
	@Override
	@Transactional
	public int creatTaskSchedule(TaskSchedule o) {
		int res=0;
		try {
			o.setCreateTime(new Date());	
			//当状态为启用时
			if(o.getJobStatus()!=null && o.getJobStatus()==0){
				ScheduleUtils.createTaskSchedule(scheduler,o);		
			}
			//更新数据库
			super.insert(o);
			res=1;
		} catch (Exception e) {
			logger.error("创建任务失败",e);
		}	
		return res;
	}
	@Override
	@Transactional
	public int updateTaskSchedule(TaskSchedule o) {
		int res=0;
		try {
			//从数据库查找原信息
			TaskSchedule taskSchedule=taskScheduleDao.getTaskScheduleById(o.getTaskScheduleId());
			//先删除
			ScheduleUtils.deleteTaskScheduler(scheduler,taskSchedule.getJobName(),taskSchedule.getJobGroup());
			//当状态为启用时
			if(o.getJobStatus()!=null && o.getJobStatus()==0){
				ScheduleUtils.createTaskSchedule(scheduler, o);		
			}
			//更新数据库
			o.setUpdateTime(new Date());
			o.setCreateTime(taskSchedule.getCreateTime());
			taskScheduleDao.update(o, "taskScheduleId");
			res=1;
		} catch (Exception e) {
			logger.error("创建任务失败",e);
		}	
		return res;
	}

	@Override
	@Transactional
	public int deleteTaskSchedule(TaskSchedule o) {
		int res=0;
		try {
			//从数据库查找原信息
			TaskSchedule taskSchedule=taskScheduleDao.getTaskScheduleById(o.getTaskScheduleId());
			//先删除
			ScheduleUtils.deleteTaskScheduler(scheduler, taskSchedule.getJobName(),taskSchedule.getJobGroup());
			//更新数据库
			taskScheduleDao.delete(o);
			res=1;
		}catch (Exception e) {
			logger.error("删除任务失败", e);
		}
		return res;
	}

	@Override
	@Transactional
	public int runOnce(TaskSchedule o) {
		int res=0;
		try {
			//从数据库查找原信息
			TaskSchedule taskSchedule=taskScheduleDao.getTaskScheduleById(o.getTaskScheduleId());
			if(taskSchedule.getJobStatus()!=null && taskSchedule.getJobStatus()==0){
				//运行一次任务
				res=2;
			}else{
				//当任务没启动，必须先创建
				ScheduleUtils.createTaskSchedule(scheduler, taskSchedule);
				//时间短可能促发多次
				//ScheduleUtils.pauseJob(scheduler,taskSchedule.getJobName(), taskSchedule.getJobGroup());
				//然后立刻运行一次任务
				ScheduleUtils.runOnce(scheduler, taskSchedule.getJobName(), taskSchedule.getJobGroup());
				try {
					//休眠3秒，等任务完成，完成不了就加长休眠时间吧...
			        Thread.sleep(3000);
			    } catch (InterruptedException e) {
			        e.printStackTrace();
			    }
				//再删除任务
				ScheduleUtils.deleteTaskScheduler(scheduler,taskSchedule.getJobName(), taskSchedule.getJobGroup());
				res=1;
			}			
		} catch (Exception e) {
			logger.error("运行一次定时任务失败", e);
		}
		return res;
	}

	@Override
	@Transactional
	public int pauseJob(TaskSchedule o) {
		int res=0;
		try {
			//从数据库查找原信息
			TaskSchedule taskSchedule=taskScheduleDao.getTaskScheduleById(o.getTaskScheduleId());
			if(taskSchedule.getJobStatus()!=null && taskSchedule.getJobStatus()==0){
				//判断jobKey为不为空，如为空，任务已停止
				//先暂停任务
				ScheduleUtils.pauseJob(scheduler, taskSchedule.getJobName(), taskSchedule.getJobGroup());		
				ScheduleUtils.deleteTaskScheduler(scheduler, taskSchedule.getJobName(), taskSchedule.getJobGroup());
				//更新数据库
				taskSchedule.setJobStatus(1);
				taskSchedule.setUpdateTime(new Date());
				taskScheduleDao.update(taskSchedule, "taskScheduleId");
				res=1;
			}else{	
				//任务没启动，谈何暂停...
				res=2;			
			}
		} catch (Exception e) {
			logger.error("暂停定时任务失败", e);
		}
		return res;
	}

	@Override
	@Transactional
	public int resumeJob(TaskSchedule o) {
		int res=0;
		try {
			//从数据库查找原信息
			TaskSchedule taskSchedule=taskScheduleDao.getTaskScheduleById(o.getTaskScheduleId());
			if (taskSchedule.getJobStatus() != null && taskSchedule.getJobStatus() == 1) {
				ScheduleUtils.createTaskSchedule(scheduler, taskSchedule);
				//更新数据库
				taskSchedule.setJobStatus(0);
				taskSchedule.setUpdateTime(new Date());
				taskScheduleDao.update(taskSchedule, "taskScheduleId");
				res=1;
			}else{
				res=2;
			}
		} catch (Exception e) {
			logger.error("恢复定时任务失败", e);
		}
		return res;
	}

	@Override
	protected BaseDao<TaskSchedule> getDao() {
		// TODO Auto-generated method stub
		return this.taskScheduleDao;
	}
	

}
