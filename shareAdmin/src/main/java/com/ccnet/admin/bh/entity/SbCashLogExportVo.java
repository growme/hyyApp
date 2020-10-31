package com.ccnet.admin.bh.entity;

import java.util.Date;

/**
 * @Author jingjie
 * @Date 2020/10/30 0030 14:40
 * @Description 提现记录导出实体
 **/
public class SbCashLogExportVo {
    //序号
    private int index;
    //会员ID
    private Integer userId;
    //会员账户
    private String loginAccount;
    //付款账户
    private String payAccount;
    //账户名称
    private String accountName;
    //提现金额
    private Double cmoney;
    //提现的方式
    private String typeName;
    //提现状态
    private String stateName;
    //备注
    private String remark;
    //申请时间
    private Date createTime;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getLoginAccount() {
        return loginAccount;
    }

    public void setLoginAccount(String loginAccount) {
        this.loginAccount = loginAccount;
    }

    public String getPayAccount() {
        return payAccount;
    }

    public void setPayAccount(String payAccount) {
        this.payAccount = payAccount;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Double getCmoney() {
        return cmoney;
    }

    public void setCmoney(Double cmoney) {
        this.cmoney = cmoney;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
