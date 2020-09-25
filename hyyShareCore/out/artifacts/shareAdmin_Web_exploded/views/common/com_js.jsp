<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<!--Basic Scripts-->
<script src="${basePath}static/js/skins.min.js" charset="utf-8"></script>
<script src="${basePath}static/js/jquery/jquery-2.1.4.min.js" charset="utf-8"></script>
<script src="${basePath}static/js/jquery/jquery-form.js" charset="utf-8"></script>
<script src="${basePath}static/js/jquery/jquery.cookie.js" charset="utf-8"></script>
<script src="${basePath}static/js/jquery/jquery.md5.js" charset="utf-8"></script>
<script src="${basePath}static/js/jquery/jquery.lazyload.js" charset="utf-8"></script>
<script src="${basePath}static/js/bootstrap.min.js" charset="utf-8"></script>
<!--Beyond js-->
<script src="${basePath}static/js/beyond.min.js" charset="utf-8"></script>
<script src="${basePath}static/js/layer/layer/layer.js" charset="utf-8"></script>
<script src="${basePath}static/js/My97DatePicker/WdatePicker.js" charset="utf-8"></script>
<script src="${basePath}static/js/ztree/js/jquery.ztree.core.js" charset="utf-8"></script>
<script src="${basePath}static/js/ztree/js/jquery.ztree.excheck.js" charset="utf-8"></script>
<!--system js-->
<script src="${basePath}static/js/public.js" charset="utf-8"></script>
<script src="${basePath}static/js/vdata.js" charset="utf-8"></script>
<script src="${basePath}static/js/ServerClock.js" charset="utf-8"></script>
<script type="text/javascript">

$(function () {
    //checkBrowser();
	initLockSreen();
    getMenuTree();
	var nowDate = "${nowDate}";
	initUserSkin();
	initUserIcon();
	$("#skin-changer li>span").click(function(){
		var color_=$(this).attr("rel");
		if(!isNull(color_)){
		  $.cookie('userSkin', color_, { expires: 30, path: '/' });
		  $('#style_color').attr("href", ccnetpath + '/static/css/skins/' + color_ + ".min.css");
		  changUserSkin(color_);
		}
	});
	
	
	function getMenuTree(){
	    var param = "menuCode="+menuCode+"&isReload="+isReload+"&now="+new Date().getTime();
	    $.ajax({
			   type:"POST",
			   cache:false,
			   async:false,
			   url:ccnetpath+"/backstage/menu/getMenu",
			   data:param,
			   dataType:'json',
			   success:function(data, textStatus) {
	    			if ("1" != data.res) {
	    				showTErrMsg(data.resMsg);
	    				if((data.resMsg).indexOf("未登录")>0){
	    				   setTimeout(function(){
								window.location.href=ccnetpath+"/loginIndex";
							},1500);
	    				}
	    				if((data.resMsg).indexOf("认证失败")>0){
	    				   setTimeout(function(){
								window.location.href=ccnetpath+"/goreglicense";
							},1500);
	    				}
	    			}else{
	                     $("#homeMenu").html(data.obj);
	    			}
	    	    }
	     });
	}

	function initUserSkin(){
		var sessionSkin = "${SESSION_USER.skin}";
		var userSkin = $.cookie('userSkin');
		if(isNull(userSkin)){
			userSkin = sessionSkin;
		}
		if(!isNull(userSkin)){
			 $('#style_color').attr("href", ccnetpath + '/static/css/skins/' + userSkin + ".min.css");
		}
	}
	
	function initUserIcon(){
	   //初始化头像
		var cuserIcon = "${SESSION_USER.picUrl}";
		cuserIcon = ccnetpath+"/static/img/avatars/"+cuserIcon+".jpg";
		var userIconBig = $.cookie('userIconBig');
		var userIconSBig = $.cookie('userIconSBig');
		var userIconSmall = $.cookie('userIconSmall');
		if(!isNull(userIconBig)){
		   parent.$("#userIconBig").attr("src",userIconBig);
		}else{
		   parent.$("#userIconBig").attr("src",cuserIcon);
		}
		if(!isNull(userIconSBig)){
		   parent.$("#userIconSBig").attr("src",userIconSBig);
		}else{
		   parent.$("#userIconSBig").attr("src",cuserIcon);
		}
		if(!isNull(userIconSmall)){
		   parent.$("#userIconSmall").attr("src",userIconSmall);
		}else{
		   parent.$("#userIconSmall").attr("src",cuserIcon);
		}
	}
	
	function changUserSkin(sk){
	   $.ajax({
         url: ccnetpath + "/backstage/user/updateSkin",
         type: 'POST',
         data: {skin:sk,tm:new Date().getTime()},
         dataType: 'json',
         success: function (data) {}
       });
	}
	
	$("#changePwd").click(function () {
		openPage("修改密码",400,255,ccnetpath+"/backstage/index/changepwd");
	});
	
	$("#lockScreen").click(function () {
      $.ajax({
         url: ccnetpath + "/lockacc",
         type: 'POST',
         data: {dlock:"1",tm:new Date().getTime()},
         dataType: 'json',
         success: function (data) {
          if(data.res=="1"){//锁定成功
            initLockSreen();
          }
         }
       });
	});
	
	//初始化锁屏
	function initLockSreen(){
	  $.ajax({
         url: ccnetpath + "/cklock",
         type: 'POST',
         data: {clock:"1",tm:new Date().getTime()},
         dataType: 'json',
         success: function (data) {
         if(data.res=="1"){
            layer.open({
				  type: 1
				  ,title:'密码锁屏' //不显示标题栏
				  ,closeBtn: false
				  ,area: '280px;'
				  ,shade: 0.75
				  ,offset:240
				  ,id: 'lockscreenId'
				  ,btn: ['解锁', '注销']
				  ,yes: function(){
				    var pass = $("#lockpwd").val();
				    if(pass==null||pass==''){
				       showTips("请输入您的登录密码！","lockpwd");
		               $("#lockpwd").focus();
		               return false;
				    }
				    unLockScreen(pass);
				  }
				  ,btn2: function(){
				     window.location.href=ccnetpath+"/systemLogout";
				  } 
				  ,btnAlign: 'c'
				  ,moveType: 1 //拖拽模式，0或者1
				  ,content: '<div style="padding: 15px; line-height: 30px; background-color: #fff; color: #ddd; font-weight: 300;">'+
				  '<input name="lockpwd" id="lockpwd" type="password" style="font-size:14px;width:100%;height:34px;line-height:32px;padding-left:6px;border:1px solid #d5d5d5;color:grey" placeholder="输入登录密码解除锁定"></div>'
				});
            }
         }
       });
	}
	
	//解除锁定
	function unLockScreen(pass){
	   $.ajax({
         url: ccnetpath + "/unlockacc",
         type: 'POST',
         data: {lockpwd:$.md5(pass),tm:new Date().getTime()},
         dataType: 'json',
         success: function (data) {
            if(data.res=="1"){//解除锁定
               layer.closeAll();
            }else{
               showTips("登录密码错误，请重试！","lockpwd");
		       $("#lockpwd").focus();
               initLockSreen();
            }
         }
       });
	}
	
	$("#userSetting").click(function () {
		openPage("个人设置",480,470,ccnetpath+"/backstage/index/setting");
	});
	
	$("#exitLogin").click(function () {
		 showTConFirm("您确定注销登录？",function(){
	       	var index = showTLoading2("注销中,请稍后...");
	       	setTimeout(function(){
				closeLayer(index);
				window.location.href=ccnetpath+"/systemLogout";
			},700);
	     });
	});
	
});
</script>



