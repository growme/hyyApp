<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<meta name="viewport"
	content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
<meta name="HandheldFriendly" content="true">
<meta content="telephone=no" name="format-detection">
<%@ include file="../../../common/header.jsp"%>
<%@ include file="../../../common/js_param.jsp"%>
<script type="text/javascript"
	src="${basePath}static/script/problem/jquery-2.1.4.min.js?t=1592032635008"></script>
<script type="text/javascript"
	src="${basePath}static/script/problem/Util.js?t=1592032635008"></script>
<link rel="stylesheet" type="text/css"
	href="${basePath}static/css/problem/Popup.css?t=1592032635008" />
<script type="text/javascript"
	src="${basePath}static/script/problem/Popup.js?t=1592032635008"></script>
<script type="text/javascript"
	src="${basePath}static/script/problem/DataListGetter.js?t=1592032635008"></script>

<script type="text/javascript"
	src="${basePath}static/script/problem/plug-in.js"></script>
<script type="text/javascript"
	src="${basePath}static/script/problem/plug-in.min.js"></script>
<script type="text/javascript">
	var $ctx = "/jkd";
	todayDay = "";
	ossPath = "http://suishouyue.oss-cn-qingdao.aliyuncs.com";

	//跳转指定原生的方法
	function gotoOrign(type) {
		try {
			if ("" == "android") {
				if ("home" == type) {
					window.mobile.button(0);
				} else if ("video" == type) {
					window.mobile.button(1);
				} else if ("task" == type) {
					window.mobile.button(2);
				} else if ("me" == type) {
					window.mobile.button(3);
				}
			} else if ("" == "iOS") {
				try {
					loadEventFunc(type, "");
				} catch (e) {
					window.webkit.messageHandlers.loadEventFunc
							.postMessage(type);
				}
			}
		} catch (e) {
		}
	}
</script>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>意见反馈</title>
<link rel="stylesheet" type="text/css"
	href="${basePath}static/css/problem/feed.css?1=3">
<script>
	var index = 120;
	var articlenum = 10;
	//设置rem 
	function mobilecal() {
		var size = 100, //规定rem与px之间值的转换
		maxWidth = 750; //设置基准宽度。
		ratio = function() {
			var r = document.documentElement.clientWidth / maxWidth;
			return r >= 1 ? 1 : r <= 0.234 ? 0.234 : r;
		};
		set = function() {
			document.documentElement.style.fontSize = ratio() * size + 'px';
		}();
		window.onresize = mobilecal;
	}
	mobilecal();
</script>
</head>

