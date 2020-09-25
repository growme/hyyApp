package com.ccnet.core.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.ccnet.core.common.utils.base.ResourceTypes;
import com.ccnet.core.dao.base.IgnoreTableField;

/**
 * 系统资源表
 * 
 * @author Jackie Wang
 * 
 */
public class Resources extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private Integer resourceId;
	private String resourceCode;
	private String resourceName;
	private String parentCode;
	private String alisaCode;
	private String levelCode;
	@IgnoreTableField
	private String parentName;
	private Integer resourceType;
	@IgnoreTableField
	private String typeName;
	private String resourceUrl;
	private String btnId;
	private String btnFun;
	private String icon;
	private Integer orderNumber;
	private Integer resourceState;
	@IgnoreTableField
	private String stateName;
	private Integer expanded;
	@IgnoreTableField
	private String expandedName;
	private Integer isleaf;
	@IgnoreTableField
	private String leafName;
	@IgnoreTableField
	private boolean checked;
	private String resourceDesc;
	private Date createTime;
	private Date updateTime;

	@IgnoreTableField
	private List<Resources> nodes = new ArrayList<Resources>();

	public Resources() {

	}

	public Resources(Integer resourceId) {
		this.resourceId = resourceId;
	}

	public Resources(Integer resourceId, String resourceName,
			String parentCode, Integer resourceType, String resourceUrl,
			String btnId, String btnFun, String icon, Integer orderNumber,
			Integer resourceState, String resourceDesc, Integer isleaf,
			Integer expanded, Date createTime, Date updateTime) {
		this.resourceId = resourceId;
		this.resourceName = resourceName;
		this.parentCode = parentCode;
		this.resourceType = resourceType;
		this.resourceUrl = resourceUrl;
		this.btnId = btnId;
		this.btnFun = btnFun;
		this.icon = icon;
		this.orderNumber = orderNumber;
		this.resourceState = resourceState;
		this.resourceDesc = resourceDesc;
		this.expanded = expanded;
		this.isleaf = isleaf;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}
	
	public boolean getChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getAlisaCode() {
		return alisaCode;
	}

	public void setAlisaCode(String alisaCode) {
		this.alisaCode = alisaCode;
	}

	public String getLevelCode() {
		return levelCode;
	}

	public void setLevelCode(String levelCode) {
		this.levelCode = levelCode;
	}

	public String getResourceCode() {
		return resourceCode;
	}

	public void setResourceCode(String resourceCode) {
		this.resourceCode = resourceCode;
	}

	public String getExpandedName() {
		return expandedName;
	}

	public void setExpandedName(String expandedName) {
		this.expandedName = expandedName;
	}

	public String getLeafName() {
		return leafName;
	}

	public void setLeafName(String leafName) {
		this.leafName = leafName;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public Integer getExpanded() {
		return expanded;
	}

	public void setExpanded(Integer expanded) {
		this.expanded = expanded;
	}

	public Integer getIsleaf() {
		return isleaf;
	}

	public void setIsleaf(Integer isleaf) {
		this.isleaf = isleaf;
	}

	public Integer getResourceId() {
		return this.resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	public String getResourceName() {
		return this.resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getParentCode() {
		return this.parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public Integer getResourceType() {
		return this.resourceType;
	}

	public void setResourceType(Integer resourceType) {
		this.resourceType = resourceType;
	}

	public String getResourceUrl() {
		return this.resourceUrl;
	}

	public void setResourceUrl(String resourceUrl) {
		this.resourceUrl = resourceUrl;
	}

	public String getBtnId() {
		return this.btnId;
	}

	public void setBtnId(String btnId) {
		this.btnId = btnId;
	}

	public String getBtnFun() {
		return this.btnFun;
	}

	public void setBtnFun(String btnFun) {
		this.btnFun = btnFun;
	}

	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Integer getOrderNumber() {
		return this.orderNumber;
	}

	public void setOrderNumber(Integer orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Integer getResourceState() {
		return this.resourceState;
	}

	public void setResourceState(Integer resourceState) {
		this.resourceState = resourceState;
	}

	public String getResourceDesc() {
		return this.resourceDesc;
	}

	public void setResourceDesc(String resourceDesc) {
		this.resourceDesc = resourceDesc;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public List<Resources> getNodes() {
		if (CollectionUtils.isNotEmpty(nodes)) {
			Collections.sort(nodes, new Comparator<Resources>() {
				@Override
				public int compare(Resources o1, Resources o2) {
					// TODO Auto-generated method stub
					return o1.getOrderNumber().compareTo(o2.getOrderNumber());
				}
			});
		}
		return nodes;
	}
	
	public List<Resources> getMenus() {
		return getChildByType(ResourceTypes.MENU);
	}
	
	public List<Resources> getFuncs() {
		return getChildByType(ResourceTypes.FUNC);
	}
	
	public List<Resources> getButtons() {
		return getChildByType(ResourceTypes.BUTTON);
	}
	
	public List<Resources> getChildByType(ResourceTypes resourceTypes) {
		List<Resources> list = new ArrayList<Resources>(0);
		for (Resources resources : getNodes()) {
			if (resources.getResourceType() != null && resources.getResourceType().equals(resourceTypes.getType())) {
				list.add(resources);
			}
		}
		return list;
	}

	public void setNodes(List<Resources> nodes) {
		this.nodes = nodes;
	}

	@Override
	public String toString() {
		return "Resources [resourceId=" + resourceId + ", resourceName="
				+ resourceName + ", parentCode=" + parentCode + ",resourceCode=" 
				+ resourceCode + ", nodes=" + nodes + "]";
	}

}