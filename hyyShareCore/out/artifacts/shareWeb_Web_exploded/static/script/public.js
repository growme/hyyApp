
function $O(id){
  return document.getElementById(id);
}

function $V(id){
  return document.getElementById(id).value;
}

function succTips(title,time){
    if(time==''||time==null) time='1000';
    Notify(title, 'top-right', time, 'success', 'fa-bolt', true);
}

function warnnTips(title,time){
    if(time==''||time==null) time='1000';
    Notify(title, 'top-right', time, 'warning', 'fa-bolt', true);
}

function errorTips(title,time){
    if(time==''||time==null) time='1000';
    Notify(title, 'top-right', time, 'danger', 'fa-bolt', true);
}


function initCheckBox(ck_name,init_val){
    var ck_obj = document.getElementsByName(ck_name);
    if(ck_obj!=null){
	    for(var j=0;j<ck_obj.length;j++){
	         if(ck_obj[j].value == init_val){
	        	 ck_obj[j].checked = true;
	         }
	    }
    }
}

function selectAll(ckid,allbox){
  var ckl = document.getElementsByName(ckid);
  var ck =  document.getElementsByName(allbox);
     if(ckl[0].checked){
        for(var i=0;i<ck.length;i++){
         ck[i].checked =true;
        }
     }else{
     for(var i=0;i<ck.length;i++){
          ck[i].checked =false;
        }
     }
}

function getCKVal(id){
  var ck_obj = document.getElementsByName(id);
  var i;  
  var j = 0;
  var checkedArry = new Array();
  if( ck_obj !=null ){
    for(i=0;i<ck_obj.length;i++){
     if(ck_obj[i].type == 'checkbox' && ck_obj[i].checked == true){
         checkedArry[j] = ck_obj[i].value;
         j = j+1;
      }
    }
  }
  return arr2String(checkedArry);
}


function getSelText(id){
	var stext = $("#"+id).find("option:selected").text();
	if(isNotNull(stext)){
		return stext;
	}else{
		return "";
	}
}
function getRadioVal(id){
  var menu_obj = document.getElementsByName(id);
  var checkedVal;
  if( menu_obj !=null ){
    for(var i=0;i<menu_obj.length;i++){
     if(menu_obj[i].type == 'radio' && menu_obj[i].checked == true){
         checkedVal = menu_obj[i].value;
         break;
      }
    }
  }
  return checkedVal;
}

function arr2String(arr){
  if(arr=='undefined'||arr==null){
     return "";
  }else{
    return arr.join(",");//转换为字符串
  }
}


/**
 * 判断是否为空
 * @param {} obj
 */
function isEmpty(obj){
    if(obj==""||obj==null||obj==undefined){
    	return true;
    }else{
        return false;
    }
}

/**
 * 判断对象是否为空
 * @param {Object} v
 * @return {TypeName} 
 */
function isNull(v){
	return v==null||typeof(v)=="undefined"||v=="";
}

/**
 * 判断对象是否不为空
 * @param {Object} v
 * @return {TypeName} 
 */
function isNotNull(v){
	return !isNull(v);
}

/**
 ** 加法函数，用来得到精确的加法结果
 ** 说明：javascript的加法结果会有误差，在两个浮点数相加的时候会比较明显。这个函数返回较为精确的加法结果。
 ** 调用：accAdd(arg1,arg2)
 ** 返回值：arg1加上arg2的精确结果
 **/
function accAdd(arg1, arg2) {
    var r1, r2, m, c;
    try {
        r1 = arg1.toString().split(".")[1].length;
    }
    catch (e) {
        r1 = 0;
    }
    try {
        r2 = arg2.toString().split(".")[1].length;
    }
    catch (e) {
        r2 = 0;
    }
    c = Math.abs(r1 - r2);
    m = Math.pow(10, Math.max(r1, r2));
    if (c > 0) {
        var cm = Math.pow(10, c);
        if (r1 > r2) {
            arg1 = Number(arg1.toString().replace(".", ""));
            arg2 = Number(arg2.toString().replace(".", "")) * cm;
        } else {
            arg1 = Number(arg1.toString().replace(".", "")) * cm;
            arg2 = Number(arg2.toString().replace(".", ""));
        }
    } else {
        arg1 = Number(arg1.toString().replace(".", ""));
        arg2 = Number(arg2.toString().replace(".", ""));
    }
    return (arg1 + arg2) / m;
}

