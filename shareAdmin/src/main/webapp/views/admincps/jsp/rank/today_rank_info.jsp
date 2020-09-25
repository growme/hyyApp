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
<link href="${basePath}static/js/bfileinput/css/fileinput.css" rel="stylesheet" type="text/css"/>
<link href="${basePath}static/css/multiselect.css" rel="stylesheet" type="text/css"/>
<link href="${basePath}static/css/product.css" rel="stylesheet" type="text/css"/>
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
						       <table class="table table-striped" id="simpledatatable"  >
						         <tbody>
			                       <tr>
										<td align="right" width="100px">广告标题:</td>
										<td ><input id="adTitle" class="form-control"  placeholder="请输入广告标题" type="text" name="adTitle"  value="${advert.adTitle}" style="width:100%;"  /></td>
								   </tr>
								   <tr>
								       <td align="right"><label>广告标签:</label></td>
								       <td>
								           <select name="adTag" id="adTag" placeholder="广告标签" style="width:100%;"   >
											  <option value="">请选择</option>
											    <c:forEach items="${adTagList}" var="dp">
											       <option  value="${dp.codeValue}" <c:if test="${dp.codeValue==advert.adTag}">selected="selected"</c:if>>${dp.valueDesc}</option>
											    </c:forEach>
											</select>
								       </td>
								   </tr>
								   <tr>
										<td align="right"><label>阅读数量(虚):</label></td>
										<td ><input id="readNum" class="form-control"  placeholder="请输入阅读数量" type="text" name="readNum"  value="${advert.readNum}" style="width:100%;"  /></td>
								   </tr>
								   <tr>
										<td align="right"><label>广告类型:</label></td>
										<td>
											<select name="adType" id="adType" placeholder="广告类型" style="width:100%;">
											    <option value="">请选择</option>
											    <c:forEach items="${adTypeMap}" var="dp">
											       <option  value="${dp.key}" <c:if test="${dp.key==advert.adType}">selected="selected"</c:if>>${dp.value}</option>
											    </c:forEach>
											</select>
										</td>
								   </tr>
								   <tr>
										<td align="right"><label>广告地址:</label></td>
										<td ><input id="adLink" class="form-control"  placeholder="请输入广告地址" type="text" name="adLink"  value="${advert.adLink}" style="width:100%;"  /></td>
								   </tr>
								   <tr>
										<td align="right"><label>排序序号:</label></td>
										<td ><input id="sortNo" class="form-control"  placeholder="请输入排序序号" type="text" name="sortNo"  value="${advert.sortNo}" style="width:100%;"  /></td>
								   </tr>
								   <tr>
										<td align="right"><label>广告状态:</label>
										</td>
										<td>
										<select name="state" id="state" placeholder="广告状态"
											style="width:100%;">
											<c:forEach items="${stateMap}" var="dp">
												<option value="${dp.key}"
													<c:if test="${dp.key==advert.state}">selected="selected"</c:if>>${dp.value}</option>
											</c:forEach>
										</select>
										</td>
									</tr>
									<tr>
										<td align="right"><label>投放时间:</label></td>
										<td >
										<input class="input-sm Wdate" style="width:100px" type="text" name="startTime" 
										id="startTime" onfocus="WdatePicker({dateFmt:'HH:mm'})" value="${advert.startTime}" /> 
										至
										<input class="input-sm Wdate" style="width:100px" type="text" name="endTime" 
										id="endTime" onfocus="WdatePicker({dateFmt:'HH:mm'})" value="${advert.endTime}" /> 
										</td>
								   </tr>
								   <tr>
										<td align="right"><label>结束时间:</label></td>
										<td >
										<input class="input-sm Wdate" style="width:100px" type="text" name="endDate" 
										id="endDate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="${advert.endDate}" /> 
										</td>
								   </tr>
								   <tr>
										<td align="right">上传图片：</td>
										<td>
										<div  style="width:100%;" >
											<input id="goodExtPic" name="goodExtPic" type="file" multiple class="file-loading">
											<div id="errorBlock" class="help-block"></div>
										</div>
										</td>
								    </tr>
								    <tr>
									<td align="right">
										图片管理：
									</td>
									<td>
									    <div id="goodPicSee" class="fileDropAone">
									       <c:if test="${!empty advert.picList}">
										      <c:forEach var="pc" items="${advert.picList}" varStatus="px">
										        <label id="${pc.picLableId}">
										            <img class="gpSeeImg" src="${pc.advertPic}" data-src="${pc.advertPic}"/>
									                <div class="fileActions">
													  <div class="file-footer-buttons">
													    <button type="button" class="kv-file-remove btn btn-xs btn-default" title="删除" onclick="trashContentPic('${advert.adId}','${pc.advertPic}')">
													    <i class="glyphicon glyphicon-trash text-danger"></i>
													    </button>
													    <button type="button" class="kv-file-zoom btn btn-xs btn-default" title="查看" onclick="showContentPic('${pc.advertPic}')">
													    <i class="glyphicon glyphicon-zoom-in"></i>
													    </button>
													  </div>
													</div>
										        </label>
										      </c:forEach>
										   </c:if>
										   <c:if test="${empty advert.picList}">
										   <i class="fa fa-user"></i> 提示信息：<span class="red" id="empty_tips">该产品还未上传任何图片，请先上传产品图片!</span>
										   </c:if>
									    </div>
									    <input type="hidden" class="form-control" id="advertPics" name="advertPics" value="${advert.advertPics}">
									</td>
							        </tr>
							        <tr>
										<td align="right"><label>备注信息:</label></td>
										<td ><input id="remark" class="form-control" placeholder="备注信息" type="text" name="remark"  value="${advert.remark}" style="width:100%;"  /></td>
								   </tr>
									<tr> 
									<td align="right" colspan="2">
										<div class="formBtn">
					                      <c:if test="${empty optype}">
					                    	<button type="button" class="btn btn-sky" id="saveBtn"><i class="fa fa-save"></i>提 交</button>
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
				             	<input type="hidden" value="${advert.adCode}" name="adCode" id="adCode" />
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
		<script type="text/javascript" charset="utf-8" src="${basePath}static/js/table.js"></script>
		<script type="text/javascript" charset="utf-8" src="${basePath}static/js/bfileinput/js/fileinput.js"></script>
		<script type="text/javascript" charset="utf-8" src="${basePath}static/js/bfileinput/js/locales/zh.js"></script>
		<script type="text/javascript" charset="utf-8" src="${basePath}static/js/ueditor/ueditor.config.js"></script>
		<script type="text/javascript" charset="utf-8" src="${basePath}static/js/ueditor/ueditor.all.min.js"> </script>
		<script type="text/javascript" charset="utf-8" src="${basePath}static/js/ueditor/lang/zh-cn/zh-cn.js"></script>
		<script type="text/javascript" charset="utf-8" src="${basePath}static/js/admincps/advertise/good_advertise.js"></script>
		
		
		<script type="text/javascript">
		
		 $(document).on('ready', function() {
			    //新增-初始化选择
			    if(isNull($("#adId").val())){
			      //$("#adTag").val("1");
		    	  $("#readNum").val("1000");
		    	  $("#adType").val("1");
		    	  $("#state").val("1");
			    }
			    //初始化编辑器
				var ue = UE.getEditor('content');
			    //初始化图片上传
			    $("#goodExtPic").fileinput({
			    	language: "zh",
			    	previewFileType: "image",
			        maxFileCount: 4,
			        uploadUrl: ccnetpath+"/file/upload/multi",
			        deleteURl:ccnetpath+"/file/trash",
			        browseClass: "btn btn-success",
			        browseLabel: "选择",
			        browseIcon: "<i class=\"glyphicon glyphicon-picture\"></i> ",
			        removeClass: "btn btn-danger",
			        removeLabel: "清除",
			        removeIcon: "<i class=\"glyphicon glyphicon-trash\"></i> ",
			        uploadClass: "btn btn-info",
			        uploadLabel: "上传",
			        dropZoneTitle: "请选择或者拖拽图片到这里&hellip;<br> ",
			        uploadIcon: "<i class=\"glyphicon glyphicon-upload\"></i> ",
			        allowedFileExtensions: ["jpg", "gif", "png", "jpeg", "tmp"],
			        slugCallback: function(filename) {  
			           return filename;
			        }  
			    });
			    
			     $("#goodExtPic").on("fileuploaded", function (event, data, previewId, index) {  
			         var sdata = data.response.url;
					 if (!isNull(sdata)) {
						 $("#advertPics").val($("#advertPics").val()+","+sdata);
						 var cid = $("#adId").val();
						 if(isNull(cid)){
							cid="nf";
						 }
						 var subPicStr = sdata.substring(sdata.lastIndexOf("/")+1,sdata.indexOf("."));
						 //返回预览
						 var previewDiv='<label id="lb_index_'+subPicStr+'"><img class="gpSeeImg" src="'+sdata+'" data-src="'+sdata+'" />';
			                 previewDiv+='<div class=fileActions> <div class="file-footer-buttons">';
			                 previewDiv+=' <button type="button" class="kv-file-remove btn btn-xs btn-default" title="删除文件" onclick="trashContentPic(\''+cid+'\',\''+sdata+'\')"><i class="glyphicon glyphicon-trash text-danger"></i></button>';
							 previewDiv+=' <button type="button" class="kv-file-zoom btn btn-xs btn-default" title="查看详情" onclick="showContentPic(\''+sdata+'\')"><i class="glyphicon glyphicon-zoom-in"></i></button>';     
							 previewDiv+='</div></div></label>';
							 if($("#empty_tips").html()!=null){
								$("#goodPicSee").empty();
							 }
						 $("#goodPicSee").append(previewDiv);
					 }
			     }); 
		 });
		</script>
	</body>
	<!--  /Body -->
</html>
