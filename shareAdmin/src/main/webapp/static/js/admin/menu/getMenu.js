//JavaScript Document
$(function () {
	getMenuTree();
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
    				showErrMsg(data.resMsg);
    				closeLayer(index);
    			}else{
                     $("#homeMenu").html(data.obj);
    			}
    	    }
     });
}