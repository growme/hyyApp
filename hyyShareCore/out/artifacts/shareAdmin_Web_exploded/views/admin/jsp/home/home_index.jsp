<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>后台管理系统首页</title>
<meta name="description" content="后台管理首页"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ include file="../../../common/includes.jsp"%>
<%@ include file="../../../common/com_css.jsp"%>
</head>
<!-- /Head -->
<!-- Body -->
<body class="bodybg1">
  <!-- Page Body -->
  <div class="page-nbody margin-10">
   
   <div class="row no-padding">
     <div class="col-xs-12 col-md-12 col-sm-12 no-padding">
       <div class="state-overview clearfix no-padding no-margin">
       <div class="col-lg-3 col-sm-6">
           <section class="panel">
           <a href="#" title="平台会员总数">
               <div class="symbol terques">
                  <i class="fa fa-users"></i>
               </div>
               <div class="value">
                   <h1>${empty total.allMemberCount?0:total.allMemberCount}</h1>
                   <p>平台会员数</p>
               </div>
               </a>
           </section>
       </div>
       <div class="col-lg-3 col-sm-6">
           <section class="panel">
           <a href="#" title="平台会员总数">
               <div class="symbol terques">
                  <i class="fa fa-users"></i>
               </div>
               <div class="value">
                   <h1>${empty total.activeMemberCount?0:total.activeMemberCount}</h1>
                   <p>活跃会员数</p>
               </div>
               </a>
           </section>
       </div>
       <div class="col-lg-3 col-sm-6">
           <section class="panel">
               <div class="symbol blue">
                   <i class="fa fa-share"></i>
               </div>
               <div class="value">
                   <h1>${empty share.totalCount?0:share.totalCount}</h1>
                   <p>总分享次数</p>
               </div>
           </section>
       </div>
       <div class="col-lg-3 col-sm-6">
           <section class="panel">
               <div class="symbol blue">
                   <i class="fa fa-money"></i>
               </div>
               <div class="value">
                   <h1><fmt:formatNumber value="${empty share.shareMoney?'0':share.shareMoney}" type="currency" pattern="0.00"/></h1>
                   <p>总分享奖励</p>
               </div>
           </section>
       </div>
       <div class="col-lg-3 col-sm-6">
           <section class="panel">
               <div class="symbol red">
                   <i class="fa fa-money"></i>
               </div>
               <div class="value">
                   <h1><fmt:formatNumber value="${empty userMoney.totalMoney?'0':userMoney.totalMoney}" type="currency" pattern="0.00"/></h1>
                   <p>会员总收益</p>
               </div>
           </section>
       </div>
       <div class="col-lg-3 col-sm-6">
           <section class="panel">
               <div class="symbol red">
                   <i class="fa fa fa-money"></i>
               </div>
               <div class="value">
                   <h1><fmt:formatNumber value="${empty promoteMoney.totalMoney?'0': promoteMoney.totalMoney}" type="currency" pattern="0.00"/></h1>
                   <p>总推广收益</p>
               </div>
           </section>
       </div>
       <div class="col-lg-3 col-sm-6">
           <section class="panel">
               <div class="symbol green">
                   <i class="fa fa fa-money"></i>
               </div>
               <div class="value">
                   <h1><fmt:formatNumber value="${empty cashMoney.traddingMoney?'0': cashMoney.traddingMoney}" type="currency" pattern="0.00"/></h1>
                   <p>提现中佣金</p>
               </div>
           </section>
       </div>
       <div class="col-lg-3 col-sm-6">
           <section class="panel">
               <div class="symbol green">
                   <i class="fa fa-money"></i>
               </div>
               <div class="value">
                   <h1><fmt:formatNumber value="${empty cashMoney.tradedMoney?'0': cashMoney.tradedMoney}" type="currency" pattern="0.00"/></h1>
                   <p>已支付佣金</p>
               </div>
           </section>
       </div>
   </div>
   </div>
  </div>
  
 <div class="row">
   <div class="col-xs-12 col-md-6">
       <div class="widget">
           <div class="widget-header ">
               <span class="widget-caption">到目前会员推广收益统计</span>
               <div class="widget-buttons">
                    <a href="#" data-toggle="maximize">
                    <i class="fa fa-expand"></i>
                 </a>
                 <a href="#" data-toggle="collapse">
                     <i class="fa fa-minus"></i>
                 </a>
               </div>
           </div>
           <div class="widget-body">
              <div class="chartcontainer" id="myChart" style="width:100%;height:450px;"></div>
           </div>
       </div>
   </div>
   <div class="col-xs-12 col-md-6">
       <div class="widget">
           <div class="widget-header">
               <span class="widget-caption">按省份统计页面访问情况</span>

               <div class="widget-buttons">
                  <a href="#" data-toggle="maximize">
                    <i class="fa fa-expand"></i>
                  </a>
                  <a href="#" data-toggle="collapse">
                    <i class="fa fa-minus"></i>
                  </a>
               </div>
           </div>

           <div class="widget-body">
               <div class="chartcontainer" id="myChart1" style="width:100%;height:450px;"></div>
                    </div>
                </div>
            </div>
        </div>
        
         <div class="row">
         <div class="col-xs-12 col-md-12 col-lg-12">
             <div class="widget">
                 <div class="widget-header">
                     <span class="widget-caption">按日期销售走势统计</span>
                     <div class="widget-buttons">
                         <a href="#" data-toggle="maximize">
                             <i class="fa fa-expand"></i>
                         </a>
                         <a href="#" data-toggle="collapse">
                             <i class="fa fa-minus"></i>
                         </a>
                     </div>
                 </div>
                 <div class="widget-body">
             <div class="chartcontainer" id="myChart2" style="width: 100%;height:400px;"></div>
        </div>
    </div>
 </div>
 </div>
   
