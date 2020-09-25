<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="utf-8" />
	<title>定时任务管理</title>
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
             <form id="taskForm" action="" method="post">
            	<table class="table table-striped table-bordered table-hover" id="simpledatatable" >
            	   <tbody>
            	    <tr>
						<td align="right" width="90px">
							任务名：
						</td>
						<td>
						 	<input type="hidden" class="form-control" id="taskScheduleId" name="taskScheduleId" value="${taskSchedule.taskScheduleId}">
							<input type="text" class="form-control" id="jobName" name="jobName" value="${taskSchedule.jobName}">
						</td>
						<td align="right">
							任务分组：
						</td>
						<td>
							<input type="text" class="form-control" id="jobGroup" name="jobGroup" value="${taskSchedule.jobGroup}">
						</td>
					</tr>
					<tr>
						<td align="right">
							任务别名：
						</td>
						<td>
							<input type="text" class="form-control" id="aliasName" name="aliasName" value="${taskSchedule.aliasName}">
						</td>
						<td align="right">
							指定执行类：
						</td>
						<td>
							<input type="text" class="form-control" id="jobClass" name="jobClass" value="${taskSchedule.jobClass}">
						</td>
					</tr>
					<tr>
						<td align="right">
							任务状态：
						</td>
						<td>
							<select name="jobStatus" id="jobStatus" style="width:100%;">
                              <option value="">选择状态</option>
							  <c:if test="${!empty enlist}">
							    <c:forEach items="${enlist}" var="dp">
							       <option value="${dp.codeValue}" <c:if test="${dp.codeValue==taskSchedule.jobStatus}">selected</c:if>>${dp.valueDesc}</option>
							    </c:forEach>
							  </c:if>
							</select>
						</td>
						<td align="right">
							运行时间表达式：
						</td>
						<td>
							<input type="text" class="form-control" id="cronExpression" name="cronExpression" value="${taskSchedule.cronExpression}">
						</td>
					</tr>
					<tr>
						<td align="right">
							备注信息：
						</td>
						<td colspan="3">
							<textarea class="form-control" id="description" name="description"  style="width:100%;height:75px">${taskSchedule.description}</textarea>
						</td>
					</tr>
					</tbody>
				 </table>
                 <div class="formBtn">
                      <c:if test="${empty optype}">
                    <button type="button" class="btn btn-sky" id="saveBtn">
                      <i class="fa fa-save"></i>提 交
                    </button>
                   </c:if>
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
<script src="${basePath}static/js/admin/task/taskSchedule.js"></script>

</body>
</html>
