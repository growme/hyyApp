<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!-- saved from url=(0313)http://a79ibqsth2ojbh6.mizhuanfenxiang.cn/download/?e=f23d59877726691bff7542171fbf636b023ebacc008c208011b34d8281a2c3f3d35a608db6719ece5ad5f2c10ef747e10fa3f2cef78cd7967550599290b478efa24b58f185002e98bc695caa3e9337cb459d0cd91c46f3736bfc7f6e9229718850cd110e936caed82dca1314eb471b9f&from=groupmessage&isappinstalled=0 -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="../../../common/header.jsp"%>
<%@ include file="../../../common/js_param.jsp"%>
<%@ include file="../../../common/ucom_css.jsp"%>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<link href="${basePath}/static/invited/download/minireset.min.css" rel="stylesheet">
<title>下载赚钱</title>
<style type="text/css">
body {
	background-color: #f0f2f5
}

.head-part {
	display: flex;
	justify-content: space-between;
	background-image:
		url('https://e-static.oss-cn-shanghai.aliyuncs.com/img/mz/bg_head.png');
	padding: 8vw 4vw 32vw;
	background-size: cover;
	background-repeat: no-repeat;
	color: #fff;
	align-items: center;
}

.head-img {
	width: 12vw;
	height: 12vw;
	display: block;
	border-radius: 50%
}

.left-one {
	width: 16vw;
}

.center-one {
	width: 40vw;
	font-size: 14px;
}

.right-one {
	width: 36vw;
	text-align: right;
	font-size: 14px;
}

.right-one span {
	color: #FFE5AF
}

.center-part {
	font-size: 6vw;
	display: flex;
	align-items: center;
	justify-content: center;
	margin: 5vw 12vw 16vw;
	font-weight: bold;
	font-family: sans-serif;
}

.center-part span {
	font-size: 14vw;
	font-weight: bold;
}

.center-part img {
	width: 18vw;
	height: auto;
	display: block;
	margin-left: 2vw;
}

.time_wrap {
	color: #000;
	display: flex;
	justify-content: space-around;
	padding: 0 40vw;
	font-size: 16px;
	text-align: center;
	margin-bottom: 4vw
}

.time_wrap div {
	border: 1px solid #979797;
	padding: 1.5vw 2vw;
	border-radius: 5px;
}

.time_wrap p {
	margin: 1.5vw 1vw 0;
}

.title-button {
	font-size: 13px;
	text-align: center;
	color: #8F8F8F;
	margin-bottom: 4vw
}

.button-img {
	text-align: center;
}

.button-part img {
	width: 86vw;
}

.withdraw-text {
	font-size: 16px;
	color: #666666;
	margin-top: 4vw;
	margin-bottom: 6vw;
	text-align: center;
}

.bottom-part {
	display: flex;
	justify-content: space-between;
	align-items: center;
	text-align: center;
	color: #666666;
	font-size: 14px;
	padding: 0 10vw;
}

.bottom-part img {
	width: 15vw;
}

.bottom-part .dian-style {
	width: 8vw;
	display: block;
	margin-top: -6vw;
}

.bottom-text {
	margin-top: 2vw
}

.time-ago {
	font-size: 12px;
}

@
keyframes rotate2 { 0% {
	-webkit-transform: scale3d(1, 1, 1);
	transform: scale3d(1, 1, 1);
}

50%
{
-webkit-transform
:
 
scale3d
(1
.1
,
1
.1
,
1
.1
);

                
transform
:
 
scale3d
(1
.1
,
1
.1
,
1
.1
);

            
}
100%
{
-webkit-transform
:
 
scale3d
(1
,
1,
1);
transform
:
 
scale3d
(1
,
1,
1);
}
}
.button-img img {
	animation: rotate2 .8s linear 0s infinite;
}

.head-list-one {
	display: flex;
	align-items: center;
	justify-content: space-between;
}

.maquee {
	width: 90%;
	overflow: hidden;
	margin: 0 auto;
	color: #7C7C7C;
}

