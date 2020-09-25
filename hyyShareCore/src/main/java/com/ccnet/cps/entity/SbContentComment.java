package com.ccnet.cps.entity;

import com.ccnet.core.entity.BaseEntity;

/**
 * 文章点赞 ClassName: SbContentComment
 * 
 * @author Jackie Wang
 * @company 北京简智科技
 * @copyright 版权所有 盗版必究
 * @date 2017-8-10
 */
public class SbContentComment extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private Integer commentId;
	private Integer contentId;
	private Integer zjCount;
	private Integer wyCount;
	private Integer dzCount;
	private Integer gxCount;
	private Integer bsCount;

	public Integer getCommentId() {
		return commentId;
	}

	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}

	public Integer getContentId() {
		return contentId;
	}

	public void setContentId(Integer contentId) {
		this.contentId = contentId;
	}

	public Integer getZjCount() {
		return zjCount;
	}

	public void setZjCount(Integer zjCount) {
		this.zjCount = zjCount;
	}

	public Integer getWyCount() {
		return wyCount;
	}

	public void setWyCount(Integer wyCount) {
		this.wyCount = wyCount;
	}

	public Integer getDzCount() {
		return dzCount;
	}

	public void setDzCount(Integer dzCount) {
		this.dzCount = dzCount;
	}

	public Integer getGxCount() {
		return gxCount;
	}

	public void setGxCount(Integer gxCount) {
		this.gxCount = gxCount;
	}

	public Integer getBsCount() {
		return bsCount;
	}

	public void setBsCount(Integer bsCount) {
		this.bsCount = bsCount;
	}

}
