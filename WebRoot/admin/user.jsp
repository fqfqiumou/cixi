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
<title>慈溪共青团管理后台</title>
<style type="text/css">
#Mybtns .btn-fl {
	margin-left: 20px;
	margin-bottom: 15px;
}
</style>

</head>
<!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
<link rel="stylesheet" href="bs/css/bootstrap.min.css">
<body ng-app="app">
	<jsp:include page="top.jsp"></jsp:include>
	<div class="container-fluid">
		<div class="row">
			<div class="col-sm-2 ">
				<jsp:include page="left.jsp"></jsp:include>
			</div>
			<div class="col-sm-10">
				<ol class="breadcrumb">
					<li class="active">用户管理</li>
					<li class="active">查看</li>
				</ol>
				<div id="Mybtns" ng-controller="companyController">
					<a href="useradd.jsp" class="btn btn-info btn-fl">添加</a>
					<div>
						<div class="table-responsive">
							<table class="table table-striped text-center">
								<thead>
									<tr>
										<td>ID</td>
										<td>用户名</td>
										<td>姓名</td>
										<td>重置密码</td>
										<td>修改</td>
										<td>删除</td>
									</tr>
								</thead>
								<tbody id="demo">
									<tr v-for="data in datas" id="data.id">
										<td>{{data.id}}</td>
										<td>{{data.name}}</td>
										<td>{{data.contact}}</td>
										<td><a @click="cz(data)">重置</a></td>
										<td><a :href="'userChange.jsp?id='+data.id">修改</a></td>
										<td v-if="data.power>0"><a @click="del(data)">停用</a></td>
										<td v-if="data.power==0"><a @click="del(data)">启用</a></td>
									</tr>
								</tbody>
							</table>
						</div>
						<div id="btn btn-info center-block" class="btn btn-info
						center-block" style="width:100px;" id="more"
						onclick="demo.loadMore();">加载更多
					</div>
					</div>
				</div>
			</div>

</body>
<!-- -->
<script src="js/jquery-1.11.3.min.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script type="text/javascript" src="bs/js/bootstrap.min.js"></script>

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
							url : "../superadmin/getUserAdminAll.do",
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
							url : "../superadmin/getUserAdminAll.do",
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

						$
							.ajax({
								url : "../superadmin/delUseradmin.do",
								data : {
									userAdminId : obj.id
								},
								dataType : "json",
								type : "post",
								success : function(data) {
									alert(data[0].message);
									location.reload();
								}
							});
					}
				},
				cz : function(obj) {
					if (obj != null) {

						$
							.ajax({
								url : "../superadmin/rePassword.do",
								data : {
									userAdminId : obj.id
								},
								type : "post",
								dataType : "json",
								success : function(data) {
									alert(data[0].message);
								}
							});
					}
				}
			}
		});
</script>

</html>
