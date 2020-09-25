<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%@ include file="../../../common/includes.jsp"%>
<%@ include file="../../../common/com_css.jsp"%>
<title>测试</title>
</head>
<body>
	<!-- Navbar -->
	<%@ include file="../../../common/navbar.jsp"%>
	<!-- /Navbar -->

	<!-- Main Container -->
	<div class="main-container container-fluid">
		<!-- Page Container -->
		<div class="page-container">
			<!-- menu Container -->
			<%@ include file="../../../common/menu.jsp"%>
			<!--  /menu Container -->
			<!-- Page Content -->
			<div class="page-content">
				<!-- Page Breadcrumb -->
				<%@ include file="../../../common/navtip.jsp"%>
				<!-- /Page Breadcrumb -->
				<!-- Page Header -->
				<%@ include file="../../../common/page_header.jsp"%>
				<!-- /Page Header -->
				<!-- Page Body -->
				<div class="page-body margin-10">
				   <h1><fmt:formatNumber value="8899" type="currency" pattern="0.00"/></h1>
				   
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
	                              <h1>222</h1>
	                              <p>平台会员数</p>
	                          </div>
	                          </a>
	                      </section>
	                  </div>
	                  
	                  <div class="col-lg-3 col-sm-6">
	                      <section class="panel">
	                          <div class="symbol blue">
	                              <i class="fa fa-money"></i>
	                          </div>
	                          <div class="value">
	                               <h1><fmt:formatNumber value="8899" type="currency" pattern="0.00"/></h1>
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
	                              <h1><fmt:formatNumber value="8899" type="currency" pattern="0.00"/></h1>
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
	                               <h1><fmt:formatNumber value="8899" type="currency" pattern="0.00"/></h1>
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
	                               <h1><fmt:formatNumber value="8899" type="currency" pattern="0.00"/></h1>
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
	                              <h1><fmt:formatNumber value="8899" type="currency" pattern="0.00"/></h1>
	                              <p>已支付佣金</p>
	                          </div>
	                      </section>
	                  </div>
	              </div>
                 </div>
                </div>
				
				
				</div>
			</div>
		</div>
	</div>


<%@ include file="../../../common/com_js.jsp"%>
<!--Page Related Scripts-->
<script src="http://hqkd8.com/admin/static/js/charts/echarts/echarts.min.js"></script>
<script src="http://hqkd8.com/admin/static/js/charts/echarts/china.js"></script>
<script src="http://hqkd8.com/admin/static/js/charts/echarts/skins/shine.js"></script>
 
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
<script src="http://hqkd8.com/admin/static/js/admin/home/home.js"></script>

</body>
</html>