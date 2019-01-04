<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
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
<script type="text/javascript" src="js/DatePicker.js"></script>
<link rel="stylesheet" type="text/css" href="css/lq.datetimepick.css"/>
<style type="text/css">
table {
	border-collapse: collapse;
	margin: 0 auto;
	text-align: center;
}

table td,table th {
	border: 1px solid #cad9ea;
	color: #666;
	height: 30px;
}

table thead th {
	background-color: #CCE8EB;
	width: 200px;
}

table tr:nth-child(odd) {
	background: #fff;
}

table tr:nth-child(even) {
	background: #F5FAFA;
}
</style>
</head>
<!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
<link rel="stylesheet" href="bs/css/bootstrap.min.css">
<body ng-app="app">
	<jsp:include page="top.jsp"></jsp:include>
	<div class="container-fluid">
		<div class="row">
			<div class="col-sm-2">
				<jsp:include page="left.jsp"></jsp:include>
			</div>
			<div class="col-sm-10">
				<ol class="breadcrumb">
					<li class="active">信息报表</li>
					<li class="active">查看</li>
				</ol>
				<div>
				开始日期<input id="datetimepicker" onclick="setmonth(this)" />
				结束日期<input id="datetimepicker2" onclick="setmonth(this)" />
				        <!--div class="form-group float-left w140" style="width: 100%">
                                    <input type="text" name="datepicker" id="datetimepicker3" class="form-control"
                                           value="" style="width: 10%"/>
                                </div-->
                                <button value="获取" onclick="demo.select();">获取</button>
					<table width="90%" class="table">
					<button value="导出" onclick="demo.export()">导出</button>
					<table id="demo" width="90%" class="table">
						<caption>
							<h3 style="text-align:center">月报表上报统计</h3>
						</caption>
						<thead>
							<tr>
								<th style="text-align:center">账号</th>
								<th style="text-align:center">姓名</th>
								<th v-for="month in monthes"  style="text-align:center">{{month}}</th>
								<th style="text-align:center">总计</th>

							</tr>
						</thead>
						<tbody>
							<tr v-for="data in datas" id="data.id">
								<td style="height: 40px">{{data.userName}}</td>
								<td style="height: 40px">{{data.userContact}}</td>
								<td v-for="month in monthes" style="height: 40px" >{{getMonthCount(data,month)}}</td>
								<td style="height: 40px">{{data.total}}</td>
								<td style="height: 40px"><img v-if="data.count>0" src="img/Green.png"
									style="height: 32px;width:32px;transform:scale(0.8)"> <img
									v-if="data.count==0" src="img/Red.png"
									style="height: 32px;width:32px;transform:scale(0.8)"></td>

							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</body>

</html>
<!-- -->
<script src="js/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="bs/js/bootstrap.min.js"></script>
<script src="js/vue.min.js" type="text/javascript"
	charset="utf-8"></script>
	<script src='js/selectUi.js' type='text/javascript'></script>
<script src='js/lq.datetimepick.js' type='text/javascript'></script>
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



	var demo = new Vue({
		el : "#demo",
		data : {
			datas : {},
			monthes : [],
		},
		created : function() {
			this.enterSearch(); //定义方法
		},
		methods : {
			enterSearch : function() {
				var d = new Date();   
				var date = (d.getFullYear()) - 1 + "-" + 
				           (d.getMonth() + 11);
				var dateEnd = (d.getFullYear()) + "-" + 
		           (d.getMonth() + 1);
				document.getElementById("datetimepicker").value=date;
				document.getElementById("datetimepicker2").value=dateEnd;
				$.ajax({
					url : "statDynote.do",
					data:{
						dateStart:document.getElementById("datetimepicker").value + "-1",
						dateEnd:document.getElementById("datetimepicker2").value + "-1"
					},
					dataType : "json",
					success : function(data) {
						demo.datas = data[0].data;
						demo.getMonthBetween(document.getElementById("datetimepicker").value,document.getElementById("datetimepicker2").value);
					
					}
				});
			},
			select : function() {
				$.ajax({
					url : "statDynote.do",
					data:{
						dateStart:document.getElementById("datetimepicker").value + "-1",
						dateEnd:document.getElementById("datetimepicker2").value + "-1"
					},
					dataType : "json",
					success : function(data) {
						demo.datas = data[0].data;
						demo.getMonthBetween(document.getElementById("datetimepicker").value,document.getElementById("datetimepicker2").value);
					
					}
				});
			},
			export : function() {
				location.href="exportDynoteStat.do?dateStart="+document.getElementById("datetimepicker").value + "-1"+"&dateEnd="+document.getElementById("datetimepicker2").value + "-1"
			},
			getMonthBetween : function(start,end){  
				 var result = [];  

	            var s = start.split("-");  

	            var e = end.split("-");  

	            var min = new Date();  

	            var max = new Date();  

	            min.setFullYear(s[0],s[1]-1);  

	            max.setFullYear(e[0],e[1]-1);  

	            var curr = min;  

	            while(curr <= max){  

	                var month = curr.getMonth();  

	                result.push(curr.getFullYear()+"-"+(month+1<10?("0"+(month+1)):(month+1)));  

	                curr.setMonth(month+1);  

	            }  

			    demo.monthes = result;
			    return result;  
			 },
			 getMonthCount : function(data,month){
				 console.log(month);
				 console.log(data[month]);
				 return data[month];
			 }
		}
	});
</script>
<script src="layer/layer.js"></script>
