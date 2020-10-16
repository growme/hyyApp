package com.ccnet.admin.controller.api;

import com.ccnet.admin.bh.service.NewsService;
import com.ccnet.core.common.utils.DateUtils;
import com.ccnet.core.common.utils.SpringWebContextUtil;
import com.ccnet.core.entity.TaskSchedule;
import com.ccnet.core.task.utils.ScheduleUtils;
import com.ccnet.core.task.utils.TaskLogUtil;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

/**
 * 抓取文章
 *
 * @author Jackie Wang
 *
 */
public class DeleteContentParamTask implements Job {

    /* 日志对象 */
    private static final Logger LOG = Logger.getLogger(DeleteContentParamTask.class);
    private NewsService newsService = SpringWebContextUtil.getBean("newsService", NewsService.class);

    @Override
    public void execute(JobExecutionContext context) {
        TaskSchedule taskSchedule = (TaskSchedule) context.getMergedJobDataMap().get(ScheduleUtils.JOB_PARAM_KEY);
        String jobName = taskSchedule.getJobName();
        String jobGroup = taskSchedule.getJobGroup();
        String jobClass = taskSchedule.getJobClass();
        String description = taskSchedule.getDescription();
        LOG.info("任务[" + jobName + "]开始运行");
        try {
            String date = DateUtils.getAfterDayDate("-30");
            String [] strArr = description.split("\\|");
            if (strArr!=null && strArr.length>=2){
                date = DateUtils.getAfterDayDate("-"+strArr[1]);
            }
            newsService.deleteContentListBeforeDate(date.substring(0,10));
            // 保存日志
            TaskLogUtil.saveTaskLog(jobGroup + ":" + jobName, jobClass, TaskLogUtil.NORMAL,
                    "任务[" + jobName + "]正常结束运行");
        } catch (Exception e) {
            LOG.error("任务[" + jobName + "]异常", e);
            // 保存异常日志
            TaskLogUtil.saveTaskLog(jobGroup + ":" + jobName, jobClass, TaskLogUtil.EXCEPTION, e.toString());
        }
    }


}
