package com.ccnet.core.common.utils.tree;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.entity.Resources;
import com.ccnet.core.entity.UserInfo;

public class MenuTreeUtil {
	
	public static String buildTreeJson(List<Resources> list, String currentResourceCode, UserInfo userInfo) {
		JSONArray jsonArray = buildTreeJsonArray(list, currentResourceCode, userInfo);
		return jsonArray.toJSONString();
	}
	 
	private static JSONArray buildTreeJsonArray(List<Resources> list, String currentResourceCode, UserInfo userInfo) {
		JSONArray jsonArray = new JSONArray();
		List<Resources> childs = null;
		for (Resources node : list) {
			childs = node.getMenus();
			JSONObject jsonObject = new JSONObject();
			if (StringUtils.startsWith(currentResourceCode, node.getResourceCode())) {
				jsonObject.put("active", CollectionUtils.isNotEmpty(childs) ? "open" : "");
			}
			String url = "#";
			if (StringUtils.isNotBlank(node.getResourceUrl())) {
				if (!StringUtils.startsWith(node.getResourceUrl(), "/")) {
					url = CPSUtil.getCXTPath() + "/" + node.getResourceUrl();
				} else {
					url = CPSUtil.getCXTPath() + node.getResourceUrl();
				}
				if (url.contains("?")) {
					url += "&mod=" + node.getResourceCode();
				} else {
					url += "?mod=" + node.getResourceCode();
				}
				//处理框架模式下首页链接问题
				if(StringUtils.contains(url, "backstage/index") || "0102".equals(node.getResourceCode())){
					url = StringUtils.replace(url,"backstage/index","backstage/home/data");
					jsonObject.put("index", "0");
				}else{
					jsonObject.put("index", node.getResourceCode());
				}
			}
			
			jsonObject.put("url", url);
			String icon = "";
			if (StringUtils.isNotBlank(node.getIcon())) {
				if (node.getIsleaf() == 1) {
					icon = node.getIcon();
				}
			}
			if (node.getIsleaf() == 1) {
				jsonObject.put("isHeader", "1");
			}else{
				jsonObject.put("isHeader", "0");
			}
			
			jsonObject.put("icon", icon);
			jsonObject.put("name", node.getResourceName());
			jsonObject.put("id", node.getResourceCode());
			jsonObject.put("order", node.getOrderNumber());
			jsonObject.put("parentId", node.getParentCode());
			
			if (CollectionUtils.isNotEmpty(childs)) {
				jsonObject.put("childs", buildTreeJson(childs, currentResourceCode, userInfo));
			}else{
				jsonObject.put("childs", "");
			}
			jsonArray.add(jsonObject);
		}
		return jsonArray;
	}
	
	/**
	 * 建立树菜单
	 * @param menusDB 菜单集合（不是树）
	 * @return 有样式的树的html字符串
	 */
    public static String buildTreeHtml(List<Resources> list, String currentResourceCode,UserInfo userInfo){  
    	StringBuffer html = new StringBuffer();
    	buildTreeHtml(list, html, currentResourceCode, userInfo);
    	return html.toString();
    }  
    
    private static void buildTreeHtml(List<Resources> list, StringBuffer html, String currentResourceCode,UserInfo userInfo) {
    	List<Resources> childs = null;
    	String menuUrlString = "#";
		for (Resources node : list) {
			childs = node.getMenus();
			String classStype = "";
			if (StringUtils.startsWith(currentResourceCode, node.getResourceCode())) {
				classStype = CollectionUtils.isNotEmpty(childs) ? "active open" : "active";
			}
			html.append("\r\n").append("<li id='menu"+node.getResourceCode()+"'"); 
			if(CPSUtil.isNotEmpty(classStype)){
				html.append(" class='").append(classStype).append("'");
			}
			html.append(">");
			if(StringUtils.isNotBlank(node.getResourceUrl())){
				if(!StringUtils.startsWith(node.getResourceUrl(), "/")){
					menuUrlString = CPSUtil.getCXTPath() + "/" + node.getResourceUrl();
				}else{
					menuUrlString = CPSUtil.getCXTPath() + node.getResourceUrl();
				}
				if(menuUrlString.contains("?")){
				    menuUrlString+="&mod="+node.getResourceCode();
				}else{
					menuUrlString+="?mod="+node.getResourceCode();
				}
			}
			html.append("\r\n").append("<a href='"+menuUrlString+"'");
			
			html.append(CollectionUtils.isEmpty(childs) ? "" : " class='menu-dropdown'").append(">");
			if (StringUtils.isNotBlank(node.getIcon())) {
				if(node.getIsleaf()==1){
				   html.append("\r\n").append("<i class='menu-icon ").append(node.getIcon()).append("'></i>");
				}
			}
			html.append("\r\n").append("<span class='menu-text'>" + node.getResourceName()+ "</span>");
			
			if (CollectionUtils.isNotEmpty(childs)) {
			    html.append("\r\n<i class='menu-expand'></i>");
			}
			html.append("\r\n").append("</a>");
			if (CollectionUtils.isNotEmpty(childs)) {
				html.append("<ul class='submenu'>");
				buildTreeHtml(childs, html, currentResourceCode,userInfo);
				html.append("</ul>");
			}
			html.append("</li>");
        }  
    }
    
    /**
     * 将传入参数构建成一个有上下级关系的资源树
     * @param list
     * @return
     */
    public static List<Resources> buildResourcesTree(List<Resources> list) {
    	Resources root = new Resources();
    	root.setResourceCode("01");
    	buildResourcesTree(list, root);
    	return root.getNodes();
    }
    
    /**
     * 从指定的节点开始递归
     * @param list
     * @param root
     */
    private static void buildResourcesTree(List<Resources> list, Resources root) {
    	if (CollectionUtils.isNotEmpty(root.getNodes())) {
    		return;
    	}
    	for (Resources resources : list) {
			if (StringUtils.equals(root.getResourceCode(), resources.getParentCode())) {
				root.getNodes().add(resources);
				//递归
				buildResourcesTree(list, resources);
			}
		}
    }
    
    public static void main(String[] args) {
		List<Resources> list = new ArrayList<Resources>();
		build(list, "0", 1);
		List<Resources> newList = buildResourcesTree(list);
		System.out.println(newList.size());
		print(newList, 1);
	}
    
    private static void build(List<Resources> list, String parent, int level) {
    	if (level > 3) {
    		return;
    	}
    	Resources resources = null;
		for (int i = 1; i < 4; i++) {
			resources = new Resources();
			resources.setResourceCode(parent + i);
			resources.setResourceName("级别" + parent + i);
			resources.setParentCode(parent);
			list.add(resources);
			build(list, parent + i, level+1);
		}
    }
    
    public static void print(List<Resources> list, int i) {
    	for (Resources resources : list) {
			System.out.println(StringUtils.repeat(" + ", i) + resources.toString());
			print(resources.getNodes(), i + 1);
		}
    }
    
}
