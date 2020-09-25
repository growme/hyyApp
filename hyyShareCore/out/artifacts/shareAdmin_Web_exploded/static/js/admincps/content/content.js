//JavaScript Document
$(function () {
	//点击增加按钮
	$("#th_add_btn").click(function () {
		window.location.href=ccnetpath+"/backstage/content/goadd";
	});
	
	//保存
	$("#saveBtn").click(saveContent);
	
	//批量删除
	$("#td_del_btn").click(function () {
		trashContent();
	});
	
	//采集文章
	$("#th_gather_btn").click(function () {
		goGatherContent();
	});
	
	//关闭窗口
	$("#closeBtn").click(function () {
		closeFrame(false);
	});
	
	//开始采集
	$("#gatherBtn").click(function () {
		gatherContent(false);
	});
	
	//发布文章
	$("#th_release_btn").click(function () {
		releaseContent();
	});
	
	//取消发布文章
	$("#th_unrelease_btn").click(function () {
		unReleaseContent();
	});
	
	//下拉联动
	$("#checkState").change(function () {
		$("#searchForm").submit();
	});
	
	$("#contentType").change(function () {
		$("#searchForm").submit();
	});
	
	//返回
	$("#backBtn").click(function () {
		closePFrame("/backstage/content/index",100);
	});
	
});

//采集文章
function goGatherContent(){
	window.location.href=ccnetpath+"/backstage/content/gather";
	//openPage("采集微信文章",600,400,ccnetpath+"/backstage/content/wechat/gather");
}

//修改
function editContent(id){
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
  window.location.href=ccnetpath+"/backstage/content/goedit?contentId="+ids;
}


//保存
function saveContent(){
	
	var ctype = $("#contentType").val();
	if(isNullStr(ctype)){
		showTips("请选择文章类型!","contentType");
        return false;
    }
	
	if(isEmpty($("#contentTitle").val())){
		showTips("文章主标题不能为空!","contentTitle");
        return false;
    }
	
	if(isEmpty($("#contentSbTitle").val())){
		showTips("文章副标题不能为空!","contentSbTitle");
        return false;
    }

	
	if(isEmpty($("#orderNo").val())){
		showTips("排序编号不能为空!","orderNo");
        return false;
    }
   if(isEmpty($("#weixinLink").val())){
		
    }else{
    	if(isValidUrl($("#weixinLink").val())|isUrl($("#weixinLink").val())){
    		
    	}else{
    		showTips("路径或URL不合法!","weixinLink");
    		$("#weixinLink").val("");
    		return false;
    	}
    }
	if(isEmpty($("#visualReadNum").val())){
		$("#visualReadNum").val(10000)
		//showTips("阅读数不能为空!","sharNum");
        //return false;
    }

	if(isEmpty($("#clickNum").val())){
		showTips("点赞数不能为空!","clickNum");
        return false;
    }
	
	if(isEmpty($("#readAward").val())){
		//showTips("浏览奖励不能为空!","readAward");
        //return false;
    }
	
	if(isEmpty($("#friendShareAward").val())){
		//showTips("请设置微信好友分享奖励!","friendShareAward");
        //return false;
    }
	
	if(isEmpty($("#timelineShareAward").val())){
		//showTips("请设置微信朋友圈分享奖励!","timelineShareAward");
        //return false;
    }
	
   	if(isEmpty($("#contentPics").val()) && isEmpty($("#contentPicLink").val())){
		showTips("请先上传图片或者使用外链图片!","contentPicLink");
        return false;
    }
	
	if(!isEmpty(ctype) && ctype==1){
		if(isEmpty($("#videoLink").val())){
		showTips("请输入腾讯视频VID!","videoLink");
        return false;
		}
    }
	
	var content = UE.getEditor('content').getContent();
	if(isEmpty(content)){
		showTips("公告内容不能为空!","content");
        return false;
    }

	//提交表单
	var reqUrl = "/backstage/content/save";
	showTConFirm('您确定的要提交吗？',function(){
	    var index = showTLoading();
	    $("#contentForm").ajaxSubmit({
	        type: 'POST',
	        dataType: 'json',
	        url: ccnetpath + reqUrl,
	        data: {
	            'contentText': content
	        },
	        success: function(data) {
	        	if ("1" != data.res) {
	   				showTErrMsg(data.resMsg);
	   				closeLayer(index);
	   			}else{
	   				showTSucMsg(data.resMsg);
	   				closePFrame("/backstage/content/index",500);
	   			}
	        }
	     });
    });
	
    return false;
}


