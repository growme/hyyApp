//JavaScript Document
$(function () {
	//初始化
	initTPIcon();
	
	 $("#fontawsome-search").keyup(function () {
         var term = $.trim(this.value);
         if (term === "") {
             $(".fa-hover").show();
             $('#fontawesome-container .alert').parent().show();
             $('#fontawesome-container section').each(function () {
                 $(this).css('display', 'block');
             });
         } else {
             $('.fa-hover > i[class*="fa-"]').parent().hide();
             $('.fa-hover > i[class*="' + term + '"]').parent().show();
             $('#fontawesome-container .alert').parent().hide();
             $('#fontawesome-container section').each(function () {
                 if ($(this).find("div").children(':visible').length == 0) {
                     $(this).css('display', 'none');
                 }
             });
         }
     });
          
     $("#glyphicon-search").keyup(function () {
          var term = $.trim(this.value);
          if (term === "") {
              $(".bs-glyphicons-list li").show();
          } else {
              $('.bs-glyphicons-list li > span[class*="glyphicon-"]').parent().hide();
              $('.bs-glyphicons-list li > span[class*="' + term + '"]').parent().show();
          }
      });
      
      $("#typicon-search").keyup(function () {
          var term = $.trim(this.value);
          if (term === "") {
              $("#typicon-preview .icon").show();
          } else {
              $('#typicon-preview .icon').hide();
              $('#typicon-preview .icon span[class*="' + term + '"]').parent().show();
          }
      });
      
      
      //图标选择
	 $("#fontawesome-container .fontawesome-icon-list").find("div").bind('click', function(){
		clearHiddenVal();
		var iconHidden=$(this).text();
		$("#faIconText").html("fa "+iconHidden);
		$("#faIconId").prop("class","fa "+iconHidden +" isize-20");
		$("#iconHidden").val("fa "+iconHidden);
		$("#colorHidden").val("");
	 });
       	
     //图标选择
	 $(".bs-glyphicons-list li").bind('click', function(){		
		clearHiddenVal();
		var iconHidden=$(this).find("span").prop("class");
		$("#ghIconText").html(iconHidden);
		$("#ghIconId").prop("class",iconHidden +" isize-20");
		$("#iconHidden").val(iconHidden);
		$("#colorHidden").val("");
	 });
	 
	 //图标选择
	 $("#typicon-preview .icon").bind('click', function(){		
		clearHiddenVal();
		var iconHidden=$(this).find("span").prop("class");
		$("#tpIconText").html(iconHidden);
		$("#tpIconId").prop("class",iconHidden +" isize-20");
		$("#iconHidden").val(iconHidden);
		$("#colorHidden").val("");
	 });
	 
	//颜色选择
	 $(".Badges-container span").bind('click', function(){		
		 var colorHidden=$(this).prop("class");
		 var scolor = colorHidden.split(" ");
		 if(scolor!=null && scolor.length>2){
			 var tcolor = scolor[1].split("-")[1];
			 $("#colorHidden").val(tcolor);
			 //处理样式选中
			 var iconHD = $("#iconHidden").val();
			 if(isNull(iconHD)){
				 showErrMsg("请先选中需要的图标！");
				 return false;
			 }
			 var hidIcon = iconHD.split(" ")[0];
			 var ccolor = iconHD + " " + tcolor +" isize-20";
			 if(hidIcon=="fa"){
				 $("#faIconId").prop("class",ccolor);
			 }
			 if(hidIcon=="glyphicon"){
				 $("#ghIconId").prop("class",ccolor);
			 }
			 if(hidIcon=="typcn"){
				 $("#tpIconId").prop("class",ccolor);
			 }
			 console.log(tcolor);
		 }
	 });
	 
	 //给上级传值
	 $("#saveFAIcon").bind('click', function(){		
		 saveIcon();
	 });
	 
	 $("#saveGHIcon").bind('click', function(){		
		 saveIcon();
	 });
	 
	 $("#saveTPIcon").bind('click', function(){		
		saveIcon();
	 });
	 
});

//清空内容
function clearHiddenVal(){
	$("#faIconText").html("");
	$("#faIconId").prop("class","");
	$("#ghIconText").html("");
	$("#ghIconId").prop("class","");
	$("#tpIconText").html("");
	$("#tpIconId").prop("class","");
	$("#iconHidden").val("");
	$("#colorHidden").val("");
}

//保存提交
function saveIcon(){
	var iconVal = $("#iconHidden").val() ;
	var colorVal = $("#colorHidden").val();
	if(isNull(iconVal)){
	   showErrMsg("您还没有选择图标！");
	   return false;
	}
	parent.$('#icon').val(iconVal+" "+colorVal);
	closeFrame(false);
}

function initTPIcon(){
   //初始化TP图标文字
   $("#typicon-preview .icon").each(function(){
       var iconHidden2=$(this).find("span").prop("class");
        $(this).find("span").html("<p class='sbtitle'>"+iconHidden2.split(" ")[1]+"</p>");
   });
 }