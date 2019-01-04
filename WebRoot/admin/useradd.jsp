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
</head>
<style type="text/css">

	#Mybtns .btn-fr{
		margin-right:20px;
		margin-bottom:15px;
	}
</style>
<!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
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
					<li class="active">用户管理</li>
					<li class="active">添加</li>
				</ol>
				<div id="Mybtns" >
					<a href="user.jsp" class="btn btn-info pull-right btn-fr clearfix">返回</a>
				<form  method="post" id="tf"
							enctype="multipart/form-data" style="padding-top:30px">
							<div class="form-group">
								<label for="exampleInputPassword1">用户名:</label> <input id="name"
									type="text" style="width:20%" class="form-control"
									name="name">
							</div>				
							<div class="form-group">
								<label for="exampleInputPassword1">手机号:</label> <input id="phone"
									type="text" style="width:20%" class="form-control"
									name="name">
							</div>				
							<div class="form-group">
								<label for="exampleInputPassword1">姓名:</label> <input id="contact"
									type="text" style="width:20%" class="form-control"
									name="name">
							</div>
							<div style="width:20%" class="form-group">
								<label for="exampleInputPassword1">权限:</label> 
								<select class="form-control" id="power">
							  		<option value ="1">普通用户</option>
							  		<option value ="413">超级管理员</option>
								</select>
							</div>					
							<button type="button" class="btn btn-default text-align"
								onclick="submitTheForm()">添加</button>
						</form>
					
				
			
			</div>
		</div>
	</div>
</body>

</html>
<!-- -->
<script src="js/jquery-1.11.3.min.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script type="text/javascript" src="bs/js/bootstrap.min.js"></script>
<script type="text/javascript">
	function submitTheForm() {
		if (document.getElementById("contact").value == null || document.getElementById("contact").value == "" || document.getElementById("contact").value.length <= 0) {
			alert("姓名不允许为空");
			return;
		}
		if (document.getElementById("phone").value == null || document.getElementById("phone").value == "" || document.getElementById("phone").value.length <= 0) {
			alert("手机号不允许为空");
			return;
		}
		if (document.getElementById("name").value == null || document.getElementById("name").value == "" || document.getElementById("name").value.length <= 0) {
			alert("用户名不允许为空");
			return;
		}
		if (document.getElementById("power").value == null || document.getElementById("power").value == "" || document.getElementById("power").value.length <= 0) {
			alert("权限不允许为空");
			return;
		}
		 $.ajax({
					url : "../superadmin/addUseradmin.do",
					data : {
						name : document.getElementById("name").value,
						phone : document.getElementById("phone").value,
						contact : document.getElementById("contact").value,
						power : document.getElementById("power").value
					},
					dataType : "json",
					success : function(data) {
						alert(data[0].message);
						self.location=document.referrer;
					}
				}); 

	}
</script>
