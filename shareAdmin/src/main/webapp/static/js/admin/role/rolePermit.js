//JavaScript Document
$(function () {
    initMenuTree(znodes);
    //关闭窗口
	$("#closeBtn").click(function () {
		closeFrame(false);
	});
	
	//保存资源
	$("#saveBtn").click(function () {
		savePermit();
	});
	
});


function getCheckedNodes(treeNodes) {
    var checks_val = new Array();
	if (!treeNodes) return null;
	for (var i=0, l=treeNodes.length; i<l; i++) {
		checks_val.push(treeNodes[i].rid);
	}
	return checks_val;
}
		
		
 function savePermit(){
    //获取已经选择的节点
	var zTree = $.fn.zTree.getZTreeObj("roleMenutree");
    var tree_nodes =zTree.getCheckedNodes(true);
    var resource_id = getCheckedNodes(tree_nodes);
    if(isNull(resource_id)){
      showErrMsg("请选择角色授权资源！");
      return false;
    }
    
    //判断演示模式
    if(isDemoMode(demo_mode)){
     showErrMsg("对不起！演示模式下不能进行此操作!");
     return false;
    }
    
    //拼接参数
    var param = "role_id=" + $("#role_id").val() + "&resource_id="+resource_id+"&now="+new Date().getTime();
    showConFirm('您确定的要提交吗？',function(){
        var index = showLoading();
        $.ajax({
    		type:'POST',
    		url:ccnetpath + "/backstage/role/savepermit",
    		data:param,
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
 
function expandNode(type) {
   var zTree = $.fn.zTree.getZTreeObj("roleMenutree");
   if (type == "expandAll") {
		zTree.expandAll(true);
	} else if (type == "collapseAll"){
		zTree.expandAll(false);
	}
}

function checkAllNode(tp){
	var zTree = $.fn.zTree.getZTreeObj("roleMenutree");
	zTree.checkAllNodes(tp);
}

function initMenuTree(nodes){
	var setting = {
		check: {
			enable: true
		},
		data: {
			simpleData: {
				enable: true
			}
		},
		view: {
			fontCss: getFontCss
		}
	};
	setting.check.chkboxType = { "Y" : "ps", "N" : "" };
  //初始化
  var ztree = $.fn.zTree.init($("#roleMenutree"), setting, nodes);
  ztree.expandAll(true);
}
function updateNodes(highlight) {
	var zTree = $.fn.zTree.getZTreeObj("roleMenutree");
	for( var i=0, l=nodeList.length; i<l; i++) {
		nodeList[i].highlight = highlight;
		zTree.updateNode(nodeList[i]);
	}
}

function getFontCss(treeId, treeNode) {
	return (!!treeNode.highlight) ? {color:"red","font-weight":"normal"} : {color:"#333", "font-weight":"normal"};
}

function filter(node) {
	return !node.isParent && node.isFirstNode;
}

function enterSubmit(){ 
   if(event.keyCode==13){
      searchNode(e);
   }
}