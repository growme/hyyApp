<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="utf-8" />
    <title>后台管理系统登录</title>
    <meta name="description" content="login page" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<%@ include file="../../../common/includes.jsp"%>
    <%@ include file="../../../common/scom_css.jsp"%>
</head>
<body>
<form action="" method="post" name="loginForm">
<canvas id="regId" style="position:fixed; top:-10px; left:0px; z-index:1; opacity:1;"></canvas>
    <div class="login-container animated fadeInDown" style="z-index:100">
        <div class="loginbox bg-white">
            <div class="loginbox-title">
              <div class="loginbox-social">
                <div class="social-title">分销后台管理系统登录</div>
              </div>
            </div>
            <div class="loginbox-or"></div>
            <div class="loginbox-textbox">
              <span class="input-icon icon-left inverted">
                <input type="text" name="login_account" id="accountNameId" class="form-control" placeholder="登录账户" value=""/>
                <i class="fa fa-user bg-lightgray2"></i>
              </span>
            </div>
            <div class="loginbox-textbox">
              <span class="input-icon icon-left inverted">
                <input type="password" name="login_password" id="passwordId" class="form-control" placeholder="登录密码" value=""/>
                <i class="fa fa-lock bg-lightgray2"></i>
              </span>
            </div>
            <div class="loginbox-textbox">
             <span class="input-icon icon-left inverted">
               <input type="text" name="captcha" id="verifyCodeId" style="width:120px;height:35px;" placeholder="验证码" value="" />
                <i class="fa fa-key bg-lightgray2"></i>
                <img src="" style="width:98px;height:35px;border:1px solid #cccccc;vertical-align:top;" 
                 id="check_code" name="check_code" title="点击换一张" alt="验证码获取中.."/>
             </span>
            </div>
            <div class="loginbox-submit">
                <input type="button" id="loginBtn" class="btn btn-primary btn-block" style="height:38px;font-size:15px;font-weight:600;border-radius:0px;" value="立即登录">
            </div>
        </div>
    </div>
    <div id="regCircle"></div>
    </form>
    <%@ include file="../../../common/com_js.jsp"%>
    <script src="${ccnetpath}/static/js/regAnimation.js"></script>
    <script src="${ccnetpath}/static/js/admin/login/login.js"></script>
   <script type="text/javascript">
   
    var window_width = $(window).width();
    var window_height = $(window).height();
    function regAnimation() {
        var $ele = $('#regId');
        if ($ele[0].getContext) {
            $('#regId').regAnimation({
                window_width: window_width,
                window_height: window_height,
                window_background: '#fff',
                star_count: '200',
                star_color: '#02c5ff',
                star_depth: '400'
            });
        }
    }

    $(function() {
         $('#regCircle').height($(".login-container").height).fadeIn(function() {
            regAnimation();
             //禁止滚动条
         	 $(document.body).css({
         	   "overflow-x":"hidden",
         	   "overflow-y":"hidden"
         	 });
         });
    });
    
     
    </script>
    
</body>
<!--Body Ends-->
</html>
