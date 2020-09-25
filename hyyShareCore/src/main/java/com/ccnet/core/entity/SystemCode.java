package com.ccnet.core.entity;



/**
 * 系统字典表
 * @author Jackie Wang
 */
public class SystemCode  extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	private String codeId;
	private String codeKey;
	private String codeName;
	private String codeValue;
	private String valueDesc;
	private Integer editMode;
	private Integer codeState;
	private Integer orderNumber;
	private String codeDesc;

	public SystemCode() {
		
	}

	public SystemCode(String codeKey) {
		this.codeKey = codeKey;
	}

	public SystemCode(String codeKey, String codeId, String codeName,
			String codeValue, String valueDesc, Integer editMode,
			Integer codeState, Integer orderNumber, String codeDesc) {
		this.codeKey = codeKey;
		this.codeId = codeId;
		this.codeName = codeName;
		this.codeValue = codeValue;
		this.valueDesc = valueDesc;
		this.editMode = editMode;
		this.codeState = codeState;
		this.orderNumber = orderNumber;
		this.codeDesc = codeDesc;
	}

	public String getCodeId() {
		return this.codeId;
	}

	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}

	public String getCodeName() {
		return this.codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	public String getCodeValue() {
		return this.codeValue;
	}

	public void setCodeValue(String codeValue) {
		this.codeValue = codeValue;
	}

	public String getValueDesc() {
		return this.valueDesc;
	}

	public void setValueDesc(String valueDesc) {
		this.valueDesc = valueDesc;
	}

	public Integer getEditMode() {
		return this.editMode;
	}

	public void setEditMode(Integer editMode) {
		this.editMode = editMode;
	}

	public Integer getCodeState() {
		return this.codeState;
	}

	public void setCodeState(Integer codeState) {
		this.codeState = codeState;
	}

	public Integer getOrderNumber() {
		return this.orderNumber;
	}

	public void setOrderNumber(Integer orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getCodeDesc() {
		return this.codeDesc;
	}

	public void setCodeDesc(String codeDesc) {
		this.codeDesc = codeDesc;
	}
	
	public String getCodeKey() {
		return this.codeKey;
	}

	public void setCodeKey(String codeKey) {
		this.codeKey = codeKey;
	}

}