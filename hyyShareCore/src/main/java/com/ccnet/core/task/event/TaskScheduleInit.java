package com.ccnet.core.task.event;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ccnet.core.service.TaskScheduleService;


/**
 * 定时任务初始化
 */
@Component
public class TaskScheduleInit {

    /** 日志对象 */
    private static final Logger LOG = Logger.getLogger(TaskScheduleInit.class);

    /** 定时任务service */
    @Autowired
    private TaskScheduleService  taskScheduleService;

    /**
     * 项目启动时初始化
     */
    @PostConstruct
    public void init() {
        LOG.info("定时任务初始化init");
        taskScheduleService.initTaskSchedule();
        LOG.info("定时任务初始化end");
    }

}
