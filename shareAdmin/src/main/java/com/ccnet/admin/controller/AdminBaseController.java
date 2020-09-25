package com.ccnet.admin.controller;

import java.util.ArrayList;
import java.util.List;

import com.ccnet.admin.LicenseHelper;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.base.ResourceTypes;
import com.ccnet.core.controller.BaseController;
import com.ccnet.core.entity.Resources;

public class AdminBaseController<T> extends BaseController<T> {

	@Override
	protected boolean doSecurityIntercept(ResourceTypes type) {
		if (LicenseHelper.license()) {
			return super.doSecurityIntercept(type);
		}
		return false;
	}

	@Override
	protected boolean isAuthedReq(ResourceTypes type) {
		if (LicenseHelper.license()) {
			return super.isAuthedReq(type);
		}
		return false;
	}

	@Override
	protected boolean isAuthedReq(ResourceTypes type, String url) {
		if (LicenseHelper.license()) {
			return super.isAuthedReq(type, url);
		}
		return false;
	}

	@Override
	protected boolean doSecurityIntercept(ResourceTypes type, String url) {
		if (LicenseHelper.license()) {
			return super.doSecurityIntercept(type, url);
		}
		return false;
	}

	@Override
	protected List<Resources> loadCurrentUserResources(boolean useCache) {
		if (LicenseHelper.license()) {
			return super.loadCurrentUserResources(useCache);
		}
		return new ArrayList<Resources>();
	}
	
	
	//判断用户设置的版本
	protected boolean isV5Admin() {
		String admin_version = CPSUtil.getParamValue("ADMIN_VERSION");
		if (CPSUtil.isNotEmpty(admin_version) && "V5".equals(admin_version)) {
		   return true;
		}else{
		   return false;
		}
	}

}
