<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="utf-8" />
	<title>增加常见问题</title>
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
          <form role="form" id="problemForm" action="" method="post">
             <input type="hidden" class="form-control" id="id" name="id" value="${systemProblemInfo.id}">
             <table class="table table-striped table-bordered" id="simpledatatable" >
            	   <tbody>
            	   <tr>
						<td align="right" width="90px">
							常见问题类型：
						</td>
						<td>
						   <select id="type" name="type">
						      <option value="">请选择...</option>
						      <option value="新手问题" <c:if test="${systemProblemInfo.type=='新手问题'}">selected</c:if> >新手问题</option>
						      <option value="金币问题" <c:if test="${systemProblemInfo.type=='金币问题'}">selected</c:if> >金币问题</option>
						      <option value="提现问题" <c:if test="${systemProblemInfo.type=='提现问题'}">selected</c:if> >提现问题</option>
						  </select>
						</td>
					</tr>
					<tr>
						<td align="right">
							排序编号：
						</td>
						<td>
						     <input type="text" class="form-control" id="sort" name="sort" placeholder="填写排序编号" value="${systemProblemInfo.sort}">
						</td>
					</tr>
            	   <tr>
						<td align="right">
							常见问题标题：
						</td>
						<td>
							<input type="text" class="form-control" id="title" name="title" placeholder="填写常见问题显示标题"  value='${systemProblemInfo.title}'>
						</td>
					</tr>
					<tr>
						<td align="right">
							文案内容：
						</td>
						<td>
							<textarea class="form-control" style="height:120px;" id="content" name="content" >${systemProblemInfo.content}</textarea>
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
<script src="${basePath}static/js/admin/problem/problem.js"></script>
</body>
</html>