</div>
<%@ include file="../../../common/scom_js.jsp"%>
<script src="${basePath}static/js/charts/echarts/echarts.min.js"></script>
<script src="${basePath}static/js/charts/echarts/china.js"></script>
<script src="${basePath}static/js/charts/echarts/skins/shine.js"></script>
 
<script type="text/javascript">
// 基于准备好的dom，初始化echarts实例
var myChart = echarts.init(document.getElementById('myChart'));
var myChart1 = echarts.init(document.getElementById('myChart1')); 
var myChart2 = echarts.init(document.getElementById('myChart2'));
//指定图表的配置项和数据
var myChartOption = {
    title : {
        text: '会员收益情况统计',
        subtext: '按资金类型',
        x:'center'
    },
    tooltip : {
        trigger: 'item',
        formatter: "{a} <br/>{b} : {c} ({d}%)"
    },
    legend: {
        orient: 'vertical',
        left: 'left',
        data: ${allStateTitles}
    },
    series : [
        {
            name: '资金类型',
            type: 'pie',
            radius : '55%',
            center: ['50%', '60%'],
            data:${allStateValues},
            itemStyle: {
                emphasis: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
            }
        }
    ]
};

var myChartOption1 = {
    
    tooltip: {
        trigger: 'item'
    },
    legend: {
        orient: 'vertical',
        left: 'left',
        data:${provStepTitles}
    },
    visualMap: {
        min: 0,
        max: 2500,
        left: 'left',
        top: 'bottom',
        text: ['高','低'], //文本，默认为数值文本
        calculable: true
    },
    toolbox: {
        show: true,
        orient: 'vertical',
        left: 'right',
        top: 'center',
        feature: {
            dataView: {readOnly: false},
            restore: {},
            saveAsImage: {}
        }
    },
    series: [
        {
            name: '',
            type: 'map',
            mapType: 'china',
            roam: false,
            label: {
                normal: {
                    show: true
                },
                emphasis: {
                    show: true
                }
            },
            data:[]
        }
        <c:forEach var="stepData" items="${provStepDatas}" varStatus="status">
	      ,{
            name: '${stepData.name}',
            type: 'map',
            mapType: 'china',
            label: {
                normal: {
                    show: true
                },
                emphasis: {
                    show: true
                }
            },
            data:${stepData.data}
         }
	    </c:forEach>
    ]
};


var myChartOption2 = {
    title: {
        text: ''
    },
    tooltip : {
        trigger: 'axis'
    },
    legend: {
        data:['访问次数','阅读次数','IP总数量']
    },
    toolbox: {
        feature: {
            saveAsImage: {}
        }
    },
    
    grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
    },
    xAxis : [
        {
            type : 'category',
            boundaryGap : false,
            data : ${visitDate}
        }
    ],
    yAxis : [
        {
            type : 'value'
        }
    ],
    series : [
        {
            name:'访问次数',
            type:'line',
            stack: '总量',
            areaStyle: {normal: {}},
            data:${visitClickCount}
        },
        {
            name:'阅读次数',
            type:'line',
            stack: '总量',
            areaStyle: {normal: {}},
            data:${visitReadCount}
        },
        {
            name:'IP总数量',
            type:'line',
            stack: '总量',
            areaStyle: {normal: {}},
            data:${visitIpCount}
        }
    ]
};
     
  myChart.setOption(myChartOption);
  myChart1.setOption(myChartOption1);
  myChart2.setOption(myChartOption2);
     
  function randomData() {
      return Math.round(Math.random()*1000);
  }
</script>
<script src="${ccnetpath}/static/js/admin/home/home.js"></script>
</body>
<!--  /Body -->
</html>
      