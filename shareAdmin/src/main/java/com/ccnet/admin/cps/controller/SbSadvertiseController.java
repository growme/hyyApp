package com.ccnet.admin.cps.controller;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.ccnet.admin.controller.AdminBaseController;
import com.ccnet.core.common.AdType;
import com.ccnet.core.common.PositionType;
import com.ccnet.core.common.StateType;
import com.ccnet.core.common.ajax.AjaxRes;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.base.Const;
import com.ccnet.core.common.utils.base.ResourceTypes;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.Page;
import com.ccnet.cps.entity.SbAdvertPosition;
import com.ccnet.cps.entity.SbSadvertInfo;
import com.ccnet.cps.service.SbSadvertiseInfoService;

/**
 *脚本广告
 * @author 1234
 *
 */

@Controller
@RequestMapping("/backstage/sadvertise/")
public class SbSadvertiseController extends AdminBaseController<SbSadvertInfo> {

	    //静态地址
		public static String INDEX_URL = "/backstage/sadvertise/index";
		public static String GO_ADD_URL = "/backstage/sadvertise/goadd";
		public static String GO_EDIT_URL = "/backstage/sadvertise/goedit";
		@Autowired
		private SbSadvertiseInfoService sbSadvertiseInfoService;
		/**
		 * 广告信息列表
		 */
		@RequestMapping("index")
		public ModelAndView advertIndex() 
		{
			Dto paramDto = getParamAsDto();
			ModelAndView mav = new ModelAndView();
			Page<SbSadvertInfo> page = newPage(paramDto);
			if (isAuthedReq(ResourceTypes.MENU))
			{
				SbSadvertInfo sbSadvertInfo = new SbSadvertInfo();
				page = sbSadvertiseInfoService.findSbSadvertiseInfoByPage(sbSadvertInfo, page, paramDto);
				mav.addObject("pm", page);
				mav.addObject("stateMap", StateType.getStateMap());
				mav.addObject("end_date",paramDto.getAsString("end_date"));
				mav.addObject("start_date",paramDto.getAsString("start_date"));
				mav.addObject("queryParam", paramDto.getAsString("queryParam"));
				mav.addObject("permitFBtn", getPermitBtn(ResourceTypes.FUNC));
				SbAdvertPosition sbAdvertPosition=new SbAdvertPosition();
				//广告位置
				mav.addObject("adPosition",PositionType.getStateMap());
				mav.addObject("permitTBtn", getPermitBtn(ResourceTypes.BUTTON));
				mav.addObject(Const.CT_MENU_NAV, "脚本广告");
				mav.setViewName("admincps/jsp/sadvertise/good_advertise_list");
			} 
			else 
			{
				mav.setViewName(Const.NO_AUTHORIZED_URL);
			}
			return mav;
		}
		
		/**
		 * 添加广告信息
		 */
		@RequestMapping("goadd")
		public ModelAndView advertgoadd()
		{
			ModelAndView mav = new ModelAndView();
			if(isAuthedReq(ResourceTypes.FUNC))
			{
				mav.addObject("adTypeMap",AdType.getStateMap());
				mav.addObject("stateMap", StateType.getStateMap());
				SbAdvertPosition sbAdvertPosition=new SbAdvertPosition();
				//广告位置
				mav.addObject("adPosition",PositionType.getStateMap());
				mav.addObject(Const.CT_MENU_NAV, "添加广告");
				mav.setViewName("admincps/jsp/sadvertise/good_advertise_info");
			}
			else
			{
				mav.setViewName(Const.NO_AUTHORIZED_URL);
			}
			return mav;
		}
		
