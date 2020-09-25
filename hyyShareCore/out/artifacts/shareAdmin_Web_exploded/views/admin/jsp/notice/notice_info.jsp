<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="utf-8" />
	<title>增加公告</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<%@ include file="../../../common/includes.jsp"%>
	<%@ include file="../../../common/com_css.jsp"%>
</head>
<body class="no-padding">
<div class="col-lg-12 col-sm-12 col-xs-12 no-padding">
    <div class="swidget">
        <div class="widget-body">
            <div id="registration-form">
          <form role="form" id="noticeForm" action="" method="post">
             <input type="hidden" class="form-control" id="noticeId" name="noticeId" value="${systemNoticeInfo.noticeId}">
             <table class="table table-striped table-bordered" id="simpledatatable" >
            	   <tbody>
            	   <tr>
						<td align="right" width="90px">
							公告类型：
						</td>
						<td>
						   <select id="noticeType" name="noticeType">
						    <c:if test="${!empty noticeTypeList}">
						      <option value="">请选择...</option>
						      <c:forEach items="${noticeTypeList}" var="nt">
						      <option value="${nt.type}" <c:if test="${systemNoticeInfo.noticeType==nt.type}">selected</c:if> >${nt.name}</option>
						      </c:forEach>
						    </c:if>
						  </select>
						</td>
					</tr>
					<tr>
						<td align="right">
							排序编号：
						</td>
						<td>
						     <input type="text" class="form-control" id="orderNumber" name="orderNumber" placeholder="填写排序编号" value="${systemNoticeInfo.orderNumber}">
						</td>
					</tr>
					<tr>
						<td align="right">
							是否发布：&nbsp;&nbsp;
						</td>
						<td>
						   <label class="no-margin">
                              <input class="checkbox-slider toggle colored-success" id="state_CK" name="state_CK" type="checkbox" <c:if test="${systemNoticeInfo.state==1}">checked</c:if> >
                              <span class="text no-margin"></span>
                              <input type="hidden" id="state" name="state" class="form-control" value="${systemNoticeInfo.state}">
                           </label>
						</td>
				   </tr>
					<tr>
						<td align="right">
							是否置顶：&nbsp;&nbsp;
						</td>
						<td>
						   <label class="no-margin">
                              <input class="checkbox-slider toggle colored-success" id="showTop_CK" name="showTop_CK" type="checkbox" <c:if test="${systemNoticeInfo.showTop==1}">checked</c:if> >
                              <span class="text no-margin"></span>
                              <input type="hidden" id="showTop" name="showTop" class="form-control" value="${systemNoticeInfo.showTop}">
                           </label>
						</td>
				   </tr>
            	   <tr>
						<td align="right">
							公告标题：
						</td>
						<td>
							<input type="text" class="form-control" id="noticeTitle" name="noticeTitle" placeholder="填写公告显示标题"  value='${systemNoticeInfo.noticeTitle}'>
						</td>
					</tr>
					<tr>
						<td align="right">
							文案内容：
						</td>
						<td>
							<script id="ntcontent" name="ntcontent" type="text/plain" style="width:100%;height:300px;">${systemNoticeInfo.noticeContent}</script>
						</td>
					</tr>
					</tbody>
				 </table>
                 <div class="formBtn">
                    <button type="button" class="btn btn-sky" id="saveBtn">
                      <i class="fa fa-save"></i>提 交
                    </button>
                   <button type="button" class="btn btn-default" id="closeBtn">
                   <i class="fa fa-power-off"></i>关 闭 
                   </button>
                </div>
          </form>
        </div>
    </div>
  </div>
</div>

<%@ include file="../../../common/com_js.jsp"%>
<script type="text/javascript" charset="utf-8" src="${basePath}static/js/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${basePath}static/js/ueditor/ueditor.all.min.js"> </script>
<script type="text/javascript" charset="utf-8" src="${basePath}static/js/ueditor/lang/zh-cn/zh-cn.js"></script>
<script src="${basePath}static/js/admin/notice/notice.js"></script>
<script type="text/javascript">
initUEditor();
</script>
</body>
</html>
