package com.ccnet.admin.controller.task;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ccnet.core.common.ajax.AjaxRes;
import com.ccnet.core.common.utils.backup.DatabaseBackupHelper;
import com.ccnet.core.common.utils.base.Const;
import com.ccnet.core.common.utils.base.ResourceTypes;
import com.ccnet.core.controller.BaseController;
import com.ccnet.core.entity.TaskLog;

@Controller
@RequestMapping("/backstage/dbback/")
public class DatabaseBackupController extends BaseController<TaskLog>{
	/**
	 * 备份日志首页
	 */
	@RequestMapping("index")
	public ModelAndView index() throws UnsupportedEncodingException {
		if (doSecurityIntercept(ResourceTypes.MENU)) {
			getParamAsDto();
			ModelAndView model = new ModelAndView();
			model.addObject("list", DatabaseBackupHelper.findAllBackFiles());
			model.addObject("permitFBtn", getPermitBtn(ResourceTypes.FUNC));	
			model.addObject("permitTBtn", getPermitBtn(ResourceTypes.BUTTON));
			model.addObject(Const.CT_MENU_NAV,"数据备份");
			model.setViewName("admin/jsp/task/dbback_list");
			return model;
		}
		return new ModelAndView(Const.NO_AUTHORIZED_URL);
	}
		
	@RequestMapping(value = "del", method = RequestMethod.POST)
	@ResponseBody
	public AjaxRes delBatch(String chks) {
		AjaxRes ar = getAjaxRes();
		if (ar.setNoAuth(doSecurityIntercept(ResourceTypes.BUTTON))) {
			try {
				String[] chk = chks.split(",");
				List<String> list = new ArrayList<String>();
				for (String s : chk) {
					list.add(s);
				}
				DatabaseBackupHelper.delete(list);
				ar.setSucceedMsg(Const.DEL_SUCCEED);
			} catch (Exception e) {
				logger.error(e.toString(), e);
				ar.setFailMsg(Const.DEL_FAIL);
			}
		}
		return ar;
	}
	
	@RequestMapping(value = "restore", method = RequestMethod.POST)
	@ResponseBody
	public AjaxRes restore(String fname) {
		AjaxRes ar = getAjaxRes();
		if (ar.setNoAuth(doSecurityIntercept(ResourceTypes.BUTTON))) {
			try {
				DatabaseBackupHelper.restore(fname);
				ar.setSucceedMsg(Const.RESTORE_SUCCESS);
			} catch (Exception e) {
				logger.error(e.toString(), e);
				ar.setFailMsg(Const.RESTORE_FAIL);
			}
		}
		return ar;
	}

}