//采集文章
function gatherContent(){
	
    var columnId = $("#columnId").val();
    var weixinLink = $("#weixinLink").val();
    if(isNull(columnId)){
    	showTips("请先选择文章所属栏目类型!","columnId");
        return false;
    }
    if(isNull(weixinLink)){
    	showTips("请输入公众号文章的URL地址!","weixinLink");
        return false;
    }
    
    //提交表单
	var reqUrl = "/backstage/content/gather/wechat";
	showTConFirm('您确定的要提交吗？',function(){
	    var index = showTLoading();
	    $("#contentForm").ajaxSubmit({
	        type: 'POST',
	        dataType: 'json',
	        url: ccnetpath + reqUrl,
	        data: {
	            'now': new Date().getTime()
	        },
	        success: function(data) {
	        	if ("1" != data.res) {
	   				showTErrMsg(data.resMsg);
	   				closeLayer(index);
	   			}else{
	   				showTSucMsg(data.resMsg);
	   				closePFrame("/backstage/content/index",500);
	   			}
	        }
	     });
    });
	
    return false;
}


//发布文章
function releaseContent(id){
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
    
	var param = "contentId="+ids+"&now="+new Date().getTime();
	showConFirm("您确定要发布文章吗？",function(){
        var index = showLoading();
        $.ajax({
			   type: "POST",
			   cache : false,
			   url: ccnetpath + "/backstage/content/release",
			   data: param,
			   dataType:'json',
			   success:function(data, textStatus) {
	    			if ("1" != data.res) {
	    				showErrMsg(data.resMsg);
	    				closeLayer(index);
	    			}else{
	    				showSucMsg(data.resMsg);
	    				closePFrame("/backstage/content/index",500);
	    			}
	    		}
	     });
	});
}


//发布文章
function unReleaseContent(id){
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
    
	var param = "contentId="+ids+"&now="+new Date().getTime();
	showConFirm("您确定要取消发布文章吗？",function(){
        var index = showLoading();
        $.ajax({
			   type: "POST",
			   cache : false,
			   url: ccnetpath + "/backstage/content/unrelease",
			   data: param,
			   dataType:'json',
			   success:function(data, textStatus) {
	    			if ("1" != data.res) {
	    				showErrMsg(data.resMsg);
	    				closeLayer(index);
	    			}else{
	    				showSucMsg(data.resMsg);
	    				closePFrame("/backstage/content/index",500);
	    			}
	    		}
	     });
	});
}

//删除文章
function trashContent(id){
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
    
	var param = "contentId="+ids+"&now="+new Date().getTime();
	showConFirm('您确定的要删除吗？',function(){
        var index = showLoading();
        $.ajax({
			   type: "POST",
			   cache : false,
			   url: ccnetpath+"/backstage/content/trash",
			   data: param,
			   dataType:'json',
			   success:function(data, textStatus) {
	    			if ("1" != data.res) {
	    				showErrMsg(data.resMsg);
	    				closeLayer(index);
	    			}else{
	    				showSucMsg(data.resMsg);
	    				closePFrame("/backstage/content/index",500);
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
 * 删除文章图片
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
  
  var param = "contentId="+cid+"&picPath="+pic+"&now="+new Date().getTime();
  showTConFirm('您确定要删除图片吗？',function(){
        var index = showTLoading();
        $.ajax({
			   type: "POST",
			   cache : false,
			   url: ccnetpath+"/backstage/content/file/trash",
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
	    				//替换路劲
	    				var pics = arr2String(getAllImgDataSrc("gpSeeImg","data-src"));
	    				if(!isNull(pics)){
	    					$("#contentPics").val(pics);
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

