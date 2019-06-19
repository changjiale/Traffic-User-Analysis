<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<head>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
	<title>集群监控</title>
	
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.2.6.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery.timers-1.1.2.js" charset="utf-8"></script>
	<script type="text/javascript">
		window.onload = function(){
			$('.box').everyTime('2s',function(){
				$.ajax({
					url: "/MyZebraPage/servlet/MonitorServlet",
					success: function(data){
						document.getElementById("engin1_img").innerHTML = ""
						document.getElementById("engin2_img").innerHTML = ""
						var arr = data.split(',');
						for(var i=0;i<arr.length;i++){
							if(arr[i].indexOf("engin1")!=-1){
								var parent = document.getElementById("engin1_img")
								var div = document.createElement("div")
								div.innerHTML = "<img src='img/engin.png' />"
								parent.appendChild(div)
							}else{
								var parent = document.getElementById("engin2_img")
								var div = document.createElement("div")
								div.innerHTML = "<img style='margin-left:50px;' src='img/engin.png' />"
								parent.appendChild(div)
							}
						}
					}})
				})
        }
    </script>
<style>
.box{ width:100%;}
.jq1{ width:1042px; margin-left:auto; margin-right:auto; font-size:24px; font-family:"微软雅黑"; font-weight:bold; text-align:center; margin-bottom:40px;}
.engin1{ width:500px; height:400px; border-color: red;border-left-width: 1px;border-style: solid;float: left; margin-left: 100px;}
.engin2{ width:500px; height:400px; border-color: red;border-left-width: 1px;border-style: solid; margin-left: 650px; }
.engin1_title,.engin2_title{ margin-left:auto; margin-right:auto; font-size:22px; font-family:"微软雅黑"; font-weight:bold; text-align:center; }
</style>
</head>
<body>
	<div class="box">
		<div class="jq1">ZooKeeper集群监控图</div>
		<div class="engin1">
			<div class="engin1_title">一级引擎</div>
			<div id="engin1_img"></div>
		</div>
		<div class="engin2">
			<div class="engin2_title">二级引擎</div>
			<div id="engin2_img">fdf</div>
		</div>
	</div>
</body>
</html>