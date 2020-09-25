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
					<div class="page-body">
				      <div class="swidget">
				        <div class="widget-body  no-padding">
				          <form role="form" id="advertiseForm" action="" method="post">
				            <div id="registration-form">
						       <table class="table table-striped table-bordered" id="simpledatatable"  >
						         <tbody>
								<tr>
									<td align="left">手机二维码如下:</td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td align="left" colspan="6">
										<div>
											<div id="code1" style="margin:10px 0;">
												<canvas width="180" height="180"></canvas>
											</div>
											<script type="text/javascript">
												$(function() {
													$('#code1')
															.qrcode(
																	{
																		render : "canvas",
																		width : 180,
																		height : 180,
																		text : "http://cps.hnccnet.com/advertise/cpsdemo?jsdemo=aHR0cDovL2Nwc2dnLmhuY2NuZXQuY29tL3N0YXRpYy9hZGpzL2Nwc2FkanMuanM/dHlwZT0xJmFtcDtudW09MyZhbXA7YXBzaWRzPTMsMiwxJmFtcDthcHNpZHMyPSZhbXA7dWlkPTImYW1wO2NpZD0xMg=="
																	});
												})
											</script>
										</div></td>
								</tr>
								<tr align="left">
									<td>广告展示:</td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td rowspan="3"><c:forEach var="img" varStatus="status1"
											items="${fn:split(advert.adPic,',')}">
											<img src="${img}" height="80px;" ;width="100px;" />
										</c:forEach>
									</td>
									<td colspan="4">${advert.adTitle }</td>
								</tr>
								<tr>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td><c:forEach items="${adTagList}" var="dp">
											<c:if test="${dp.adTagId==advert.adTag}">${dp.adTagName}</c:if>
										</c:forEach></td>
									<td>阅读数量:</td>
									<td>${advert.readNum }</td>
									<td>发布于：</td>
									<td>${advert.createTime }</td>
								</tr>
							</tbody>
					 </table>
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
		<script src="${basePath}static/js/admincps/sadvertise/good_advertise.js"></script>
	<script type="text/javascript" src="${basePath}static/js/ueditor/third-party/zeroclipboard/ZeroClipboard.js"></script>
		<script src="${basePath}static/js/bfileinput/js/fileinput.js"></script>
		<script src="${basePath}static/js/bfileinput/js/locales/zh.js"></script>
		<script type="text/javascript">
$(function(){
$("#fuzhi").click(function(){
//var chnSel=$('#CH');
ZeroClipboard.config({ swfPath: ccnetpath + "/static/js/ueditor/third-party/zeroclipboard/ZeroClipboard.swf" });
//ZeroClipboard.config({ swfPath: SITE_PUBLIC + "static/script/ZeroClipboard.swf" });
        
        var client = new ZeroClipboard( document.getElementById("fuzhi") );
        
        client.on('ready', function (event) {
       
            client.on('copy', function (event) {
            alert(2);
                if(!isEmpty($('#adScript').val())){
                   event.clipboardData.setData('text/html', $('#adScript').val());
                   event.clipboardData.setData('text/plain', $('#adScript').val());
                }else{
                   showTErrMsg("请先生成脚本后复制!");
                   return false;
                }
            });
            client.on( 'aftercopy', function(event) {
                if(!isEmpty($('#adScript').val())){
                   showTSucMsg('复制成功');
                }else{
                   showTErrMsg("请先生成脚本后复制!");
                   return false;
                }
            });
      }); 
});
      
          });

		
		var picdata="";
			$(document).on('ready', function() {
            
		    $("#goodExtPic").fileinput({
		    	language: "zh",
		    	previewFileType: "image",
		        maxFileCount: 3,
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
				 picdata+=sdata+",";
				 //$("#goodExtPic").val()+","+
					 $("#adPic").val(picdata);
					 }
		     });  
		     
		});
			  
		</script>
	</body>

	<!--  /Body -->
</html>