<body>
	<form id="uploadForm" method="post"
		action="${basePath}/app/problem/consultSheetAdd"
		enctype="multipart/form-data" target="iframe" name="fileForm">
		<div class="wrap">
			<div class="line"></div>
			<div class="head">
				<span class="classify">问题分类</span> <span class="place">请选择问题分类</span>
				<img class="down rot" src="${basePath}static/images/problem/o.png">
				<ul class="choose dis_none">

					<li>实名认证问题</li>

					<li>提现问题</li>

					<li>产品意见</li>

					<li>收入问题</li>

					<li>收徒问题</li>

					<li>其他</li>

				</ul>
			</div>
			<div class="line"></div>
			<div class="main">
				<textarea id="new_alert" type="text" name="content"
					placeholder="请详细描述您遇到的问题(200字以内)" maxlength="200"></textarea>
				<ul class="imglist">

					<li><img id="srcimg1" class="srcimg"
						src="${basePath}static/images/problem/up.png"> <img
						class="del delet1"
						src="${basePath}static/images/problem/x_alt.png"> <input
						type="file" class="filechoose" id="chooseImage1" value=""
						name="files"></li>
					<li><img id="srcimg2" class="srcimg"
						src="${basePath}static/images/problem/up.png"> <img
						class="del delet2"
						src="${basePath}static/images/problem/x_alt.png"> <input
						type="file" class="filechoose" id="chooseImage2" value=""
						name="files"></li>
					<li><img id="srcimg3" class="srcimg"
						src="${basePath}static/images/problem/up.png"> <img
						class="del delet3"
						src="${basePath}static/images/problem/x_alt.png"> <input
						type="file" class="filechoose" id="chooseImage3" value=""
						name="files"></li>
				</ul>
				<p class="tit">上传问题截图有助于了解您的问题并及时回复您,感谢您的配合以及对聚看点的支持!如您提的建议被采纳,会给予您相应的金币作为奖励。官方客服QQ群:1037071443</p>
			</div>
			<input type="text" name="typecode" value="" class="typecode"
				style="display: none"> <input type="text" class="fileNames"
				name="fileNames" value="" style="display: none"> <input
				type="text" class="fileNames" name="fileNames" value=""
				style="display: none"> <input type="text" class="fileNames"
				name="fileNames" value="" style="display: none"> <input
				type="text" class="source" name="source" value=""
				style="display: none">
		</div>
		<div class="foot">
			<input type="submit" value="提交">
		</div>
		<div class="cover">
			<div class="cover_content">
				<p class="con">内容不能为空</p>
				<p class="con1">您的反馈已收到,我们会认真处理,请留意消息通知</p>
				<p class="c_btn">确定</p>
			</div>
		</div>
	</form>
	<iframe id="frame" name="iframe" style="display: none;"></iframe>
	<div class="allblack">正在提交中,请稍后...</div>
	<div class="cover_p">
		<div class="cover_content">
			<p class="con2">上传错误,文件格式必须为：png/jpg/jpeg</p>
			<p class="c_btn2">确定</p>
		</div>
	</div>
