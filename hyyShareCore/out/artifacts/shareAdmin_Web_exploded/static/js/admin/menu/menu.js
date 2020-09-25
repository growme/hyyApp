//JavaScript Document
$(function () {
	initSelectedVal();
	//点击增加按钮
	$("#th_add_btn").click(function () {
		openPage("增加资源",860,600,ccnetpath+"/backstage/resources/goadd");
	});
	
	//点击修改按钮
	$("#th_edit_btn").click(function () {
		editResources();
	});
	
	//点击复制按钮
	$("#th_copy_btn").click(function () {
		copyResources();
	});
	
	//关闭窗口
	$("#closeBtn").click(function () {
		closeFrame(false);
	});
	
	//选择图标
	$("#icon").click(function () {
		showIcon();
	});
	
	//保存资源
	$("#saveBtn").click(function () {
		saveResources();
	});
	
	//点击删除按钮
	$("#th_del_btn").click(function () {
		delResources();
	});
	
});

//修改资源
function editResources(id){
   var ids = "";
   if(isEmpty(id)){
        var ids = getCKVal("ck");
		if(ids == null || ids.length==0){
			showWarnMsg("请先选择要操作的数据！");
			return;
		}
		if(ids.split(",").length>1){
			showWarnMsg("对不起！不能进行批量操作！");
			return;
		}
	  }else{
	    ids = id;
	  }
   openPage("修改资源",860,600,ccnetpath + "/backstage/resources/goedit?resource_id="+ids+"&optype=edit");
}

//复制资源
function copyResources(id){
   var ids = "";
   if(isEmpty(id)){
        var ids = getCKVal("ck");
		if(ids == null || ids.length==0){
			showWarnMsg("请先选择要操作的数据！");
			return;
		}
		if(ids.split(",").length>1){
			showWarnMsg("对不起！不能进行批量操作！");
			return;
		}
	  }else{
	    ids = id;
	  }
   openPage("快速添加资源",860,600,ccnetpath + "/backstage/resources/gocopy?resource_id="+ids);
}

//保存资源
function saveResources(){
	
	if(isEmpty($("#resourceName").val())){
		showTips("资源名称不能为空!","resourceName");
        return false;
    }else{
        if(!isUName($("#resourceName").val())){
        	showTips("资源名称只能由字母、数字、下划线组成!","resourceName");
            return false;
        }
    }
	
	if(isEmpty($("#parentCode").val())){
		showTips("请选择上级资源!","parentName");
        return false;
    }
	
	if(isEmpty($("#resourceType").val())){
		showTips("请选择资源类型!","resourceType");
        return false;
    }
	
	if(isEmpty($("#resourceState").val())){
		showTips("请选择资源状态!","resourceState");
        return false;
    }
	
	if(isEmpty($("#expanded").val())){
		showTips("请选择是否展开!","expanded");
        return false;
    }
	
	if(isEmpty($("#isleaf").val())){
	    showTips("请选择节点类型!","isleaf");
        return false;
    }
	
	if(!isEmpty($("#resourceUrl").val())){
    	if(!isValidUrl($("#resourceUrl").val())){
    		showTips("访问路径不合法!","resourceUrl");
            return false;
    	}
    }
	//判断是否选择按钮
	if(isNotNull($("#resourceType").val()) && getSelText("resourceType").indexOf("按钮")!=-1){
		if(isEmpty($("#btnId").val())){
			showTips("请填写按钮的ID标识!","btnId");
	        return false;
	    }else{
	    	if(!isEnNum($("#btnId").val())){
	    		showTips("ID标识只能由字母、数字、下划线组成!","btnId");
	            return false;
	        }
	    }
		
		
		if(isEmpty($("#btnFun").val())){
			showTips("请填写按钮对应的JS方法名称!","btnFun");
	        return false;
	    }/*else{
	    	if(!isEnNum($("#btnFun").val())){
	    		showTips("方法名称只能由字母、数字、下划线组成!","btnFun");
	            return false;
	        }
	    }*/
		
    }
	
	if(isEmpty($("#icon").val())){
		showTips("请填写资源显示图标!","icon");
        return false;
    }
	
	if(isEmpty($("#orderNumber").val())){
		showTips("排序编号不能为空!","orderNumber");
        return false;
    }else{
    	if(!isNumber($("#orderNumber").val())){
    		showTips("排序编号只能为数字!","orderNumber");
            return false;
        }
    	
    }
	
	//判断演示模式
    if(isDemoMode(demo_mode)){
     showErrMsg("对不起！演示模式下不能进行此操作!");
     return false;
    }
    
	//提交表单
	var reqUrl = "/backstage/resources/save";
    if(!isEmpty($("#resourceId").val()) && $("#optype").val()!="copy"){
    	reqUrl = "/backstage/resources/edit";
    }
    var datas = $('#menuForm').serialize();    
    datas = datas+"&tm="+new Date().getTime();
    showConFirm('您确定的要提交吗？',function(){
       var index = showLoading();
       $.ajax({
   		type:'POST',
   		url:ccnetpath + reqUrl,
   		data:datas,
   		dataType:'json',
   		success:function(data, textStatus) {
   			if ("1" != data.res) {
   				showErrMsg(data.resMsg);
   				closeLayer(index);
   			}else{
   				showSucMsg(data.resMsg);
   				closeFrame(true);
   			}
   		}
   	  });
    });
}

