package com.ccnet.web.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.base.Const;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.controller.BaseController;
import com.ccnet.core.dao.base.Page;
import com.ccnet.cps.entity.MemberInfo;
import com.ccnet.cps.entity.SbAdvertInfo;
import com.ccnet.cps.entity.SbColumnInfo;
import com.ccnet.cps.entity.SbContentInfo;
import com.ccnet.cps.service.SbAdvertiseInfoService;
import com.ccnet.cps.service.SbColumnInfoService;
import com.ccnet.cps.service.SbContentInfoService;

/**
 * 文章列表
 * @author 1234
 *
 */
@Controller
@RequestMapping("/content/")
public class ContentInfoController  extends BaseController<Object>  {
	
	
	@Autowired
	private SbContentInfoService contentInfoService;
	
	@Autowired
	private SbAdvertiseInfoService advertiseInfoService;
	
	@Autowired
	private SbColumnInfoService   columnInfoService;
	
	/**
	 * 文章信息列表
	 */
	@RequestMapping("index")
	public String contentIndex(Model model,HttpServletRequest req,HttpServletResponse res) 
	{
		Dto paramDto = getParamAsDto();
		model.addAttribute("user_theme", "blue");
		model.addAttribute(Const.MENU_SELECTED_INDEX, "money");
		String c=paramDto.getAsString("c");
		String m=paramDto.getAsString("m");
		SbContentInfo contentInfo=new SbContentInfo();
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		MemberInfo memberInfo=null;
		if (null != session) {
			Object obj = session.getAttribute(Const.MSESSION_USER);
			memberInfo=(MemberInfo)obj;
		}
		if(CPSUtil.isNotEmpty(c)&&CPSUtil.isNotEmpty(m)){//详情页	
			long cutime = System.currentTimeMillis() / 10;
			CPSUtil.xprint("cutime==" + cutime);
			CPSUtil.xprint("文章ID==" + c);
			CPSUtil.xprint("用户ID==" + m);
			// 获取正文信息
			contentInfo.setContentId(Integer.parseInt(c));
			SbContentInfo content = contentInfoService.find(contentInfo);
			model.addAttribute("contentInfo", content);
			// addReadRequest(cid, uid, contentInfo, userInfo, req);
			// 获取相关内容
			SbContentInfo sbcontent = new SbContentInfo();
			sbcontent.setColumnId(contentInfo.getColumnId());
			// model.addAttribute("similar",contentInfoService.findSomeList(sbcontent,2));
			// 获取广告信息
			List<SbAdvertInfo> adList = advertiseInfoService.getRandomAdvert(2);
			model.addAttribute("advInfo", adList.get(0));
			model.addAttribute("tadvInfo", adList.get(1));
			model.addAttribute("uid", m);
			model.addAttribute("cid", c);
			model.addAttribute("SHOW_TOP_ADV", 1);
			model.addAttribute("SHOW_BOTTOM_ADV", 1);
			model.addAttribute("SHOW_RET_CONTENT", 1);
			// 处理跳转详情
			if ("1".equals(content == null ? "" : content.getContentType() + "")) {
				return "/user/jsp/content/vcontent_info";
			} else {
				return "/user/jsp/content/content_info";
			}
		}
		String columnId=paramDto.getAsString("cid");
		List<SbColumnInfo> scoList=columnInfoService.findList(new SbColumnInfo());
		if(scoList.size()>0){
		Page<SbContentInfo> page = newPage(paramDto);
		
		if(CPSUtil.isNotEmpty(columnId)){
			contentInfo.setColumnId(Integer.parseInt(columnId));	
		}else{
			contentInfo.setColumnId(scoList.get(0).getColumnId());	
		}
		
		Page<SbContentInfo> pager=contentInfoService.findByPage(contentInfo, page);
		model.addAttribute("pm", pager);
		model.addAttribute("colist", scoList);
		model.addAttribute("scolist", scoList);
		model.addAttribute("cid", contentInfo.getColumnId());
		model.addAttribute("uid", memberInfo.getMemberId());
		
		}
	   return "/user/jsp/content/content_index";

	}

}
