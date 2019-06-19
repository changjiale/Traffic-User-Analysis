<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<head>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
	<title>应用欢迎度</title>
	<link href="${pageContext.request.contextPath }/css/css.css" rel="stylesheet" type="text/css" />
	
    <script src="${pageContext.request.contextPath }/js/echarts/dist/echarts-all.js"></script>
	<script src="${pageContext.request.contextPath }/js/laydate/laydate.js"></script>
	<script type="text/javascript">
		window.onload = function(){
	        // 基于准备好的dom，初始化echarts图表
       		var myChart = echarts.init(document.getElementById('main')); 

        	//设置选项
     	 	option = {
	     	 	//标题
			    ${map.title},
			    //提示框
			    tooltip : {
			        trigger: 'item',
			        formatter: "{a} <br/>{b} : {c} ({d}%)"
			    },
			    //图例
			    ${map.legend},
			    //工具箱
			    toolbox: {
			        show : true,
			        feature : {
			            mark : {show: true},
			            dataView : {show: true, readOnly: false},
			            magicType : {
			                show: true, 
			                type: ['pie', 'funnel'],
			                option: {
			                    funnel: {
			                        x: '25%',
			                        width: '50%',
			                        funnelAlign: 'left',
			                        max: ${map.sum}
			                    }
			                }
			            },
			            restore : {show: true},
			            saveAsImage : {show: true}
			        }
			    },
			    //是否启用拖拽重计算特性，默认关闭
			    calculable : true,
			    //驱动图表生成的数据内容
			    ${map.series}
			};

	        // 为echarts对象加载数据 
	        myChart.setOption(option); 
        }
        
    </script>
</head>
<body>
	<div class="box">
		<div>
			<img src="${pageContext.request.contextPath }/img/bg_01.jpg" width="980" height="33" />
		</div>
		<div class="middle">
			<ul class="list_one">
				<li class="list_on"><a href="#">统计分析</a>
				</li>
				<li><a href="#">系统配置</a>
				</li>
			</ul>
			<ul class="list_two clear">
				<li class="list_on01"><a href="#">应用欢迎度</a>
				</li>
				<li><a href="#">网站表现</a>
				</li>
				<li><a href="#">小区上网能力</a>
				</li>
				<li><a href="#">小区上网喜好</a>
				</li>
			</ul>
			<div style="margin-left:14px;">
				<img src="${pageContext.request.contextPath }/img/bd01.jpg" width="945" height="13"
					style="*float:left;" />
				<div class="box_middle">
					<form method="post" action="${pageContext.request.contextPath }/servlet/APP02Servlet">
						<table width="729" border="0" cellspacing="0" cellpadding="0" class="table_list">
							<tr>
								<td width="115" height="63" align="center" valign="middle">天</td>
								<td width="236" align="left" valign="middle">
									${param.date }
								</td>
								<td width="104" align="center" valign="middle">指标:</td>
								<td width="274" align="left" valign="middle">
									<c:if test="${param.type==1 }">总流量</c:if>
									<c:if test="${param.type==2 }">上行流量</c:if>
									<c:if test="${param.type==3 }">下行流量</c:if>
									<c:if test="${param.type==4 }">尝试次数</c:if>
									<c:if test="${param.type==5 }">接受次数</c:if>
								</td>
								<td width="50"> 
								</td>
							</tr>
							<tr align="center" width="800" valign="middle">
								<td colspan="5">
									<div id="main" style="height:400px"></div>
								</td>
							</tr>
							<tr>
								<td height="147" colspan="5" align="center" valign="middle">TOP10饼图【大类】</td>
							</tr>
						</table>
					</form>
				</div>
				<img src="${pageContext.request.contextPath }/img/bd03.jpg" width="945" height="13" />
			</div>
		</div>
		<div>
			<img src="${pageContext.request.contextPath }/img/bg_05.jpg" width="980" height="44" />
		</div>
	</div>
</body>
</html>
