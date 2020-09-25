package com.ccnet.core.entity;

import java.util.Date;

import com.ccnet.core.common.ContentDomainType;
import com.ccnet.core.common.DomainStateType;
import com.ccnet.core.common.StateType;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.dao.base.IgnoreTableField;

/**
 * 派单域名
 * 
 * @author jackie
 * 
 */
public class SbContentDomain extends BaseEntity {

	private static final long serialVersionUID = -220191630341809103L;

	private String domainId; // 主键
	private String domainName; // 域名
	private Integer enabled; // 是否有效
	@IgnoreTableField
	private boolean filterDomain;//过滤无效
	@IgnoreTableField
	private String enabledName;
	private Integer orderNumber; // 排序
	private Date createTime; // 创建时间
	private Integer personal; // 专属域名 0 否 1 是
	@IgnoreTableField
	private String personalName;
	private Integer domainType; // 类型 0 微信 1 非微信
	@IgnoreTableField
	private String domainTypeName;
	private Date lastUpdateTime;//最后更新

	public String getDomainId() {
		return domainId;
	}

	public void setDomainId(String domainId) {
		this.domainId = domainId;
	}

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public Integer getEnabled() {
		return enabled;
	}

	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}

	public Integer getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(Integer orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public String getEnabledName() {
		if(CPSUtil.isNotEmpty(getEnabled())){
			DomainStateType dsType = DomainStateType.getStateType(getEnabled());
			if(dsType!=null){
			   enabledName = dsType.getName();
			}
		}
		return enabledName;
	}

	public void setEnabledName(String enabledName) {
		this.enabledName = enabledName;
	}

	public boolean isFilterDomain() {
		return filterDomain;
	}

	public void setFilterDomain(boolean filterDomain) {
		this.filterDomain = filterDomain;
	}

	public Integer getDomainType() {
		return domainType;
	}

	public void setDomainType(Integer domainType) {
		this.domainType = domainType;
	}

	public String getDomainTypeName() {
		if(CPSUtil.isNotEmpty(getDomainType())){
			domainTypeName = ContentDomainType.getContentDomainType(getDomainType()).getName();
		}
		return domainTypeName;
	}

	public void setDomainTypeName(String domainTypeName) {
		this.domainTypeName = domainTypeName;
	}

	public Integer getPersonal() {
		return personal;
	}

	public void setPersonal(Integer personal) {
		this.personal = personal;
	}

	public String getPersonalName() {
		if(CPSUtil.isNotEmpty(getPersonal())){
			personalName = StateType.getStateType(getPersonal()).getName();
		}
		return personalName;
	}

	public void setPersonalName(String personalName) {
		this.personalName = personalName;
	}
	
}