.topRec_List dl, .maquee {
	width: 100%;
	overflow: hidden;
	margin: 0 auto;
	color: #fff
}

.maquee {
	height: 12vw;
}

.topRec_List ul {
	width: 100%;
	height: 195px;
}

.bg-all {
	display: none
}

.wx_user_photo {
	position: absolute;
	left: 41vw;
	top: 32.5vw;
}

.wx_user_photo img {
	width: 19vw;
	border-radius: 100%;
}

/* 引导用户信任软件 */
.tips_box {
	position: fixed;
	top: 0;
	left: 0;
	z-index: 999;
	background: rgba(256, 256, 256, 0.85);
	width: 100%;
	height: 100%;
	display: none;
}

.tips_box .tips_gif {
	position: absolute;
	top: 50%;
	left: 50%;
	transform: translate(-50%, -50%);
	width: 80%;
}

.tips_gif img {
	width: 100%;
	height: auto;
}

.tips_gif .clear_btn {
	width: 20vw;
	font-size: 17px;
	line-height: 34px;
	border-radius: 5px;
	position: absolute;
	bottom: -50px;
	left: 50%;
	transform: translateX(-50%);
	background: #e51314;
	color: #fff;
	text-align: center;
}
</style>
</head>

<body class="register-button">
	<!-- <div id="JweixinTip" class="bg-all" style="position: fixed; top: 0;left: 0;background: rgba(0,0,0,.4);width: 100%;height: 100%;z-index: 999">
      <img style="width: 100%" src="http://e-static.oss-cn-shanghai.aliyuncs.com/img/mz/openbrowser2.png" alt="">
  </div> -->
	<div class="wx_user_photo">
		<img src="${basePath}/static/invited/download/logo_mz.png" alt="">
	</div>
	<div class="head-part">
		<div class="topRec_List">
			<div class="maquee">
				<ul id="list-all" style="margin-top: 0px;">
					<li><div class="head-list-one">
							<div class="left-one">
								<div>
									<img class="head-img" src="${basePath}/static/invited/download/12.jpg">
								</div>
							</div>
							<div class="center-one">
								<div>胡图图</div>
								<div class="time-ago">6分钟前</div>
							</div>
							<div class="right-one">
								提现<span>48元</span>到支付宝
							</div>
						</div></li>
					<li><div class="head-list-one">
							<div class="left-one">
								<div>
									<img class="head-img" src="${basePath}/static/invited/download/13.jpg">
								</div>
							</div>
							<div class="center-one">
								<div>释青春</div>
								<div class="time-ago">5分钟前</div>
							</div>
							<div class="right-one">
								提现<span>68元</span>到支付宝
							</div>
						</div></li>
					<li><div class="head-list-one">
							<div class="left-one">
								<div>
									<img class="head-img" src="${basePath}/static/invited/download/14.jpg">
								</div>
							</div>
							<div class="center-one">
								<div>眉目清朗</div>
								<div class="time-ago">1分钟前</div>
							</div>
							<div class="right-one">
								提现<span>88元</span>到支付宝
							</div>
						</div></li>
					<li><div class="head-list-one">
							<div class="left-one">
								<div>
									<img class="head-img" src="${basePath}/static/invited/download/15.jpg">
								</div>
							</div>
							<div class="center-one">
								<div>拼搏作兴趣</div>
								<div class="time-ago">3分钟前</div>
							</div>
							<div class="right-one">
								提现<span>98元</span>到支付宝
							</div>
						</div></li>
					<li><div class="head-list-one">
							<div class="left-one">
								<div>
									<img class="head-img" src="${basePath}/static/invited/download/16.jpg">
								</div>
							</div>
							<div class="center-one">
								<div>清醒一杯</div>
								<div class="time-ago">6分钟前</div>
							</div>
							<div class="right-one">
								提现<span>58元</span>到支付宝
							</div>
						</div></li>
					<li><div class="head-list-one">
							<div class="left-one">
								<div>
									<img class="head-img" src="${basePath}/static/invited/download/17.jpg">
								</div>
							</div>
							<div class="center-one">
								<div>每一天</div>
								<div class="time-ago">12分钟前</div>
							</div>
							<div class="right-one">
								提现<span>88元</span>到支付宝
							</div>
						</div></li>
					<li><div class="head-list-one">
							<div class="left-one">
								<div>
									<img class="head-img" src="${basePath}/static/invited/download/18.jpg">
								</div>
							</div>
							<div class="center-one">
								<div>心中有海</div>
								<div class="time-ago">10分钟前</div>
							</div>
							<div class="right-one">
								提现<span>98元</span>到支付宝
							</div>
						</div></li>
					<li><div class="head-list-one">
							<div class="left-one">
								<div>
									<img class="head-img" src="${basePath}/static/invited/download/19.jpg">
								</div>
							</div>
							<div class="center-one">
								<div>为明天</div>
								<div class="time-ago">5分钟前</div>
							</div>
							<div class="right-one">
								提现<span>58元</span>到支付宝
							</div>
						</div></li>
					<li><div class="head-list-one">
							<div class="left-one">
								<div>
									<img class="head-img" src="${basePath}/static/invited/download/20.jpg">
								</div>
							</div>
							<div class="center-one">
								<div>独自走</div>
								<div class="time-ago">2分钟前</div>
							</div>
							<div class="right-one">
								提现<span>68元</span>到支付宝
							</div>
						</div></li>
					<li><div class="head-list-one">
							<div class="left-one">
								<div>
									<img class="head-img" src="${basePath}/static/invited/download/21.jpg">
								</div>
							</div>
							<div class="center-one">
								<div>酒醉夜未阑</div>
								<div class="time-ago">4分钟前</div>
							</div>
							<div class="right-one">
								提现<span>88元</span>到支付宝
							</div>
						</div></li>
					<li><div class="head-list-one">
							<div class="left-one">
								<div>
									<img class="head-img" src="${basePath}/static/invited/download/22.jpg">
								</div>
							</div>
							<div class="center-one">
								<div>相思赋予谁</div>
								<div class="time-ago">7分钟前</div>
							</div>
							<div class="right-one">
								提现<span>98元</span>到支付宝
							</div>
						</div></li>
					<li><div class="head-list-one">
							<div class="left-one">
								<div>
									<img class="head-img" src="${basePath}/static/invited/download/23.jpg">
								</div>
							</div>
							<div class="center-one">
								<div>明月下西楼</div>
								<div class="time-ago">1分钟前</div>
							</div>
							<div class="right-one">
								提现<span>58元</span>到支付宝
							</div>
						</div></li>
					<li><div class="head-list-one">
							<div class="left-one">
								<div>
									<img class="head-img" src="${basePath}/static/invited/download/24.jpg">
								</div>
							</div>
							<div class="center-one">
								<div>眼角落樱花</div>
								<div class="time-ago">11分钟前</div>
							</div>
							<div class="right-one">
								提现<span>88元</span>到支付宝
							</div>
						</div></li>
					<li><div class="head-list-one">
							<div class="left-one">
								<div>
									<img class="head-img" src="${basePath}/static/invited/download/25.jpg">
								</div>
							</div>
							<div class="center-one">
								<div>倾酒向涟漪</div>
								<div class="time-ago">12分钟前</div>
							</div>
							<div class="right-one">
								提现<span>98元</span>到支付宝
							</div>
						</div></li>
					<li><div class="head-list-one">
							<div class="left-one">
								<div>
									<img class="head-img" src="${basePath}/static/invited/download/26.jpg">
								</div>
							</div>
							<div class="center-one">
								<div>碧海潮生</div>
								<div class="time-ago">2分钟前</div>
							</div>
							<div class="right-one">
								提现<span>58元</span>到支付宝
							</div>
						</div></li>
					<li><div class="head-list-one">
							<div class="left-one">
								<div>
									<img class="head-img" src="${basePath}/static/invited/download/27.jpg">
								</div>
							</div>
							<div class="center-one">
								<div>冰肌玉骨</div>
								<div class="time-ago">8分钟前</div>
							</div>
							<div class="right-one">
								提现<span>58元</span>到支付宝
							</div>
						</div></li>
					<li><div class="head-list-one">
							<div class="left-one">
								<div>
									<img class="head-img" src="${basePath}/static/invited/download/28.jpg">
								</div>
							</div>
							<div class="center-one">
								<div>梦醉为红颜</div>
								<div class="time-ago">5分钟前</div>
							</div>
							<div class="right-one">
								提现<span>88元</span>到支付宝
							</div>
						</div></li>
					<li><div class="head-list-one">
							<div class="left-one">
								<div>
									<img class="head-img" src="${basePath}/static/invited/download/29.jpg">
								</div>
							</div>
							<div class="center-one">
								<div>泪咽却无声</div>
								<div class="time-ago">6分钟前</div>
							</div>
							<div class="right-one">
								提现<span>98元</span>到支付宝
							</div>
						</div></li>
					<li><div class="head-list-one">
							<div class="left-one">
								<div>
									<img class="head-img" src="${basePath}/static/invited/download/6.jpg">
								</div>
							</div>
							<div class="center-one">
								<div>你乖一点</div>
								<div class="time-ago">10分钟前</div>
							</div>
							<div class="right-one">
								提现<span>48元</span>到支付宝
							</div>
						</div></li>
					<li><div class="head-list-one">
							<div class="left-one">
								<div>
									<img class="head-img" src="${basePath}/static/invited/download/7.jpg">
								</div>
							</div>
							<div class="center-one">
								<div>森林散布</div>
								<div class="time-ago">12分钟前</div>
							</div>
							<div class="right-one">
								提现<span>68元</span>到支付宝
							</div>
						</div></li>
					<li><div class="head-list-one">
							<div class="left-one">
								<div>
									<img class="head-img" src="${basePath}/static/invited/download/8.jpg">
								</div>
							</div>
							<div class="center-one">
								<div>邮梦人</div>
								<div class="time-ago">4分钟前</div>
							</div>
							<div class="right-one">
								提现<span>88元</span>到支付宝
							</div>
						</div></li>
					<li><div class="head-list-one">
							<div class="left-one">
								<div>
									<img class="head-img" src="${basePath}/static/invited/download/9.jpg">
								</div>
							</div>
							<div class="center-one">
								<div>雨天晒阳</div>
								<div class="time-ago">20分钟前</div>
							</div>
							<div class="right-one">
								提现<span>98元</span>到支付宝
							</div>
						</div></li>
					<li><div class="head-list-one">
							<div class="left-one">
								<div>
									<img class="head-img" src="${basePath}/static/invited/download/10.jpg">
								</div>
							</div>
							<div class="center-one">
								<div>半顆心</div>
								<div class="time-ago">10分钟前</div>
							</div>
							<div class="right-one">
								提现<span>58元</span>到支付宝
							</div>
						</div></li>
					<li><div class="head-list-one">
							<div class="left-one">
								<div>
									<img class="head-img" src="${basePath}/static/invited/download/11.jpg">
								</div>
							</div>
							<div class="center-one">
								<div>快乐的小孩</div>
								<div class="time-ago">8分钟前</div>
							</div>
							<div class="right-one">
								提现<span>58元</span>到支付宝
							</div>
						</div></li>
				</ul>
			</div>
		</div>
	</div>

	<div class="center-part">
		<div>
			￥<span>9.88</span>
		</div>
	</div>
	<div class="button-part">
		<div class="time_wrap">
			<div class="t_h">09</div>
			<p>:</p>
			<div class="t_m">40</div>
			<p>:</p>
			<div class="t_s">10</div>
		</div>
		<div class="title-button">后红包失效，赶快领取</div>
		<div class="button-img download-btn" id="download-btn">
			<img class="register-button" data-clipboard-text=""
				src="${basePath}/static/invited/download/button_change.png">
		</div>
		<div class="withdraw-text">如何提现？</div>
	</div>
	<div class="bottom-part">

		<div>
			<div>
				<img src="${basePath}/static/invited/download/icon_download.png">
			</div>
			<div class="bottom-text">下载APP</div>
		</div>
		<div>
			<img class="dian-style" src="${basePath}/static/invited/download/arrow_dian.png">
		</div>
		<div>
			<div>
				<img src="${basePath}/static/invited/download/icon_head.png">
			</div>
			<div class="bottom-text">登录</div>
		</div>
		<div>
			<img class="dian-style" src="${basePath}/static/invited/download/arrow_dian.png">
		</div>
		<div>
			<div>
				<img src="${basePath}/static/invited/download/icon_hongbao.png">
			</div>
			<div class="bottom-text">领红包</div>
		</div>
	</div>
	<!-- 引导信任授权 -->
	<div class="tips_box">
		<div class="tips_gif">
			<img src="${basePath}/static/invited/download/mz.gif" alt="">
			<div class="clear_btn">点击关闭</div>
		</div>
	</div>


	<script type="text/javascript" charset="UTF-8"
		src="//res.cdn.openinstall.io/openinstall.js"></script>
	<script type="text/javascript">
    //openinstall初始化时将与openinstall服务器交互，应尽可能早的调用
    /*web页面向app传递的json数据(json string/js Object)，应用被拉起或是首次安装时，通过相应的android/ios api可以获取此数据*/
    var downloadData = OpenInstall.parseUrlParams();//openinstall.js中提供的工具函数，解析url中的所有查询参数
    console.log(downloadData);
    new OpenInstall({
        /*appKey必选参数，openinstall平台为每个应用分配的ID*/
        appKey : "qiqaiw",
        /*可选参数，自定义android平台的apk下载文件名；个别andriod浏览器下载时，中文文件名显示乱码，请慎用中文文件名！*/
        //apkFileName : 'com.fm.openinstalldemo-v2.2.0.apk',
        /*可选参数，是否优先考虑拉起app，以牺牲下载体验为代价*/
        //preferWakeup:true,
        /*自定义遮罩的html*/
        //mask:function(){
        //  return "<div id='openinstall_shadow' style='position:fixed;left:0;top:0;background:rgba(0,255,0,0.5);filter:alpha(opacity=50);width:100%;height:100%;z-index:10000;'></div>"
        //},
        /*openinstall初始化完成的回调函数，可选*/
        onready : function() {
            var m = this, button = document.getElementById("download-btn");
            button.style.visibility = "visible";

            /*在app已安装的情况尝试拉起app*/
            m.schemeWakeup();
            /*用户点击某个按钮时(假定按钮id为downloadButton)，安装app*/
            button.onclick = function() {
                m.wakeupOrInstall();
                return false;
            }
        }
    }, downloadData);

