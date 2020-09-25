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
										<td align="right" width="90px">广告标题:</td>
										<td><input  style="width:100%;"  id="adTitle" class="form-control" type="text" name="adName" value="${advert.adName}" /></td>
								   </tr>
							        <tr>
										<td align="right"><label>广告位置:</label></td>
										<td>
                                        <select name="adPosition" id="adPosition" placeholder="广告位置"  style="width:100%;" >
				                              <option value="">选择位置</option>
											    <c:forEach items="${adPosition}" var="dp" >
											       <option  value="${dp.key}" <c:if test="${dp.key==advert.adPosition}">selected="selected"</c:if>>${dp.value}</option>
											    </c:forEach>
										</select>
                                        </td>
								   </tr>
								   <tr>
										<td align="right"><label>排序序号:</label></td>
										<td><input  style="width:100%;" id="sortNo"  class="form-control" type="number" name="sortNo"  value="${advert.sortNo}" /></td>
								   </tr>
								   <tr>
										<td align="right"><label>广告状态:</label></td>
										<td>
											<select name="state" id="state" placeholder="广告状态"  style="width:100%;" >
				                
											    <c:forEach items="${stateMap}" var="dp">
											       <option  value="${dp.key}" <c:if test="${dp.key==advert.state}">selected="selected"</c:if>>${dp.value}</option>
											    </c:forEach>
											</select>
										</td>
								   </tr>
									<tr  id="adScriptTr"> 
									  <td align="right"><label>广告脚本:</label></td>
									  <td>
									  	<textarea  class="form-control" id="adScript"  name="adScript" rows="4" placeholder="广告脚本"  style="width:100%;">${advert.adScript}</textarea>
					                  </td>
								  </tr>			   
								  <tr> 
									  <td align="right"><label>备注信息:</label></td>
									  <td>
									  	<textarea class="form-control" id="remark" name="remark" rows="4" placeholder="备注信息"  style="width:100%;">${advert.remark}</textarea>
					                  </td>
								  </tr>	
								 </tbody>
							   </table>
				             </div>
				             	<div>
				             		<input type="hidden"  name="adId" id="adId" value="${advert.adId}" />
				             		<input type="hidden"  name="userId" value="${SESSION_USER.userId}" />
				             	</div>
			             		<div class="formBtn">
				                      <c:if test="${empty optype}">
				                     <input id="demojs" name="demojs" type="hidden">
				                      <button type="button"  id="add" class="btn btn-yellow"><i class="fa fa-pencil"></i>生成脚本</button>
				                      <button type="button"  id="fuzhi" class="btn btn-primary"><i class="fa fa-copy"></i>复制脚本</button>
				                      <button type="button"  id="chakan" class="btn btn-green"><i class="fa fa-eye"></i>查看样例</button>
				                     
				                    	<button type="button" class="btn btn-sky" id="saveBtn"><i class="fa fa-save"></i>提 交</button>
				                   	  </c:if>
				                   	<!--  <button type="button" class="btn btn-default" id="closeBtn"><i class="fa fa-power-off"></i>关 闭 </button> -->
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
