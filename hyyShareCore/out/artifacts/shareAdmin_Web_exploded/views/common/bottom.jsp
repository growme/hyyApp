<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="right-service closed">
   <img class="RS-img" src="${path}/views/user/img/wxb.png"/>
   <div class="RS-wrap">
       <div class="RS-title">@在线客服<span class="RS-title-right-btn"><i class="fa fa-angle-right"></i></span></div>
       <div class="RS-title-for-closed"><i class="fa fa-ellipsis-h"></i><br>在线客服</div>
       <ul class="RS-ul">
           <li class="RS-i1"><a href="tencent://message/?uin=${cust_sqq}&Site=wxb.com&Menu=yes">合作咨询<br><span>${cust_sname}</span></a></li>
           <li class="RS-i2"><a href="tencent://message/?uin=${cust_sqq1}&Site=wxb.com&Menu=yes">异常订单<br><span>${cust_sname1}</span></a></li>
           <li class="RS-i3"><a href="tencent://message/?uin=${cust_sqq2}&Site=wxb.com&Menu=yes">财务返款<br><span>${cust_sname2}</span></a></li>
       </ul>
       <div class="RS-bottom">${supply_phone}</div>
   </div>
</div>

<div class="copyright">
    <div class="container text-center">
            <p>
            <span class="copyright-icon copyright-icon-1"><img src="${basePath}/views/user/images/copyright-pic-1.jpg" width="94"></span>
            <span class="copyright-icon copyright-icon-2"><img src="${basePath}/views/user/images/copyright-pic-2.jpg" width="94"></span>
            <span class="copyright-icon copyright-icon-4"><img src="${basePath}/views/user/images/copyright-pic-4.jpg" width="109"></span>
        </p>
    	<p>
           Copyright©2014 湖南蜂码网络科技有限公司 All Rights Reserved 版权所有 湘ICP备1100873632号-1
        </p>
        <p class="p2">客服电话：400-123-1234</p>
    </div>
</div>
