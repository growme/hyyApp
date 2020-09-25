//JavaScript Document
$(function () {
	//initGoodList();
	//点击增加按钮
	$("#th_add_btn").click(function () {
		openPage("增加栏目",350,240,ccnetpath+"/backstage/column/goadd");
	});
	

	//保存栏目
	$("#saveBtn").click(function () {
		saveColumn();
	});
	
	//关闭窗口
	$("#closeBtn").click(function () {
		closeFrame(false);
	});
	
	//返回
	$("#backBtn").click(function () {
		closePFrame("/backstage/column/index",100);
	});
	
	
});


//修改栏目
function editColumn(id){
   var ids = "";
   if(isEmpty(id)){
    var ids = getCKVal("ck");
	if(ids == null || ids.length==0){
		showTWarnMsg("请先选择要操作的数据！");
		return;
	}
	if(ids.split(",").length>1){
		showTWarnMsg("对不起！不能进行批量操作！");
			return;
	}
  }else{
    ids = id;
  }
 openPage("修改栏目",350,240,ccnetpath+"/backstage/column/goedit?columnId="+ids);
  //window.location.href=ccnetpath+"/backstage/column/goedit?columnId="+ids;
}


//保存栏目
function saveColumn(){
	
	if(isEmpty($("#columnName").val())){
		showTips("栏目名称不能为空!","columnName");
        return false;
    }

	if(isNullStr($("#columnType").val())){
		showTips("请选择栏目类型!","columnType");
		return false;
	}

	if(isEmpty($("#orderNo").val())){
		showTips("请设置排序编号!","orderNo");
		return false;
	}

	//提交表单
	var reqUrl = "/backstage/column/save";
    showConFirm('您确定的要提交吗？',function(){
	    var index = showLoading();
	    $.ajax({
	        type: 'POST',
	        dataType: 'json',
	        url: ccnetpath + reqUrl,
	        data:$("#columnForm").serialize(),
	        success: function(data) {
	        	if ("1" != data.res) {
	   				showErrMsg(data.resMsg);
	   				closeLayer(index);
	   			}else{
	   				showSucMsg(data.resMsg);
	   				closePFrame("/backstage/column/index",500);
	   			}
	        }
	     });
    });
    return false;
}


//删除栏目
function trashColumn(id){
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
    
	var param = "columnId="+ids+"&now="+new Date().getTime();
	showConFirm('您确定的要删除吗？',function(){
        var index = showLoading();
        $.ajax({
			   type: "POST",
			   cache : false,
			   url: ccnetpath+"/backstage/column/trash",
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


/************************************上级资源下拉树********************************/
var typeSetting = {
    view: {
        dblClickExpand: false
    },
    data: {
        simpleData: {
            enable: true
        }
    },
    callback: {
        onClick: typeOnClick
    }
};

function typeOnClick(e, treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj("typeTree");
    nodes = zTree.getSelectedNodes();
    $("#typeId").val(nodes[0].id);
    $("#typeName").val(nodes[0].name);
    //获取文案信息
    initGoodList();
}

function showTypeTree(){
    $.ajax({
        url:ccnetpath+"/backstage/goodtype/getTypetree",
        type:"POST",
        async:true,
        dataType:"json",
        success:function(msg){
        	if(msg.res==1){
        		var typeNodes = eval("["+msg.obj+"]");
                $.fn.zTree.init($("#typeTree"), typeSetting, typeNodes);
                var deptObj = $("#typeName");
                var deptOffset = $("#typeName").position();
                $("#typeTreeContent").css({left:deptOffset.left + "px", top:deptOffset.top + deptObj.outerHeight() + "px"}).slideDown("fast");
                $("#typeTreeContent").css({width:deptObj.outerWidth() + "px"});
                
                var zTree = $.fn.zTree.getZTreeObj("typeTree");
                var node = zTree.getNodeByParam("id", $("#typeId").val(), null);
                zTree.selectNode(node);
                //zTree.expandAll(true);
                $("body").bind("mousedown", onBodyDownByTypeTree);
        	}else{
        		showTErrMsg(msg.resMsg);
        		return false;
        	}
            
        }
    });
}

function onBodyDownByTypeTree(event) {
    if(event.target.id.indexOf('switch') == -1){
    	hideTypeTree();
    }
}

function hideTypeTree() {
    $("#typeTreeContent").fadeOut("fast");  
    $("body").unbind("mousedown", onBodyDownByTypeTree);
}

/************************************上级资源下拉树********************************/

