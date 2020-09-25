package com.ccnet.api.entity;


import java.io.Serializable;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * Title: 返回结果信息格式化
 * @Author :zhusj
 */
public class ResultDTO<T> implements Serializable {
    
    @JsonProperty
    private int status;

    @JsonProperty
    @JsonInclude(Include.NON_NULL)
    private String status_name;
    
	@JsonProperty
	@JsonInclude(Include.NON_NULL)
	private String status_detail;
	
    
    @JsonProperty
    @JsonInclude(Include.NON_NULL)
    private Integer is_show;
    
    
	@JsonProperty
	@JsonInclude(Include.NON_NULL)
    private T body;
    
	@JsonIgnoreProperties
	private ResultCode _code;

    public ResultDTO(ResultCode code) {
    	changeCode(code);
    }

	public ResultDTO(ResultCode code, String statusName) {
		setName(code, statusName);
	}
    
	/**
	 * 改变状态
	 * @param code
	 * @return
	 */
	public ResultDTO<T> changeCode(ResultCode code){
		boolean ok = code==ResultCode.BasicCode.正常;
		this._code=code;
		this.status=code.code();
		this.status_name = ok?null:code.name();
		this.status_detail = ok?null:code.description();
		this.is_show = ok?null:(code.show()?1:0);
		if(!ok) this.body = null;
		return this;
	}

	/**
	 * 返回通知
	 * @param statusName
	 * @return
	 */
	public ResultDTO<T> setName(ResultCode code, String statusName){
		boolean ok = code==ResultCode.BasicCode.正常;
		this._code=code;
		this.status=code.code();
		this.status_name = statusName;
		this.status_detail = ok?null:code.description();
		this.is_show = ok?null:(code.show()?1:0);
		if(!ok) this.body = null;
		return this;
	}
	
	/**
	 * 设置显隐
	 * @param is_show
	 * @return
	 */
	public ResultDTO<T> show(boolean is_show){
		if(this._code==ResultCode.BasicCode.正常){
			throw new IllegalStateException("正常状态是无法设置显示状态");
		}
		this.is_show = is_show?1:0;
		return this;
	}
	
	public ResultDTO<T> setBody(T data){
		this.body = data;
		return this;
	}
	
	public T getBody(){
		return this.body;
	}
	
	public boolean ok() {
		return this._code==ResultCode.BasicCode.正常;
	}
	
	public ResultCode code() {
		return _code;
	} 
	
	public static <T> ResultDTO<T> OK(){
		return new ResultDTO<T>(ResultCode.BasicCode.正常);
	}
	
	public static <T> ResultDTO<T> OK(T data){
		ResultDTO<T> r = ResultDTO.OK();
		r.setBody(data);
		return r;
	}
    
	public static <T> ResultDTO<T> ERROR(ResultCode code){
		if(code==ResultCode.BasicCode.正常){
			throw new IllegalStateException("异常时code不对");
		}
		return new ResultDTO<T>(code);
	}

	public static <T> ResultDTO<T> INFO(ResultCode code,String status_name) {
		return new ResultDTO<T>(code,status_name);
	}
    
    public String toErrorString() {
    	if(this._code==ResultCode.BasicCode.正常){
			throw new IllegalStateException("当前code是正常");
		}
    	return "{\"status\":"+this._code.code()+",\"status_name\":\""+this._code.name()+"\",\"status_detail\":\""+this._code.description()+"\",\"is_show\":"+(this._code.show()?1:0)+"}";
    }
    
    public static void main(String[] args) throws JsonProcessingException {
    	ObjectMapper m = new ObjectMapper();
    	System.out.println(m.writeValueAsString(ResultDTO.OK(new ArrayList<>())));
	}
}

