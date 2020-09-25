//JavaScript Document
$(function () {
	changeType();
	//点击增加按钮
	$("#th_add_btn").click(function () {
		window.location.href=ccnetpath+"/backstage/advertise/goadd";
	});
	
	$("#th_del_btn").click(function () {
	   trashAdvertise();
	});
	
	$("#th_batch_up_btn").click(function () {
		batchUpAdvertise();
	});
	
	$("#th_batch_down_btn").click(function () {
		batchDownAdvertise();
	});
	
	//标题绑定增加 
	$("#addTrBtn").click(function () {
		insertRows3($O('avTitleTable'),24);
	});
	
	//标题绑定删除
	$("#delTrBtn").click(function () {
		del_row($O('avTitleTable'));
	});
	
	//图片绑定增加 
	$("#addPicTrBtn").click(function () {
		insertRows4($O('avPicTable'),25);
	});
	
	//图片绑定删除
	$("#delPicTrBtn").click(function () {
		del_row($O('avPicTable'));
	});
	
	//保存广告
	$("#saveBtn").click(function () {
		saveAdvertise();
	});
	
	//保存广告
	$("#saveAdUrlBtn").click(function () {
		saveAdvertiseUrl();
	});
	
	//关闭窗口
	$("#closeBtn").click(function () {
		closeFrame(false);
	});
	
	//返回
	$("#backBtn").click(function () {
		closePFrame("/backstage/advertise/index",100);
	});
	
	//下拉联动
	$("#state").change(function () {
		$("#searchForm").submit();
	});
	$("#adTag").change(function () {
		$("#searchForm").submit();
	});
	$("#adType").change(function () {
		$("#searchForm").submit();
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
		var urlStr = localhostPath+ccnetpath+"/backstage/advertise/goadScript.js?adId="+ adId;
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


$("#chakan").click(function(){
    var demoJs = $("#demojs").val();
    if(!isEmpty(demoJs)){
    	openPage("查看广告",730,720,ccnetpath+"/backstage/advertise/goedit?adId="+$("#adId").val());
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
  window.location.href=ccnetpath+"/backstage/advertise/goedit?advertId="+ids;
}

//修改广告
function editUrlAdvertise(id){
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
  window.location.href=ccnetpath+"/backstage/advertise/goeditUrl?advertId="+ids;
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
	
	if(isEmpty($("#adTag").val())){
		showTips("广告标签不能为空！","adTag");
        return false;
    }
	
	if(isEmpty($("#readNum").val())){
		showTips("阅读数量不能为空!","readNum");
        return false;
    }
	
	if(isEmpty($("#adType").val())){
		showTips("广告类型不能为空!","adType");
        return false;
    }
	
	if(isEmpty($("#adLink").val())){
		showTips("广告地址不能为空!","adLink");
        return false;
    }else{
    	if($("#adLink").val().indexOf("http://") < 0  && $("#adLink").val().indexOf("https://") < 0){
    		showTips("广告地址不正确，请重新填写!","adLink");
            return false;
    	}
    }
	
	if(isEmpty($("#sortNo").val())){
		showTips("排序编号不能为空!","sortNo");
        return false;
    }

	if (isEmpty($("#state").val())) {
		showTips("广告状态不能为空!", "state");
		return false;
	}
	if (isEmpty($("#startTime").val())) {
		showTips("投放开始时间不能为空!", "startTime");
		return false;
	}
	if (isEmpty($("#endTime").val())) {
		showTips("投放结束时间不能为空!", "endTime");
		return false;
	}
	
	if($("#startTime").val()>=$("#endTime").val()){
		showTips("结束时间不能小于开始时间!", "endTime");
		return false;
	}
	
	if (isEmpty($("#endDate").val())) {
		showTips("结束时间不能为空!", "endDate");
		return false;
	}
	
	var titleParam = delTitleParam();
	//alert(titleParam);
	if(titleParam==null || titleParam.length<1){
		showTErrMsg("至少需要一个广告标题");
        return false;
	}
	
	var picParam = delPicParam();
	//alert(picParam);
	if(picParam==null || picParam.length<1){
		showTErrMsg("至少需要一张广告图片");
        return false;
	}
	
	//提交表单
	var reqUrl = "/backstage/advertise/save";
    showTConFirm('您确定的要提交吗？',function(){
    	//alert($("#advertiseForm").serialize());
	    var index = showTLoading();
	    $("#advertiseForm").ajaxSubmit({
	        type: 'POST',
	        dataType: 'json',
	        url: ccnetpath + reqUrl,
	        data: {
	          'adTitle': arr2Str(titleParam,"<<<>>>"),
	          'adPic': arr2Str(picParam,"<<<>>>"),
	        },
	        success: function(data) {
	        	if ("1" != data.res) {
	   				showTErrMsg(data.resMsg);
	   				closeLayer(index);
	   			}else{
	   				showTSucMsg(data.resMsg);
	   				closePFrame("/backstage/advertise/index",500);
	   			}
	        }
	     });
    });
    return false;
}

//保存广告
function saveAdvertiseUrl(){
	
	if(isEmpty($("#adLink").val())){
		showTips("广告地址不能为空!","adLink");
        return false;
    }else{
    	if($("#adLink").val().indexOf("http://") < 0  && $("#adLink").val().indexOf("https://") < 0){
    		showTips("广告地址不正确，请重新填写!","adLink");
            return false;
    	}
    }
	
	//提交表单
	var reqUrl = "/backstage/advertise/updateUrl";
    showTConFirm('您确定的要提交吗？',function(){
    	//alert($("#advertiseForm").serialize());
	    var index = showTLoading();
	    $("#advertiseForm").ajaxSubmit({
	        type: 'POST',
	        dataType: 'json',
	        url: ccnetpath + reqUrl,
	        success: function(data) {
	        	if ("1" != data.res) {
	   				showTErrMsg(data.resMsg);
	   				closeLayer(index);
	   			}else{
	   				showTSucMsg(data.resMsg);
	   				closePFrame("/backstage/advertise/index",500);
	   			}
	        }
	     });
    });
    return false;
}

//提交前标题参数处理
function delTitleParam(){
	var avTitle="";
    var result = new Array();
    var rlen = $O('avTitleTable').rows.length;
    for(var i=0;i<rlen-1;i++){
       var title = $("#avTitle_"+(i+1)).val();
       if(!isNull(title)){
    	   result.push(title);
       }
    }
    return uniqueArr(result);
}

//提交前图片参数处理
function delPicParam(){
	var avPic="";
    var result = new Array();
    var rlen = $O('avPicTable').rows.length;
    for(var i=0;i<rlen-1;i++){
    	var avPic = $("#avPic_"+(i+1)).val();
       if(!isNull(avPic)){
    	   result.push(avPic);
       }
    }
    return uniqueArr(result);
}



//批量上架
function batchUpAdvertise(id){
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
	showConFirm('您确定要上架吗？',function(){
        var index = showLoading();
        $.ajax({
			   type: "POST",
			   cache : false,
			   url: ccnetpath+"/backstage/advertise/batchup",
			   data: param,
			   dataType:'json',
			   success:function(data, textStatus) {
	    			if ("1" != data.res) {
	    				showErrMsg(data.resMsg);
	    				closeLayer(index);
	    			}else{
	    				showSucMsg(data.resMsg);
	    				closePFrame("/backstage/advertise/index",500);
	    			}
	    		}
	     });
	});
}

//批量下架架
function batchDownAdvertise(id){
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
	showConFirm('您确定要下架吗？',function(){
        var index = showLoading();
        $.ajax({
			   type: "POST",
			   cache : false,
			   url: ccnetpath+"/backstage/advertise/batchdown",
			   data: param,
			   dataType:'json',
			   success:function(data, textStatus) {
	    			if ("1" != data.res) {
	    				showErrMsg(data.resMsg);
	    				closeLayer(index);
	    			}else{
	    				showSucMsg(data.resMsg);
	    				closePFrame("/backstage/advertise/index",500);
	    			}
	    		}
	     });
	});
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
			   url: ccnetpath+"/backstage/advertise/trash",
			   data: param,
			   dataType:'json',
			   success:function(data, textStatus) {
	    			if ("1" != data.res) {
	    				showErrMsg(data.resMsg);
	    				closeLayer(index);
	    			}else{
	    				showSucMsg(data.resMsg);
	    				setTimeout("window.location.href=ccnetpath+'/backstage/advertise/index';", 600);
	    				
	    			}
	    		}
	     });
	});
}

//获取匹配的图片集合
function getAllImgDataSrc(cls,attr){
	var arr = new Array();
	$("."+cls).each(function() {
		arr.push($(this).attr(attr));
	});
	return arr;
}

/**
 * 删除广告图片
 */
function trashContentPic(cid,pic){
  if(isEmpty(cid) || isEmpty(pic)){
	  showTWarnMsg("对不起，参数错误");
	  return false;
  }
  
  var imgs = getAllImgDataSrc("gpSeeImg","data-src");
  if(imgs.length==1){
	  showTWarnMsg("对不起，至少需要一张文章预览图片！");
	  return false;
  }
  
  //判断演示模式
  if(isDemoMode(demo_mode)){
   showErrMsg("对不起！演示模式下不能进行此操作!");
   return false;
  }
  
  var param = "aId="+cid+"&picPath="+pic+"&now="+new Date().getTime();
  showTConFirm('您确定要删除图片吗？',function(){
        var index = showTLoading();
        $.ajax({
			   type: "POST",
			   cache : false,
			   url: ccnetpath+"/backstage/advertise/file/trash",
			   data: param,
			   dataType:'json',
			   success:function(data, textStatus) {
	    			if ("1" != data.res) {
	    				closeLayer(index);
	    				showTErrMsg(data.resMsg);
	    			}else{
	    				closeLayer(index);
	    				showTSucMsg("删除图片成功");
	    				//移除被删除的图片层
	    				$("#lb_index_"+data.resMsg).remove();
	    				//替换路径
	    				var pics = arr2String(getAllImgDataSrc("gpSeeImg","data-src"));
	    				if(!isNull(pics)){
	    					$("#advertPics").val(pics);
	    				}
	    			}
	    		}
	     });
	});
}

//图片预览
function showContentPic(pic){
	layer.open({
	  type: 1,
	  title: false,
	  offset:80,
	  closeBtn: 0,
	  skin: 'layui-layer-nobg', //没有背景色
	  shadeClose: true,
	  content: '<div id="qrgpic"><img src="'+pic+'" class="cimg"></div>'
	});
}


//图片预览
function setPicPreview(obj,mid,w,h) {
	var srcImg = obj.value;
	var imgObjPreview=document.getElementById(mid);
	if(!isNull(srcImg)){
		if(checkImgType(obj)){
			imgObjPreview.style.display = 'block';
	        imgObjPreview.style.width = w+"px";
	        imgObjPreview.style.height = h+'px';
	        imgObjPreview.src = srcImg;
		}else{
			imgObjPreview.src=ccnetpath+"/static/img/no_pic.png"
		}
	}
}
