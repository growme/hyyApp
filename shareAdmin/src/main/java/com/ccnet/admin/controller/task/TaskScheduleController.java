package com.ccnet.admin.controller.task;

import java.io.UnsupportedEncodingException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ccnet.core.common.ajax.AjaxRes;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.base.Const;
import com.ccnet.core.common.utils.base.ResourceTypes;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.controller.BaseController;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.entity.TaskSchedule;
import com.ccnet.core.service.TaskScheduleService;
/**
 * 任务管理
 */
@Controller
@RequestMapping("/backstage/taskschedule/")
public class TaskScheduleController extends BaseController<TaskSchedule>{

	 @Autowired
	 private TaskScheduleService taskScheduleService;
	 
	public static String GO_ADD_URL = "/backstage/taskschedule/goadd";
	public static String GO_EDIT_URL = "/backstage/taskschedule/goedit";
	 
	/**
	 * 任务首页
	 */
	@RequestMapping("index")
	public ModelAndView index() throws UnsupportedEncodingException {
		if (doSecurityIntercept(ResourceTypes.MENU)) {
			//获取集合数据
			Dto paramDto = getParamAsDto();
			Page<TaskSchedule> page = newPage(paramDto);
			page = taskScheduleService.findTaskScheduleByPage(new TaskSchedule(), page, paramDto);
			ModelAndView model = new ModelAndView();
			model.addObject("pm", page);
			model.addObject("queryParam",paramDto.getAsString("queryParam"));
			model.addObject("permitFBtn", getPermitBtn(ResourceTypes.FUNC));	
			model.addObject("permitTBtn", getPermitBtn(ResourceTypes.BUTTON));
			model.addObject(Const.CT_MENU_NAV,"定时任务管理");
			model.setViewName("admin/jsp/task/task_list");
			return model;
		}
		return new ModelAndView(Const.NO_AUTHORIZED_URL);
	}
		
