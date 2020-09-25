/**
 * jquery 插件扩张库
 * 更新时间 2018-02-25
 * 作者 Jackie
 */
;(function($){
    $.extend({
        /* @param title 弹窗标题
         * @param content 弹窗内容
         * @param butcontent 弹窗按钮文字内容
         * @param button 弹窗按钮执行后的回调函数
         * **/
        Layer:function( options ){
            var layercontent = '<div class="layer_container">' +
                '<div class="layer_mask"></div>' +
                '<div class="layer_cloud">' +
                '<p class="layer_close iconfont icon-error"></p>' +
                '<p class="layer_title">'+options.title+'</p>' +
                '<p class="layer_content">'+options.content+'</p>' +
                '<p class="layer_button">'+options.button+'</p>' +
                '</div></div>';
            $('body').append(layercontent);
           if(!options.title){
           	$(".layer_title").remove();
           }
            var objheight = $(".layer_cloud").height();
            $(".layer_cloud").css('marginTop',-objheight/2 - 30);
            $(".layer_mask").show();
            $(".layer_cloud").show(300);
            if( options.yes && typeof options.yes == 'function' ){
                $(".layer_button , .layer_close").bind('click',function(){
                    options.yes();
                });
            }
        },
        //关闭layer弹窗
        closeLayer: function(){
            $(".layer_container").remove();
        },
        /*
         * 获取min-max的随机数
         * 如果只传一个参数那么 返回0-max 的随机数
         * 如果穿两个参数那么返回 min-max 的随机数
         * */
        rund: function(){
            var len = arguments.length;
            switch ( len ){
                case 1:
                    var length = arguments[0]+1;
                    return Math.floor(Math.random()*length);
                case 2:
                    var min = arguments[0] ,
                        max = arguments[1] ;
                    if( arguments[0]-arguments[1]>0 ){
                        min = arguments[1];
                        max = arguments[0];
                    }
                    return Math.floor( Math.random()*( max-min+1 ) + min );
            }
        },
        /*
        * 返回随机数组
        *   传入一个数组 这个数组打乱后的一个随机数组
        *   array
        * */
        roundArray: function( array ){
            var m = array.length, t, i;
            // 如果还剩有元素…
            while (m) {
                // 随机选取一个元素…
                i = Math.floor(Math.random() * m--);
                // 与当前元素进行交换
                t = array[m];
                array[m] = array[i];
                array[i] = t;
            }
            return array;
        },
        /*
        * 图片切换 改变透明度
        * */
        imgtab: function( lists , listssmall ){
            $(lists).bind('click',function(){
                var index = $(this).index();
                $(this).addClass('this').siblings('li').removeClass('this');
                $( listssmall ).eq(index).addClass('this').siblings('li').removeClass('this');
            });
        }
    });
})(jQuery);