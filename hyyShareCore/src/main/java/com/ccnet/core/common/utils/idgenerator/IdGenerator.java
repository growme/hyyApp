package com.ccnet.core.common.utils.idgenerator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.SpringWebContextUtil;
import com.ccnet.core.common.utils.base.Const;
import com.ccnet.core.service.ResourcesService;

/**
 * ID生成器
 * @author jackie wang
 * @since 2016-10-19
 */
public class IdGenerator {
	private static Log log = LogFactory.getLog(IdGenerator.class);
	//资源业务类
	private static ResourcesService resourcesService = (ResourcesService) SpringWebContextUtil.getApplicationContext().getBean("resourcesService");  
	
	public IdGenerator(){}
	
	 /**
     * 菜单编号ID生成器(自定义)
     * @param parentID 菜单编号的参考编号 10001011
     * @return
     */
	public static synchronized String getMenuIdGenerator(String parentID){
		String maxSubMenuId = resourcesService.getMaxSubMenuCode(parentID);
		String menuId = null;
		if(CPSUtil.isEmpty(maxSubMenuId)){
			menuId = "01";
		}else{
			int length = maxSubMenuId.length();
			String temp = maxSubMenuId.substring(length-2, length);
			int intMenuId = Integer.valueOf(temp).intValue() + 1;
			if(intMenuId > 0 && intMenuId < 10){
				menuId = "0" + String.valueOf(intMenuId);
			}else if(10 <= intMenuId && intMenuId <= 99){
				menuId = String.valueOf(intMenuId);
			}else if(intMenuId > 99){
				log.error(Const.Exception_Head + "生成菜单编号越界了.同级兄弟节点编号为[01-99]\n请和您的系统管理员联系!");
			}else{
				log.error(Const.Exception_Head + "生成菜单编号发生未知错误,请和开发人员联系!");
			}
		}
		CPSUtil.xprint("id==="+parentID + menuId);
		return parentID + menuId;
	}
	
}