	/**
	 * 添加角色
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "goadd", method = RequestMethod.GET)
	public ModelAndView goAddTaskSchedule() {
		ModelAndView mav = new ModelAndView();
		if(isAuthedReq(ResourceTypes.FUNC)){		
			mav.addObject("enlist", CPSUtil.getCodeList("ENABLED"));
			mav.setViewName("admin/jsp/task/task_info");
		}else{
			mav.setViewName(Const.NO_AUTHORIZED_URL);
		}
		return mav;
	}
	
	/**
	 * 修改
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "goedit", method = RequestMethod.GET)
	public ModelAndView goEditTaskSchedule() {
		ModelAndView mav = new ModelAndView();
		if(isAuthedReq(ResourceTypes.FUNC) || isAuthedReq(ResourceTypes.BUTTON)){		
			Dto paramDto = getParamAsDto();
			String taskScheduleId = paramDto.getAsString("task_id");
			TaskSchedule taskSchedule = null;
			if(StringUtils.isNotBlank(taskScheduleId)){
				taskSchedule = new TaskSchedule();
				taskSchedule.setTaskScheduleId(taskScheduleId);
				taskSchedule = taskScheduleService.find(taskSchedule);
			}
			mav.addObject("taskSchedule", taskSchedule);
			mav.addObject("optype", paramDto.getAsString("optype"));
			mav.addObject("enlist", CPSUtil.getCodeList("ENABLED"));
			mav.setViewName("admin/jsp/task/task_info");
		}else{
			mav.setViewName(Const.NO_AUTHORIZED_URL);
		}
		return mav;
	}

	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public AjaxRes add(TaskSchedule o) {
		AjaxRes ar = getAjaxRes();
		if (ar.setNoAuth(doSecurityIntercept(ResourceTypes.BUTTON, GO_ADD_URL) || doSecurityIntercept(ResourceTypes.FUNC, GO_ADD_URL))) {
			try {
				o.setTaskScheduleId(get32UUID());
				int res = taskScheduleService.creatTaskSchedule(o);
				if (res == 1)
					ar.setSucceedMsg(Const.SAVE_SUCCEED);
				else
					ar.setFailMsg(Const.SAVE_FAIL);
			} catch (Exception e) {
				logger.error(e.toString(), e);
				ar.setFailMsg(Const.SAVE_FAIL);
			}
		}
		return ar;
	}
	
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public AjaxRes update(TaskSchedule o) {
		AjaxRes ar = getAjaxRes();
		if (ar.setNoAuth(doSecurityIntercept(ResourceTypes.BUTTON, GO_EDIT_URL))) {
			try {
				int res = taskScheduleService.updateTaskSchedule(o);
				if (res == 1)ar.setSucceedMsg(Const.UPDATE_SUCCEED);
				else         ar.setFailMsg(Const.UPDATE_FAIL);
			} catch (Exception e) {
				logger.error(e.toString(), e);
				ar.setFailMsg(Const.UPDATE_FAIL);
			}
		}
		return ar;
	}

	@RequestMapping(value = "del", method = RequestMethod.POST)
	@ResponseBody
	public AjaxRes del() {
		AjaxRes ar = getAjaxRes();
		if (ar.setNoAuth(doSecurityIntercept(ResourceTypes.BUTTON))) {
			Dto paramDto = getParamAsDto();
			String taskScheduleId = paramDto.getAsString("task_id");
			try {
				TaskSchedule o = new TaskSchedule();
				o.setTaskScheduleId(taskScheduleId);
				int res = taskScheduleService.deleteTaskSchedule(o);
				if (res == 1)
					ar.setSucceedMsg(Const.DEL_SUCCEED);
				else
					ar.setFailMsg(Const.DEL_FAIL);
			} catch (Exception e) {
				logger.error(e.toString(), e);
				ar.setFailMsg(Const.DEL_FAIL);
			}
		}
		return ar;
	}
	
	@RequestMapping(value = "runOnce", method = RequestMethod.POST)
	@ResponseBody
	public AjaxRes runOnce() {
		AjaxRes ar = getAjaxRes();
		if (ar.setNoAuth(doSecurityIntercept(ResourceTypes.BUTTON))) {
			Dto paramDto = getParamAsDto();
			String taskScheduleId = paramDto.getAsString("task_id");
			try {
				TaskSchedule o = new TaskSchedule();
				o.setTaskScheduleId(taskScheduleId);
				int res = taskScheduleService.runOnce(o);
				if (res == 1) {
					ar.setSucceedMsg("运行成功");
				} else if (res == 2) {
					ar.setFailMsg("请先停止正在运行的任务实例");
				} else {
					ar.setFailMsg("运行失败");
				}
			} catch (Exception e) {
				logger.error(e.toString(), e);
				ar.setFailMsg("运行失败");
			}
		}
		return ar;
	}
		
	@RequestMapping(value = "resumeJob", method = RequestMethod.POST)
	@ResponseBody
	public AjaxRes resumeJob() {
		AjaxRes ar = getAjaxRes();
		if (ar.setNoAuth(doSecurityIntercept(ResourceTypes.BUTTON))) {
			Dto paramDto = getParamAsDto();
			String taskScheduleId = paramDto.getAsString("task_id");
			try {
				TaskSchedule o = new TaskSchedule();
				o.setTaskScheduleId(taskScheduleId);
				int res = taskScheduleService.resumeJob(o);
				if (res == 1)
					ar.setSucceedMsg("启动成功");
				else if (res == 2)
					ar.setFailMsg("已经有一个实例正在运行");
				else
					ar.setFailMsg("启动失败");
			} catch (Exception e) {
				logger.error(e.toString(), e);
				ar.setFailMsg("启动失败");
			}
		}
		return ar;
	}
		
	@RequestMapping(value = "pauseJob", method = RequestMethod.POST)
	@ResponseBody
	public AjaxRes pauseJob() {
		AjaxRes ar = getAjaxRes();
		if (ar.setNoAuth(doSecurityIntercept(ResourceTypes.BUTTON))) {
			Dto paramDto = getParamAsDto();
			String taskScheduleId = paramDto.getAsString("task_id");
			try {
				TaskSchedule o = new TaskSchedule();
				o.setTaskScheduleId(taskScheduleId);
				int res = taskScheduleService.pauseJob(o);
				if (res == 1)
					ar.setSucceedMsg("暂停成功");
				else if (res == 2)
					ar.setFailMsg("项目没启动，不用暂停");
				else
					ar.setFailMsg("暂停失败");
			} catch (Exception e) {
				logger.error(e.toString(), e);
				ar.setFailMsg("暂停失败");
			}
		}
		return ar;
	}
}