//给Number类型增加一个add方法，调用起来更加方便。
Number.prototype.add = function (arg) {
    return accAdd(arg, this);
};


/**
 ** 减法函数，用来得到精确的减法结果
 ** 说明：javascript的减法结果会有误差，在两个浮点数相减的时候会比较明显。这个函数返回较为精确的减法结果。
 ** 调用：accSub(arg1,arg2)
 ** 返回值：arg1加上arg2的精确结果
 **/
function accSub(arg1, arg2) {
    var r1, r2, m, n;
    try {
        r1 = arg1.toString().split(".")[1].length;
    }
    catch (e) {
        r1 = 0;
    }
    try {
        r2 = arg2.toString().split(".")[1].length;
    }
    catch (e) {
        r2 = 0;
    }
    m = Math.pow(10, Math.max(r1, r2)); //last modify by deeka //动态控制精度长度
    n = (r1 >= r2) ? r1 : r2;
    return ((arg1 * m - arg2 * m) / m).toFixed(n);
}

// 给Number类型增加一个mul方法，调用起来更加方便。
Number.prototype.sub = function (arg) {
    return accMul(arg, this);
};

/**
 ** 乘法函数，用来得到精确的乘法结果
 ** 说明：javascript的乘法结果会有误差，在两个浮点数相乘的时候会比较明显。这个函数返回较为精确的乘法结果。
 ** 调用：accMul(arg1,arg2)
 ** 返回值：arg1乘以 arg2的精确结果
 **/
function accMul(arg1, arg2) {
    var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
    try {
        m += s1.split(".")[1].length;
    }
    catch (e) {
    }
    try {
        m += s2.split(".")[1].length;
    }
    catch (e) {
    }
    return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m);
}

// 给Number类型增加一个mul方法，调用起来更加方便。
Number.prototype.mul = function (arg) {
    return accMul(arg, this);
};


/** 
 ** 除法函数，用来得到精确的除法结果
 ** 说明：javascript的除法结果会有误差，在两个浮点数相除的时候会比较明显。这个函数返回较为精确的除法结果。
 ** 调用：accDiv(arg1,arg2)
 ** 返回值：arg1除以arg2的精确结果
 **/
function accDiv(arg1, arg2) {
    var t1 = 0, t2 = 0, r1, r2;
    try {
        t1 = arg1.toString().split(".")[1].length;
    }
    catch (e) {
    }
    try {
        t2 = arg2.toString().split(".")[1].length;
    }
    catch (e) {
    }
    with (Math) {
        r1 = Number(arg1.toString().replace(".", ""));
        r2 = Number(arg2.toString().replace(".", ""));
        return (r1 / r2) * pow(10, t2 - t1);
    }
}

//给Number类型增加一个div方法，调用起来更加方便。
Number.prototype.div = function (arg) {
    return accDiv(this, arg);
};

/*
 格式化数字
*/
function fmoney(s, n) {
	n = n > 0 && n <= 20 ? n : 2;
	s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";
	var l = s.split(".")[0].split("").reverse(), r = s.split(".")[1];
	t = "";
	for (i = 0; i < l.length; i++) {
		t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");
	}
	return t.split("").reverse().join("") + "." + r;
}


/**
 * 打印json数组
 * 
 * @param {}
 *            json
 * @return {}
 */
function printJson(json) {
	var str = "";
	for (var i in json) {
		str += "["+i+"]=" + json[i] + ",\n";
	}
	var param = str.substring(0,str.length-2);
	alert(param);
}


/**
 * 同步获取数据方法
 * @param {} url 请求地址
 * @param {} param 请求参数
 * @param {} method 请求方法（post/get）
 * @return {}
 */
function commonLoad(url,param,method){
	var obj;var value;
	if (window.ActiveXObject) {
		obj = new ActiveXObject('Microsoft.XMLHTTP');
	} else if (window.XMLHttpRequest) {
		obj = new XMLHttpRequest();
	}
	obj.open(method, url, false);
	obj.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
	obj.send(param);
	var result = obj.responseText;
	return result;
}

