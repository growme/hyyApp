package com.ccnet.admin.controller.task;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ccnet.core.common.ajax.AjaxRes;
import com.ccnet.core.common.utils.base.Const;
import com.ccnet.core.common.utils.base.ResourceTypes;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.controller.BaseController;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.entity.TaskLog;
import com.ccnet.core.service.TaskLogService;

@Controller
@RequestMapping("/backstage/tasklog/")
public class TaskLogController extends BaseController<TaskLog>{

	 @Autowired
	 private TaskLogService taskLogService;

	/**
	 * 日志首页
	 */
	@RequestMapping("index")
	public ModelAndView index() throws UnsupportedEncodingException {
		if (doSecurityIntercept(ResourceTypes.MENU)) {
			//获取集合数据
			Dto paramDto = getParamAsDto();
			Page<TaskLog> page = newPage(paramDto);
			page = taskLogService.findTaskLogByPage(new TaskLog(), page, paramDto);
			ModelAndView model = new ModelAndView();
			model.addObject("pm", page);
			model.addObject("end_date",paramDto.getAsString("end_date"));
			model.addObject("start_date",paramDto.getAsString("start_date"));
			model.addObject("queryParam",paramDto.getAsString("queryParam"));
			model.addObject("permitFBtn", getPermitBtn(ResourceTypes.FUNC));	
			model.addObject("permitTBtn", getPermitBtn(ResourceTypes.BUTTON));
			model.addObject(Const.CT_MENU_NAV,"定时任务日志");
			model.setViewName("admin/jsp/task/tasklog_list");
			return model;
		}
		return new ModelAndView(Const.NO_AUTHORIZED_URL);
	}
		
	@RequestMapping(value = "del", method = RequestMethod.POST)
	@ResponseBody
	public AjaxRes delBatch(String chks) {
		AjaxRes ar = getAjaxRes();
		if (ar.setNoAuth(doSecurityIntercept(ResourceTypes.FUNC))) {
			try {
				String[] chk = chks.split(",");
				List<TaskLog> list = new ArrayList<TaskLog>();
				for (String s : chk) {
					TaskLog sd = new TaskLog();
					sd.setLogId(s);
					list.add(sd);
				}
				taskLogService.deleteBatch(list);
				ar.setSucceedMsg(Const.DEL_SUCCEED);
			} catch (Exception e) {
				logger.error(e.toString(), e);
				ar.setFailMsg(Const.DEL_FAIL);
			}
		}
		return ar;
	}
	
	/**
	 * 清空日志
	 * @return
	 */
	@RequestMapping(value="clear", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes clearLog(){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(isAuthedReq(ResourceTypes.FUNC))){
			try {
				if(taskLogService.truncateTarskLog()){
					ar.setSucceedMsg(Const.CLEAR_SUCCEED);
				}else{
					ar.setFailMsg(Const.CLEAR_FAIL);
				}
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.CLEAR_FAIL);
			}
		}
		return ar;
	}

}
