<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- Head -->
<head>
<meta charset="utf-8" />
<title>${CT_MENU_NAV}</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ include file="../../../common/includes.jsp"%>
<%@ include file="../../../common/com_css.jsp"%>
<link href="${basePath}static/js/bfileinput/css/fileinput.css"
	rel="stylesheet" type="text/css" />
<style type="text/css">
.appLogoImg {
	width: 159px;
	height: 60px;
	margin: 0px !important;
	border: 1px solid #eee;
	border-radius: 0px;
}

.siteLogoImg {
	width: 159px;
	height: 64px;
	margin: 0px !important;
	border: 1px solid #eee;
	border-radius: 0px;
}

.siteBLogoImg {
	width: 239px;
	height: 52px;
	margin: 0px !important;
	border: 1px solid #eee;
	border-radius: 0px;
}

.siteIcoImg {
	width: 32px;
	height: 32px;
	margin: 0px !important;
	border: 1px solid #eee;
	border-radius: 0px;
}

.info-box {
	font-size: 14px;
	margin-bottom: 10px;
	border: 1px solid #eee;
}

.info-box .info-hd {
	line-height: 35px;
	height: 35px;
	font-size: 14px;
	padding: 0 14px;
	background: #f5f5f5;
	border-bottom: 1px solid #eee;
}

.info-box .info-hd .pos-top {
	top: -13px !important;
}

.info-box .info-bd {
	padding: 10px 20px;
	font-size: 12px;
	zoom: 1;
}

.info-box.mini-padding .info-bd {
	padding: 20px 30px;
}

.info-box .info-bd .empty {
	padding: 35px 0;
	text-align: center;
	color: #8d8d8d;
}

.info-box .info-bd .data-box.with-padding {
	margin: 0 -13px;
}

.info-title {
	font-weight: bold;
}