</script>
	<script src="${basePath}/static/invited/download/clipboard.min.js"></script>
	<script>
    var urlParams = OpenInstall.parseUrlParams();

    var clipboard = new ClipboardJS('.download-btn', {
        text: function () {
            return urlParams.e;
        }
    });

    clipboard.on('success', function (e) {
        console.log(e);
    });
    clipboard.on('error', function (e) {
        console.log(e);
    });
</script>

	<script src="${basePath}/static/invited/download/jquery3.4.0.min.js"></script>
	<script type="text/javascript">
    var time = new Date().getTime() + 600000;
    countTime();

    function countTime() {
        var now = new Date().getTime();
        var leftTime = time - now;
        var minutes = addNumber(Math.floor(leftTime / 1000 / 60 % 60));
        var seconds = addNumber(Math.floor(leftTime / 1000 % 60));
        var msec = addNumber(Math.floor(leftTime % 1000));
        $(".time_wrap").find('.t_h').text(minutes);
        $(".time_wrap").find('.t_m').text(seconds);
        $(".time_wrap").find('.t_s').text(msec);
        if (leftTime > 0) {
            setTimeout(function () {
                countTime();
            }, 100);
        } else {
            clearTimeout(countTime());

        }
    }

    function addNumber(num) {
        if (num >= 100) {
            num = num.toString();
            num = num.substr(0, 2);
            num = parseInt(num);
        }
        var num = (num > 9) ? num : ('0' + num);
        return num;
    }

    var imgs = 'http://e-static.oss-cn-shanghai.aliyuncs.com/img/mz/logo_mz.png';
    //console.log(imgs);
    $(".wx_user_photo img").attr('src', imgs);

    const imgRootUrl = 'http://e-static.oss-cn-shanghai.aliyuncs.com/img/weapp/red-package/hds/';
    const data = [{
        name: '你乖一点',
        imgurl: imgRootUrl + '6.jpg',
        time: ' 10分钟前',
        money: '48元'
    },
        {
            name: '森林散布',
            imgurl: imgRootUrl + '7.jpg',
            time: ' 12分钟前',
            money: '68元'
        },
        {
            name: '邮梦人',
            imgurl: imgRootUrl + '8.jpg',
            time: ' 4分钟前',
            money: '88元'
        },
        {
            name: '雨天晒阳',
            imgurl: imgRootUrl + '9.jpg',
            time: ' 20分钟前',
            money: '98元'
        },
        {
            name: '半顆心',
            imgurl: imgRootUrl + '10.jpg',
            time: ' 10分钟前',
            money: '58元'
        },
        {
            name: '快乐的小孩',
            imgurl: imgRootUrl + '11.jpg',
            time: ' 8分钟前',
            money: '58元'
        },
        {
            name: '胡图图',
            imgurl: imgRootUrl + '12.jpg',
            time: ' 6分钟前',
            money: '48元'
        },
        {
            name: '释青春',
            imgurl: imgRootUrl + '13.jpg',
            time: ' 5分钟前',
            money: '68元'
        },
        {
            name: '眉目清朗',
            imgurl: imgRootUrl + '14.jpg',
            time: ' 1分钟前',
            money: '88元'
        },
        {
            name: '拼搏作兴趣',
            imgurl: imgRootUrl + '15.jpg',
            time: ' 3分钟前',
            money: '98元'
        },
        {
            name: '清醒一杯',
            imgurl: imgRootUrl + '16.jpg',
            time: ' 6分钟前',
            money: '58元'
        },
        {
            name: '每一天',
            imgurl: imgRootUrl + '17.jpg',
            time: ' 12分钟前',
            money: '88元'
        },
        {
            name: '心中有海',
            imgurl: imgRootUrl + '18.jpg',
            time: ' 10分钟前',
            money: '98元'
        },
        {
            name: '为明天',
            imgurl: imgRootUrl + '19.jpg',
            time: ' 5分钟前',
            money: '58元'
        },
        {
            name: '独自走',
            imgurl: imgRootUrl + '20.jpg',
            time: ' 2分钟前',
            money: '68元'
        },
        {
            name: '酒醉夜未阑',
            imgurl: imgRootUrl + '21.jpg',
            time: ' 4分钟前',
            money: '88元'
        },
        {
            name: '相思赋予谁',
            imgurl: imgRootUrl + '22.jpg',
            time: ' 7分钟前',
            money: '98元'
        },
        {
            name: '明月下西楼',
            imgurl: imgRootUrl + '23.jpg',
            time: ' 1分钟前',
            money: '58元'
        },
        {
            name: '眼角落樱花',
            imgurl: imgRootUrl + '24.jpg',
            time: ' 11分钟前',
            money: '88元'
        },
        {
            name: '倾酒向涟漪',
            imgurl: imgRootUrl + '25.jpg',
            time: ' 12分钟前',
            money: '98元'
        },
        {
            name: '碧海潮生',
            imgurl: imgRootUrl + '26.jpg',
            time: ' 2分钟前',
            money: '58元'
        },
        {
            name: '冰肌玉骨',
            imgurl: imgRootUrl + '27.jpg',
            time: ' 8分钟前',
            money: '58元'
        },
        {
            name: '梦醉为红颜',
            imgurl: imgRootUrl + '28.jpg',
            time: ' 5分钟前',
            money: '88元'
        },
        {
            name: '泪咽却无声',
            imgurl: imgRootUrl + '29.jpg',
            time: ' 6分钟前',
            money: '98元'
        }
    ];
    for (var i = 0; i < data.length; i++) {
        var html = "";
        html += '<li>'
        html += '<div class="head-list-one">'
        html += '<div class="left-one">'
        html += '<div><img class="head-img" src="' + data[i].imgurl + '"></div>'
        html += '</div>'
        html += '<div class="center-one">'
        html += '<div>' + data[i].name + '</div>'
        html += '<div class="time-ago">' + data[i].time + '</div>'
        html += '</div>'
        html += '<div class="right-one">提现<span>' + data[i].money + '</span>到支付宝</div>'
        html += '</div>'
        html += '</li>'
        $('#list-all').append(html)
    }

    function autoScroll(obj) {
        $(obj).find("ul").animate({
            marginTop: "-43px"
        }, 500, function () {
            $(this).css({
                marginTop: "0px"
            }).find("li:first").appendTo(this);
        })
    }

    setInterval('autoScroll(".maquee")', 3000);
