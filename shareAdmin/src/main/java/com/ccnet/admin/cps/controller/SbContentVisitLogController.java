package com.ccnet.admin.cps.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ccnet.core.common.AccountState;
import com.ccnet.core.common.ShareType;
import com.ccnet.core.common.ajax.AjaxRes;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.base.Const;
import com.ccnet.core.common.utils.base.ResourceTypes;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.controller.BaseController;
import com.ccnet.core.dao.base.Page;
import com.ccnet.cps.entity.SbContentVisitLog;
import com.ccnet.cps.service.SbContentVisitLogService;

/**
 * 文章指纹记录
 * @author jackie wang
 *
 */
@Controller
@RequestMapping("/backstage/visit/")
public class SbContentVisitLogController extends BaseController<SbContentVisitLog> {

	    // 静态地址
		public static String INDEX_URL = "/backstage/visit/index";
		public static String GO_ADD_URL = "/backstage/visit/goadd";
		public static String GO_EDIT_URL = "/backstage/visit/goedit";
		@Autowired
		private SbContentVisitLogService sbVisitLogService;

		/**
		 * 查询日志列表
		 * 
		 * @return
		 */
		@RequestMapping("index")
		public ModelAndView visitIndex() {
			Dto paramDto = getParamAsDto();
			ModelAndView mav = new ModelAndView();
			// UserInfo userInfo = UserInfoShiroUtil.getCurrentUser();
			Page<SbContentVisitLog> page = newPage(paramDto);
			if (isAuthedReq(ResourceTypes.MENU)) {
				SbContentVisitLog visitLog = new SbContentVisitLog();
				Integer accountState = paramDto.getAsInteger("accountState");
				if(CPSUtil.isNotEmpty(accountState)){
					visitLog.setAccountState(accountState);
				}
				
				page = sbVisitLogService.findSbContentVisitLogByPage(visitLog, page, paramDto);// 分页查询
				mav.addObject("pm", page);
				mav.addObject("end_date", paramDto.getAsString("end_date"));
				mav.addObject("start_date", paramDto.getAsString("start_date"));
				mav.addObject("queryParam", paramDto.getAsString("queryParam"));
				mav.addObject("loginAccount", paramDto.getAsString("loginAccount"));
				mav.addObject("memberId", paramDto.getAsString("memberId"));
				mav.addObject("accountState", accountState);
				mav.addObject("channelList", ShareType.values());
				mav.addObject("stlist", AccountState.values());
				mav.addObject("permitFBtn", getPermitBtn(ResourceTypes.FUNC));
				mav.addObject("permitTBtn", getPermitBtn(ResourceTypes.BUTTON));
				mav.addObject(Const.CT_MENU_NAV, "文章日志");
				mav.setViewName("admincps/jsp/visit/visit_list");
			} else {
				mav.setViewName(Const.NO_AUTHORIZED_URL);
			}
			
			return mav;
		}
		
		
		/**
		 * 清空日志
		 * 
		 * @return
		 */
		@RequestMapping(value = "trash", method = RequestMethod.POST)
		@ResponseBody
		public AjaxRes trashVisitLog() {
			AjaxRes ar = getAjaxRes();

			if (isAuthedReq(ResourceTypes.FUNC) || isAuthedReq(ResourceTypes.BUTTON)) {
				Dto paramDto = getParamAsDto();
				String visitId = paramDto.getAsString("visit_id");
				if(CPSUtil.isNotEmpty(visitId)){
					if (sbVisitLogService.trashSbContentVisitLogList(visitId)) {
						ar.setSucceedMsg(Const.DEL_SUCCEED);
					} else {
						ar.setFailMsg(Const.DEL_FAIL);
					}
				}else{
					ar.setFailMsg(Const.NO_PARAM_ERROR);
				}
			}else{
				ar.setFailMsg(Const.NO_AUTHORIZED_MSG);
			}
			return ar;
		}
		

		/**
		 * 清空日志
		 * @return
		 */
		@RequestMapping(value = "clear", method = RequestMethod.POST)
		@ResponseBody
		public AjaxRes clearVisitLog() {
			AjaxRes ar = getAjaxRes();
			Dto paramDto = getParamAsDto();
			if (isAuthedReq(ResourceTypes.FUNC) || isAuthedReq(ResourceTypes.BUTTON)) {
				if (sbVisitLogService.truncateSbContentVisitLog()) {
					ar.setSucceedMsg(Const.CLEAR_SUCCEED);
				} else {
					ar.setFailMsg(Const.CLEAR_FAIL);
				}
			}else{
				ar.setFailMsg(Const.NO_AUTHORIZED_MSG);
			}
			return ar;
		}
		
}
