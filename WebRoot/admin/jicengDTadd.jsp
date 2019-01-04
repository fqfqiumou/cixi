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
					<li class="active">基层动态</li>
					<li class="active">添加</li>
				</ol>
				<div id="Mybtns" >
					<a href="sglfastMessage.jsp" class="btn btn-info pull-right btn-fr clearfix">返回</a>
				<form  method="post" id="tf"
							enctype="multipart/form-data" style="padding-top:30px">
							<div class="form-group">
								<label for="exampleInputPassword1">名称:</label> <input id="name"
									type="text" style="width:20%" class="form-control"
									name="name">
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
		if (document.getElementById("name").value == null || document.getElementById("name").value.length <= 0) {
			alert("标题不允许为空");
			return;
		}
		 $.ajax({
					url : "addDynamic.do",
					data : {
						name : document.getElementById("name").value
					},
					dataType : "json",
					success : function(data) {
						alert(data[0].message);
						window.setTimeout("window.location='sglbaseShow.jsp'",1000); 
					}
				}); 

	}
</script>