//选择图标
function showIcon(){
	openPPage("选择图标",780,500,ccnetpath+"/backstage/resources/showIcon",false);
}

function seeResources(id){
   var ids = "";
   if(isEmpty(id)){
        var ids = getCKVal("ck");
		if(ids == null || ids.length==0){
			showWarnMsg("请先选择要操作的数据！");
			return;
		}
		if(ids.split(",").length>1){
			showWarnMsg("对不起！不能进行批量操作！");
			return;
		}
	  }else{
	    ids = id;
	  }
   openPage("查看资源",860,600,ccnetpath + "/backstage/resources/goedit?resource_id="+ids+"&optype=see");
}

function initSelectedVal(){
	if(isNull($("#resourceId").val())){
		$("#expanded").val("0");
		$("#isleaf").val("0");
		$("#resourceType").val("0");
		$("#resourceState").val("0");
	}
}


function delResources(id){
    var ids = "";
    if(isEmpty(id)){
      var ids = getCKVal("ck");
		 if(ids == null || ids.length==0){
			showWarnMsg("请先选择要操作的数据！");
			return;
		 }
	   }else{
	     ids = id;
	   }
    
    //判断演示模式
    if(isDemoMode(demo_mode)){
     showErrMsg("对不起！演示模式下不能进行此操作!");
     return false;
    }
    
	var param = "resource_id="+ids+"&now="+new Date().getTime();
	showConFirm('您确定要删除吗？',function(){
	    var index = showLoading();
	    $.ajax({
			   type: "POST",
			   cache : false,
			   url: ccnetpath+"/backstage/resources/trash",
			   data: param,
			   dataType:'json',
			   success:function(data, textStatus) {
	    			if ("1" != data.res) {
	    				showErrMsg(data.resMsg);
	    				closeLayer(index);
	    			}else{
	    				showSucMsg(data.resMsg);
	    				closeFrame(true);
	    			}
	    	    }
	     });
	});
}

function checkParentID(){
	//如果是修改 顶级节点不能修改
	var resourceId = $("#resourceId").val();
	var parentId = $("#parentCode").val();
	if(isNotNull(resourceId)){
		if(parentId=="01"){//顶级节点不能修改上级
			$('#parentName').attr("disabled",true);
			showTips("顶级资源节点不能修改上级!","parentName");
			return false;
		}
	}
	$('#parentName').attr("disabled",false);
	return true;
}
/************************************上级资源下拉树********************************/
var parentMenuSetting = {
    view: {
        dblClickExpand: false
    },
    data: {
        simpleData: {
            enable: true
        }
    },
    callback: {
        onClick: parentMenuOnClick
    }
};