</script>

	<script>
    var ua = navigator.userAgent.toLocaleLowerCase();
    var isQQ = ua.indexOf('mqqbrowser') < 0 && ua.indexOf(" qq") > -1;
    var isWeixin1 = ua.match(/MicroMessenger/i) == 'micromessenger';
    var u = navigator.userAgent,
        app = navigator.appVersion;
    var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Linux') > -1; //g
    var isIOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
    var url = !isWeixin1 && !isQQ;
    if (url && isIOS) {
        $('.tips_box').show();
    }
    $('.clear_btn').click(function () {
        $('.tips_box').hide();
    })
</script>

	<div
		style="position: static; width: 0px; height: 0px; border: none; padding: 0px; margin: 0px;">
		<div id="trans-tooltip">
			<div id="tip-left-top"
				style="background: url(&quot;chrome-extension://ccfjcepmiaackkccabgeeegeklgifffd/imgs/map/tip-left-top.png&quot;);"></div>
			<div id="tip-top"
				style="background: url(&quot;chrome-extension://ccfjcepmiaackkccabgeeegeklgifffd/imgs/map/tip-top.png&quot;) repeat-x;"></div>
			<div id="tip-right-top"
				style="background: url(&quot;chrome-extension://ccfjcepmiaackkccabgeeegeklgifffd/imgs/map/tip-right-top.png&quot;);"></div>
			<div id="tip-right"
				style="background: url(&quot;chrome-extension://ccfjcepmiaackkccabgeeegeklgifffd/imgs/map/tip-right.png&quot;) repeat-y;"></div>
			<div id="tip-right-bottom"
				style="background: url(&quot;chrome-extension://ccfjcepmiaackkccabgeeegeklgifffd/imgs/map/tip-right-bottom.png&quot;);"></div>
			<div id="tip-bottom"
				style="background: url(&quot;chrome-extension://ccfjcepmiaackkccabgeeegeklgifffd/imgs/map/tip-bottom.png&quot;) repeat-x;"></div>
			<div id="tip-left-bottom"
				style="background: url(&quot;chrome-extension://ccfjcepmiaackkccabgeeegeklgifffd/imgs/map/tip-left-bottom.png&quot;);"></div>
			<div id="tip-left"
				style="background: url(&quot;chrome-extension://ccfjcepmiaackkccabgeeegeklgifffd/imgs/map/tip-left.png&quot;);"></div>
			<div id="trans-content"></div>
		</div>
		<div id="tip-arrow-bottom"
			style="background: url(&quot;chrome-extension://ccfjcepmiaackkccabgeeegeklgifffd/imgs/map/tip-arrow-bottom.png&quot;);"></div>
		<div id="tip-arrow-top"
			style="background: url(&quot;chrome-extension://ccfjcepmiaackkccabgeeegeklgifffd/imgs/map/tip-arrow-top.png&quot;);"></div>
	</div>
</body>
</html>