</body>
<script src="${basePath}static/script/problem/jquery.form.js"></script>
<script>
	var srcimg;
	//var formdata = new FormData();
	var allarr = [];
	var imgfile1;
	var imgfile2;
	var imgfile3;
	var bool = false;
	var brr = [];
	var tex;

	//点击选择分类
	$(".place,.down").click(function() {
		$(".choose").toggleClass("dis_none");
		$(".down").toggleClass("rot");
	})
	//点击分类选项
	$(".choose").on("click", "li", function() {
		$(".place").html("");
		tex = $(this).html();
		$(".place").html(tex);
		$(".choose").addClass("dis_none");
		$(".down").addClass("rot");
		$(".typecode").val(tex);
	})
	//点击第一个图片
	$('#chooseImage1').on(
			'change',
			function() {
				var filePath = $(this).val(), //获取到input的value，里面是文件的路径
				fileFormat = filePath.substring(filePath.lastIndexOf("."))
						.toLowerCase();
				srcimg = window.URL.createObjectURL(this.files[0]); //转成可以在本地预览的格式
				imgfile1 = this.files[0];
				//formdata.append("file", imgfile1);
				// 检查是否是图片
				if (!fileFormat.match(/.png|.jpg|.jpeg/)) {
					$(".cover_p").show();
					$("#chooseImage1").val("");
					return;
				} else {
					allarr.push(imgfile1);
				}
				$("#srcimg1").attr("src", srcimg);
				$(".delet1").show();
				$("#chooseImage1").hide();
				$(".delet1").click(function() {
					brr.push(imgfile1);
					$("#srcimg1").attr("src", "../../consult_new/img/up.png");
					$(".delet1").hide();
					$("#chooseImage1").val("");
					$("#chooseImage1").show();
				})
			});
	//点击第二个图片
	$('#chooseImage2').on(
			'change',
			function() {
				var filePath = $(this).val(), //获取到input的value，里面是文件的路径
				fileFormat = filePath.substring(filePath.lastIndexOf("."))
						.toLowerCase();
				srcimg = window.URL.createObjectURL(this.files[0]); //转成可以在本地预览的格式
				imgfile2 = this.files[0];
				//formdata.append("file", imgfile2);
				// 检查是否是图片
				if (!fileFormat.match(/.png|.jpg|.jpeg/)) {
					$(".cover_p").show();
					$("#chooseImage2").val("");
					return;
				} else {
					allarr.push(imgfile2);
				}
				$("#srcimg2").attr("src", srcimg);
				$(".delet2").show();
				$("#chooseImage2").hide();
				$(".delet2").click(function() {
					brr.push(imgfile2);
					$("#srcimg2").attr("src", "../../consult_new/img/up.png");
					$(".delet2").hide();
					$("#chooseImage2").val("");
					$("#chooseImage2").show();
				})
			});
	//点击第三个图片
	$('#chooseImage3').on(
			'change',
			function() {
				var filePath = $(this).val(), //获取到input的value，里面是文件的路径
				fileFormat = filePath.substring(filePath.lastIndexOf("."))
						.toLowerCase();
				srcimg = window.URL.createObjectURL(this.files[0]); //转成可以在本地预览的格式
				imgfile3 = this.files[0];
				//formdata.append("file", imgfile3);
				// 检查是否是图片
				if (!fileFormat.match(/.png|.jpg|.jpeg/)) {
					$(".cover_p").show();
					$("#chooseImage3").val("");
					return;
				} else {
					allarr.push(imgfile3);
				}
				$("#srcimg3").attr("src", srcimg);
				$(".delet3").show();
				$("#chooseImage3").hide();
				$(".delet3").click(function() {
					brr.push(imgfile3);
					$("#srcimg3").attr("src", "../../consult_new/img/up.png");
					$(".delet3").hide();
					$("#chooseImage3").val("");
					$("#chooseImage3").show();
				})
			});

	$(".c_btn2").click(function() {
		$(".cover_p").hide();
	})
	//点击提交验证
	var bool = true;
	if (bool == true) {
		// function doUpload() {
		$("#uploadForm")
				.bind(
						"submit",
						function() {
							var value = $("#new_alert").val();
							if ($(".place").html() == "请选择问题分类"
									|| $(".place").html() == "") {
								$(".con").html("");
								$(".con").html("分类不能为空");
								$(".cover").show();
								//点击确定隐藏弹框
								$(".c_btn").click(function() {
									$(".cover").hide();
								})
								return false
							} else if (!value.trim()) {
								$(".con").html("");
								$(".con").html("内容不能为空");
								$(".cover").show();
								//点击确定隐藏弹框
								$(".c_btn").click(function() {
									$(".cover").hide();
								})
								return false
							} else {
								for (var i = 0; i < brr.length; i++) {
									for (var j = 0; j < allarr.length; j++) {
										if (brr[i] == allarr[j]) {
											allarr.splice(j, 1);
											j--;
										}
									}
								}
								//传输文件名
								for (var m = 0; m < allarr.length; m++) {
									$(".fileNames").eq(m).val(allarr[m].name);
									console.log($(".fileNames").eq(m).val())
								}
								//如果图片为空,就直接上传否则判断文件是否传输完
								if (allarr.length == 0) {
									$(".con").hide();
									$(".con1").show();
									$(".cover").show();
									$(".c_btn")
											.click(
													function() {
														$(".cover").hide();
														window.location.href = "${basePath}/app/problem/consultSheetAdd";
													})
									bool = false;
									setTimeout(function() {
										bool = true;
									}, 5000);
									return true
								} else {
									//展示正在提交中
									$(".allblack").show();
									return true
								}

							}
						})
	}
	// }
	// $(function(){
	/** 验证文件是否导入成功  */
	$("#uploadForm")
			.ajaxForm(
					function(data) {
						var datas = JSON.parse(data)
						if (datas.code == "200") {
							$(".allblack").hide();
							$(".con").hide();
							$(".con1").show();
							$(".cover").show();
							$(".c_btn")
									.click(
											function() {
												$(".cover").hide();
												window.location.href = "${basePath}/app/problem/consultSheetAdd";
											})
						}
					});
	// });
</script>
</html>