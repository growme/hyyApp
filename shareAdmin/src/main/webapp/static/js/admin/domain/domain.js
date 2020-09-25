//JavaScript Document
$(function () {
	
	//点击增加
	$("#th_add_btn").click(function () {
		openPage("增加域名",400,320,ccnetpath+"/backstage/domain/goadd");
	});
	
	//泛解析
	$("#th_parse_btn").click(function () {
		openPage("域名泛解析",400,340,ccnetpath+"/backstage/domain/geparse");
	});
	
	$("#enabled").change(function () {
		$("#searchForm").submit();
	});
	
	$("#domainType").change(function () {
		$("#searchForm").submit();
	});
	
	//点击修改
	$("#th_edit_btn").click(function () {
        var ids = getCKVal("ck");
		if(ids == null || ids.length==0){
			showErrMsg("请先选择要操作的数据！");
			return;
		}
		if(ids.split(",").length>1){
			showErrMsg("对不起！不能进行批量更新！");
			return;
	    }
        openPage("修改域名",400,320,ccnetpath + "/backstage/domain/goedit?domain_id="+ids);
	});
	
	//点击删除
	$("#th_trash_btn").click(function () {
		trashDomain();
	});
	
	$("#closeBtn").click(function () {
		closeFrame(false);
	});
	
	//保存域名
	$("#saveBtn").click(function () {
		saveDomain();
	});
	
	//保存子域名
	$("#saveParseBtn").click(function () {
		saveParseDomain();
	});
});


function saveDomain(){
	
	var domainId = $("#domainId").val();
    var domainName = $("#domainName").val();
    var orderNumber = $("#orderNumber").val();
    if(isEmpty(domainName)){
       showErrMsg("对不起，请输入域名内容!");
       return false;
    }
    
    if(isEmpty(orderNumber)){
        showErrMsg("对不起，请输入排序编号!");
        return false;
     }else{
     	if(!isNumber(orderNumber)){
     	    showErrMsg("排序编号只能为数字，请再次输入!");
     	    return false;
     	}
     }
    
    if(isEmpty($("#enabled").val())){
        showErrMsg("对不起，请选择域名状态!");
        return false;
     }
    
    var reqUrl = "/backstage/domain/save";
    if(!isEmpty(domainId)){
    	reqUrl = "/backstage/domain/edit";
    }
    
    //判断演示模式
    if(isDemoMode(demo_mode)){
       showErrMsg("对不起！演示模式下不能进行此操作!");
       return false;
    }
    
    var datas = $("#domainForm").serialize();
    datas = datas+"&tm="+new Date().getTime();
	showConFirm('您确定的要提交吗？',function(){
	    var index = showLoading();
	    $("#domainForm").ajaxSubmit({
		        type: 'POST',
		        dataType: 'json',
		        url: ccnetpath + reqUrl,
		        success: function(data) {
		        	if ("1" != data.res) {
		        		closeLayer(index);
		   				showErrMsg(data.resMsg);
		   			}else{
		   				showSucMsg(data.resMsg);
		   				closeFrame(true);
		   			}
		        }
		     });
	    });
	    return false;
}

//保存泛解析
function saveParseDomain(){
	var domainName = $("#domainName").val();
	var domainType = $("#domainType").val();
    var domainNumber = $("#domainNumber").val();
    var domainPrefix = $("#domainPrefix").val();
    var domainLength = $("#domainLength").val();
    
    if(isEmpty(domainName)){
       showErrMsg("请输入您已经泛解析（*）的主域名!");
       return false;
    }else{
    	if(domainName.indexOf("www.")!=-1){
    	   $("#domainName").focus();
     	   showErrMsg("对不起，请去掉域名前的www.!");
     	   return false;
     	}
     }
    
    if(isEmpty($("#domainType").val())){
        showErrMsg("对不起，请选择域名类型!");
        return false;
     }
    
    if(isEmpty($("#domainPrefix").val())){
        showErrMsg("对不起，请选择子域名前缀类型!");
        return false;
     }
    
    if(isEmpty($("#domainLength").val())){
        showErrMsg("对不起，请选择子域名前缀长度!");
        return false;
     }
    
    if(isEmpty($("#domainNumber").val())){
        showErrMsg("对不起，请选择生成子域名前个数!");
        return false;
     }
    
    //判断演示模式
    if(isDemoMode(demo_mode)){
       showErrMsg("对不起！演示模式下不能进行此操作!");
       return false;
    }
    
    var datas = $("#parseDomainForm").serialize();
    datas = datas+"&tm="+new Date().getTime();
    showConFirm('您确定的要提交吗？',function(){
        var index = showLoading();
        $.ajax({
			   type: "POST",
			   cache : false,
			   url: ccnetpath+"/backstage/domain/saveParse",
			   data: datas,
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

function trashDomain(id){
    var ids = "";
    if(isEmpty(id)){
      var ids = getCKVal("ck");
		 if(ids == null || ids.length==0){
			showTWarnMsg("请先选择要操作的数据！");
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
      
	  var param = "domain_id="+ids+"&now="+new Date().getTime();
	  showTConFirm('您确定的要删除吗？',function(){
	        var index = showLoading();
	        $.ajax({
				   type: "POST",
				   cache : false,
				   url: ccnetpath+"/backstage/domain/trash",
				   data: param,
				   dataType:'json',
				   success:function(data, textStatus) {
		    			if ("1" != data.res) {
		    				showTErrMsg(data.resMsg);
		    				closeLayer(index);
		    			}else{
		    				showTSucMsg(data.resMsg);
		    				closeFrame(true);
		    			}
		    	    }
		     });
		});
}

function editDomain(id){
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
   openPage("修改域名",400,320,ccnetpath + "/backstage/domain/goedit?domain_id="+ids);
}


