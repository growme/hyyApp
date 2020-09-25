
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

function getRadioAllVal(id){
	  var radioVal = new Array();
	  var menu_obj = document.getElementsByName(id);
	  if( menu_obj !=null ){
	    for(var i=0;i<menu_obj.length;i++){
	     if(menu_obj[i].type == 'radio'){
	         radioVal.push(menu_obj[i].value);
	      }
	    }
	  }
	  return arr2String(radioVal);
	}

function arr2String(arr){
  if(arr=='undefined'||arr==null){
     return "";
  }else{
    return arr.join(",");//转换为字符串
  }
}

function arr2Str(arr,prex){
	  if(arr=='undefined'||arr==null){
	     return "";
	  }else{
	    return arr.join(prex);//转换为字符串
	  }
	}

function uniqueArr(arr) {
    var result = [], hash = {};
    for (var i = 0, elem; (elem = arr[i]) != null; i++) {
        if (!hash[elem]) {
            result.push(elem);
            hash[elem] = true;
        }
    }
    return result;
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

//图片预览
function setPreview(obj,mid,did,w,h) {
       var imgObjPreview=document.getElementById(mid);
       if(obj.files && obj.files[0]){
               //火狐下，直接设img属性
               imgObjPreview.style.display = 'block';
               imgObjPreview.style.width = w+"px";
               imgObjPreview.style.height = h+'px';                    
               //imgObjPreview.src = docObj.files[0].getAsDataURL();
		       //火狐7以上版本不能用上面的getAsDataURL()方式获取，需要一下方式  
		       imgObjPreview.src = window.URL.createObjectURL(obj.files[0]);

       }else{
               //IE下，使用滤镜
               obj.select();
               var imgSrc = document.selection.createRange().text;
               var localImagId = document.getElementById(did);
               //必须设置初始大小
               localImagId.style.width = w+"px";
               localImagId.style.height = h+"px";
               //图片异常的捕捉，防止用户修改后缀来伪造图片
        try{
               localImagId.style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
               localImagId.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
         }catch(e){
        	   showErrMsg("您上传的图片格式不正确，请重新选择!");
               return false;
         }
               imgObjPreview.style.display = 'none';
               document.selection.empty();
       }
       return true;
  }


/* 
 * 判断图片类型 
 *  
 * @param ths  
 *          type="file"的javascript对象 
 * @return true-符合要求,false-不符合 
 */  
function checkImgType(ths){ 
    var vid = ths.id;
    if (ths.value == "") {  
        Exceptalert("请选择要上传图片！");  
        return false;  
    } else {  
        if (!/\.(gif|jpg|jpeg|png|GIF|JPG|PNG)$/.test(ths.value)) {
        	showErrMsg("图片类型必须是.gif,jpeg,jpg,png中的一种");  
            clearFile(vid);
			document.getElementById(vid).focus();
            return false;  
        }
    }  
    return true;  
}

/* 
 * 判断图片大小 
 *  
 * @param ths  
 *          type="file"的javascript对象 
 * @param width 
 *          需要符合的宽  
 * @param height 
 *          需要符合的高 
 * @return true-符合要求,false-不符合 
 */  
function checkImgPX(ths, width, height) {  
    var img = null;  
    img = document.createElement("img");  
    document.body.insertAdjacentElement("beforeEnd", img); // firefox不行  
    img.style.visibility = "hidden";   
    img.src = ths.value;  
    var imgwidth = img.offsetWidth;  
    var imgheight = img.offsetHeight;  
      
    if(imgwidth != width || imgheight != height) {  
    	showErrMsg("图的尺寸应该是" + width + "x"+ height);  
        ths.value = "";  
        return false;  
    }  
    return true;  
}

//现在图片的高度和宽度
function limitImgSize(sid,maxh,maxw){
   if(sid!=null){
   var obj =document.getElementById(sid);//显示图片的对象
   alert(obj);
   alert(obj.width);
   alert(obj.height);
      if(obj.width != maxw || obj.height != maxh){
    	  showErrMsg("图的尺寸应该是" + maxh + "x"+ maxw); 
         clearFile(sid);
      }
      
      if(obj.fileSize>300*1024){
    	  showErrMsg("图片大小超过限制,请限制在300KB以内"); 
         clearFile(sid);
        
      }
   }
}

function clearFile(id){
	 if(document.getElementById(id).outerHTML) {
	     document.getElementById(id).outerHTML = document.getElementById(id).outerHTML;
	  }else{
	     document.getElementById(id).value = "";
	 }
}

function getFileName(vid){
  var path = document.getElementById(vid).value; 
  return path.substring(path.lastIndexOf("\\")+1,path.lastIndexOf(".")); 
} 

/****************************layer 方法封装  start*****************************/
 function showSucMsg(title){
	 clearFocus();
     layer.msg(title,{icon:1,shade:[0.3,'#666666'],time:1500, shift: 1});
 }
 
 function showTSucMsg(title){
	 clearFocus();
     layer.msg(title, {icon:1,shade:[0.3,'#666666'],time:1500,offset:150, shift: 1});
 }
 
 function showErrMsg(title){
	 clearFocus();
     layer.msg(title, {icon:2,shade:[0.3,'#666666'],time:1500,shift: 6});
 }
 
 function showTErrMsg(title){
	 clearFocus();
     layer.msg(title, {icon:2,shade:[0.3,'#666666'],time:1500,offset:150,shift: 6});
 }
 
 function showWarnMsg(title){
	 clearFocus();
     layer.msg(title, {icon:0,shade:[0.3,'#666666'],time:1500, shift: 2});
 }
 
 function showTWarnMsg(title){
	 clearFocus();
     layer.msg(title, {icon:0,shade:[0.3,'#666666'],time:1500,offset:150, shift: 6});
 }
 
 function showErrorMsg(title,time){
	 clearFocus();
     layer.msg(title, {icon:2,shade:[0.3,'#666666'],time:time,shift: 6});
 }
 
 function showLoading(tp){
	 clearFocus();
    var index = layer.load(tp, {
        shade: [0.3,'#000000'],
        time:10000,
        shadeClose: false
    });
    return index;
 }
 
 function showTLoading(tp){
	 clearFocus();
    var index = layer.load(tp, {
        shade: [0.3,'#000000'],
        time:10000,
        offset:150,
        shadeClose: false
    });
    return index;
 }
 
 function showLoading2(msg){ 
	 clearFocus();
	 var index = layer.msg(msg, {
	  icon: 16,
	  time:10000,
	  shade: [0.5,'#000000']
	});
    return index;
 }
 
 function showTLoading2(msg){ 
	 clearFocus();
	 var index = layer.msg(msg, {
	  icon: 16,
	  time:10000,
	  offset:150,
	  shade: [0.5,'#000000']
	});
    return index;
 }
 function closeLayer(yd){
	clearFocus();
    layer.close(yd);
 }
 
 //显示提示
 function showTips(msg,mid){
	 clearFocus();
	 layer.tips(msg, $("#"+mid), {
		tips: [1, '#FF4351']
	 });
	 $("#"+mid).focus();
 }
 
 //显示提示
 function showTips2(msg,mid){
	 clearFocus();
	 layer.tips(msg, $("#"+mid), {
		tips: [4, '#FF4351']
	 });
	 $("#"+mid).focus();
 }
 
 function showConFirm(title,func1,func2){
	clearFocus();
    layer.confirm(title,{icon: 3, shift: 1},func1,func2);
 }
 
 function showTConFirm(title,func1,func2){
	clearFocus();
    layer.confirm(title,{icon: 3,offset:200,shift: 1},func1,func2);
 }
 
 //打开页面
 function openPage(t,w,h,url,scroll){
	 clearFocus();
    var op = layer.open({
        type: 2,
        title: '<i class="fa fa-sitemap"></i> '+t,
        shadeClose: false,
        shade:[0.5,'#666666'],
        closeBtn: 1,
        shift: 1,
        offset:150,
        scrollbar: false,
        skin: 'layui-layer-nobg', //没有背景色
        maxmin: true, //开启最大化最小化按钮
        area: [w+"px",h+"px"],//设置宽度和高度
        content:[url,'yes']//yes开启滚动条 no禁用
    });
    return op;
 }
 
 
 //打开页面
 function openPPage(t,w,h,url,scroll){
	clearFocus();
    var op = layer.open({
        type: 2,
        shift: 1,
        title: '<i class="fa fa-sitemap"></i> '+t,
        shadeClose: false,
        shade:[0.5,'#666666'],
        closeBtn: 1,
        skin: 'layui-layer-nobg', //没有背景色
        maxmin: true, //开启最大化最小化按钮
        area: [w+"px",h+"px"],//设置宽度和高度
        scrollbar: false,
        content:[url,'no'],//yes开启滚动条 no禁用
	    zIndex: layer.zIndex, //重点1
	    success: function(layero){
	      layer.setTop(layero); //重点2
	    }
    });
    return op;
 }
 
 //清除焦點
 function clearFocus(){
	 var focusedElement = document.activeElement;  
	 if(focusedElement){
		 focusedElement.blur();
	 }
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
    	 window.parent.location.href=ccnetpath+url;
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
 
 function clearRollBar(bool){
	 if(bool==true){
		 //禁止滚动条
	 	 $(document.body).css({
	 	   "overflow-x":"hidden",
	 	   "overflow-y":"hidden"
	 	 });
	 }else{
		 $(document.body).css({
	 	   "overflow-x":"hidden",
	 	   "overflow-y":"auto"
	 	 });
	 }
	 
 }
 
 function initToolBarHiden(){
	var n = $("#sidebar").hasClass("menu-compact");
	if(!n){
		$(".sidebar-collapse").toggleClass("active");
		$(".row").toggleClass("padding-left-5");
		$(".swidget").toggleClass("padding-left-5");
		$("#sidebar").toggleClass("menu-compact");
	}
 }
 
 //is demo_mode
 function isDemoMode(mod){
	 return mod==1?true:false;
 }
 
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