/****************************layer 方法封装  start*****************************/
 function showSucMsg(title){
     layer.msg(title, {icon:1,shade:[0.3,'#666666'],time:1500, shift: 1});
 }
 
 function showTSucMsg(title){
     layer.msg(title, {icon:1,shade:[0.3,'#666666'],time:1500,offset:80, shift: 1});
 }
 
 function showErrMsg(title){
     layer.msg(title, {icon:2,shade:[0.3,'#666666'],time:1500,shift: 6});
 }
 
 function showTErrMsg(title){
     layer.msg(title, {icon:2,shade:[0.3,'#666666'],time:1500,offset:80,shift: 6});
 }
 
 function showWarnMsg(title){
     layer.msg(title, {icon:0,shade:[0.3,'#666666'],time:1500, shift: 2});
 }
 
 function showErrorMsg(title,time){
     layer.msg(title, {icon:2,shade:[0.3,'#666666'],time:time,shift: 6});
 }
 
 function showLoading(tp){
    var index = layer.load(tp, {
        shade: [0.3,'#000000'],
        shadeClose: false
    });
    return index;
 }
 
 function showTLoading(tp){
	    var index = layer.load(tp, {
	        shade: [0.3,'#000000'],
	        offset:80,
	        shadeClose: false
	    });
	    return index;
	 }
 
 function closeLayer(yd){
    layer.close(yd);
 }
 
 //显示提示
 function showTips(msg,mid){
	 layer.tips(msg, $("#"+mid), {
		tips: [2, '#FF4351']
	 });
	 $("#"+mid).focus();
 }
 
 function showConFirm(title,func1,func2){
    layer.confirm(title,{icon: 3, shift: 1},func1,func2);
 }
 
 function showTConFirm(title,func1,func2){
    layer.confirm(title,{icon: 3,offset:80,shift: 1},func1,func2);
 }

 
 //打开页面
 function openCPage(t,w,h,cxt){
    var op = layer.open({
        type: 2,
        title: '<i class="fa fa-sitemap"></i> '+t,
        shadeClose: false,
        shade:[0.5,'#666666'],
        closeBtn: 1,
        shift: 1,
        skin: 'layui-layer-nobg', //没有背景色
        maxmin: true, //开启最大化最小化按钮
        area: [w+"px",h+"px"],//设置宽度和高度
        content:cxt//带内容
    });
    return op;
 }
 
 //打开页面
 function openPage(t,w,h,url,scroll){
    var op = layer.open({
        type: 1,
        title: '<i class="fa fa-sitemap"></i> '+t,
        shadeClose: false,
        shade:[0.5,'#666666'],
        closeBtn: 1,
        offset:150,
        shift: 1,
        skin: 'layui-layer-nobg', //没有背景色
        maxmin: true, //开启最大化最小化按钮
        area: [w+"px",h+"px"],//设置宽度和高度
        content:[url,'yes']//yes开启滚动条 no禁用
    });
    return op;
 }
 
 
 //打开页面
 function openPPage(t,w,h,url,scroll){
    var op = layer.open({
        type: 2,
        shift: 1,
        title: '<i class="fa fa-user"></i> '+t,
        shadeClose: false,
        shade:[0.5,'#666666'],
        closeBtn: 1,
        skin: 'layui-layer-nobg', //没有背景色
        maxmin: true, //开启最大化最小化按钮
        area: [w+"px",h+"px"],//设置宽度和高度
        scrollbar: false,
        content:[url,'yes'],//yes开启滚动条 no禁用
	    zIndex: layer.zIndex, //重点1
	    success: function(layero){
	      layer.setTop(layero); //重点2
	    }
    });
    return op;
 }
 
//打开页面
 function openCPage(t,w,h,ct,scroll){
    var op = layer.open({
        type: 2,
        shift: 1,
        title: '<i class="fa fa-user"></i> '+t,
        shadeClose: false,
        shade:[0.5,'#666666'],
        closeBtn: 1,
        skin: 'layui-layer-nobg', //没有背景色
        maxmin: true, //开启最大化最小化按钮
        area: [w+"px",h+"px"],//设置宽度和高度
        scrollbar: false,
        content:ct,//yes开启滚动条 no禁用
	    zIndex: layer.zIndex, //重点1
	    success: function(layero){
	      layer.setTop(layero); //重点2
	    }
    });
    return op;
 }
 
 //关闭窗口
 function closeFrame(rel){
     var index = parent.layer.getFrameIndex(window.name);
	 if(rel){
		 setTimeout(function(){
	       window.parent.location.reload();
	       parent.layer.close(index);
		 },800);
	 }else{
	    parent.layer.close(index);
	 }
 }
 
 //关闭窗口
 function closePFrame(url,timeout){
	 setTimeout(function(){
    	 window.parent.location.href=SITE_PUBLIC+url;
    	 var index = parent.layer.getFrameIndex(window.name);
    	 parent.layer.close(index);
     },timeout);
 }
 
 function closeSFrame(timeout){
	 setTimeout(function(){
    	 var index = parent.layer.getFrameIndex(window.name);
    	 parent.layer.close(index);
     },timeout);
 }
 
