var getArgs = (function() {
    var sc = document.getElementsByTagName("script");
    var paramsArr = sc[sc.length - 1].src.split('?')[1].split('&');
    var args = {},
        argsStr = [],
        param, t, name, value;
    for (var ii = 0, len = paramsArr.length; ii < len; ii++) {
        param = paramsArr[ii].split('=');
        name = param[0], value = param[1];
        if (typeof args[name] == "undefined") {
            args[name] = value;
        } else if (typeof args[name] == "string") {
            args[name] = [args[name]];
            args[name].push(value);
        } else {
            args[name].push(value);
        }
    }
    return function() {
        return args;
    }
})();

 var getDomain = function(){
   var sc = document.getElementsByTagName("script");
   var the_req_url = sc[sc.length - 1].src;
   var lead_slashes = the_req_url.indexOf("//");
   var domain_start = lead_slashes + 2;
   var without_resource = the_req_url.substring(domain_start, the_req_url.length);
   var next_slash = without_resource.indexOf("/");
   return without_resource.substring(0, next_slash);
}

var Adgs = getArgs();
/*var cpsdomain = getDomain();
var apsids = Adgs["apsids"];
var apsids2 = Adgs["apsids2"];
var apsnum = parseInt(Adgs["num"]);
var apstype = parseInt(Adgs["type"]);
var height = (apsnum * 205);
Adgs["uid"] = parseInt(Adgs["uid"]);
if (apstype == "0") {
    height = (apsnum * 205);
} else if (apstype == "1") {
    height = (apsnum * 100);
} else if (apstype == "2") {
    height = (apsnum * 27);
}*/
var adid = Adgs["adId"];
openPage("查看广告",730,700,ccnetpath+"/backstage/advertise/goedit?adId="+adid);
//Adgs['url'] = "http://"+c+"/advertise/goedit/?adid=" + apstype + "&t=" + Adgs["uid"] + "&num=" + apsnum + (Adgs["cid"] ? "&q=" + Adgs["cid"] : '') + (apsids ? "&apsids=" + apsids : '') + (apsids2 ? "&apsids2=" + apsids2 : '');
/*var d = new Date();
var str = d.getFullYear() + "" + (d.getMonth() + 1) + "" + d.getDate() + "" + Math.floor(Math.random() * 99 + 1);
Adgs['url'] = Adgs['url'] + "&arnd=" + str;
document.write('<iframe id="content" width="100%" height="'+height+'" marginwidth="0" marginheight="0" frameborder="0" src="' + Adgs['url'] + '" onload="if(' + apstype + '==0){this.height=(parseInt((this.clientWidth-16)*0.23685)+96)*' + apsnum + ';}"></iframe>');*/