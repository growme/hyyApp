package com.ccnet.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ccnet.api.entity.Headers;
import com.ccnet.api.entity.ResultDTO;
import com.ccnet.api.service.ApiContentService;
import com.ccnet.api.entity.ResultCode.BasicCode;
import com.ccnet.core.common.utils.wxpay.GetPropertiesValue;
import com.ccnet.core.controller.BaseController;
import com.ccnet.core.dao.base.Page;
import com.ccnet.cps.entity.SbContentInfo;
import com.ccnet.cps.entity.SbContentPic;
import com.ccnet.jpz.entity.JpHotSearch;
import com.ccnet.jpz.service.JpHotSearchService;

@Controller
@RequestMapping("/api/search")
public class JpSearchController extends BaseController<T> {

	@Autowired
	private ApiContentService apiContentService;
	@Autowired
	private JpHotSearchService jpHotSearchService;
	private final String DOMAIN = GetPropertiesValue.getValue("ConfigURL.properties", "ziyuan.server");


	/**
	 * 文章搜索
	 */
	@RequestMapping(value = "contentSearch")
	@ResponseBody
	public ResultDTO<?> contentSearch(String name, Integer contentType, Headers header, Page<SbContentInfo> page) {
		try {
			SbContentInfo jpUserCollect = new SbContentInfo();
			jpUserCollect.setContentType(contentType);
			jpUserCollect.setContentTitle(name);
			List<SbContentInfo> list = apiContentService.findSbContentInfoByPage(jpUserCollect, page);
			if (list != null)
				for (int i = 0; i < list.size(); i++) {
					List<SbContentPic> picList = new ArrayList<SbContentPic>();
					if (list.get(i).getContentPics() != null) {
						String[] strArr = list.get(i).getContentPics().split(",");
						for (int z = 0; z < strArr.length; z++) {
							SbContentPic pic = new SbContentPic();
							if (strArr[z].indexOf("http") < 0) {
								pic.setContentPic(DOMAIN + strArr[z]);
							} else {
								pic.setContentPic(strArr[z]);
							}
							picList.add(pic);
						}
					}
					list.get(i).setPicList(picList);
					if (list.get(i).getContentText() != null) {
						list.get(i).setContentText("");
					}
				}
			JpHotSearch jpHotSearch =new JpHotSearch();
			jpHotSearch.setTerm(name);
			jpHotSearchService.updateOrInsertHotSearch(jpHotSearch );
			return ResultDTO.OK(list);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultDTO.ERROR(BasicCode.逻辑错误);
		}
	}
	
	/**
	 * 搜索热词
	 */
	@RequestMapping(value = "hotSearch")
	@ResponseBody
	public ResultDTO<?> hotSearch(Headers header, Page<T> page) {
		try {
			List<JpHotSearch> list = jpHotSearchService.getHotSearchList();
			return ResultDTO.OK(list);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultDTO.ERROR(BasicCode.逻辑错误);
		}
	}
}
