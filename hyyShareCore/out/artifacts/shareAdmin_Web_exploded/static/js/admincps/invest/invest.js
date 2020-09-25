//JavaScript Document
$(function () {
	//点击增加按钮
	$("#th_add_btn").click(function () {
		openPage("增加会员",400,400,ccnetpath+"/backstage/invest/goadd");
	});
	
	$("#invest_state").change(function () {
		$("#searchForm").submit();
	});
	
	//点击删除按钮
	$("#th_del_btn").click(function () {
		delinvest();
	});
	
	//点击锁定按钮
	$("#th_lock_btn").click(function () {
		freezeAccount('',1);
	});
	
	//点击激活按钮
	$("#th_unlock_btn").click(function () {
		freezeAccount('',0);
	});
	
	//点击重置按钮
	$("#th_reset_btn").click(function () {
		resetinvestPwd();
	});
	
	//关闭窗口
	$("#closeBtn").click(function () {
		closeFrame(false);
	});
	
	//点击删除按钮
	$("#th_trash_btn").click(function () {
		trashinvest();
	});
	
	//保存注册信息
	$("#saveZcxxBtn").click(function () {
		saveinvest();
	});
	
	//保存密码
	$("#resetBtn").click(function () {
		saveinvestPwd();
	});
	
	if(isNull($('#investId').val())){
		$('#userState').val("0");
	}
	
});


function checkMoney(obj){
	if(isNull(obj.value)){
		obj.focus();
	   showErrMsg("请输入整数或者小数!");
	   return false;
	}
	if(!isPriceNumber(obj.value)){
	   obj.value="";
	   obj.focus();
	   showErrMsg("只能输入整数或者小数!");
	   return false;
	}
}

function saveinvest(){
	
	if(isEmpty($("#userId").val())){
       showErrMsg("对不起，请选择广告主!");
       return false;
    }

	if(isEmpty($("#money").val())){
       showErrMsg("对不起，请输入金额!");
       return false;
    }
	
    var reqUrl = "/backstage/invest/save";
    if(!isEmpty($("#id").val())){
    	reqUrl = "/backstage/invest/edit";
    }
    showConFirm('您确定的要提交吗？',function(){
    var index = showLoading();
    $("#baseSettingForm").ajaxSubmit({
       type: 'POST',
       dataType: 'json',
       url: ccnetpath + reqUrl,
       data: {
           'flag':$("#flag").val()
       },
       success: function(data) {
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

//修改用户
function editinvest(id){
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
   openPage("修改会员",400,400,ccnetpath+"/backstage/invest/goedit?id="+ids);
   //openTab("/backstage/invest/goedit?invest_id="+ids+"&flag=0");
}

function freezeAccount(id,st){
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
    
	   var mst= "锁定账号将无法登录，确定继续?";
	   if(st==0){
	      mst= "您确定要激活用户账号?";
	   }
	   
	    //判断演示模式
	    if(isDemoMode(demo_mode)){
	     showErrMsg("对不起！演示模式下不能进行此操作!");
	     return false;
	    }
	    
	    var param = "invest_id="+ids+"&user_state="+st+"&now="+new Date().getTime();
	    showConFirm(mst,function(){
        var index = showLoading();
        $.ajax({
		   type: "POST",
		   url: ccnetpath + "/backstage/invest/freeze",
		   data: param,
		   dataType:'json',
		   success:function(data, textStatus) {
			    //data = JSON.parse(data);
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



function trashinvest(id){
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
	    
	    var param = "invest_id="+ids+"&now="+new Date().getTime();
	    showConFirm("您确定要删除会员？",function(){
        var index = showLoading();
        $.ajax({
		   type: "POST",
		   url: ccnetpath + "/backstage/invest/trash",
		   data: param,
		   dataType:'json',
		   success:function(data, textStatus) {
			    //data = JSON.parse(data);
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

