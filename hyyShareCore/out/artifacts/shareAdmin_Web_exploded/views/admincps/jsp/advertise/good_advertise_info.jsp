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
				<form role="form" id=advertiseForm action="" method="post">

					<div class="info-box">
						<div class="info-hd">
							<span class="no-margin-top"><i class="fa fa-gift"></i>${Const.CT_MENU_NAV}</span>
						</div>
						<div class="info-bd no-margin no-padding">
							<table class="table table-striped" id="simpledatatable">
								<tbody>
									<tr>
										<td align="right" style="width: 120px;"><label>广告标签:</label></td>
										<td><select name="adTag" id="adTag" placeholder="广告标签"
											style="width: 100%;">
												<option value="">请选择</option>
												<c:forEach items="${adTagList}" var="dp">
													<option value="${dp.codeValue}"
														<c:if test="${dp.codeValue==advert.adTag}">selected="selected"</c:if>>${dp.valueDesc}</option>
												</c:forEach>
										</select></td>
									</tr>
									<tr>
										<td align="right"><label>阅读数量(虚):</label></td>
										<td><input id="readNum" class="form-control"
											placeholder="请输入阅读数量" type="text" name="readNum"
											value="${advert.readNum}" style="width: 100%;" /></td>
									</tr>
									<tr>
										<td align="right"><label>广告类型:</label></td>
										<td><select name="adType" id="adType" placeholder="广告类型"
											style="width: 100%;">
												<option value="">请选择</option>
												<c:forEach items="${adTypeMap}" var="dp">
													<option value="${dp.key}"
														<c:if test="${dp.key==advert.adType}">selected="selected"</c:if>>${dp.value}</option>
												</c:forEach>
										</select></td>
									</tr>
									<tr>
										<td align="right"><label>广告地址:</label></td>
										<td><input id="adLink" class="form-control"
											placeholder="请输入广告地址" type="text" name="adLink"
											value="${advert.adLink}" style="width: 100%;" /></td>
									</tr>
									<tr>
										<td align="right"><label>广告备用地址:</label></td>
										<td><input id="adLink2" class="form-control"
											placeholder="请输入广告备用地址" type="text" name="adLink2"
											value="${advert.adLink2}" style="width: 100%;" /></td>
									</tr>
									<tr>
										<td align="right"><label>单次点击费用:</label></td>
										<td><input id="unitPrice" class="form-control"
											placeholder="请输入单次点击费用" type="text" name="unitPrice"
											value="${advert.unitPrice}" style="width: 100%;" /></td>
									</tr>
									<tr>
										<td align="right">充值广告主：</td>
										<td><select name="adUserId" id="adUserId"
											style="width: 100%;">
												<option value="">请选择...</option>
												<c:if test="${!empty userList}">
													<c:forEach items="${userList}" var="dp">
														<option value="${dp.userId}"
															<c:if test="${dp.userId==advert.adUserId}">selected</c:if>>${dp.userName}</option>
													</c:forEach>
												</c:if>
										</select></td>
									</tr>
									<tr>
										<td align="right"><label>排序序号:</label></td>
										<td><input id="sortNo" class="form-control"
											placeholder="请输入排序序号" type="text" name="sortNo"
											value="${advert.sortNo}" style="width: 100%;" /></td>
									</tr>
									<tr>
										<td align="right"><label>是否有效:</label></td>
										<td><select name="state" id="state" placeholder="广告状态"
											style="width: 100%;">
												<c:forEach items="${stateMap}" var="dp">
													<option value="${dp.key}"
														<c:if test="${dp.key==advert.state}">selected="selected"</c:if>>${dp.value}</option>
												</c:forEach>
										</select></td>
									</tr>
									<tr>
										<td align="right"><label>投放时间:</label></td>
										<td><input class="input-sm Wdate" style="width: 100px"
											type="text" name="startTime" id="startTime"
											onfocus="WdatePicker({dateFmt:'HH:mm'})"
											value="${advert.startTime}" /> 至 <input
											class="input-sm Wdate" style="width: 100px" type="text"
											name="endTime" id="endTime"
											onfocus="WdatePicker({dateFmt:'HH:mm'})"
											value="${advert.endTime}" /></td>
									</tr>
									<tr>
										<td align="right"><label>结束日期:</label></td>
										<td><input class="input-sm Wdate" style="width: 100px"
											type="text" name="endDate" id="endDate"
											onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
											value="${advert.endDate}" /></td>
									</tr>
									<tr>
										<td align="right"><label>禁止投放地点:</label></td>
										<td><c:forEach items="${districtList}" var="dp">
												<label> <input type="checkbox" id="districtId_${dp.id}"
													<c:if test="${fn:contains(advert.districtIds,dp.id)}">checked</c:if>
													name="districtIds" value="${dp.id}"> <span class="text">${dp.name}</span>
												</label>
											</c:forEach></td>
									</tr>
									<tr>
										<td align="right">设置标题：</td>
										<td>
											<table class="table table-striped table-bordered"
												id="avTitleTable">
												<tr align="center">
													<th style="width: 65px;" class="text-center">序 号</th>
													<th>广告标题</th>
												</tr>
												<c:if test="${!empty advert.adTitleList}">
													<c:forEach items="${advert.adTitleList}" var="st"
														varStatus="idx">
														<tr align="center">
															<td align="center" width="70">标题 ${idx.index+1}：</td>
															<td align="left"><input id="avTitle_${idx.index+1}"
																name="avTitle_${idx.index+1}" class="form-control"
																placeholder="请输入广告标题" type="text" value="${st.avTitle}">
															</td>
														</tr>
													</c:forEach>
												</c:if>
											</table>
											<div class="padding-top-5">
												<button type="button" class="btn btn-sm btn-primary"
													id="addTrBtn">
													<i class="fa fa-plus"></i>增加
												</button>
												<button type="button" class="btn btn-sm btn-danger"
													id="delTrBtn">
													<i class="fa fa-minus"></i>删除
												</button>
												(<span class="red">提示：广告标题可以添加多个，至少需要一个标题</span>)
											</div>
										</td>
									</tr>

									<tr>
										<td align="right">设置图片：</td>
										<td>
											<table class="table table-striped table-bordered"
												id="avPicTable">
												<tr>
													<th style="width: 65px;" class="text-center">序 号</th>
													<th style="width: 120px;">图片预览</th>
													<th>图片地址</th>
												</tr>
												<c:if test="${!empty advert.adPicList}">
													<c:forEach items="${advert.adPicList}" var="st"
														varStatus="idx">
														<tr align="center">
															<td align="center" width="70">图片 ${idx.index+1}：</td>
															<td align="center" width="120"><c:if
																	test="${empty st.avPic}">
																	<img class="avImgPre lazy" id="spic_${idx.index+1}"
																		name="spic" src="${basePath}static/img/no_pic.png">
																</c:if> <c:if test="${!empty st.avPic}">
																	<img class="avImgPre lazy" id="spic_${idx.index+1}"
																		name="spic" src="${st.avPic}">
																</c:if></td>

															<td align="left"><input id="avPic_${idx.index+1}"
																name="avPic_${idx.index+1}" class="form-control"
																onchange="setPicPreview(this,'spic_${idx.index+1}',120,80);"
																placeholder="请输入广告图片地址，建议将图片存放到外网云服务器上面，如阿里云CDN、新浪云等等"
																type="text" value="${st.avPic}" /></td>
														</tr>
													</c:forEach>
												</c:if>
											</table>
											<div class="padding-top-5">
												<button type="button" class="btn btn-sm btn-primary"
													id="addPicTrBtn">
													<i class="fa fa-plus"></i>增加
												</button>
												<button type="button" class="btn btn-sm btn-danger"
													id="delPicTrBtn">
													<i class="fa fa-minus"></i>删除
												</button>
												(<span class="red">提示：为了更好的显示至少三张不同的图片，建议使用外链形式（阿里云CDN、新浪云等）</span>)
											</div>
										</td>
									</tr>

									<tr>
										<td align="right"><label>备注信息:</label></td>
										<td><input id="remark" class="form-control"
											placeholder="备注信息" type="text" name="remark"
											value="${advert.remark}" style="width: 100%;" /></td>
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
						<input type="hidden" value="${advert.adId}" name="adId" id="adId" />
						<input type="hidden" value="${advert.adCode}" name="adCode"
							id="adCode" />
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
	<script type="text/javascript" charset="utf-8"
		src="${basePath}static/js/table.js"></script>
	<script type="text/javascript" charset="utf-8"
		src="${basePath}static/js/admincps/advertise/good_advertise.js"></script>

	<script type="text/javascript">
		$(document).on('ready', function() {
			if (isNull($("#adId").val())) {
				//$("#adTag").val("1");
				$("#readNum").val("1000");
				$("#adType").val("1");
				$("#state").val("1");
			}
		});
	</script>
</body>
<!--  /Body -->
</html>
