<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="utf-8" />
	<title>角色授权</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<%@ include file="../../../common/includes.jsp"%>
	<%@ include file="../../../common/com_css.jsp"%>
	</head>
<body class="padding-top-10">
<table width="100%" border="0" align="center" style="margin-left:10px;" >
   <tr>
       <td height="25" align="left" colspan="2">
           <label>
           <input type="radio" onclick="expandNode('expandAll');" name="exp" checked=checked value="expandAll"/>
           <span class="text">展开</span>
        </label>
        <label>
           <input type="radio" onclick="expandNode('collapseAll');" name="exp" value="collapseAll"/>
           <span class="text">收缩</span>
        </label>
         <input type="hidden" id="role_id" name="role_id" value="${role_id}"/>
     </td>
  </tr>
</table>
<div class="col-lg-12 col-sm-12 col-xs-12">
    <div class="widget">
        <div class="widget-body">
            <div class="content_wraps">
		         <div style="width:100%;height:320px;overflow:auto">
					<div class="zTreeDemoBackgrounds">
						<ul id="roleMenutree" class="ztree"></ul>
					</div>
				</div>
			</div>
        </div>
  </div>
  <table width="100%" border="0" align="center" style="margin-left:10px;" >
   <tr>
       <td height="25" align="left" colspan="2">
           <label>
           <input type="radio" onclick="checkAllNode(true);" name="ck" value="ckall"/>
           <span class="text">全选</span>
        </label>
        <label>
           <input type="radio" onclick="checkAllNode(false);" name="ck" value="unckall"/>
           <span class="text">反选</span>
        </label>
     </td>
  <td>
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
  </td>
  </tr>
</table>
</div>

<%@ include file="../../../common/com_js.jsp"%>
<script type="text/javascript">
    var znodes = [${listerTreeData}];
</script>
<script src="${basePath}static/js/admin/role/rolePermit.js"></script>

</body>
</html>
