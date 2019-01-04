<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
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
<style type="text/css">
#Mybtns .btn-fr {
	margin-right: 20px;
	margin-bottom: 15px;
}

#Mybtns .btn-fl {
	margin-left: 20px;
	margin-bottom: 15px;
}

#addbox {
	width: 500px;
	display: none;
	margin: 10px auto 0;
}

#updatebox {
	width: 500px;
	display: none;
	margin: 10px auto 0;
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
					<li class="active">工作专区</li>
					<li class="active">查看</li>
				</ol>
				<div id="Mybtns" ng-controller="chassisseeController">
					<a href="GZZQadd.jsp" class="btn btn-info btn-fl">添加</a> <a
						href="sglfastMessage.jsp"
						class="btn btn-info pull-right btn-fr clearfix">返回</a>
					<div>
						<div class="table-responsive">
							<table class="table table-striped text-center">
								<thead>
									<tr>
										<td>ID</td>
										<td>名称</td>
										<td>帖子</td>
										<td>修改</td>
										<td>删除</td>
									</tr>
								</thead>
								<tbody id="demo">
									<tr v-for="data in datas" id="data.id">
										<td>{{data.id}}</td>
										<td>{{data.name}}</td>
										<td><a :href="'GZZQsee.jsp?id='+data.id">查看</a></td>
										<td><a :href="'GZZQchange.jsp?id='+data.id">修改</a></td>
										<td><a @click="del(data)">删除</a></td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
						<div class="btn btn-info center-block" id="btn btn-info center-block"
							style="width:100px;display:none" id="more" onclick="demo.loadMore();">加载更多</div>
				</div>
			</div>
		</div>
</body>
<!-- -->
<script src="js/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="bs/js/bootstrap.min.js"></script>
<script src="layer/layer.js"></script>
<script src="js/vue.min.js" type="text/javascript"
	charset="utf-8"></script>
<script type="text/javascript">
	var Start = 0;
	var demo = new Vue(
		{
			el : "#demo",
			data : {
				datas : {}
			},
			created : function() {
				this.enterSearch(); //定义方法

			},
			methods : {
				enterSearch : function() {
					$
						.ajax({
							url : "getZoneAll.do",
							data : {
								start : Start,
								size : 10
							},
							dataType : "json",
							success : function(data) {
								demo.datas = data[0].data;
								if (data[0].data.length >= 10) {
									document
										.getElementById("btn btn-info center-block").style.display = "block";
								} else {
									document
										.getElementById("btn btn-info center-block").style.display = "none";
								}
								Start = Start + data[0].data.length;
							}
						});
				},
				loadMore : function() {
					$
						.ajax({
							url : "getZoneAll.do",
							data : {
								start : Start,
								size : 10
							},
							dataType : "json",
							success : function(data) {
								demo.datas = demo.datas
									.concat(data[0].data);

								if (data[0].data.length >= 10) {
									document
										.getElementById("btn btn-info center-block").style.display = "block";
								} else {
									document
										.getElementById("btn btn-info center-block").style.display = "none";
								}
								Start = Start + data[0].data.length;
								console.log(Start);
							}
						});
				},
				del : function(obj) {
					if (obj != null) {
						if (confirm("确认删除吗?其中内容将全部丢失！")) {
							$
								.ajax({
									url : "delZone.do",
									data : {
										id : obj.id
									},
									dataType : "json",
									success : function(data) {
										alert(data[0].message);
										if (data[0].message == '成功') {
											var index = demo.datas
												.indexOf(obj);
											if (index > -1) {
												demo.datas.splice(index, 1);
											}
										}
									}
								});

						}

					}
				}
			}
		});
</script>
</html>
