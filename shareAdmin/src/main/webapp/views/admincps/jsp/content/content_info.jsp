<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8" />
<title>${CT_MENU_NAV}</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ include file="../../../common/includes.jsp"%>
<%@ include file="../../../common/com_css.jsp"%>
<link href="${basePath}static/js/bfileinput/css/fileinput.css"
	rel="stylesheet" type="text/css" />
<link href="${basePath}static/css/multiselect.css" rel="stylesheet"
	type="text/css" />
<link href="${basePath}static/css/product.css" rel="stylesheet"
	type="text/css" />
<style type="text/css">
.info-box {
	font-size: 14px;
	margin-bottom: 10px;
	border: 1px solid #fafafa;
}

.info-box .info-hd {
	line-height: 35px;
	height: 35px;
	font-size: 14px;
	padding: 0 14px;
	background: #f5f5f5;
	border-bottom: 0px solid #e7e7eb;
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

.cimg {
	margin: 0;
	display: block;
	width: 100%;
	height: auto;
}

.table>tbody>tr>td {
	border-top: 0px solid #ddd !important;
	padding-right: 0px;
	margin-right: 0px;
}
</style>
</head>
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
	</c:if>
	<!-- Page Body -->
	<div class="page-body">
		<div class="swidget">
			<div class="widget-body no-padding">
				<form role="form" id="contentForm" action="" method="post">
					<div class="info-box">
						<div class="info-hd">
							<span class="no-margin-top"><i class="fa fa-gift"></i>
								添加文章</span>
						</div>
						<div class="info-bd no-margin no-padding">
							<table class="table table-striped" id="simpledatatable">
								<tbody>
									<tr>
										<td align="right" style="width: 150px;">文章类型:</td>
										<td><select name="contentType" id="contentType"
											placeholder="上级栏目" style="width: 100%;"
											onchange="onContentType()">
												<option value="">选择文章类型</option>
												<c:forEach items="${typeMap}" var="dp">
													<option value="${dp.key}"
														<c:if test="${dp.key==contentInfo.contentType}">selected="selected"</c:if>>${dp.value}</option>
												</c:forEach>
										</select></td>
									</tr>
									<tr>
										<td align="right"><label>所属栏目:</label></td>
										<td><select name="columnId" id="columnId"
											placeholder="所属栏目" style="width: 100%;">
												<option value="">请选择</option>
												<%-- <c:forEach items="${columnList}" var="dp">
													<option value="${dp.columnId}"
														<c:if test="${dp.columnId==contentInfo.columnId}">selected="selected"</c:if>>${dp.columnName}</option>
												</c:forEach> --%>
										</select></td>
									</tr>
									<tr>
										<td align="right"><label>文章标题:</label></td>
										<td><input id="contentTitle" class="form-control"
											placeholder="请输入文章主标题" type="text" name="contentTitle"
											value="${contentInfo.contentTitle}" style="width: 100%;" /></td>
									</tr>
									<tr>
										<td align="right"><label>文章摘要:</label></td>
										<td><input id="contentSbTitle" class="form-control"
											placeholder="请输入文章副标题" type="text" name="contentSbTitle"
											value="${contentInfo.contentSbTitle}" style="width: 100%;" /></td>
									</tr>
									<tr>
										<td align="right"><label>是否置顶高价文:</label></td>
										<td><select name="homeToped" id="homeToped"
											onchange="onhomeToped()" placeholder="是否置顶"
											style="width: 100%;">
												<c:forEach items="${weightMap}" var="dp">
													<option value="${dp.key}"
														<c:if test="${dp.key==contentInfo.homeToped}">selected="selected"</c:if>>${dp.value}</option>
												</c:forEach>
										</select></td>
									</tr>
									<tr style="display: none;" id="highPriceAdst">
										<td align="right"><label>高价文对应广告:</label></td>
										<td><select name="highPriceAd" id="highPriceAd"
											onchange="onHighPriceAdst()" placeholder="高价问广告"
											style="width: 100%;">
												<c:forEach items="${adList}" var="dp">
													<option value="${dp.adId}"
														<c:if test="${dp.adId==contentInfo.highPriceAd}">selected="selected"</c:if>>${dp.adTitle}</option>
												</c:forEach>
										</select></td>
									</tr>
									<tr>
										<td align="right"><label>审核状态:</label></td>
										<td><select name="checkState" id="checkState"
											placeholder="审核状态" style="width: 100%;">
												<c:forEach items="${cstateMap}" var="dp">
													<option value="${dp.state}"
														<c:if test="${dp.state==contentInfo.checkState}">selected="selected"</c:if>>${dp.name}</option>
												</c:forEach>
										</select></td>
									</tr>
									<tr>
										<td align="right"><label>排序编号:</label></td>
										<td><input id="orderNo" class="form-control"
											placeholder="请输入排序编号" type="number" name="orderNo"
											value="${contentInfo.orderNo}" style="width: 100%;" /></td>
									</tr>
									<tr>
										<td align="right"><label>公众号名称:</label></td>
										<td><input id="weixinName" class="form-control"
											placeholder="请输入文章展示页面顶部显示的公众号名称" type="text"
											name="weixinName" value="${contentInfo.weixinName}"
											style="width: 100%;" /></td>
									</tr>
									<tr>
										<td align="right"><label>公众号地址:</label></td>
										<td><input id="weixinLink" class="form-control"
											placeholder="请输入文章展示页面顶部显示的公众号地址" type="text"
											name="weixinLink" value="${contentInfo.weixinLink}"
											style="width: 100%;" /></td>
									</tr>
									<tr>
										<td align="right"><label>推广地址:</label></td>
										<td><input id="articleUrl" class="form-control"
											placeholder="请输入小说/NK/裂变/推广平台获取的推广地址，可以直接连接的地址！" type="text"
											name="articleUrl" value="${contentInfo.articleUrl}"
											style="width: 100%;" /></td>
									</tr>
									<tr>
										<td align="right"><label>广告采集:</label></td>
										<td><select name="urlGather" id="urlGather"
											placeholder="广告采集" style="width: 100%;">

												<c:forEach items="${stateMap}" var="dp">
													<option value="${dp.key}"
														<c:if test="${dp.key==contentInfo.urlGather}">selected="selected"</c:if>>${dp.value}</option>
												</c:forEach>
										</select></td>
									</tr>
									<tr>
										<td align="right"><label>阅读数(虚):</label></td>
										<td><input id="visualReadNum" class="form-control"
											placeholder="请输入文章展示虚拟阅读量" type="number" name="visualReadNum"
											value="${contentInfo.visualReadNum}" style="width: 100%;" /></td>
									</tr>
									<tr>
										<td align="right"><label>点赞数(虚):</label></td>
										<td><input id="clickNum" class="form-control"
											placeholder="请输入文章展示虚拟点赞量" type="number" name="clickNum"
											value="${contentInfo.clickNum}" style="width: 100%;" /></td>
									</tr>
									<tr>
										<td align="right"><label>浏览奖励(元):</label></td>
										<td><input id="readAward" class="form-control"
											placeholder="请输入文章阅读奖励" type="number" name="readAward"
											value="${contentInfo.readAward}" style="width: 100%;" /></td>
									</tr>
									<tr>
										<td align="right"><label>好友分享奖励(元):</label></td>
										<td><input id="friendShareAward" class="form-control"
											placeholder="请输入文章分享奖励" type="number" name="friendShareAward"
											value="${contentInfo.friendShareAward}" style="width: 100%;" /></td>
									</tr>
									<tr>
										<td align="right"><label>朋友圈分享奖励(元):</label></td>
										<td><input id="timelineShareAward" class="form-control"
											placeholder="请输入文章分享奖励" type="number"
											name="timelineShareAward"
											value="${contentInfo.timelineShareAward}"
											style="width: 100%;" /></td>
									</tr>
									<tr>
										<td align="right">上传图片：</td>
										<td>
											<div style="width: 100%;">
												<input id="goodExtPic" name="goodExtPic" type="file"
													multiple class="file-loading">
												<div id="errorBlock" class="help-block"></div>
											</div>
										</td>
									</tr>
									<tr>
										<td align="right">图片管理：</td>
										<td>
											<div id="goodPicSee" class="fileDropAone">
												<c:if test="${!empty contentInfo.picList}">
													<c:forEach var="pc" items="${contentInfo.picList}"
														varStatus="px">
														<label id="${pc.picLableId}"> <img
															class="gpSeeImg" src="${pc.contentPic}"
															data-src="${pc.contentPic}" />
															<div class="fileActions">
																<div class="file-footer-buttons">
																	<button type="button"
																		class="kv-file-remove btn btn-xs btn-default"
																		title="删除"
																		onclick="trashContentPic('${contentInfo.contentId}','${pc.contentPic}')">
																		<i class="glyphicon glyphicon-trash text-danger"></i>
																	</button>
																	<button type="button"
																		class="kv-file-zoom btn btn-xs btn-default" title="查看"
																		onclick="showContentPic('${pc.contentPic}')">
																		<i class="glyphicon glyphicon-zoom-in"></i>
																	</button>
																</div>
															</div>
														</label>
													</c:forEach>
												</c:if>
												<c:if test="${empty contentInfo.picList}">
													<i class="fa fa-user"></i> 提示信息：<span class="red"
														id="empty_tips">该产品还未上传任何图片，请先上传产品图片!</span>
												</c:if>
											</div> <input type="hidden" class="form-control" id="contentPics"
											name="contentPics" value="${contentInfo.contentPics}">
										</td>
									</tr>
									<tr>
										<td align="right"><label>外网图片链接: </label></td>
										<td><input id="contentPicLink" class="form-control"
											placeholder="外链图片地址可以是其他网站图片地址，也可以自己将图片上传到oss或者新浪微博图片服务器生成的地址！"
											type="text" name="contentPicLink"
											value="${contentInfo.contentPicLink}" style="width: 100%;" /></td>
									</tr>
									<tr>
										<td align="right"><label>视频VID: </label></td>
										<td><input id="videoLink" class="form-control"
											placeholder="如果是视频文章请输入腾讯视频VID" type="text" name="videoLink"
											value="${contentInfo.videoLink}" style="width: 100%;" /></td>
									</tr>
									<tr>
										<td align="right"><label>抓取图片:</label></td>
										<td><select name="gatherPic" id="gatherPic"
											placeholder="抓取图片" style="width: 100%;">
												<c:forEach items="${stateMap}" var="dp">
													<option value="${dp.key}"
														<c:if test="${dp.key==contentInfo.gatherPic}">selected="selected"</c:if>>${dp.value}</option>
												</c:forEach>
										</select></td>
									</tr>
									<tr>
										<td width="100px;" align="right"><label>文章内容:</label></td>
										<td><script id="content" name="content" type="text/plain"
												style="width: 100%; height: 400px;">${contentInfo.contentText}</script>
										</td>
									</tr>
									<tr>
										<td align="right" colspan="2">
											<div class="formBtn">
												<c:if test="${empty optype}">
													<button type="button" class="btn btn-sky" id="saveBtn">
														<i class="fa fa-save"></i>提 交
													</button>
												</c:if>
											</div>
										</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
					<div>
						<input type="hidden" value="${contentInfo.contentId}"
							name="contentId" id="contentId" /> <input type="hidden"
							value="${contentInfo.contentCode}" name="contentCode"
							id="contentCode" />
					</div>
				</form>
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
	<script src="${basePath}static/js/table.js"></script>
	<script src="${basePath}static/js/bfileinput/js/fileinput.js"></script>
	<script src="${basePath}static/js/bfileinput/js/locales/zh.js"></script>
	<script type="text/javascript" charset="utf-8"
		src="${basePath}static/js/ueditor/ueditor.config.js"></script>
	<script type="text/javascript" charset="utf-8"
		src="${basePath}static/js/ueditor/ueditor.all.min.js">
		
	</script>
	<script type="text/javascript" charset="utf-8"
		src="${basePath}static/js/ueditor/lang/zh-cn/zh-cn.js"></script>

	<script type="text/javascript">
		$(document)
				.on(
						'ready',
						function() {
							//新增-初始化选择
							if (isNull($("#contentId").val())) {
								$("#contentType").val("0");
								$("#homeToped").val("0");
								$("#showTop").val("1");
								$("#orderNo").val("99");
								$("#shareNum").val("10000");
								$("#clickNum").val("10000");
								$("#gatherPic").val("1");
							}
							//初始化编辑器
							var ue = UE.getEditor('content');
							//初始化图片上传
							$("#goodExtPic")
									.fileinput(
											{
												language : "zh",
												previewFileType : "image",
												maxFileCount : 4,
												uploadUrl : ccnetpath
														+ "/file/upload/multi",
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
												dropZoneTitle : "请选择或者拖拽图片到这里&hellip;<br> ",
												uploadIcon : "<i class=\"glyphicon glyphicon-upload\"></i> ",
												allowedFileExtensions : [
														"jpg", "gif", "png",
														"jpeg", "tmp" ],
												slugCallback : function(
														filename) {
													return filename;
												}
											});

							$("#goodExtPic")
									.on(
											"fileuploaded",
											function(event, data, previewId,
													index) {
												var sdata = data.response.url;
												if (!isNull(sdata)) {
													$("#contentPics").val(
															$("#contentPics")
																	.val()
																	+ ","
																	+ sdata);
													var cid = $("#contentId")
															.val();
													if (isNull(cid)) {
														cid = "nf";
													}
													var subPicStr = sdata
															.substring(
																	sdata
																			.lastIndexOf("/") + 1,
																	sdata
																			.indexOf("."));
													//返回预览
													var previewDiv = '<label id="lb_index_'+subPicStr+'"><img class="gpSeeImg" src="'+sdata+'" data-src="'+sdata+'" />';
													previewDiv += '<div class=fileActions> <div class="file-footer-buttons">';
													previewDiv += ' <button type="button" class="kv-file-remove btn btn-xs btn-default" title="删除文件" onclick="trashContentPic(\''
															+ cid
															+ '\',\''
															+ sdata
															+ '\')"><i class="glyphicon glyphicon-trash text-danger"></i></button>';
													previewDiv += ' <button type="button" class="kv-file-zoom btn btn-xs btn-default" title="查看详情" onclick="showContentPic(\''
															+ sdata
															+ '\')"><i class="glyphicon glyphicon-zoom-in"></i></button>';
													previewDiv += '</div></div></label>';
													if ($("#empty_tips").html() != null) {
														$("#goodPicSee")
																.empty();
													}
													$("#goodPicSee").append(
															previewDiv);
												}
											});
							onContentType();
							onhomeToped();
						});

		function onContentType() {
			var contentType = $("#contentType").val();
			var option = "<option value=''>请选择栏目</option>";
			<c:forEach items="${columnList}" var="dp">
			if ("${dp.columnType}" == contentType) {
				option += '<option value="${dp.columnId}" <c:if test="${dp.columnId==contentInfo.columnId}">selected="selected"</c:if>>${dp.columnName}</option>'
			}
			</c:forEach>
			$("#columnId").html(option);
		}
		function onhomeToped() {
			var homeToped = $("#homeToped").val();
			if (homeToped == 2) {
				$("#highPriceAdst").show();
			} else {
				$("#highPriceAdst").hide();
			}
		}
		function onHighPriceAdst() {
			var homeToped = $("#highPriceAd").val();
			var option = "";
			<c:forEach items="${adList}" var="dp">
			if ("${dp.adId}" == homeToped) {
				option += '${dp.adTitle}'
			}
			</c:forEach>
			$("#contentTitle").val(option);
			$("#contentSbTitle").val(option);
			var ue = UE.getEditor('content');//初始化对象
			ue.setContent('<img src="/static/img/toutiao/fenxiang.jpg"/>');
			//判断ueditor 编辑器是否创建成功
			// editor准备好之后才可以使用
			//content.add('<img src="http://img.jf258.com/i/5a2758085985x850822124b26.jpg"/>');
		}
	</script>
	<script src="${basePath}static/js/admincps/content/content.js"></script>
</body>
<!--  /Body -->
</html>
