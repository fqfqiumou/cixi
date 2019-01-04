<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML>
<html>
<head>
<meta charset="UTF-8">
<meta name="Generator" content="EditPlus®">
<meta name="Author" content="">
<meta name="Keywords" content="">
<meta name="Description" content="">
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
<title>Document</title>
</head>
<!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
<link rel="stylesheet" type="text/css" href="css/lq.datetimepick.css"/>
<link rel="stylesheet" href="bs/css/bootstrap.min.css">
<body>
	<jsp:include page="top.jsp"></jsp:include>
	<div class="container-fluid">
		<div class="row">
			<div class="col-sm-2">
				<jsp:include page="left.jsp"></jsp:include>
			</div>
			<div class="col-sm-10">
			 
				<ol class="breadcrumb">
					<li class="active">信息系统</li>
					<li class="active">查看</li>
				</ol>
				  <div class="form-group float-left w140" style="width: 100%">
                                    <input type="text" name="datepicker" id="datetimepicker3" class="form-control"
                                           value="" style="width: 10%"/>
                                </div>
                                <button value="获取" onclick="select();">获取</button>
				<div id="container" style="height: 100%;width: 100%;height:600px"></div>
			</div>
		</div>
	</div>
	<script src="js/jquery-1.11.3.min.js"></script>
	<script src="libs/echarts.common.min.js"></script>
	<script type="text/javascript" src="bs/js/bootstrap.min.js"></script>
		<script src='js/selectUi.js' type='text/javascript'></script>
<script src='js/lq.datetimepick.js' type='text/javascript'></script>
	<script src="layer/layer.js"></script>
	<script type="text/javascript">
	$(function () {
	    $("#datetimepicker3").on("click", function (e) {
	        e.stopPropagation();
	        $(this).lqdatetimepicker({
	            css: 'datetime-day',
	            dateType: 'D',
	            selectback: function () {

	            }
	        });

	    });
	});
	var d = new Date();   
	var date = (d.getFullYear()) + "-" + 
	           (d.getMonth() + 1) + "-" +
	           (d.getDate());
	document.getElementById("datetimepicker3").value=date;
		var datas;
		var dom = document.getElementById("container");
		var myChart = echarts.init(dom);
		var app = {};
		option = null;
		app.title = '堆叠柱状图';
		$.ajax({
			url : "tongji.do",
			dataType : "json",
			success : function(data) {
				datas = data[0].data;
	
				option = {
					tooltip : {
						trigger : 'axis',
						axisPointer : { // 坐标轴指示器，坐标轴触发有效
							type : 'shadow' // 默认为直线，可选为：'line' | 'shadow'
						}
					},
					legend : {
						data : [ '显示数据' ]
					},
					grid : {
						left : '3%',
						right : '4%',
						bottom : '3%',
						containLabel : true
					},
					xAxis : [
						{
							type : 'category',
							data : [ datas[0].name, datas[1].name, datas[2].name, datas[3].name, datas[4].name, datas[5].name, datas[6].name, datas[7].name ]
						}
					],
					yAxis : [
						{
							type : 'value'
						}
					],
					series : [
						{
							name : '帖子数量',
							type : 'bar',
							data : [ datas[0].count, datas[1].count, datas[2].count, datas[3].count, datas[4].count, datas[5].count, datas[6].count, datas[7].count ],
							color : '#777777'
						},
	
	
					]
				};
				;
				if (option && typeof option === "object") {
					myChart.setOption(option, true);
				}
				myChart.on('click', function(params) {
					myChart.clear();
					myChart.setOption(option, true);
				});
			}
		});
		function select(){
			$.ajax({
				url : "tongjiByDate.do",
				data:{
					date:document.getElementById("datetimepicker3").value
				},
				dataType : "json",
				success : function(data) {
					datas = data[0].data;
		
					option = {
						tooltip : {
							trigger : 'axis',
							axisPointer : { // 坐标轴指示器，坐标轴触发有效
								type : 'shadow' // 默认为直线，可选为：'line' | 'shadow'
							}
						},
						legend : {
							data : [ '显示数据' ]
						},
						grid : {
							left : '3%',
							right : '4%',
							bottom : '3%',
							containLabel : true
						},
						xAxis : [
							{
								type : 'category',
								data : [ datas[0].name, datas[1].name, datas[2].name, datas[3].name, datas[4].name, datas[5].name, datas[6].name, datas[7].name ]
							}
						],
						yAxis : [
							{
								type : 'value'
							}
						],
						series : [
							{
								name : '帖子数量',
								type : 'bar',
								data : [ datas[0].count, datas[1].count, datas[2].count, datas[3].count, datas[4].count, datas[5].count, datas[6].count, datas[7].count ],
								color : '#777777'
							},
		
		
						]
					};
					;
					if (option && typeof option === "object") {
						myChart.setOption(option, true);
					}
					myChart.on('click', function(params) {
						myChart.clear();
						myChart.setOption(option, true);
					});
				}
			});
			
		}
	</script>
</body>
</html>

