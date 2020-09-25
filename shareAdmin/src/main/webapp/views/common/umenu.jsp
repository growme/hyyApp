<%@ page language="java" contentType="text/html;charset=UTF-8" %>

<!-- Page Sidebar -->
     <div class="page-sidebar" id="sidebar">
         <!-- Page Sidebar Header-->
         <div class="sidebar-header-wrapper">
             <input type="text" class="searchinput" />
             <i class="searchicon fa fa-search"></i>
             <div class="searchhelper">系统搜索</div>
         </div>
         <!-- /Page Sidebar Header -->
         <!-- Sidebar Menu -->
         <ul class="nav sidebar-menu">
             <!--Dashboard-->
              <li <c:if test="${CT_MENU_INDEX=='home_index'}"> class="active" </c:if> >
                  <a href="${basePath}user/index.html">
                      <i class="menu-icon glyphicon glyphicon-home"></i>
                      <span class="menu-text"> 系统首页 </span>
                  </a>
              </li>
              
              <li <c:if test="${fn:containsIgnoreCase(CT_MENU_INDEX,'project')}"> class="active open" </c:if>>
                  <a href="#" class="menu-dropdown">
                      <i class="menu-icon glyphicon glyphicon glyphicon-list-alt"></i>
                      <span class="menu-text"> 项目管理 </span>
                      <i class="menu-expand"></i>
                  </a>

                  <ul class="submenu">
                      <li <c:if test="${CT_MENU_INDEX=='project_cx'}"> class="active" </c:if> >
                          <a href="${basePath}project/list.html">
                              <span class="menu-text">查询项目</span>
                          </a>
                      </li>
                      <li <c:if test="${CT_MENU_INDEX=='project_qk'}"> class="active" </c:if> >
                          <a href="${basePath}project/blist.html">
                              <span class="menu-text">缺卡项目</span>
                          </a>
                      </li>
                      <li <c:if test="${CT_MENU_INDEX=='project_sh'}"> class="active" </c:if> >
                          <a href="${basePath}project/clist.html">
                              <span class="menu-text">审核项目</span>
                          </a>
                      </li>
                      <li <c:if test="${CT_MENU_INDEX=='project_zs'}"> class="active" </c:if> >
                          <a href="${basePath}project/zlist.html">
                              <span class="menu-text">专属对接</span>
                          </a>
                      </li>
                  </ul>
              </li>
              
              <li <c:if test="${CT_MENU_INDEX=='user_list'}"> class="active" </c:if> >
                  <a href="${basePath}member/list.html">
                      <i class="menu-icon glyphicon glyphicon-user"></i>
                      <span class="menu-text"> 用户管理 </span>
                  </a>
              </li>
             
              <li <c:if test="${fn:containsIgnoreCase(CT_MENU_INDEX,'record')}"> class="active open" </c:if> >
                  <a href="#" class="menu-dropdown">
                      <i class="menu-icon glyphicon glyphicon-briefcase"></i>
                      <span class="menu-text"> 业务管理 </span>

                      <i class="menu-expand"></i>
                  </a>

                  <ul class="submenu">
                      <li <c:if test="${CT_MENU_INDEX=='sms_record'}"> class="active" </c:if> >
                          <a href="${basePath}csms/list.html">
                              <span class="menu-text">消费记录</span>
                          </a>
                      </li>
                      <li <c:if test="${CT_MENU_INDEX=='charge_record'}"> class="active" </c:if> >
                          <a href="${basePath}charge/list.html">
                              <span class="menu-text">充值记录</span>
                          </a>
                      </li>
                      <li <c:if test="${CT_MENU_INDEX=='crash_record'}"> class="active" </c:if> >
                          <a href="${basePath}crash/list.html">
                              <span class="menu-text">提现记录</span>
                          </a>
                      </li>
                      <li <c:if test="${CT_MENU_INDEX=='pay_record'}"> class="active" </c:if> >
                          <a href="${basePath}pay/list.html">
                              <span class="menu-text">结算流水</span>
                          </a>
                      </li>
                      <li <c:if test="${CT_MENU_INDEX=='send_record'}"> class="active" </c:if>>
                          <a href="${basePath}send/list.html">
                              <span class="menu-text">发送记录</span>
                          </a>
                      </li>
                  </ul>
               </li>
              
               <li <c:if test="${fn:containsIgnoreCase(CT_MENU_INDEX,'column')}"> class="active open" </c:if> >
                  <a href="#" class="menu-dropdown">
                      <i class="menu-icon glyphicon glyphicon-folder-close"></i>
                      <span class="menu-text"> 内容管理 </span>
                      <i class="menu-expand"></i>
                  </a>
                  <ul class="submenu">
                      <li <c:if test="${CT_MENU_INDEX=='column_list'}"> class="active" </c:if> >
                          <a href="${basePath}coc/clist.html">
                             <span class="menu-text">栏目管理</span>
                         </a>
                     </li>
                     <li <c:if test="${CT_MENU_INDEX=='column_content'}"> class="active" </c:if> >
                         <a href="${basePath}coc/ctlist.html">
                             <span class="menu-text">栏目内容</span>
                         </a>
                     </li>
                 </ul>
               </li>
              
               <li <c:if test="${fn:containsIgnoreCase(CT_MENU_INDEX,'setting')}"> class="active open" </c:if> >
                  <a href="#" class="menu-dropdown">
                      <i class="menu-icon glyphicon glyphicon-cog"></i>
                      <span class="menu-text"> 系统管理 </span>
                      <i class="menu-expand"></i>
                  </a>

                  <ul class="submenu">
                      <li <c:if test="${CT_MENU_INDEX=='setting_code'}"> class="active" </c:if> >
                          <a href="${basePath}code/list.html">
                             <span class="menu-text">字典参数</span>
                         </a>
                     </li>
                     <li <c:if test="${CT_MENU_INDEX=='setting_param'}"> class="active" </c:if> >
                         <a href="${basePath}param/list.html">
                             <span class="menu-text">系统参数</span>
                         </a>
                     </li>
                     <li <c:if test="${CT_MENU_INDEX=='setting_level'}"> class="active" </c:if> >
                         <a href="${basePath}ulevel/list.html">
                             <span class="menu-text">等级管理</span>
                         </a>
                     </li>
                     <li <c:if test="${CT_MENU_INDEX=='setting_log'}"> class="active" </c:if>>
                         <a href="${basePath}log/list.html">
                             <span class="menu-text">登录日志</span>
                         </a>
                     </li>
                     <li <c:if test="${CT_MENU_INDEX=='setting_notice'}"> class="active" </c:if>>
                         <a href="${basePath}notice/list.html">
                             <span class="menu-text">公告管理</span>
                         </a>
                     </li>
                     <li <c:if test="${CT_MENU_INDEX=='setting_api'}"> class="active" </c:if>>
                         <a target="_blank" href="${basePath}user/api.html">
                             <span class="menu-text">接口说明</span>
                         </a>
                     </li>
                 </ul>
             </li>

             
         </ul>
         <!-- /Sidebar Menu -->
     </div>
     <!-- /Page Sidebar -->