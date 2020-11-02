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
    //付款账户
    private String payAccount;
    //账户名称
    private String accountName;
    //提现金额
    private Double cmoney;
    //备注
    private String remark;


    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
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



    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


}