.table>tbody>tr>td {
	border-top: 0px dashed #eee !important;
}
</style>
</head>
<!-- /Head -->
<!-- Body -->
<body class="bodybg">
	<!-- Loading Container -->
	<%@ include file="../../../common/loading.jsp"%>
	<!--  /Loading Container -->
	<c:if test="${ADMIN_VERSION!='V5'}">
		<!-- Navbar -->
		<%@ include file="../../../common/navbar.jsp"%>
		<!-- /Navbar -->
		<!-- Main Container -->
		<div class="main-container container-fluid">
			<!-- Page Container -->
			<div class="page-container">
				<!-- menu Container -->
				<%@ include file="../../../common/menu.jsp"%>
				<!--  /menu Container -->
				<!-- Page Content -->
				<div class="page-content">
					<!-- Page Breadcrumb -->
					<%@ include file="../../../common/navtip.jsp"%>
					<!-- /Page Breadcrumb -->
					<!-- Page Body -->
	</c:if>
	<div class="page-body">
		<div class="swidget">
			<div class="widget-body">

				<div class="widget-main">
					<div class="tabbable">
						<ul class="nav nav-tabs tabs-flat" id="mySettingTab">
							<li <c:if test="${flag==0}"> class="active" </c:if> id="flag_0">
								<a data-toggle="tab" href="#baseSetting">网站配置</a>
							</li>
							<li <c:if test="${flag==1}"> class="active" </c:if> id="flag_1">
								<a data-toggle="tab" href="#domainSetting">防作弊配置</a>
							</li>
							<li <c:if test="${flag==2}"> class="active" </c:if> id="flag_2">
								<a data-toggle="tab" href="#orderSetting">推广设置</a>
							</li>
							<li <c:if test="${flag==4}"> class="active" </c:if> id="flag_4">
								<a data-toggle="tab" href="#serverSetting">客服设置</a>
							</li>
							<li <c:if test="${flag==3}"> class="active" </c:if> id="flag_3">
								<a data-toggle="tab" href="#otherSetting">其他设置</a>
							</li>
						</ul>

						<div
							class="tab-content tabs-flat no-padding-left no-padding-right">
							<input type="hidden" class="form-control" id="flag" name="flag"
								value="${flag}"> <input type="hidden"
								class="form-control" id="mod" name="mod" value="${menuId}">
							<div id="baseSetting"
								class='tab-pane<c:if test="${flag==0}"> in active </c:if>'>
								<form role="form" id="baseSettingForm" action="" method="post">
									<div class="info-box">
										<div class="info-hd">
											<span class="no-margin-top"><i class="fa fa-edit"></i>
												基本配置</span>
										</div>
										<div class="info-bd no-margin no-padding">
											<table class="table">
												<tbody>

													<tr>
														<td align="right" width="180px">前台主题风格&nbsp;&nbsp;</td>
														<td>

															<div id="iconImg"
																style="overflow-y: auto; overflow-x: hidden;">

																<label class="padding-5"> <input type="radio"
																	class="picUrlRadio" id="USER_DEFAULT_THEME_1"
																	<c:if test="${systemParam.USER_DEFAULT_THEME=='red'}">checked</c:if>
																	name="USER_DEFAULT_THEME" value="red"> <span
																	class="text"><img class="header-avatar3"
																		src="${basePath}static/img/theme/red.png"></span>
																</label> <label class="padding-5"> <input type="radio"
																	class="picUrlRadio" id="USER_DEFAULT_THEME_2"
																	<c:if test="${systemParam.USER_DEFAULT_THEME=='blue'}">checked</c:if>
																	name="USER_DEFAULT_THEME" value="blue"> <span
																	class="text"><img class="header-avatar3"
																		src="${basePath}static/img/theme/blue.png"></span>
																</label> <label class="padding-5"> <input type="radio"
																	class="picUrlRadio" id="USER_DEFAULT_THEME_3"
																	<c:if test="${systemParam.USER_DEFAULT_THEME=='orange'}">checked</c:if>
																	name="USER_DEFAULT_THEME" value="orange"> <span
																	class="text"><img class="header-avatar3"
																		src="${basePath}static/img/theme/orange.png"></span>
																</label> <label class="padding-5"> <input type="radio"
																	class="picUrlRadio" id="USER_DEFAULT_THEME_4"
																	<c:if test="${systemParam.USER_DEFAULT_THEME=='teal'}">checked</c:if>
																	name="USER_DEFAULT_THEME" value="teal"> <span
																	class="text"><img class="header-avatar3"
																		src="${basePath}static/img/theme/teal.png"></span>
																</label>

															</div>

														</td>
													</tr>

													<tr>
														<td align="right" width="180px">网站入口域名&nbsp;&nbsp;</td>
														<td><input type="text" class="form-control"
															placeholder="设置网站如果域名后，非入口域名无法直接通过域名访问前后台！"
															id="HOME_INDEX_DOMAIN" name="HOME_INDEX_DOMAIN"
															value="${systemParam.HOME_INDEX_DOMAIN}"></td>
													</tr>

													<tr>
														<td align="right" width="180px">后台主题风格&nbsp;&nbsp;</td>
														<td><select name="ADMIN_VERSION" id="ADMIN_VERSION"
															style="width: 100%;">
																<option value="">请选择...</option>
																<option value="V3">炫动版</option>
																<option value="V5">经典版</option>
														</select> <span class="danger">提示：</span>后台主题风格
															炫动版：为HTML5整体刷新版本，经典版：为框架版本（局部刷新）修改后整体刷新或者重新登录查看效果!</td>
													</tr>

													<tr>
														<td align="right" width="180px">网站名称&nbsp;&nbsp;</td>
														<td><input type="text" class="form-control"
															placeholder="网站前台系统简称 例如：微乐赚" id="SITE_NAME"
															name="SITE_NAME" value="${systemParam.SITE_NAME}">
														</td>
													</tr>
													<tr>
														<td align="right" width="180px">网站前台标题&nbsp;&nbsp;</td>
														<td><input type="text" class="form-control"
															placeholder="自媒体前台网站显示的网站名称 例如：微乐赚" id="SITE_TITLE"
															name="SITE_TITLE" value="${systemParam.SITE_TITLE}">
														</td>
													</tr>
													<tr>
														<td align="right" width="180px">网站后台标题&nbsp;&nbsp;</td>
														<td><input type="text" class="form-control"
															placeholder="网站后台显示的网站标题 例如：微乐赚-后台管理系统"
															id="BACK_SYSTEM_TITLE" name="BACK_SYSTEM_TITLE"
															value="${systemParam.BACK_SYSTEM_TITLE}"></td>
													</tr>
													<tr>
														<td align="right" width="180px">网站SEO关键词&nbsp;&nbsp;
														</td>
														<td><textarea class="form-control" rows="3"
																placeholder="提供给百度和其他搜索引擎进行网站收录和采集使用" id="SITE_KEYWORDS"
																name="SITE_KEYWORDS">${systemParam.SITE_KEYWORDS}</textarea>
														</td>
													</tr>
													<tr>
														<td align="right" width="180px">版权备案信息&nbsp;&nbsp;</td>
														<td><textarea class="form-control" rows="3"
																placeholder="网站前台底部和后台登录显示的版权信息" id="BOTTOM_COPYRIGHT"
																name="BOTTOM_COPYRIGHT">${systemParam.BOTTOM_COPYRIGHT}</textarea>
														</td>
													</tr>

													<tr>
														<td align="right" width="180px">流量统计方式&nbsp;&nbsp;</td>
														<td><select name="SITE_COUNT_METHOD"
															id="SITE_COUNT_METHOD" style="width: 100%;">
																<option value="">请选择...</option>
																<option value="cnzz">CNZZ统计</option>
																<option value="baidu">百度统计</option>
														</select> <span class="danger">提示：</span>如果一个平台的统计代码出现异常，可以选择切换到其它平台!
														</td>
													</tr>

													<tr>
														<td align="right" width="180px">
															统计代码(全局-CNZZ)&nbsp;&nbsp;</td>
														<td><textarea class="form-control" rows="2"
																placeholder="CNZZ网站流量监控代码" id="SITE_CNZZ_COUNT_CODE"
																name="SITE_CNZZ_COUNT_CODE">${systemParam.SITE_CNZZ_COUNT_CODE}</textarea>
														</td>
													</tr>
													<tr>
													<tr>
														<td align="right" width="180px">
															统计代码(全局备-CNZZ)&nbsp;&nbsp;</td>
														<td><textarea class="form-control" rows="2"
																placeholder="CNZZ网站流量监控代码" id="SITE_CNZZ_COUNT_BCODE"
																name="SITE_CNZZ_COUNT_BCODE">${systemParam.SITE_CNZZ_COUNT_BCODE}</textarea>
														</td>
													</tr>
													<tr>
													<tr>
														<td align="right" width="180px">
															统计代码(全局-BAIDU)&nbsp;&nbsp;</td>
														<td><textarea class="form-control" rows="2"
																placeholder="BAIDU网站流量监控代码" id="SITE_BAIDU_COUNT_CODE"
																name="SITE_BAIDU_COUNT_CODE">${systemParam.SITE_BAIDU_COUNT_CODE}</textarea>
														</td>
													</tr>
													<tr>
													<tr>
														<td align="right" width="180px">
															统计代码(全局备-BAIDU)&nbsp;&nbsp;</td>
														<td><textarea class="form-control" rows="2"
																placeholder="BAIDU网站流量监控代码" id="SITE_BAIDU_COUNT_BCODE"
																name="SITE_BAIDU_COUNT_BCODE">${systemParam.SITE_BAIDU_COUNT_BCODE}</textarea>
														</td>
													</tr>
													<tr>
													<tr>
														<td align="right" width="180px">开启登录IP限制&nbsp;&nbsp;
														</td>
														<td><label class="no-margin"> <input
																class="checkbox-slider toggle colored-success"
																id="VALIDATE_LOGINIP_ENABLE_CK"
																<c:if test="${systemParam.VALIDATE_LOGINIP_ENABLE==1}">checked</c:if>
																name="VALIDATE_LOGINIP_ENABLE_CK" type="checkbox"
																onchange="changeCheckVal('VALIDATE_LOGINIP_ENABLE');">
																<span class="text no-margin"></span> <input
																type="hidden" id="VALIDATE_LOGINIP_ENABLE"
																name="VALIDATE_LOGINIP_ENABLE" class="form-control"
																value="${systemParam.VALIDATE_LOGINIP_ENABLE}">
														</label> <span class="danger">警告：</span>如果开启后台登录IP限制，建议使用固定IP登录服务器后台，如果IP不固定将导致所有账户无法登录系统，谨慎设置!
														</td>
													</tr>

													<tr>
														<td align="right" width="180px">登录IP白名单&nbsp;&nbsp;</td>
														<td><input type="text" class="form-control"
															placeholder="后台系统验证登陆ip白名单即可以登录后台的ip地址"
															id="VALIDATE_LOGINIP_ADDRESS"
															name="VALIDATE_LOGINIP_ADDRESS"
															value="${systemParam.VALIDATE_LOGINIP_ADDRESS}">
															<span class="danger">警告：</span>后台系统验证登陆IP白名单即可以登录后台的IP地址
															多个英文分号(;)隔开，如果IP不固定将导致所有账户无法登录系统，谨慎设置!</td>
													</tr>

												</tbody>
											</table>
										</div>
									</div>
									<center>
										<button type="button" class="btn btn-primary"
											id="saveBaseSettingBtn">
											<i class="fa fa-save"></i>提交保存
										</button>
									</center>
								</form>

							</div>

							<div id="domainSetting"
								class='tab-pane<c:if test="${flag==1}"> in active </c:if>'>
								<form role="form" id="domainSettingForm" action="" method="post">
									<div class="info-box">
										<div class="info-hd">
											<span class="no-margin-top"><i class="fa fa-edit"></i>
												防作弊参数</span>
										</div>
										<div class="info-bd no-margin no-padding">
											<table class="table">
												<tbody>
													<tr>
														<td align="right" width="180px">文章开始记账时间&nbsp;&nbsp;</td>
														<td><input type="text" class="form-control"
															title="用户阅读文章多久后开始记账 默认10秒"
															placeholder="用户阅读文章多久后开始记账 默认10秒"
															id="CT_ARTICLE_RECORD_TIME" name="CT_ARTICLE_RECORD_TIME"
															value="${systemParam.CT_ARTICLE_RECORD_TIME}"></td>
													</tr>
													<tr>
														<td align="right" width="180px">视频开始记账时间&nbsp;&nbsp;</td>
														<td><input type="text" class="form-control"
															title="用户观看视频多久后开始记账 默认30秒"
															placeholder="用户观看视频多久后开始记账 默认30秒"
															id="CT_ARTICLE_RECORD_TIME_VIDEO"
															name="CT_ARTICLE_RECORD_TIME_VIDEO"
															value="${systemParam.CT_ARTICLE_RECORD_TIME_VIDEO}"></td>
													</tr>
													<tr>
														<td align="right" width="180px">页面最低滑动次数&nbsp;&nbsp;</td>
														<td><input type="text" class="form-control"
															title="用户阅读文章时手指至少滑动页面次数 默认 2次"
															placeholder="用户阅读文章时手指至少滑动页面次数 默认 2次"
															id="CT_ARTICLE_TOUCH_COUNT" name="CT_ARTICLE_TOUCH_COUNT"
															value="${systemParam.CT_ARTICLE_TOUCH_COUNT}"></td>
													</tr>
													<tr>
														<td align="right" width="180px">页面最大滑动次数&nbsp;&nbsp;</td>
														<td><input type="text" class="form-control"
															title="用户阅读文章时手指最大滑动次数" placeholder="用户阅读文章时手指最大滑动次数"
															id="CT_ARTICLE_TOUCH_COUNT_MAX"
															name="CT_ARTICLE_TOUCH_COUNT_MAX"
															value="${systemParam.CT_ARTICLE_TOUCH_COUNT_MAX}"></td>
													</tr>
													<tr>
														<td align="right" width="180px">点击展开次数&nbsp;&nbsp;</td>
														<td><input type="text" class="form-control"
															title="用户阅读文章时至少点击展开按钮次数 默认 2次"
															placeholder="用户阅读文章时至少点击展开按钮次数 默认 2次"
															id="CT_ARTICLE_EXPAND_COUNT"
															name="CT_ARTICLE_EXPAND_COUNT"
															value="${systemParam.CT_ARTICLE_EXPAND_COUNT}"></td>
													</tr>

													<tr>
														<td align="right" width="180px">点击展开间隔&nbsp;&nbsp;</td>
														<td><input type="text" class="form-control"
															title="用户阅读文章时两次点击展开按钮至少间隔多少秒 默认 1秒"
															placeholder="用户阅读文章时两次点击展开按钮至少间隔多少秒 默认 1秒"
															id="CT_ARTICLE_EXPAND_TIME" name="CT_ARTICLE_EXPAND_TIME"
															value="${systemParam.CT_ARTICLE_EXPAND_TIME}"></td>
													</tr>

													<tr>
														<td align="right" width="180px">手机晃动次数&nbsp;&nbsp;</td>
														<td><input type="text" class="form-control"
															title="用户阅读文章时手机至少晃动多少次 默认 1次"
															placeholder="用户阅读文章时手机至少晃动多少次 默认 1次"
															id="CT_PHONE_MOVE_COUNT" name="CT_PHONE_MOVE_COUNT"
															value="${systemParam.CT_PHONE_MOVE_COUNT}"></td>
													</tr>

													<tr>
														<td align="right" width="180px">心跳更新间隔&nbsp;&nbsp;</td>
														<td><input type="text" class="form-control"
															title="用户阅读文章时间隔多久时间未更新心跳的记录保存到数据库 比如 5分钟"
															placeholder="用户阅读文章时间隔多久时间未更新心跳的记录保存到数据库 比如 5分钟"
															id="CT_HEART_BEAT_UPDATE_TIME"
															name="CT_HEART_BEAT_UPDATE_TIME"
															value="${systemParam.CT_HEART_BEAT_UPDATE_TIME}">
														</td>
													</tr>

													<tr>
														<td align="right" width="180px">心跳缓存时间&nbsp;&nbsp;</td>
														<td><input type="text" class="form-control"
															title="用户阅读文章发送的心跳数据在 Redis缓存库中保留的最长时间 单位为：秒  比如 1200 表示20分钟后删除 设置为0 则永不过期！"
															placeholder="用户阅读文章发送的心跳数据在 Redis缓存库中保留的最长时间 单位为：秒  比如 1200 表示20分钟后删除 设置为0 则永不过期！"
															id="CT_CONTENT_HEART_BEAT_CACHE_TIME"
															name="CT_CONTENT_HEART_BEAT_CACHE_TIME"
															value="${systemParam.CT_CONTENT_HEART_BEAT_CACHE_TIME}">
														</td>
													</tr>

													<tr>
														<td align="right" width="180px">
															SESSION缓存时间&nbsp;&nbsp;</td>
														<td><input type="text" class="form-control"
															title="用户阅读文章的session缓存数据在 Redis缓存库中保留的最长时间 单位为：秒  比如 1200 表示20分钟后删除 设置为0 则永不过期！"
															placeholder="用户阅读文章的session缓存数据在 Redis缓存库中保留的最长时间 单位为：秒  比如 1200 表示20分钟后删除 设置为0 则永不过期！"
															id="CT_CONTENT_READ_CACHE_TIME"
															name="CT_CONTENT_READ_CACHE_TIME"
															value="${systemParam.CT_CONTENT_READ_CACHE_TIME}">
														</td>
													</tr>

													<tr>
														<td align="right" width="180px">文章指纹缓存时间&nbsp;&nbsp;
														</td>
														<td><input type="text" class="form-control"
															title="用户阅读文章的指纹数据（判断是否首次访问）在 Redis缓存库中保留的最长时间 单位为：秒  比如 1200 表示20分钟后删除 设置为0 则永不过期！"
															placeholder="用户阅读文章的指纹数据（判断是否首次访问）在 Redis缓存库中保留的最长时间 单位为：秒  比如 1200 表示20分钟后删除 设置为0 则永不过期！"
															id="CT_CONTENT_HASHKEY_CACHE_TIME"
															name="CT_CONTENT_HASHKEY_CACHE_TIME"
															value="${systemParam.CT_CONTENT_HASHKEY_CACHE_TIME}">
														</td>
													</tr>
													<tr>
														<td align="right" width="180px">同ip一天计费几次&nbsp;&nbsp;
														</td>
														<td><input type="text" class="form-control"
															title="同ip一天计费几次  默认1次" placeholder="同ip一天计费几次  默认1次"
															id="REPEAT_IP_BILLING_NUM" name="REPEAT_IP_BILLING_NUM"
															value="${systemParam.REPEAT_IP_BILLING_NUM}"></td>
													</tr>
													<tr>
														<td align="right" width="180px">同一个openid计费几次&nbsp;&nbsp;
														</td>
														<td><input type="text" class="form-control"
															title="同一个openid计费几次 默认一次！"
															placeholder="同一个openid计费几次 默认一次！"
															id="PEPEAT_OPENID_BILLING_NUM"
															name="PEPEAT_OPENID_BILLING_NUM"
															value="${systemParam.PEPEAT_OPENID_BILLING_NUM}">
														</td>
													</tr>
													<tr>
														<td align="right" width="180px">转发和阅读同一个ip是否计费一次&nbsp;&nbsp;
														</td>
														<td><input type="text" class="form-control"
															title="转发和阅读同一个ip是否计费一次   默认计费1次  可以0次  这个不限制天数！"
															placeholder="转发和阅读同一个ip是否计费一次   默认计费1次  可以0次  这个不限制天数！"
															id="PEPEAT_IP_USERID_BILLING_NUM"
															name="PEPEAT_IP_USERID_BILLING_NUM"
															value="${systemParam.PEPEAT_IP_USERID_BILLING_NUM}">
														</td>
													</tr>
													<tr>
														<td align="right" width="180px">应用上架开光0关闭
															1打开&nbsp;&nbsp;</td>
														<td><input type="text" class="form-control"
															title="应用上架开光0关闭 1打开！" placeholder="应用上架开光0关闭 1打开！"
															id="PEPEAT_ON_LINE_SWITCH" name="PEPEAT_ON_LINE_SWITCH"
															value="${systemParam.PEPEAT_ON_LINE_SWITCH}"></td>
													</tr>

													<tr>
														<td align="right" width="180px">
															开启域名301跳转&nbsp;&nbsp;</td>
														<td><label class="no-margin"> <input
																class="checkbox-slider toggle colored-success"
																id="PAGE_DOMAIN_FORWARD_CK"
																<c:if test="${systemParam.PAGE_DOMAIN_FORWARD==1}">checked</c:if>
																name="PAGE_DOMAIN_FORWARD_CK" type="checkbox"
																onchange="changeCheckVal('PAGE_DOMAIN_FORWARD');">
																<span class="text no-margin"></span> <input
																type="hidden" id="PAGE_DOMAIN_FORWARD"
																name="PAGE_DOMAIN_FORWARD" class="form-control"
																value="${systemParam.PAGE_DOMAIN_FORWARD}">
														</label> <span class="danger">提示：</span>是否开启下单域名301跳转,开启跳转需要在<span
															class="danger">【监控管理->域名监控】</span>配置跳转域名和推广域名才能生效！</td>
													</tr>

													<tr>
														<td align="right" width="150px">开启域名泛解析&nbsp;&nbsp;</td>
														<td><label class="no-margin"> <input
																class="checkbox-slider toggle colored-success"
																id="GENERIC_PARSE_DOMAIN_CK"
																<c:if test="${systemParam.GENERIC_PARSE_DOMAIN==1}">checked</c:if>
																name="GENERIC_PARSE_DOMAIN_CK" type="checkbox"
																onchange="changeCheckVal('GENERIC_PARSE_DOMAIN');">
																<span class="text no-margin"></span> <input
																type="hidden" id="GENERIC_PARSE_DOMAIN"
																name="GENERIC_PARSE_DOMAIN" class="form-control"
																value="${systemParam.GENERIC_PARSE_DOMAIN}">
														</label> <span class="danger">提示：</span>开启域名泛解析需要配置泛解析主域名不要带www
															开启后301跳转失效！</td>
													</tr>

													<tr>
														<td align="right" width="150px">泛解析前缀长度&nbsp;&nbsp;</td>
														<td><select name="DOMAIN_FOR_GENERIC_PARSE_LENGTH"
															id="DOMAIN_FOR_GENERIC_PARSE_LENGTH" style="width: 100%;">
																<option value="">请选择...</option>
																<option value="8">8位字符</option>
																<option value="10">10位字符</option>
																<option value="20">20位字符</option>
																<option value="32">32位字符</option>
														</select></td>
													</tr>

													<tr>
														<td align="right" width="150px">泛解析前缀类型&nbsp;&nbsp;</td>
														<td><select name="DOMAIN_FOR_GENERIC_PARSE_TYPE"
															id="DOMAIN_FOR_GENERIC_PARSE_TYPE" style="width: 100%;">
																<option value="">请选择...</option>
																<option value="0">纯数字</option>
																<option value="1">纯字母</option>
																<option value="2">二者混合</option>
														</select></td>
													</tr>

													<tr>
														<td align="right" width="180px">
															落地页只能微信访问&nbsp;&nbsp;</td>
														<td><label class="no-margin"> <input
																class="checkbox-slider toggle colored-success"
																id="PAGE_WECHAT_READ_CK"
																<c:if test="${systemParam.PAGE_WECHAT_READ==1}">checked</c:if>
																name="PAGE_WECHAT_READ_CK" type="checkbox"
																onchange="changeCheckVal('PAGE_WECHAT_READ');">
																<span class="text no-margin"></span> <input
																type="hidden" id="PAGE_WECHAT_READ"
																name="PAGE_WECHAT_READ" class="form-control"
																value="${systemParam.PAGE_WECHAT_READ}">
														</label> <span class="danger">提示：</span>选择开启后推广文章页面只能在微信下面打开！</td>
													</tr>

													<tr>
														<td align="right" width="180px">小说推广关闭广告&nbsp;&nbsp;
														</td>
														<td><label class="no-margin"> <input
																class="checkbox-slider toggle colored-success"
																id="CLOSE_NOVEL_PAGE_ADVERTISE_CK"
																<c:if test="${systemParam.CLOSE_NOVEL_PAGE_ADVERTISE==1}">checked</c:if>
																name="CLOSE_NOVEL_PAGE_ADVERTISE_CK" type="checkbox"
																onchange="changeCheckVal('CLOSE_NOVEL_PAGE_ADVERTISE');">
																<span class="text no-margin"></span> <input
																type="hidden" id="CLOSE_NOVEL_PAGE_ADVERTISE"
																name="CLOSE_NOVEL_PAGE_ADVERTISE" class="form-control"
																value="${systemParam.CLOSE_NOVEL_PAGE_ADVERTISE}">
														</label> <span class="danger">提示：</span>选择开启后推广文章页面内容如果是小说则关闭所有广告！
														</td>
													</tr>

													<tr>
														<td align="right" width="180px">
															关闭小说二维码跳转&nbsp;&nbsp;</td>
														<td><label class="no-margin"> <input
																class="checkbox-slider toggle colored-success"
																id="CLOSE_NOVEL_FORWARD_BARCODE_CK"
																<c:if test="${systemParam.CLOSE_NOVEL_FORWARD_BARCODE==1}">checked</c:if>
																name="CLOSE_NOVEL_FORWARD_BARCODE_CK" type="checkbox"
																onchange="changeCheckVal('CLOSE_NOVEL_FORWARD_BARCODE');">
																<span class="text no-margin"></span> <input
																type="hidden" id="CLOSE_NOVEL_FORWARD_BARCODE"
																name="CLOSE_NOVEL_FORWARD_BARCODE" class="form-control"
																value="${systemParam.CLOSE_NOVEL_FORWARD_BARCODE}">
														</label> <span class="danger">提示：</span>选择开启后推广文章页面内容如果是小说则关闭二维码形式的跳转，采用直接跳转到小说平台
														</td>
													</tr>

													<tr>
														<td align="right" width="180px">X站跳转随机&nbsp;&nbsp;</td>
														<td><label class="no-margin"> <input
																class="checkbox-slider toggle colored-success"
																id="CT_RANDOM_FORWARD_LINK_SWITCH_CK"
																<c:if test="${systemParam.CT_RANDOM_FORWARD_LINK_SWITCH==1}">checked</c:if>
																name="CT_RANDOM_FORWARD_LINK_SWITCH_CK" type="checkbox"
																onchange="changeCheckVal('CT_RANDOM_FORWARD_LINK_SWITCH');">
																<span class="text no-margin"></span> <input
																type="hidden" id="CT_RANDOM_FORWARD_LINK_SWITCH"
																name="CT_RANDOM_FORWARD_LINK_SWITCH"
																class="form-control"
																value="${systemParam.CT_RANDOM_FORWARD_LINK_SWITCH}">
														</label> <span class="danger">提示：</span>选择开启则下面设置的好友X站地址和朋友圈X站地址失效，系统自动从入口地址随机获取！
														</td>
													</tr>

													<tr>
														<td align="right" width="180px">好友X站开关&nbsp;&nbsp;</td>
														<td><label class="no-margin"> <input
																class="checkbox-slider toggle colored-success"
																id="CT_USER_SHARE_PY_SWITCH_CK"
																<c:if test="${systemParam.CT_USER_SHARE_PY_SWITCH==1}">checked</c:if>
																name="CT_USER_SHARE_PY_SWITCH_CK" type="checkbox"
																onchange="changeCheckVal('CT_USER_SHARE_PY_SWITCH');">
																<span class="text no-margin"></span> <input
																type="hidden" id="CT_USER_SHARE_PY_SWITCH"
																name="CT_USER_SHARE_PY_SWITCH" class="form-control"
																value="${systemParam.CT_USER_SHARE_PY_SWITCH}">
														</label> <span class="danger">提示：</span>选择开启发出去到微信朋友圈的文章会首先用对应的X站域名跳转一次，关闭则用自己的域名跳转！
														</td>
													</tr>

													<tr>
														<td align="right" width="180px">开启好友地址编码&nbsp;&nbsp;
														</td>
														<td><label class="no-margin"> <input
																class="checkbox-slider toggle colored-success"
																id="CT_USER_SHARE_PY_ENCODE_CK"
																<c:if test="${systemParam.CT_USER_SHARE_PY_ENCODE==1}">checked</c:if>
																name="CT_USER_SHARE_PY_ENCODE_CK" type="checkbox"
																onchange="changeCheckVal('CT_USER_SHARE_PY_ENCODE');">
																<span class="text no-margin"></span> <input
																type="hidden" id="CT_USER_SHARE_PY_ENCODE"
																name="CT_USER_SHARE_PY_ENCODE" class="form-control"
																value="${systemParam.CT_USER_SHARE_PY_ENCODE}">
														</label> <span class="danger">提示：</span>选择开启URL路径编码
															则会对X站跳转后的路径进行URL编码！</td>
													</tr>

													<tr>
														<td align="right" width="180px">好友地址编码次数&nbsp;&nbsp;
														</td>
														<td><input type="text" class="form-control"
															placeholder="文章访问路劲编码次数 默认一次"
															id="CT_USER_SHARE_PY_ENCODE_COUNT"
															name="CT_USER_SHARE_PY_ENCODE_COUNT"
															value="${systemParam.CT_USER_SHARE_PY_ENCODE_COUNT}">
															<span class="danger">提示：</span>选择开启URL路径编码
															则会对X站跳转后的路径进行URL编码次数 默认一次 如果一次不行可以尝试两次！</td>
													</tr>

													<tr>
														<td align="right" width="180px">好友X站地址&nbsp;&nbsp;</td>
														<td><input type="text" class="form-control"
															title="分享到微信好友的文章借助X站跳转的地址"
															placeholder="分享到微信好友的文章借助X站跳转的地址"
															id="CT_USER_SHARE_PY_XSSURL"
															name="CT_USER_SHARE_PY_XSSURL"
															value="${systemParam.CT_USER_SHARE_PY_XSSURL}"></td>
													</tr>

													<tr>
														<td align="right" width="180px">
															好友落地域名关闭HTTP头&nbsp;&nbsp;</td>
														<td><label class="no-margin"> <input
																class="checkbox-slider toggle colored-success"
																id="CT_USER_SHARE_PY_LINK_HTTP_CK"
																<c:if test="${systemParam.CT_USER_SHARE_PY_LINK_HTTP==1}">checked</c:if>
																name="CT_USER_SHARE_PY_LINK_HTTP_CK" type="checkbox"
																onchange="changeCheckVal('CT_USER_SHARE_PY_LINK_HTTP');">
																<span class="text no-margin"></span> <input
																type="hidden" id="CT_USER_SHARE_PY_LINK_HTTP"
																name="CT_USER_SHARE_PY_LINK_HTTP" class="form-control"
																value="${systemParam.CT_USER_SHARE_PY_LINK_HTTP}">
														</label> <span class="danger">提示：</span>选择开启将会去掉落地地址最前面http://
															默认是加上http://</td>
													</tr>


													<tr>
														<td align="right" width="180px">朋友圈X站开关&nbsp;&nbsp;</td>
														<td><label class="no-margin"> <input
																class="checkbox-slider toggle colored-success"
																id="CT_USER_SHARE_PYQ_SWITCH_CK"
																<c:if test="${systemParam.CT_USER_SHARE_PYQ_SWITCH==1}">checked</c:if>
																name="CT_USER_SHARE_PYQ_SWITCH_CK" type="checkbox"
																onchange="changeCheckVal('CT_USER_SHARE_PYQ_SWITCH');">
																<span class="text no-margin"></span> <input
																type="hidden" id="CT_USER_SHARE_PYQ_SWITCH"
																name="CT_USER_SHARE_PYQ_SWITCH" class="form-control"
																value="${systemParam.CT_USER_SHARE_PYQ_SWITCH}">
														</label> <span class="danger">提示：</span>选择开启发出去给微信好友的文章会首先用对应的X站域名跳转一次，关闭则用自己的域名跳转！
														</td>
													</tr>

													<tr>
														<td align="right" width="180px">
															开启朋友圈地址编码&nbsp;&nbsp;</td>
														<td><label class="no-margin"> <input
																class="checkbox-slider toggle colored-success"
																id="CT_USER_SHARE_PYQ_ENCODE_CK"
																<c:if test="${systemParam.CT_USER_SHARE_PYQ_ENCODE==1}">checked</c:if>
																name="CT_USER_SHARE_PYQ_ENCODE_CK" type="checkbox"
																onchange="changeCheckVal('CT_USER_SHARE_PYQ_ENCODE');">
																<span class="text no-margin"></span> <input
																type="hidden" id="CT_USER_SHARE_PYQ_ENCODE"
																name="CT_USER_SHARE_PYQ_ENCODE" class="form-control"
																value="${systemParam.CT_USER_SHARE_PYQ_ENCODE}">
														</label> <span class="danger">提示：</span>选择开启URL路径编码
															则会对X站跳转后的路径进行URL编码！</td>
													</tr>

													<tr>
														<td align="right" width="180px">
															朋友圈地址编码次数&nbsp;&nbsp;</td>
														<td><input type="text" class="form-control"
															placeholder="文章访问路劲编码次数 默认一次"
															id="CT_USER_SHARE_PYQ_ENCODE_COUNT"
															name="CT_USER_SHARE_PYQ_ENCODE_COUNT"
															value="${systemParam.CT_USER_SHARE_PYQ_ENCODE_COUNT}">
															<span class="danger">提示：</span>选择开启URL路径编码
															则会对X站跳转后的路径进行URL编码次数 默认一次 如果一次不行可以尝试两次！</td>
													</tr>

													<tr>
														<td align="right" width="180px">朋友圈X站地址&nbsp;&nbsp;</td>
														<td><input type="text" class="form-control"
															title="分享到朋友圈的文章借助X站跳转的地址"
															placeholder="分享到朋友圈的文章借助X站跳转的地址"
															id="CT_USER_SHARE_PYQ_XSSURL"
															name="CT_USER_SHARE_PYQ_XSSURL"
															value="${systemParam.CT_USER_SHARE_PYQ_XSSURL}">
														</td>
													</tr>

													<tr>
														<td align="right" width="180px">
															朋友落地域名关闭HTTP头&nbsp;&nbsp;</td>
														<td><label class="no-margin"> <input
																class="checkbox-slider toggle colored-success"
																id="CT_USER_SHARE_PYQ_LINK_HTTP_CK"
																<c:if test="${systemParam.CT_USER_SHARE_PYQ_LINK_HTTP==1}">checked</c:if>
																name="CT_USER_SHARE_PYQ_LINK_HTTP_CK" type="checkbox"
																onchange="changeCheckVal('CT_USER_SHARE_PYQ_LINK_HTTP');">
																<span class="text no-margin"></span> <input
																type="hidden" id="CT_USER_SHARE_PYQ_LINK_HTTP"
																name="CT_USER_SHARE_PYQ_LINK_HTTP" class="form-control"
																value="${systemParam.CT_USER_SHARE_PYQ_LINK_HTTP}">
														</label> <span class="danger">提示：</span>选择开启将会去掉落地地址最前面http://
															默认是加上http://</td>
													</tr>


													<tr>
														<td align="right" width="180px">开启广告API&nbsp;&nbsp;</td>
														<td><label class="no-margin"> <input
																class="checkbox-slider toggle colored-success"
																id="CT_ADVERTISE_SERVER_API_SWITCH_CK"
																<c:if test="${systemParam.CT_ADVERTISE_SERVER_API_SWITCH==1}">checked</c:if>
																name="CT_ADVERTISE_SERVER_API_SWITCH_CK" type="checkbox"
																onchange="changeCheckVal('CT_ADVERTISE_SERVER_API_SWITCH');">
																<span class="text no-margin"></span> <input
																type="hidden" id="CT_ADVERTISE_SERVER_API_SWITCH"
																name="CT_ADVERTISE_SERVER_API_SWITCH"
																class="form-control"
																value="${systemParam.CT_ADVERTISE_SERVER_API_SWITCH}">
														</label> <span class="danger">提示：</span>选择开启广告API，
															需要您提供广告服务器对应API请求地址，用户每次点击广告会自动从广告服务器获取广告跳转地址(<span
															class="danger">要确保API接口能正常访问</span>)！</td>
													</tr>

													<tr>
														<td align="right" width="180px">广告AP接口地址&nbsp;&nbsp;
														</td>
														<td><input type="text" class="form-control"
															title="广告服务器提供的api接口地址" placeholder="广告服务器提供的api接口地址"
															id="CT_ADVERTISE_SERVER_API_URL"
															name="CT_ADVERTISE_SERVER_API_URL"
															value="${systemParam.CT_ADVERTISE_SERVER_API_URL}">
															<span class="danger">返回地址格式：callback({"luodi":"XXX","luodigurl":"XXX","advertising":"http://www.abc.com"})</span>

														</td>
													</tr>

													<tr>
														<td align="right" width="180px">域名自动检测接口
															ID&nbsp;&nbsp;</td>
														<td><input type="text" class="form-control"
															placeholder="第三方域名检测是否被微信屏蔽接口ID" id="DOMIAN_API_USER_ID"
															name="DOMIAN_API_USER_ID"
															value="${systemParam.DOMIAN_API_USER_ID}"></td>
													</tr>

													<tr>
														<td align="right" width="180px">域名自动检测接口
															KEY&nbsp;&nbsp;</td>
														<td><input type="text" class="form-control"
															placeholder="第三方域名检测是否被微信屏蔽接口KEY"
															id="DOMIAN_API_USER_KEY" name="DOMIAN_API_USER_KEY"
															value="${systemParam.DOMIAN_API_USER_KEY}"></td>
													</tr>

													<tr>
														<td align="right" width="180px">高德物理定位接口
															KEY&nbsp;&nbsp;</td>
														<td><input type="text" class="form-control"
															placeholder="高德物理定位接口 KEY" id="CT_GEO_API_KEY"
															name="CT_GEO_API_KEY"
															value="${systemParam.CT_GEO_API_KEY}"></td>
													</tr>

												</tbody>
											</table>

										</div>
									</div>

									<center>
										<button type="button" class="btn btn-primary"
											id="saveDomainSettingBtn">
											<i class="fa fa-save"></i>提交保存
										</button>
									</center>

								</form>
							</div>

							<div id="orderSetting"
								class='tab-pane<c:if test="${flag==2}"> in active </c:if>'>
								<form role="form" id="orderSettingForm" action="" method="post">
									<div class="info-box">
										<div class="info-hd">
											<span class="no-margin-top"><i class="fa fa-edit"></i>
												推广设置</span>
										</div>
										<div class="info-bd no-margin no-padding">
											<table class="table">
												<tbody>
													<tr>
														<td align="right" width="180px">会员注册新人红包&nbsp;&nbsp;
														</td>
														<td><input type="text" class="form-control"
															title="会员注册成功后的到的新人红包金额 例如：2.0 元"
															placeholder="会员注册成功后的到的新人红包金额 例如：2.0 元"
															id="CT_MEMBER_REGISTER_MONEY"
															name="CT_MEMBER_REGISTER_MONEY"
															value="${systemParam.CT_MEMBER_REGISTER_MONEY}">
														</td>
													</tr>
													<tr>
														<td align="right" width="180px">邀请注册上级奖励&nbsp;&nbsp;
														</td>
														<td><input type="text" class="form-control"
															title="邀请会员注册成功上级得到的奖励金额 例如：0.5"
															placeholder="邀请会员注册成功上级得到的奖励金额 例如：0.5"
															id="CT_RECOM_REGISTER_REWARD"
															name="CT_RECOM_REGISTER_REWARD"
															value="${systemParam.CT_RECOM_REGISTER_REWARD}">
														</td>
													</tr>
													<%-- <tr>
														<td align="right" width="180px">文章阅读奖励金额&nbsp;&nbsp;
														</td>
														<td><input type="text" class="form-control"
															title="全局默认文章阅读奖励金额 例如：0.2"
															placeholder="全局默认文章阅读奖励金额 例如：0.2"
															id="CT_ARTICLE_READ_MONEY" name="CT_ARTICLE_READ_MONEY"
															value="${systemParam.CT_ARTICLE_READ_MONEY}"></td>
													</tr>
													<tr>
														<td align="right" width="180px">
															文章分享朋友奖励金额&nbsp;&nbsp;</td>
														<td><input type="text" class="form-control"
															title="全局默认文章分享朋友奖励金额 例如：0.5"
															placeholder="全局默认文章分享朋友奖励金额 例如：0.5"
															id="CT_ARTICLE_SHARE_MONEY" name="CT_ARTICLE_SHARE_MONEY"
															value="${systemParam.CT_ARTICLE_SHARE_MONEY}"></td>
													</tr>

													<tr>
														<td align="right" width="180px">
															文章分享朋友圈奖励金额&nbsp;&nbsp;</td>
														<td><input type="text" class="form-control"
															title="全局默认文章分享朋友圈奖励金额 例如：0.5"
															placeholder="全局默认文章分享朋友圈奖励金额 例如：0.5"
															id="CT_ARTICLE_TIME_LINE_SHARE_MONEY"
															name="CT_ARTICLE_TIME_LINE_SHARE_MONEY"
															value="${systemParam.CT_ARTICLE_TIME_LINE_SHARE_MONEY}">
														</td>
													</tr> --%>


													<tr>
														<td align="right" width="180px">
															文章阅读师傅提成比例&nbsp;&nbsp;</td>
														<td><input type="text" class="form-control"
															title="全局默认文章阅读师傅提成比例 例如：0.2表示20% 请填写小数"
															placeholder="全局默认文章阅读师傅提成比例 例如：0.2表示20% 请填写小数"
															id="CT_PARENT_MONEY_PERCENT"
															name="CT_PARENT_MONEY_PERCENT"
															value="${systemParam.CT_PARENT_MONEY_PERCENT}"></td>
													</tr>
													<tr>
														<td align="right" width="180px">
															文章阅读师爷提成比例&nbsp;&nbsp;</td>
														<td><input type="text" class="form-control"
															title="全局默认文章阅读师爷提成比例 例如：0.05表示5% 请填写小数"
															placeholder="全局默认文章阅读师爷提成比例 例如：0.05表示5% 请填写小数"
															id="CT_PARENT_PARENT_MONEY_PERCENT"
															name="CT_PARENT_PARENT_MONEY_PERCENT"
															value="${systemParam.CT_PARENT_PARENT_MONEY_PERCENT}">
														</td>
													</tr>


													<tr>
														<td align="right" width="180px">首次提现师傅奖励&nbsp;&nbsp;
														</td>
														<td><input type="text" class="form-control"
															title="会员首次提现师傅奖励例如：2.0 元"
															placeholder="会员首次提现师傅奖励金额 例如：2.0 元"
															id="CT_PARENT_WITHDRAW_FIRST"
															name="CT_PARENT_WITHDRAW_FIRST"
															value="${systemParam.CT_PARENT_WITHDRAW_FIRST}">
														</td>
													</tr>
													<tr>
														<td align="right" width="180px">第二次提现师傅奖励&nbsp;&nbsp;
														</td>
														<td><input type="text" class="form-control"
															title="会员第二次提现师傅奖励例如：2.0 元"
															placeholder="会员第二次提现师傅奖励金额 例如：2.0 元"
															id="CT_PARENT_WITHDRAW_SECOND"
															name="CT_PARENT_WITHDRAW_SECOND"
															value="${systemParam.CT_PARENT_WITHDRAW_SECOND}">
														</td>
													</tr>
													<tr>
														<td align="right" width="180px">第三次提现师傅奖励&nbsp;&nbsp;
														</td>
														<td><input type="text" class="form-control"
															title="会员第三次提现师傅奖励例如：2.0 元"
															placeholder="会员第三次提现师傅奖励金额 例如：2.0 元"
															id="CT_PARENT_WITHDRAW_THIRD"
															name="CT_PARENT_WITHDRAW_THIRD"
															value="${systemParam.CT_PARENT_WITHDRAW_THIRD}">
														</td>
													</tr>
													<tr>
														<td align="right" width="180px">提现满一百元师傅奖励&nbsp;&nbsp;
														</td>
														<td><input type="text" class="form-control"
															title="会员提现满一百元师傅奖励例如：2.0 元"
															placeholder="会员提现满一百元师傅奖励金额 例如：2.0 元"
															id="CT_PARENT_WITHDRAW_FULL"
															name="CT_PARENT_WITHDRAW_FULL"
															value="${systemParam.CT_PARENT_WITHDRAW_FULL}"></td>
													</tr>
													<tr>
														<td align="right" width="180px">全局用户扣量比例&nbsp;&nbsp;
														</td>
														<td><input type="text" class="form-control"
															title="全局用户扣量比例，用户扣量比覆盖该值 例如：30.表示30% 请填写整数"
															placeholder="全局用户扣量比例，用户扣量比覆盖该值 例如：30.表示30% 请填写整数"
															id="CT_USER_DISS_PERCENT" name="CT_USER_DISS_PERCENT"
															value="${systemParam.CT_USER_DISS_PERCENT}"></td>
													</tr>

													<tr>
														<td align="right" width="180px">全局广告虚增比例&nbsp;&nbsp;
														</td>
														<td><input type="text" class="form-control"
															title="全局广告虚增比例 ，例如：30.表示30% 请填写整数"
															"  
											    placeholder="全局广告虚增比例 ，例如：30.表示30% 请填写整数"
															"  
											    id="CT_DEFAULT_SIGN_BONUS"
															name="CT_AD_SHAM_PERCENT"
															value="${systemParam.CT_AD_SHAM_PERCENT}"></td>
													</tr>
													<tr>
														<td align="right" width="180px">连续签到叠加金额&nbsp;&nbsp;
														</td>
														<td><input type="text" class="form-control"
															title="会员前台连续签到叠加金额 例如：0.01"
															placeholder="会员前台连续签到叠加金额 例如：0.01"
															id="CT_DEFAULT_SIGN_ADD_BONUS"
															name="CT_DEFAULT_SIGN_ADD_BONUS"
															value="${systemParam.CT_DEFAULT_SIGN_ADD_BONUS}">
														</td>
													</tr>
													<tr>
														<td align="right" width="180px">日推广收益上限&nbsp;&nbsp;</td>
														<td><input type="text" class="form-control"
															title="全局默认会员推广获得的收益的最大金额值 例如：100"
															placeholder="全局默认会员推广获得的收益的最大金额值 例如：100"
															id="CT_MAX_DAILY_READ_MONEY"
															name="CT_MAX_DAILY_READ_MONEY"
															value="${systemParam.CT_MAX_DAILY_READ_MONEY}"></td>
													</tr>
													<tr>
														<td align="right" width="180px">分享文章计费标准&nbsp;&nbsp;
														</td>
														<td><input type="text" class="form-control"
															title="会员分享出去的文章被阅读多少次后计算分享奖励 例如：5"
															placeholder="会员分享出去的文章被阅读多少次后计算分享奖励 例如：5"
															id="CT_USER_SHARE_READ_COUNT"
															name="CT_USER_SHARE_READ_COUNT"
															value="${systemParam.CT_USER_SHARE_READ_COUNT}">
															<span class="danger">提示：</span>会员分享出去的文章被阅读多少次后计算分享奖励
															例如：5 表示文章有效阅读5次后则给分享会员计算分享奖励！</td>
													</tr>
													<tr>
														<td align="right" width="180px">会员邀请注册文案&nbsp;&nbsp;
														</td>
														<td><textarea class="form-control" rows="3"
																title="系统前台给每个推广会员生产的带短连接的推广文案信息"
																placeholder="系统前台给每个推广会员生产的带短连接的推广文案信息"
																id="CT_USER_VISIT_CONTENT_COPY"
																name="CT_USER_VISIT_CONTENT_COPY">${systemParam.CT_USER_VISIT_CONTENT_COPY}</textarea>
														</td>
													</tr>

													<tr>
														<td align="right" width="180px">
															普通文章全局获取奖励&nbsp;&nbsp;</td>
														<td><input type="text" class="form-control"
															title="普通文章奖励（参数为0时详情获取奖励）"
															placeholder="普通文章奖励（参数为0时详情获取奖励）" id="REPEAT_MONEY"
															name="REPEAT_MONEY" value="${systemParam.REPEAT_MONEY}">
															<span class="danger">提示：</span>普通文章奖励（参数为0时详情获取奖励）!</td>
													</tr>
													<tr>
														<td align="right" width="180px">朋友圈第三方&nbsp;&nbsp;</td>
														<td><input type="text" class="form-control"
															title="朋友圈第三方" placeholder="朋友圈第三方" id="REPEAT_PYQ"
															name="REPEAT_PYQ" value="${systemParam.REPEAT_PYQ}">
															<span class="danger">提示：</span>朋友圈第三方（1第三方，0系统方法）!</td>
													</tr>
													<tr>
														<td align="right" width="180px">开启公众号登录模式&nbsp;&nbsp;</td>
														<td><label class="no-margin"> <input
																class="checkbox-slider toggle colored-success"
																id="PEPEAT_LOGIN_CODE_CK"
																<c:if test="${systemParam.PEPEAT_LOGIN_CODE==1}">checked</c:if>
																name="PEPEAT_LOGIN_CODE_CK" type="checkbox"> <span
																class="text no-margin"></span> <input type="hidden"
																id="PEPEAT_LOGIN_CODE" name="PEPEAT_LOGIN_CODE"
																class="form-control"
																value="${systemParam.PEPEAT_LOGIN_CODE}">
														</label></td>
													</tr>
													<tr>
														<td align="right" width="180px">公众号阅读跳转地址&nbsp;&nbsp;</td>
														<td><input type="text" class="form-control"
															title="公众号阅读跳转地址" placeholder="公众号阅读跳转地址"
															id="JP_AD_WXYD_URL" name="JP_AD_WXYD_URL"
															value="${systemParam.JP_AD_WXYD_URL}"> <span
															class="danger">提示：</span>公众号阅读跳转地址</td>
													</tr>
													<tr>
														<td align="right" width="180px">阅读返回跳转地址&nbsp;&nbsp;</td>
														<td><input type="text" class="form-control"
															title="分享阅读返回跳转地址" placeholder="分享阅读返回跳转地址"
															id="JP_AD_WXYD_GB_URL" name="JP_AD_WXYD_GB_URL"
															value="${systemParam.JP_AD_WXYD_GB_URL}"> <span
															class="danger">提示：</span>分享阅读返回跳转地址</td>
													</tr>
													<tr>
														<td align="right" width="180px">文章屏蔽地域：&nbsp;&nbsp;</td>
														<td><c:forEach items="${districtList}" var="dp">
																<label> <input type="checkbox"
																	id="REPEAT_DISTRICT_${dp.id}"
																	<c:if test="${fn:contains(systemParam.REPEAT_DISTRICT,dp.id)}">checked</c:if>
																	name="REPEAT_district" value="${dp.id}"> <span
																	class="text">${dp.name}</span>
																</label>
															</c:forEach><input type="hidden" id="REPEAT_DISTRICT"
															name="REPEAT_DISTRICT" value=""></td>
													</tr>
												</tbody>
											</table>
										</div>
									</div>

									<div class="info-box">
										<div class="info-hd">
											<span class="no-margin-top"><i
												class="fa fa-envelope-o"></i> 版本设置</span>
										</div>
										<div class="info-bd no-margin no-padding">
											<table class="table">
												<tbody>
													<tr>
														<td align="right" width="180px">安卓版本设置&nbsp;&nbsp;</td>
														<td><input type="text" class="form-control"
															title="安卓版本设置" placeholder="安卓版本设置"
															id="REPEAT_ANDROID_VERSION" name="REPEAT_ANDROID_VERSION"
															value="${systemParam.REPEAT_ANDROID_VERSION}"><span
															class="danger">提示：</span>版本号和下载地址中间以,分隔</td>
													</tr>
													<tr>
														<td align="right" width="180px">IOS版本设置&nbsp;&nbsp;</td>
														<td><input type="text" class="form-control"
															title="IOS版本设置" placeholder="IOS版本设置"
															id="REPEAT_IOS_VERSION" name="REPEAT_IOS_VERSION"
															value="${systemParam.REPEAT_IOS_VERSION}"> <span
															class="danger">提示：</span>版本号和下载地址中间以,分隔</td>
													</tr>
												</tbody>
											</table>
										</div>
									</div>

									<div class="info-box">
										<div class="info-hd">
											<span class="no-margin-top"><i
												class="fa fa-envelope-o"></i> 邮件设置</span>
										</div>
										<div class="info-bd no-margin no-padding">
											<table class="table">
												<tbody>
													<tr>
														<td align="right" width="180px">发送预警邮件&nbsp;&nbsp;</td>
														<td><label class="no-margin"> <input
																class="checkbox-slider toggle colored-success"
																id="CT_SEND_MAIL_CK"
																<c:if test="${systemParam.CT_SEND_MAIL==1}">checked</c:if>
																name="CT_SEND_MAIL_CK" type="checkbox"> <span
																class="text no-margin"></span> <input type="hidden"
																id="CT_SEND_MAIL" name="CT_SEND_MAIL"
																class="form-control" value="${systemParam.CT_SEND_MAIL}">
														</label></td>
													</tr>
													<tr>
														<td align="right" width="180px">邮件服务器&nbsp;&nbsp;</td>
														<td><select name="CT_MAIL_SMTP" id="CT_MAIL_SMTP"
															style="width: 150px">
																<option value="smtp.163.com">163邮箱</option>
																<option value="smtp.qq.com">QQ邮箱</option>
																<option value="smtp.126.com">126邮箱</option>
																<option value="smtp.sina.com">新浪邮箱</option>
														</select></td>
													</tr>

													<tr>
														<td align="right" width="180px">预警邮件接收人&nbsp;&nbsp;</td>
														<td><input type="text" class="form-control"
															placeholder="邮件接收人账户 多个账户使用逗号分割" id="CT_MAIL_REC_ADDR"
															name="CT_MAIL_REC_ADDR"
															value="${systemParam.CT_MAIL_REC_ADDR}"></td>
													</tr>
													<tr>
														<td align="right" width="180px">登录账号&nbsp;&nbsp;</td>
														<td><input type="text" class="form-control"
															placeholder="邮箱登录账号" id="CT_MAIL_ACCOUNT"
															name="CT_MAIL_ACCOUNT"
															value="${systemParam.CT_MAIL_ACCOUNT}"></td>
													</tr>
													<tr>
														<td align="right" width="180px">账号密码&nbsp;&nbsp;</td>
														<td><input type="password" class="form-control"
															placeholder="邮箱登录密码" id="CT_MAIL_PASSOWRD"
															name="CT_MAIL_PASSOWRD"
															value="${systemParam.CT_MAIL_PASSOWRD}"></td>
													</tr>
												</tbody>
											</table>
										</div>
									</div>

									<div class="info-box">
										<div class="info-hd">
											<span class="no-margin-top"><i class="fa fa-comments"></i>
												短信设置</span>
										</div>
										<div class="info-bd no-margin no-padding">
											<table class="table">
												<tbody>
													<tr>
														<td align="right" width="180px">短信平台&nbsp;&nbsp;</td>
														<td><a href="http://www.ihuyi.com" target="_blank">上海互亿</a>（您还没有账号？点击<a
															href="http://sms.ihuyi.com/?ab4zc" target="_blank"><u>这里</u></a>立即注册！请选择<span
															class="danger"> 短信验证码平台 </span>注册 后联系客服充值，配置短信模板）</td>
													</tr>
													<tr>
														<td align="right" width="180px">语音验证码注册&nbsp;&nbsp;</td>
														<td><label class="no-margin"> <input
																class="checkbox-slider toggle colored-success"
																id="MOBILE_REG_VOICE_CHECK_CK"
																name="MOBILE_REG_VOICE_CHECK_CK"
																<c:if test="${systemParam.MOBILE_REG_VOICE_CHECK==1}">checked</c:if>
																type="checkbox" value=""> <span
																class="text no-margin"></span> <input type="hidden"
																id="MOBILE_REG_VOICE_CHECK"
																name="MOBILE_REG_VOICE_CHECK" class="form-control"
																value="${systemParam.MOBILE_REG_VOICE_CHECK}">
														</label> <span class="danger">提示：</span>选择开启语音验证码注册后，所有新会员注册和找回密码都必须使用语音验证码来进行验证</span>)！
														</td>
													</tr>
													<tr>
														<td align="right" width="180px">语音验证码提现&nbsp;&nbsp;</td>
														<td><label class="no-margin"> <input
																class="checkbox-slider toggle colored-success"
																id="MOBILE_PAY_VOICE_CHECK_CK"
																name="MOBILE_PAY_VOICE_CHECK_CK"
																<c:if test="${systemParam.MOBILE_PAY_VOICE_CHECK==1}">checked</c:if>
																type="checkbox" value=""> <span
																class="text no-margin"></span> <input type="hidden"
																id="MOBILE_PAY_VOICE_CHECK"
																name="MOBILE_PAY_VOICE_CHECK" class="form-control"
																value="${systemParam.MOBILE_PAY_VOICE_CHECK}">
														</label> <span class="danger">提示：</span>选择开启语音验证码提现后，所有新会员发起提现时都必须使用语音验证码来进行验证才能提交</span>)！
														</td>
													</tr>
													<tr>
														<td align="right" width="180px">
															语音短信APPID&nbsp;&nbsp;</td>
														<td><input type="text" class="form-control"
															placeholder="语音短信验证码平台APPID" id="CT_VSMS_ACCOUNT"
															name="CT_VSMS_ACCOUNT"
															value="${systemParam.CT_VSMS_ACCOUNT}"></td>
													</tr>
													<tr>
														<td align="right" width="180px">
															语音短信APPKEY&nbsp;&nbsp;</td>
														<td><input type="password" class="form-control"
															placeholder="语音短信验证码平台APPKEY" id="CT_VSMS_PASSOWRD"
															name="CT_VSMS_PASSOWRD"
															value="${systemParam.CT_VSMS_PASSOWRD}"></td>
													</tr>

													<tr>
														<td align="right" width="180px">发送预警短信&nbsp;&nbsp;</td>
														<td><label class="no-margin"> <input
																class="checkbox-slider toggle colored-success"
																id="CT_SEND_SMS_CK" name="CT_SEND_SMS_CK"
																<c:if test="${systemParam.CT_SEND_SMS==1}">checked</c:if>
																type="checkbox" value=""> <span
																class="text no-margin"></span> <input type="hidden"
																id="CT_SEND_SMS" name="CT_SEND_SMS" class="form-control"
																value="${systemParam.CT_SEND_SMS}">
														</label></td>
													</tr>
													<tr>
														<td align="right" width="180px">短信APPID&nbsp;&nbsp;</td>
														<td><input type="text" class="form-control"
															placeholder="短信验证码平台APPID" id="CT_SMS_ACCOUNT"
															name="CT_SMS_ACCOUNT"
															value="${systemParam.CT_SMS_ACCOUNT}"></td>
													</tr>
													<tr>
														<td align="right" width="180px">短信APPKEY&nbsp;&nbsp;
														</td>
														<td><input type="password" class="form-control"
															placeholder="短信验证码平台APPKEY" id="CT_SMS_PASSOWRD"
															name="CT_SMS_PASSOWRD"
															value="${systemParam.CT_SMS_PASSOWRD}"></td>
													</tr>
													<tr>
														<td align="right" width="180px">预警短信接收人&nbsp;&nbsp;</td>
														<td><input type="text" class="form-control"
															placeholder="短信接收人手机 多个账户使用逗号分割" id="CT_SMS_REC_PHONE"
															name="CT_SMS_REC_PHONE"
															value="${systemParam.CT_SMS_REC_PHONE}"></td>
													</tr>
													<tr>
														<td align="right" width="180px">预警短信模板&nbsp;&nbsp;</td>
														<td><input type="text" id="CT_SMS_TEMPLATE"
															placeholder="域名被封发送预警短信模板，请前往短信平台设置后回填到此"
															name="CT_SMS_TEMPLATE" class="form-control"
															value="${systemParam.CT_SMS_TEMPLATE}"></td>
													</tr>
													<tr>
														<td align="right" width="180px">模板设置提示&nbsp;&nbsp;</td>
														<td>在此页面设置好短信模板（注意不要修改带有<span class="red">$符号</span>的变量的字符串）在<a
															href="http://sms.ihuyi.com/?ab4zc" target="_blank">短信平台</a>设置模板的时候将带$符号的字符串替换为<span
															class="red">【变量】</span>即可
														</td>
													</tr>

												</tbody>
											</table>
										</div>
									</div>

									<center>
										<button type="button" class="btn btn-primary"
											id="saveOrderSettingBtn">
											<i class="fa fa-save"></i>提交保存
										</button>
									</center>
								</form>
							</div>

							<div id="otherSetting"
								class='tab-pane<c:if test="${flag==3}"> in active </c:if>'>
								<form role="form" id="otherSettingForm" action="" method="post">

									<div class="info-box">
										<div class="info-hd">
											<span class="no-margin-top"><i class="fa fa-edit"></i>
												APP图片设置</span>
										</div>
										<div class="info-bd no-margin no-padding">
											<table class="table">
												<tbody>
													<tr>
														<td align="right">APP水印图片：</td>
														<td>
															<div id="sm_logo1">
																<img id="appLogo_pre" class="appLogoImg"
																	onerror="javascript:this.src='${basePath}static/img/no_pic.png';"
																	src="${systemParam.REPEAT_APP_WATERMARK}" /> <input
																	type="hidden" class="form-control"
																	id="REPEAT_APP_WATERMARK" name="REPEAT_APP_WATERMARK"
																	value="${systemParam.REPEAT_APP_WATERMARK}">
															</div> <span class="danger">提示：</span>logo图片(尺寸：159px*64px)，图片类型只能是jpg,
															gif, png, jpeg, tmp 的一种注意区分后缀大小写！
														</td>
													</tr>
													<tr>
														<td align="right" width="150psx">上传LOGO&nbsp;&nbsp;</td>
														<td><input id="appLogo" name="appLogo" type="file"
															onchange="setPreview(this,'appLogo_pre','sm_logo1',159,64);"
															data-show-preview="false" class="file-loading">
															<div id="errorBlock" class="help-block"></div></td>
													</tr>
												</tbody>
											</table>
										</div>
									</div>

									<div class="info-box">
										<div class="info-hd">
											<span class="no-margin-top"><i class="fa fa-edit"></i>
												系统前台图片设置</span>
										</div>
										<div class="info-bd no-margin no-padding">
											<table class="table">
												<tbody>
													<tr>
														<td align="right">网站LOGO：</td>
														<td>
															<div id="sm_logo">
																<img id="siteLogo_pre" class="siteLogoImg"
																	onerror="javascript:this.src='${basePath}static/img/no_pic.png';"
																	src="${systemParam.SITE_LOGO}" /> <input type="hidden"
																	class="form-control" id="SITE_LOGO" name="SITE_LOGO"
																	value="${systemParam.SITE_LOGO}">
															</div> <span class="danger">提示：</span>logo图片(尺寸：159px*64px)，图片类型只能是jpg,
															gif, png, jpeg, tmp 的一种注意区分后缀大小写！
														</td>
													</tr>
													<tr>
														<td align="right" width="150psx">上传LOGO&nbsp;&nbsp;</td>
														<td><input id="siteLogo" name="siteLogo" type="file"
															onchange="setPreview(this,'siteLogo_pre','sm_logo',159,64);"
															data-show-preview="false" class="file-loading">
															<div id="errorBlock" class="help-block"></div></td>
													</tr>
													<tr style="border-top: 1px dashed #ccc">
														<td align="right">网站ICO图标：</td>
														<td>
															<div id="sm_ico">
																<img id="siteIco_pre" class="siteIcoImg"
																	onerror="javascript:this.src='${basePath}static/img/no_pic.png';"
																	src="${systemParam.SITE_ICO}" /> <input type="hidden"
																	class="form-control" id="SITE_ICO" name="SITE_ICO"
																	value="${systemParam.SITE_ICO}">
															</div> <span class="danger">提示：</span>ico图片(尺寸：16px*16px 可以<a
															href="http://www.ico.la/" target="_blank">在线生成</a>)，图片类型只能是ico注意区分后缀大小写！
														</td>
													</tr>
													<tr>
														<td align="right" width="150psx">上传ICO图标&nbsp;&nbsp;
														</td>
														<td><input id="siteIco" name="siteIco" type="file"
															onchange="setPreview(this,'siteIco_pre','sm_ico',64,64);"
															data-show-preview="false" class="file-loading">
															<div id="errorBlock" class="help-block"></div></td>
													</tr>
												</tbody>
											</table>
										</div>
									</div>

									<div class="info-box">
										<div class="info-hd">
											<span class="no-margin-top"><i class="fa fa-edit"></i>
												系统后台图片设置</span>
										</div>
										<div class="info-bd no-margin no-padding">
											<table class="table">
												<tbody>
													<tr>
														<td align="right">网站LOGO：</td>
														<td>
															<div id="sm_logo2">
																<img id="backstageLogo_pre" class="siteBLogoImg"
																	onerror="javascript:this.src='${basePath}static/img/no_pic.png';"
																	src="${systemParam.BACK_SITE_LOGO}" /> <input
																	type="hidden" class="form-control" id="BACK_SITE_LOGO"
																	name="BACK_SITE_LOGO"
																	value="${systemParam.BACK_SITE_LOGO}">
															</div> <span class="danger">提示：</span>logo图片(尺寸：239px*52px)，图片类型只能是jpg,
															gif, png, jpeg, tmp 的一种注意区分后缀大小写！
														</td>
													</tr>
													<tr>
														<td align="right" width="150psx">上传LOGO&nbsp;&nbsp;</td>
														<td><input id="backstageLogo" name="backstageLogo"
															type="file"
															onchange="setPreview(this,'backstageLogo_pre','sm_logo2',239,52);"
															data-show-preview="false" class="file-loading">
															<div id="errorBlock" class="help-block"></div></td>
													</tr>
													<tr style="border-top: 1px dashed #ccc">
														<td align="right">网站ICO图标：</td>
														<td>
															<div id="sm_ico2">
																<img id="backstageIco_pre" class="siteIcoImg"
																	onerror="javascript:this.src='${basePath}static/img/no_pic.png';"
																	src="${systemParam.BACK_SITE_ICO}" /> <input
																	type="hidden" class="form-control" id="BACK_SITE_ICO"
																	name="BACK_SITE_ICO"
																	value="${systemParam.BACK_SITE_ICO}">
															</div> <span class="danger">提示：</span>ico图片(尺寸：16px*16px 可以<a
															href="http://www.ico.la/" target="_blank">在线生成</a>)，图片类型只能是ico注意区分后缀大小写！
														</td>
													</tr>
													<tr>
														<td align="right" width="150psx">上传ICO图标&nbsp;&nbsp;
														</td>
														<td><input id="backstageIco" name="backstageIco"
															type="file"
															onchange="setPreview(this,'backstageIco_pre','sm_ico2',32,32);"
															data-show-preview="false" class="file-loading">
															<div id="errorBlock" class="help-block"></div></td>
													</tr>
												</tbody>
											</table>
										</div>
									</div>

									<center>
										<button type="button" class="btn btn-primary"
											id="saveOtherSettingBtn">
											<i class="fa fa-save"></i>提交保存
										</button>
									</center>

								</form>
							</div>


							<div id="serverSetting"
								class='tab-pane<c:if test="${flag==4}"> in active </c:if>'>
								<form role="form" id="serverSettingForm" action="" method="post">
									<div class="info-box">
										<div class="info-hd">
											<span class="no-margin-top"><i class="fa fa-edit"></i>
												客服配置</span>
										</div>
										<div class="info-bd no-margin no-padding">
											<table class="table">
												<tbody>
													<tr>
														<td align="right" width="180px">商务合作&nbsp;&nbsp;</td>
														<td><input type="text" class="form-control"
															placeholder="网站前台显示的客服电话" id="BUSINESS_QQ"
															name="BUSINESS_QQ" value="${systemParam.BUSINESS_QQ}">
														</td>
													</tr>
													<tr>
														<td align="right" width="180px">客服微信&nbsp;&nbsp;</td>
														<td><input type="text" class="form-control"
															placeholder="前台网站显示客服微信号码" id="SUPPLY_WX"
															name="SUPPLY_WX" value="${systemParam.SUPPLY_WX}">
														</td>
													</tr>
													<tr>
														<td align="right" width="180px">客服QQ-1&nbsp;&nbsp;</td>
														<td><input type="text" class="form-control"
															placeholder="前台网站显示客服QQ号码" id="SUPPLY_QQ"
															name="SUPPLY_QQ" value="${systemParam.SUPPLY_QQ}">
														</td>
													</tr>
													<tr>
														<td align="right" width="180px">客服QQ-2&nbsp;&nbsp;</td>
														<td><input type="text" class="form-control"
															placeholder="前台网站显示客服QQ号码" id="SUPPLY_QQ_2"
															name="SUPPLY_QQ_2" value="${systemParam.SUPPLY_QQ_2}">
														</td>
													</tr>
													<tr>
														<td align="right" width="180px">客服QQ-3&nbsp;&nbsp;</td>
														<td><input type="text" class="form-control"
															placeholder="前台网站显示客服QQ号码" id="SUPPLY_QQ_3"
															name="SUPPLY_QQ_3" value="${systemParam.SUPPLY_QQ_3}">
														</td>
													</tr>
													<tr>
														<td align="right" width="180px">QQ交流群-1&nbsp;&nbsp;</td>
														<td><input type="text" class="form-control"
															placeholder="前台网站显示的客服QQ群" id="SUPPLY_MQQ"
															name="SUPPLY_MQQ" value="${systemParam.SUPPLY_MQQ}">
														</td>
													</tr>
													<tr>
														<td align="right" width="180px">QQ交流群-2&nbsp;&nbsp;</td>
														<td><input type="text" class="form-control"
															placeholder="前台网站显示的客服QQ群" id="SUPPLY_MQQ_2"
															name="SUPPLY_MQQ_2" value="${systemParam.SUPPLY_MQQ_2}">
														</td>
													</tr>
													<tr>
														<td align="right" width="180px">QQ交流群-3&nbsp;&nbsp;</td>
														<td><input type="text" class="form-control"
															placeholder="前台网站显示的客服QQ群" id="SUPPLY_MQQ_3"
															name="SUPPLY_MQQ_3" value="${systemParam.SUPPLY_MQQ_3}">
														</td>
													</tr>
												</tbody>
											</table>
										</div>
									</div>

									<center>
										<button type="button" class="btn btn-primary"
											id="saveServerSettingBtn">
											<i class="fa fa-save"></i>提交保存
										</button>
									</center>

								</form>
							</div>

						</div>
					</div>
				</div>

			</div>
		</div>
	</div>
	<!-- /Page Body -->
	<c:if test="${ADMIN_VERSION!='V5'}">
		</div>
		<!-- /Page Content -->
		</div>
		<!-- /Page Container -->
		<!-- Main Container -->
		</div>
	</c:if>
	<%@ include file="../../../common/com_js.jsp"%>
	<script src="${basePath}static/js/bfileinput/js/fileinput.js"></script>
	<script src="${basePath}static/js/bfileinput/js/locales/zh.js"></script>
	<script src="${basePath}static/js/admin/param/param.js"></script>
	<script>
		$("#ADMIN_VERSION").val("${systemParam.ADMIN_VERSION}");
		$("#SITE_COUNT_METHOD").val("${systemParam.SITE_COUNT_METHOD}");
		$("#DOMAIN_FOR_GENERIC_PARSE_TYPE").val(
				"${systemParam.DOMAIN_FOR_GENERIC_PARSE_TYPE}");
		$("#DOMAIN_FOR_GENERIC_PARSE_LENGTH").val(
				"${systemParam.DOMAIN_FOR_GENERIC_PARSE_LENGTH}");
		$(document)
				.on(
						'ready',
						function() {
							$("#appLogo")
									.fileinput(
											{
												language : "zh",
												uploadUrl : ccnetpath
														+ "/file/upload/single?fk=appLogo",
												deleteURl : ccnetpath
														+ "/file/trash",
												browseClass : "btn btn-success",
												browseLabel : "选择",
												browseIcon : "<i class=\"glyphicon glyphicon-picture\"></i> ",
												removeClass : "btn btn-danger",
												removeLabel : "清除",
												removeIcon : "<i class=\"glyphicon glyphicon-trash\"></i> ",
												uploadClass : "btn btn-info",
												uploadLabel : "上传",
												dropZoneTitle : "请选择或者拖拽图片到这里&hellip;",
												uploadIcon : "<i class=\"glyphicon glyphicon-upload\"></i> ",
												allowedFileExtensions : [
														"jpg", "JPG", "gif",
														"png", "jpeg", "tmp" ],
												slugCallback : function(
														filename) {
													return filename;
												}
											});
							$("#appLogo").on(
									"fileuploaded",
									function(event, data, previewId, index) {
										var sdata = data.response.url;
										console.log(sdata);
										if (!isNull(sdata)) {
											$("#REPEAT_APP_WATERMARK").val(
													sdata);
											$("#appLogo_pre")
													.attr("src", sdata);
										}
									});
							$("#siteLogo")
									.fileinput(
											{
												language : "zh",
												uploadUrl : ccnetpath
														+ "/file/upload/single?fk=siteLogo",
												deleteURl : ccnetpath
														+ "/file/trash",
												browseClass : "btn btn-success",
												browseLabel : "选择",
												browseIcon : "<i class=\"glyphicon glyphicon-picture\"></i> ",
												removeClass : "btn btn-danger",
												removeLabel : "清除",
												removeIcon : "<i class=\"glyphicon glyphicon-trash\"></i> ",
												uploadClass : "btn btn-info",
												uploadLabel : "上传",
												dropZoneTitle : "请选择或者拖拽图片到这里&hellip;",
												uploadIcon : "<i class=\"glyphicon glyphicon-upload\"></i> ",
												allowedFileExtensions : [
														"jpg", "JPG", "gif",
														"png", "jpeg", "tmp" ],
												slugCallback : function(
														filename) {
													return filename;
												}
											});

							$("#siteLogo").on(
									"fileuploaded",
									function(event, data, previewId, index) {
										var sdata = data.response.url;
										if (!isNull(sdata)) {
											$("#SITE_LOGO").val(sdata);
											$("#siteLogo_pre").attr("src",
													sdata);
										}
									});

							$("#siteIco")
									.fileinput(
											{
												language : "zh",
												previewFileType : "image",
												maxFileCount : 1,
												uploadUrl : ccnetpath
														+ "/file/upload/single?fk=siteIco",
												deleteURl : ccnetpath
														+ "/file/trash",
												browseClass : "btn btn-success",
												browseLabel : "选择",
												browseIcon : "<i class=\"glyphicon glyphicon-picture\"></i> ",
												removeClass : "btn btn-danger",
												removeLabel : "清除",
												removeIcon : "<i class=\"glyphicon glyphicon-trash\"></i> ",
												uploadClass : "btn btn-info",
												uploadLabel : "上传",
												dropZoneTitle : "请选择或者拖拽图片到这里&hellip;",
												uploadIcon : "<i class=\"glyphicon glyphicon-upload\"></i> ",
												allowedFileExtensions : [
														"ico", "ICO" ],
												slugCallback : function(
														filename) {
													return filename;
												}
											});

							$("#siteIco").on(
									"fileuploaded",
									function(event, data, previewId, index) {
										var sdata = data.response.url;
										if (!isNull(sdata)) {
											$("#SITE_ICO").val(sdata);
											$("#siteIco_pre")
													.attr("src", sdata);
										}
									});

							$("#backstageLogo")
									.fileinput(
											{
												language : "zh",
												uploadUrl : ccnetpath
														+ "/file/upload/single?fk=backstageLogo",
												deleteURl : ccnetpath
														+ "/file/trash",
												browseClass : "btn btn-success",
												browseLabel : "选择",
												browseIcon : "<i class=\"glyphicon glyphicon-picture\"></i> ",
												removeClass : "btn btn-danger",
												removeLabel : "清除",
												removeIcon : "<i class=\"glyphicon glyphicon-trash\"></i> ",
												uploadClass : "btn btn-info",
												uploadLabel : "上传",
												dropZoneTitle : "请选择或者拖拽图片到这里&hellip;",
												uploadIcon : "<i class=\"glyphicon glyphicon-upload\"></i> ",
												allowedFileExtensions : [
														"jpg", "JPG", "gif",
														"png", "PNG", "jpeg",
														"tmp" ],
												slugCallback : function(
														filename) {
													return filename;
												}
											});

							$("#backstageLogo").on(
									"fileuploaded",
									function(event, data, previewId, index) {
										var sdata = data.response.url;
										if (!isNull(sdata)) {
											$("#BACK_SITE_LOGO").val(sdata);
											$("#backstageLogo_pre").attr("src",
													sdata);
										}
									});

							$("#backstageIco")
									.fileinput(
											{
												language : "zh",
												previewFileType : "image",
												maxFileCount : 1,
												uploadUrl : ccnetpath
														+ "/file/upload/single?fk=backstageIco",
												deleteURl : ccnetpath
														+ "/file/trash",
												browseClass : "btn btn-success",
												browseLabel : "选择",
												browseIcon : "<i class=\"glyphicon glyphicon-picture\"></i> ",
												removeClass : "btn btn-danger",
												removeLabel : "清除",
												removeIcon : "<i class=\"glyphicon glyphicon-trash\"></i> ",
												uploadClass : "btn btn-info",
												uploadLabel : "上传",
												dropZoneTitle : "请选择或者拖拽图片到这里&hellip;",
												uploadIcon : "<i class=\"glyphicon glyphicon-upload\"></i> ",
												allowedFileExtensions : [
														"ico", "ICO" ],
												slugCallback : function(
														filename) {
													return filename;
												}
											});

							$("#backstageIco").on(
									"fileuploaded",
									function(event, data, previewId, index) {
										var sdata = data.response.url;
										if (!isNull(sdata)) {
											$("#BACK_SITE_ICO").val(sdata);
											$("#backstageIco_pre").attr("src",
													sdata);
										}
									});
						});
	</script>
</body>
<!--  /Body -->
</html>