		/**
		 * 修改广告
		 * @return
		 */
		@RequestMapping(value = "goedit", method = RequestMethod.GET)
		public ModelAndView advertgoEdit() 
		{
			//sbAdvertInfo = sbAdvertiseInfoService.getSbAdvertInfoByID(adId);

			ModelAndView mav = new ModelAndView();
			SbSadvertInfo sbSadvertInfo = new SbSadvertInfo();
			if(isAuthedReq(ResourceTypes.FUNC) || isAuthedReq(ResourceTypes.BUTTON)){		
				Dto paramDto = getParamAsDto();
				Integer optype = paramDto.getAsInteger("optype");
				Integer adId = paramDto.getAsInteger("advertId");
				Integer chakanid=paramDto.getAsInteger("adId");
				SbAdvertPosition sbAdvertPosition=new SbAdvertPosition();
				//广告位置
				mav.addObject("adPosition",PositionType.getStateMap());
				if(CPSUtil.isNotEmpty(chakanid)){//查看脚本广告
					mav.addObject("adId", chakanid);
					sbSadvertInfo = sbSadvertiseInfoService.getSbAdvertInfoByID(chakanid);
					mav.addObject("advert", sbSadvertInfo);
					mav.addObject(Const.CT_MENU_NAV, "查看广告");
					mav.setViewName("admincps/jsp/sadvertise/good_advertise_info2");
					return mav;
				}
			
				if(CPSUtil.isNotEmpty(adId)){
					sbSadvertInfo = sbSadvertiseInfoService.getSbAdvertInfoByID(adId);
				}
				//mav.addObject("adTagMap", "AdTagType.getStateMap()");
				mav.addObject("adTypeMap",AdType.getStateMap());
				mav.addObject("optype", optype);
				mav.addObject("advert", sbSadvertInfo);
				mav.addObject("stateMap", StateType.getStateMap());
				mav.addObject(Const.CT_MENU_NAV, "编辑广告");
				
				mav.setViewName("admincps/jsp/sadvertise/good_advertise_info");
			}else{
				mav.setViewName(Const.NO_AUTHORIZED_URL);
			}
			return mav;
		}
		


		/**
		 * 保存广告
		 * @return
		 */
		@RequestMapping(value="save", method=RequestMethod.POST)
		@ResponseBody
		public AjaxRes advertsave(SbSadvertInfo sbSadvertInfo)
		{
			AjaxRes ar=getAjaxRes();
			if(ar.setNoAuth(isAuthedReq(ResourceTypes.FUNC,GO_ADD_URL)))
			{
				try {
					boolean isOk=false;
					if(CPSUtil.isNotEmpty(sbSadvertInfo.getAdId())){
						isOk=sbSadvertiseInfoService.saveSbAdvertInfo(sbSadvertInfo);
					}else{
						sbSadvertInfo.setCreateTime(new Date());
						isOk=sbSadvertiseInfoService.saveSbAdvertInfo(sbSadvertInfo);
					}
					if(isOk)
					{
						ar.setRes(Const.SUCCEED);
						ar.setSucceedMsg(Const.SAVE_SUCCEED);
					}
					else
					{
						ar.setRes(Const.FAIL);
						ar.setFailMsg(Const.SAVE_FAIL);
					}
				} 
				catch (Exception e) 
				{
					logger.error(e.toString(),e);
					ar.setFailMsg(Const.SAVE_FAIL);
				}
			}
			return ar;
		}
		
		/**
		 * 删除广告
		 * @return
		 */
		@RequestMapping(value = "trash", method = RequestMethod.POST)
		@ResponseBody
		public AjaxRes advertgoDel() 
		{
			AjaxRes ar=getAjaxRes();
			if(ar.setNoAuth(isAuthedReq(ResourceTypes.FUNC,GO_ADD_URL)))
			{
				if(isAuthedReq(ResourceTypes.FUNC) || isAuthedReq(ResourceTypes.BUTTON))
				{	
					Dto paramDto = getParamAsDto();
					Integer adId = paramDto.getAsInteger("advertId");
					SbSadvertInfo sbAdvertInfo = new SbSadvertInfo();
					sbAdvertInfo.setAdId(adId);
					if(CPSUtil.isNotEmpty(adId))
					{
						if(sbSadvertiseInfoService.trashSbAdvertInfo(sbAdvertInfo))
						{
							ar.setRes(Const.SUCCEED);
							ar.setSucceedMsg(Const.DEL_SUCCEED);
						}
						else
						{
							ar.setRes(Const.FAIL);
							ar.setFailMsg(Const.DEL_FAIL);
						}
					} 
				}
			}
			return ar;
		}
	
}
