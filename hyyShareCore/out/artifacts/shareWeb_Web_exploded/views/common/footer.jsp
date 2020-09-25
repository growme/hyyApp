<%@ page language="java" pageEncoding="UTF-8"%>
<!-- 底部导航栏 -->
<div class="yzk_foot">
	<a href="${basePath}home/index.html" class="${MENU_INDEX=='home'?'on':''}"><i class="iconfont icon-shouye"></i><p>首页</p></a>
	<a href="${basePath}article/list.html" class="${MENU_INDEX=='clist'?'on':''}"><i class="iconfont icon-zhuanqian" ></i><p>赚钱</p></a>
	<a href="${basePath}draw/cash.html" class="${MENU_INDEX=='cash'?'on':''}"><i class="iconfont icon-tixian"></i><p>提现</p></a>        
	<a href="${basePath}invited/detail.html" class="${MENU_INDEX=='visit'?'on':''}"><i class="iconfont icon-vip"></i><p>收徒</p></a>
</div>
<!-- 底部导航栏 -->