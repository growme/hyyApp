package com.ccnet.admin.cps.controller;

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
import com.ccnet.cps.entity.SbContentViewLog;
import com.ccnet.cps.service.SbContentViewLogService;

/**
 * 文章访问日志
 * @author jackie wang
 *
 */
@Controller
@RequestMapping("/backstage/view/")
public class SbContentViewLogController extends BaseController<SbContentViewLog> {

	    // 静态地址
		public static String INDEX_URL = "/backstage/view/index";
		public static String GO_ADD_URL = "/backstage/view/goadd";
		public static String GO_EDIT_URL = "/backstage/view/goedit";
		@Autowired
		private SbContentViewLogService sbViewLogService;

		/**
		 * 查询日志
		 * 
		 * @return
		 */
		@RequestMapping("index")
		public ModelAndView viewIndex() {
			Dto paramDto = getParamAsDto();
			ModelAndView mav = new ModelAndView();
			Page<SbContentViewLog> page = newPage(paramDto);
			if (isAuthedReq(ResourceTypes.MENU)) {
				SbContentViewLog viewLog = new SbContentViewLog();
				page = sbViewLogService.findSbContentViewLogByPage(viewLog, page, paramDto);// 分页查询
				mav.addObject("pm", page);
				mav.addObject("end_date", paramDto.getAsString("end_date"));
				mav.addObject("start_date", paramDto.getAsString("start_date"));
				mav.addObject("queryParam", paramDto.getAsString("queryParam"));
				mav.addObject("permitFBtn", getPermitBtn(ResourceTypes.FUNC));
				mav.addObject("permitTBtn", getPermitBtn(ResourceTypes.BUTTON));
				mav.addObject(Const.CT_MENU_NAV, "文章日志");
				mav.setViewName("admincps/jsp/view/view_list");
			} else {
				mav.setViewName(Const.NO_AUTHORIZED_URL);
			}
			
			return mav;
		}
		
		
		/**
		 * 删除日志
		 * 
		 * @return
		 */
		@RequestMapping(value = "trash", method = RequestMethod.POST)
		@ResponseBody
		public AjaxRes trashViewLog() {
			AjaxRes ar = getAjaxRes();

			if (isAuthedReq(ResourceTypes.FUNC) || isAuthedReq(ResourceTypes.BUTTON)) {
				Dto paramDto = getParamAsDto();
				String viewId = paramDto.getAsString("viewId");
				if(CPSUtil.isNotEmpty(viewId)){
					if (sbViewLogService.trashSbContentViewLogList(viewId)) {
						ar.setRes(Const.SUCCEED);
						ar.setSucceedMsg(Const.CLEAR_SUCCEED);
					} else {
						ar.setRes(Const.FAIL);
						ar.setSucceedMsg(Const.CLEAR_FAIL);
					}
				}else{
					ar.setFailMsg(Const.NO_PARAM_ERROR);
				}
			}
			return ar;
		}
		

		/**
		 * 清空日志
		 * @return
		 */
		@RequestMapping(value = "clear", method = RequestMethod.POST)
		@ResponseBody
		public AjaxRes clearViewLog() {
			AjaxRes ar = getAjaxRes();
			Dto paramDto = getParamAsDto();
			if (isAuthedReq(ResourceTypes.FUNC) || isAuthedReq(ResourceTypes.BUTTON)) {
				if (sbViewLogService.truncateSbContentViewLog()) {
					ar.setRes(Const.SUCCEED);
					ar.setSucceedMsg(Const.CLEAR_SUCCEED);
				} else {
					ar.setRes(Const.FAIL);
					ar.setSucceedMsg(Const.CLEAR_FAIL);
				}
			}
			return ar;
		}
		
}