/****************************layer 方法封装  over*****************************/
 
 /**
  * 检测浏览器是否为兼容模式
  */
function checkBrowser(){
	 //document.Browser.Name.value=navigator.appName; 
	 //document.Browser.Version.value=navigator.appVersion; 
	 //document.Browser.Code.value=navigator.appCodeName; 
	 //document.Browser.Agent.value=navigator.userAgent; 
     if(window.navigator.userAgent.indexOf('AppleWebKit') < 0) {
    	 showErrorMsg('很抱歉，为了不影响您的正常使用，请不要使用兼容模式访问！');
    	 return false;
    }
} 

function base64decode(str) {
    var base64DecodeChars = new Array( - 1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1);
    var c1, c2, c3, c4;
    var i, len, out;
    len = str.length;
    i = 0;
    out = "";
    while (i < len) {
        do {
            c1 = base64DecodeChars[str.charCodeAt(i++) & 0xff];
        } while ( i < len && c1 == - 1 );
        if (c1 == -1) {
            break;
        }
        do {
            c2 = base64DecodeChars[str.charCodeAt(i++) & 0xff];
        } while ( i < len && c2 == - 1 );
        if (c2 == -1) {
            break;
        }
        out += String.fromCharCode((c1 << 2) | ((c2 & 0x30) >> 4));
        do {
            c3 = str.charCodeAt(i++) & 0xff;
            if (c3 == 61) return out;
            c3 = base64DecodeChars[c3];
        } while ( i < len && c3 == - 1 );
        if (c3 == -1) {
            break;
        }
        out += String.fromCharCode(((c2 & 0XF) << 4) | ((c3 & 0x3C) >> 2));
        do {
            c4 = str.charCodeAt(i++) & 0xff;
            if (c4 == 61) {
                return out;
            }
            c4 = base64DecodeChars[c4];
        } while ( i < len && c4 == - 1 );
        if (c4 == -1) {
            break;
        }
        out += String.fromCharCode(((c3 & 0x03) << 6) | c4);
    }
    return out;
};

function base64encode(str) {
	var base64EncodeChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
    var out, i, len;
    var c1, c2, c3;

    len = str.length;
    i = 0;
    out = "";
    while(i < len) {
	c1 = str.charCodeAt(i++) & 0xff;
	if(i == len)
	{
	    out += base64EncodeChars.charAt(c1 >> 2);
	    out += base64EncodeChars.charAt((c1 & 0x3) << 4);
	    out += "==";
	    break;
	}
	c2 = str.charCodeAt(i++);
	if(i == len)
	{
	    out += base64EncodeChars.charAt(c1 >> 2);
	    out += base64EncodeChars.charAt(((c1 & 0x3)<< 4) | ((c2 & 0xF0) >> 4));
	    out += base64EncodeChars.charAt((c2 & 0xF) << 2);
	    out += "=";
	    break;
	}
	c3 = str.charCodeAt(i++);
	out += base64EncodeChars.charAt(c1 >> 2);
	out += base64EncodeChars.charAt(((c1 & 0x3)<< 4) | ((c2 & 0xF0) >> 4));
	out += base64EncodeChars.charAt(((c2 & 0xF) << 2) | ((c3 & 0xC0) >>6));
	out += base64EncodeChars.charAt(c3 & 0x3F);
    }
    return out;
}

//生成二维码
function showBarCode(vid,url,w,h){
   $("#"+vid).empty();
   $("#"+vid).qrcode({
	  render: "canvas", 
	  width: w, 
	  height:h, 
	  text: url
   });	
 }