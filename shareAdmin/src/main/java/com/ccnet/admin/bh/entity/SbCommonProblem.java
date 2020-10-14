package com.ccnet.admin.bh.entity;

import com.ccnet.core.entity.BaseEntity;

import java.util.Date;

/**
 * 常见问题
 */
public class SbCommonProblem extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String title; // 标题
    private String content; // 内容
    private String type; // 类型
    private Date createTime; // 创建时间
    private Date updateTime; // 更新时间
    private Integer sort; // 排序
    private Integer status; // 状态

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
