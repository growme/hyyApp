<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>后台管理系统首页</title>
<meta name="description" content="后台管理首页" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ include file="../../../common/includes.jsp"%>
<%@ include file="../../../common/com_css.jsp"%>
</head>
<!-- /Head -->
<!-- Body -->
<body class="bodybg">
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
			</div>
			<!-- /Page Content -->
		</div>
		<!-- /Page Container -->
		<!-- Main Container -->
	</div>
	<%@ include file="../../../common/com_js.jsp"%>
	<!--Page Related Scripts-->
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
