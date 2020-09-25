package com.ccnet.core.service;

import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.entity.TaskSchedule;
import com.ccnet.core.service.base.BaseService;
/**
 * 定时任务service
 */
public interface TaskScheduleService extends BaseService<TaskSchedule>{

    /**
     * 初始化定时任务
     */
    public void initTaskSchedule();
    
    public Page<TaskSchedule> findTaskScheduleByPage(TaskSchedule taskSchedule,
			Page<TaskSchedule> page, Dto pdDto);

    /**
     * 新增
     * 
     * @param TaskLog
     * @return
     */
    public int creatTaskSchedule(TaskSchedule o);

    
    /**
     * 直接修改 只能修改运行的时间，参数、同异步等无法修改
     * 
     * @param TaskLog
     */
    public int updateTaskSchedule(TaskSchedule o);

    /**
     * 删除
     * 
     * @param taskScheduleId
     */
    public int deleteTaskSchedule(TaskSchedule o);

    /**
     * 运行一次任务
     *
     * @param taskScheduleId the schedule job id
     * @return
     */
    public int runOnce(TaskSchedule o);

    /**
     * 暂停任务
     *
     * @param taskScheduleId the schedule job id
     * @return
     */
    public int pauseJob(TaskSchedule o);

    /**
     * 恢复任务
     *
     * @param taskScheduleId the schedule job id
     * @return
     */
    public int resumeJob(TaskSchedule o);
}
