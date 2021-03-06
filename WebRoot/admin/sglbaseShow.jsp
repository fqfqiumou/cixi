<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
					<li class="active">基层动态</li>
					<li class="active">查看</li>
				</ol>
				<div id="Mybtns" ng-controller="companyController">
				<c:if  test="${sessionScope.useradmin.power==413}">
					<a href="jicengDTadd.jsp" class="btn btn-info btn-fl">添加</a>
				</c:if>	
					<div>
						<div class="table-responsive">
							<table class="table table-striped text-center">
								<thead>
									<tr>
										<td>ID</td>
										<td>名称</td>
										<td>操作</td>
									</tr>
								</thead>
								<tbody id="demo">
									<tr v-for="data in datas" id="data.id">
										<td>{{data.id}}</td>
										<td>{{unescape(data.name)}}</td>
										<td><a :href="'JCDTsee.jsp?id='+data.id">查看</a></td>
										<c:if  test="${sessionScope.useradmin.power==413}">
										<td><a :href="'jicengDTchange.jsp?id='+data.id">修改</a></td>
										<td><a @click="del(data)">删除</a></td>
										</c:if>
									</tr>
								</tbody>
							</table>
						</div>
						<div class="btn btn-info center-block"
							id="btn btn-info center-block" style="width:100px;display:none"
							id="more" onclick="demo.loadMore();">加载更多</div>
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
							url : "getDynamicAll.do",
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
							url : "getDynamicAll.do",
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
				unescape : function(text) {
					var temp = document.createElement("div");
					temp.innerHTML = text;
					var output = temp.innerText || temp.textContent;
					temp = null;
					return output;
				},
				del : function(obj) {
					if (obj != null) {
						if (confirm("确认删除吗?其中内容将全部丢失！")) {
							$.ajax({
								url : "delDynamic.do",
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