function parentMenuOnClick(e, treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj("parentMenuTree");
    nodes = zTree.getSelectedNodes();
    //判断是否等于自己
    var node_id = nodes[0].id;
    var hid_node = $("#resourceCode").val();
    if(isNotNull(hid_node)){//修改节点
    	if(node_id==hid_node){
        	showTips("上级节点不能是自己!","parentName");
        	return false;
        }
        if(node_id.indexOf(hid_node)!= -1){
        	showTips("上级节点不能是自己的下级节点!","parentName");
        	return false;
        }
    }
    
    $("#parentCode").val(nodes[0].id);
    $("#parentName").val(nodes[0].name);
}

function showParentMenuTree(){
	if(checkParentID()){
	    $.ajax({
	        url:ccnetpath+"/backstage/resources/getMenutree",
	        type:"POST",
	        async:true,
	        dataType:"json",
	        success:function(msg){
	            var resNodes = eval("["+msg.obj+"]");
	            $.fn.zTree.init($("#parentMenuTree"), parentMenuSetting, resNodes);
	            var deptObj = $("#parentName");
	            var deptOffset = $("#parentName").offset();
	            $("#menuTreeContent").css({left:deptOffset.left + "px", top:deptOffset.top + deptObj.outerHeight() + "px"}).slideDown("fast");
	            $("#menuTreeContent").css({width:deptObj.outerWidth() + "px"});
	            var zTree = $.fn.zTree.getZTreeObj("parentMenuTree");
	            var node = zTree.getNodeByParam("id", $("#parentCode").val(), null);
	            zTree.selectNode(node);
	            //zTree.expandAll(true);
	            $("body").bind("mousedown", onBodyDownByMenuTree);
	        }
	    });
    }
}

function onBodyDownByMenuTree(event) {
    if(event.target.id.indexOf('switch') == -1){
        hideMenuTree();
    }
}

function hideMenuTree() {
    $("#menuTreeContent").fadeOut("fast");  
    $("body").unbind("mousedown", onBodyDownByMenuTree);
}

/************************************上级资源下拉树********************************/



/************************************上级资源下拉树********************************/
var menuTypeSetting = {
    view: {
        dblClickExpand: false
    },
    data: {
        simpleData: {
            enable: true
        }
    },
    callback: {
        onClick: menuTypeOnClick
    }
};

function menuTypeOnClick(e, treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj("menuTypeTree");
    nodes = zTree.getSelectedNodes();
    $("#parent_id").val(nodes[0].id);
    if(nodes[0].id=="01"){
    	$("#parent_name").val("全部分类");
    }else{
    	$("#parent_name").val(nodes[0].name);
    }
    $("#searchForm").submit();
}

function showMenuTypeTree(){
    $.ajax({
        url:ccnetpath+"/backstage/resources/getMenutree",
        type:"POST",
        async:true,
        dataType:"json",
        success:function(msg){
            var typeNodes = eval("["+msg.obj+"]");
            $.fn.zTree.init($("#menuTypeTree"), menuTypeSetting, typeNodes);
            var deptObj = $("#parent_name");
            var deptOffset = $("#parent_name").position();
            $("#menuTypeTreeContent").css({left:deptOffset.left + "px", top:deptOffset.top + deptObj.outerHeight() + "px"}).slideDown("fast");
            var zTree = $.fn.zTree.getZTreeObj("menuTypeTree");
            var node = zTree.getNodeByParam("id", $("#parent_id").val(), null);
            zTree.selectNode(node);
            //zTree.expandAll(true);
            $("body").bind("mousedown", onBodyDownByMenuTypeTree);
        }
    });
}

function onBodyDownByMenuTypeTree(event) {
    if(event.target.id.indexOf('switch') == -1){
    	hideMenuTypeTree();
    }
}

function hideMenuTypeTree() {
    $("#menuTypeTreeContent").fadeOut("fast");  
    $("body").unbind("mousedown", onBodyDownByMenuTypeTree);
}

/************************************上级资源下拉树********************************/

