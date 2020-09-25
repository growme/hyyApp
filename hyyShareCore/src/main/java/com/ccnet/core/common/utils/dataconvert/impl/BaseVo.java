package com.ccnet.core.common.utils.dataconvert.impl;

import java.io.Serializable;

import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.dataconvert.Dto;

/**
 * 简单值对象<br>
 * 简单值对象和数据库表没有一一对应关系<br>
 * <b>注意：没有特殊需要建议不用VO,对于固定的数据存储结构请使用PO;对于动态可变的
 * 的数据存储结构请使用Dto来代替.
 * @author wgp
 * @since 2013-01-15
 * @see java.io.Serializable
 */
public abstract class BaseVo implements Serializable{
	
    /**
     * 将值对象转换为Dto对象
     * 
     * @return dto 返回的Dto对象
     */
	public Dto toDto(){
		Dto dto = new BaseDto();
		CPSUtil.copyPropFromBean2Dto(this, dto);
		return dto;
	}
    
	/**
	 * 将值对象转换为JSON字符串
	 * @return String 返回的JSON格式字符串
	 */
    public String toJson(){
    	Dto dto = toDto();
    	return dto.toJson();
    }
}
