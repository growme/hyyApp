//JavaScript Document
$(function () {
//	initGoodList();
	changeType();
	//点击增加按钮
	$("#th_add_btn").click(function () {
		//openPage("增加广告",730,720,ccnetpath+"/backstage/advertise/goadd");
		window.location.href=ccnetpath+"/backstage/sadvertise/goadd";
	});
	
	
	/*//图片绑定增加 
	$("#addPicTrBtn").click(function () {
		insertRows4($O('avPicTable'),25);
	});
	
	//图片绑定删除
	$("#delPicTrBtn").click(function () {
		del_row($O('avPicTable'));
	});*/
	
	//保存广告
	$("#saveBtn").click(function () {
		saveAdvertise();
	});
	
	//关闭窗口
	$("#closeBtn").click(function () {
		closeFrame(false);
	});
	
	//返回
	$("#backBtn").click(function () {
		closePFrame("/backstage/sadvertise/index",100);
	});
	//生成脚本
	$("#add").click(function () {
	    //获取当前网址，如： http://localhost:9527/zdss-web/login/login.do
	    var curWwwPath = window.document.location.href;
	 //   console.log("当前网址：" + curWwwPath);
	    
	    //获取主机地址之后的目录，如：zdss-web/login/login.do
	    var pathName = window.document.location.pathname;
	  //  console.log("当前路径：" + pathName);
	    
	    var pos = curWwwPath.indexOf(pathName);
	 //   console.log("路径位置：" + pos);
	    
	    //获取主机地址，如： http://localhost:9527
	    var localhostPath = curWwwPath.substring(0, pos);
	    console.log("当前主机地址：" + localhostPath);
		var adId=$("#adId").val();
		var urlStr = localhostPath+ccnetpath+"/backstage/sadvertise/goadScript.js?adId="+ adId;
		var scriptStr="<script type=\"text/javascript\" src=\"" + urlStr + "\"><\/script>";
		$("#adScript").val(scriptStr);
		$("#demojs").val(urlStr);
	});
	$("#adType").change(function(){
		changeType();
	});
	
});


//改变类型
function changeType(){
	
	if($("#adType").val()==1){
		
		$("#adPicTr1").hide();	
		 $("#adLinkTr").hide();	
		 $("#adPicTr2").hide();	
		 $("#adScriptTr").show();
		 $("#add").show();
		 $("#fuzhi").show();
		if(isEmpty($("#adId").val())){//判断新增时为隐藏
			 $("#chakan").hide();
		}else{
			 $("#chakan").show();
		}
		
	}
 if($("#adType").val()==2){

	 $("#adPicTr1").show();	
	 $("#adLinkTr").show();	
	 $("#adPicTr2").show();	
	 $("#adScriptTr").hide();
	 $("#add").hide();
	 $("#fuzhi").hide();
	 $("#chakan").hide();
	}
}
/*$("#chakan").click(function(){ 
	var adId=$("#adId").val();
	openPage("查看广告",730,720,ccnetpath+"/backstage/advertise/goedit?adId="+adId);
	
});*/
$("#chakan").click(function(){
    var demoJs = $("#demojs").val();
    if(!isEmpty(demoJs)){
    	openPage("查看广告",730,720,ccnetpath+"/backstage/sadvertise/goedit?adId="+$("#adId").val());
    }else{
      showTErrMsg("请先生成脚本后查看样例!");
      return false;
    }
});

//选择图片
function changePic(){
	$("#up").trigger("click");
}
function changeUrl(){
	var url=$("#up").val();
	$("#txt_pic").val(url);
	
	var file=document.querySelector('input[type=file]').files[0];
	var reader=new FileReader();
	var filePath=reader.readAsDataURL(file);
	reader.onloadend=function(){
			$("#ImgPr").attr("src",reader.result);
	};
	if(file){
		$("#ImgPr").attr("src",filePath);
	}

}


//修改广告
function editAdvertise(id){
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
  //openPage("修改广告",730,710,ccnetpath+"/backstage/advertise/goedit?advertId="+ids);
  window.location.href=ccnetpath+"/backstage/sadvertise/goedit?advertId="+ids;
}

//判断是否为空
function checkNull(){
			// 遍历一行中的input
			$(this).find("[isNull='NO']").each(function(j){
				if(j == 0 && !$(this).val()){
					checked = false;
					$(this).focus();
					alert("自定义字段名称不可为空！");
					return;
				}
			});
}

//保存广告
function saveAdvertise(){
	
	if(isEmpty($("#adTitle").val())){
		showTips("广告标题不能为空!","adTitle");
        return false;
    }
	
	/*
	if(isEmpty($("#adLink").val())){
		if($("#adType").val()==2){
		showTips("广告地址不能为空!","adLink");
        return false;
		}
    }
	*/
	if (isEmpty($("#adPosition").val())) {
		showTips("广告位置不能为空!", "adPosition");
		return false;
	}

	if (isEmpty($("#adScript").val())) {
		showTips("广告脚本不能为空!", "adScript");
		return false;
	}
	
	if(isEmpty($("#sortNo").val())){
		showTips("排序编号不能为空!","sortNo");
        return false;
    }
	
	if(isEmpty($("#state").val())){
		showTips("排序编号不能为空!","state");
        return false;
    }
	
	/*
	if(isEmpty($("#adTag").val())){
		showTips("广告标签不能为空！","adTag");
        return false;
    }
	if(isEmpty($("#readNum").val())){
		showTips("阅读数量不能为空!","readNum");
        return false;
    }
	if(isEmpty($("#adPic").val())){
		if($("#adType").val()==2){
		showTips("请选择广告图片！","adPic");
        return false;
		}
    }
    */
	
	
	//提交表单
	var reqUrl = "/backstage/sadvertise/save";

    showTConFirm('您确定的要提交吗？',function(){
    	//alert($("#advertiseForm").serialize());
	    var index = showTLoading();
	    $("#advertiseForm").ajaxSubmit({
	        type: 'POST',
	        dataType: 'json',
	        url: ccnetpath + reqUrl,
	        data: $("#advertiseForm").serialize(),
	        success: function(data) {
	        	if ("1" != data.res) {
	   				showTErrMsg(data.resMsg);
	   				closeLayer(index);
	   			}else{
	   				//alert(data.resMsg);
	   				showTSucMsg(data.resMsg);
	   				//closePFrame("/backstage/advertise/index",500);
	   				setTimeout("window.location.href=ccnetpath+'/backstage/sadvertise/index';", 600);
	   			
	   			}
	        }
	     });
    });
    return false;
}


//删除广告
function trashAdvertise(id){
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
    
	var param = "advertId="+ids+"&now="+new Date().getTime();
	showConFirm('您确定的要删除吗？',function(){
        var index = showLoading();
        $.ajax({
			   type: "POST",
			   cache : false,
			   url: ccnetpath+"/backstage/sadvertise/trash",
			   data: param,
			   dataType:'json',
			   success:function(data, textStatus) {
	    			if ("1" != data.res) {
	    				showErrMsg(data.resMsg);
	    				closeLayer(index);
	    			}else{
	    				showSucMsg(data.resMsg);
	    				setTimeout("window.location.href=ccnetpath+'/backstage/sadvertise/index';", 600);
	    				
	    			}
	    		}
	     });
	});
}

