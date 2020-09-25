<%@ page language="java" contentType="text/html;charset=UTF-8" %>

<div class="navbar">
<div class="navbar-inner">
	<div class="navbar-container">
		<!-- Navbar Barnd -->
	<div class="navbar-header pull-left">
		<a href="#" class="navbar-brand"> 
		<small><img src="${siteLogo}" alt="" /></small>
		</a>
	</div>
	<!-- /Navbar Barnd -->

	<!-- Sidebar Collapse -->
	<div class="sidebar-collapse" id="sidebar-collapse">
		<i class="collapse-icon fa fa-bars"></i>
	</div>
	<!-- /Sidebar Collapse -->
	
	<!-- Account Area and Settings --->
	<div class="navbar-header pull-right">
		<div class="navbar-account">
			<ul class="account-area">
			
			   <li class="light-blue">
				  <a data-toggle="dropdown" href="#" class="pull-right dropdown-toggle padding8">
			       <span class="time">
			          <em id="nowtime">loading time...</em>
			       </span>
			       </a>
			    </li>
				<li>
					<a class="login-area dropdown-toggle" data-toggle="dropdown" style="border-left:0px solid #fff">
					    <div class="avatar" title="View your public profile">
					      <c:if test="${empty SESSION_USER.picUrl && SESSION_USER.sex==0}">
					         <img id="userIconSmall" src="${basePath}/static/img/avatars/man.jpg"> 
					      </c:if>
					      <c:if test="${empty SESSION_USER.picUrl && SESSION_USER.sex==1}">
					         <img id="userIconSmall" src="${basePath}/static/img/avatars/women.jpg"> 
					      </c:if>
					      
					      <c:if test="${!empty SESSION_USER.picUrl}">
                             <img id="userIconSmall" src="${basePath}/static/img/avatars/${SESSION_USER.picUrl}.jpg">
					      </c:if>
                        </div>
						<section>
						<h2>
							<span class="profile"> 
							  <span>欢迎回来 (${SESSION_USER.loginAccount})</span>
							</span>
						</h2> 
						</section>
						 <i class="dropdown-icon fa fa-sort-down" style="margin-top:3px;margin-left:10px;font-size:14px;"></i> 
					</a>
					<!--Login Area Dropdown-->
					<ul class="pull-right dropdown-menu dropdown-arrow dropdown-login-area">
                       <!--Avatar Area-->
                       <li>
                          <div class="avatar-area">
                             <c:if test="${empty SESSION_USER.picUrl && SESSION_USER.sex==0}">
					         <img id="userIconBig" src="${basePath}/static/img/avatars/man.jpg" class="avatar"> 
					      </c:if>
					      <c:if test="${empty SESSION_USER.picUrl && SESSION_USER.sex==1}">
					         <img id="userIconBig" src="${basePath}/static/img/avatars/women.jpg" class="avatar"> 
					      </c:if>
					      <c:if test="${!empty SESSION_USER.picUrl}">
                             <img id="userIconBig" src="${basePath}/static/img/avatars/${SESSION_USER.picUrl}.jpg" class="avatar">
					      </c:if>
                              <span class="caption">${SESSION_USER.nickName}</span>
                          </div>
                       </li>
                       <!--Avatar Area-->
                       <li class="edit">
                           <a href="#" id="changePwd" class="pull-left">
                           <i class="fa fa-key default"></i>修改密码
                           </a>
                           <a href="#" id="userSetting" class="pull-right">
                               <i class="fa fa-user default"></i>个人设置
                           </a>
                       </li>
                       <!--Theme Selector Area-->
                       <li class="theme-area">
                           <ul class="colorpicker" id="skin-changer">
                               <li class="userskin"><span class="colorpick-btn" style="background-color:#5DB2FF;" rel="blue"></span></li>
                               <li class="userskin"><span class="colorpick-btn" style="background-color:#2dc3e8;" rel="azure"></span></li>
                               <li class="userskin"><span class="colorpick-btn" style="background-color:#03B3B2;" rel="teal"></span></li>
                               <li class="userskin"><span class="colorpick-btn" style="background-color:#53a93f;" rel="green"></span></li>
                               <li class="userskin"><span class="colorpick-btn" style="background-color:#FF8F32;" rel="orange"></span></li>
                               <li class="userskin"><span class="colorpick-btn" style="background-color:#f3015c;" rel="pink"></span></li>
                               <li class="userskin"><span class="colorpick-btn" style="background-color:#AC193D;" rel="darkred"></span></li>
                               <li class="userskin"><span class="colorpick-btn" style="background-color:#8C0095;" rel="purple"></span></li>
                               <li class="userskin"><span class="colorpick-btn" style="background-color:#0072C6;" rel="darkblue"></span></li>
                               <li class="userskin"><span class="colorpick-btn" style="background-color:#585858;" rel="gray"></span></li>
                               <li class="userskin"><span class="colorpick-btn" style="background-color:#474544;" rel="black"></span></li>
                               <li class="userskin"><span class="colorpick-btn" style="background-color:#001940;" rel="deepblue"></span></li>
                           </ul>
                       </li>
                       <!--/Theme Selector Area-->
                       <li class="dropdown-footer">
                           <a href="#" id="lockScreen" class="pull-left" style="margin-top:-3px;">
                             <i class="fa fa-lock danger" style="margin-top:5px;margin-right:2px;font-size:16px;"></i>锁屏离开
                           </a>
                           <a href="##" id="exitLogin"> 
                             <i class="fa fa-sign-out danger" style="margin-top:-6px;margin-right:2px;font-size:14px;"></i>注销退出
                           </a>
                       </li>
                   </ul>
					<!--/Login Area Dropdown-->
				</li>
				<!-- /Account Area -->
			</ul>

		</div>
	</div>
	<!-- /Account Area and Settings -->
		</div>
	</div>
</div>
