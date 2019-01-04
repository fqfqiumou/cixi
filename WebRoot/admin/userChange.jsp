<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML >
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
<link href="themes/default/css/umeditor.css" type="text/css"
	rel="stylesheet">
<script type="text/javascript" src="third-party/jquery.min.js"></script>
<script type="text/javascript" charset="utf-8"
	src="js/umeditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="js/umeditor.min.js"></script>
<script type="text/javascript" src="lang/zh-cn/zh-cn.js"></script>
<style type="text/css">
h1 {
	font-family: "微软雅黑";
	font-weight: normal;
}

.btn {
	display: inline-block;
	*display: inline;
	padding: 4px 12px;
	margin-bottom: 0;
	*margin-left: .3em;
	font-size: 14px;
	line-height: 20px;
	color: #333333;
	text-align: center;
	text-shadow: 0 1px 1px rgba(255, 255, 255, 0.75);
	vertical-align: middle;
	cursor: pointer;
	background-color: #f5f5f5;
	*background-color: #e6e6e6;
	background-image: -moz-linear-gradient(top, #ffffff, #e6e6e6);
	background-image: -webkit-gradient(linear, 0 0, 0 100%, from(#ffffff),
		to(#e6e6e6));
	background-image: -webkit-linear-gradient(top, #ffffff, #e6e6e6);
	background-image: -o-linear-gradient(top, #ffffff, #e6e6e6);
	background-image: linear-gradient(to bottom, #ffffff, #e6e6e6);
	background-repeat: repeat-x;
	border: 1px solid #cccccc;
	*border: 0;
	border-color: #e6e6e6 #e6e6e6 #bfbfbf;
	border-color: rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.25);
	border-bottom-color: #b3b3b3;
	-webkit-border-radius: 4px;
	-moz-border-radius: 4px;
	border-radius: 4px;
	filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#ffffffff',
		endColorstr='#ffe6e6e6', GradientType=0);
	filter: progid:DXImageTransform.Microsoft.gradient(enabled=false);
	*zoom: 1;
	-webkit-box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.2), 0 1px 2px
		rgba(0, 0, 0, 0.05);
	-moz-box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.2), 0 1px 2px
		rgba(0, 0, 0, 0.05);
	box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.2), 0 1px 2px
		rgba(0, 0, 0, 0.05);
}

.btn:hover, .btn:focus, .btn:active, .btn.active, .btn.disabled, .btn[disabled]
	{
	color: #333333;
	background-color: #e6e6e6;
	*background-color: #d9d9d9;
}

.btn:active, .btn.active {
	background-color: #cccccc \9;
}

.btn:first-child {
	*margin-left: 0;
}

.btn:hover, .btn:focus {
	color: #333333;
	text-decoration: none;
	background-position: 0 -15px;
	-webkit-transition: background-position 0.1s linear;
	-moz-transition: background-position 0.1s linear;
	-o-transition: background-position 0.1s linear;
	transition: background-position 0.1s linear;
}

.btn:focus {
	outline: thin dotted #333;
	outline: 5px auto -webkit-focus-ring-color;
	outline-offset: -2px;
}

.btn.active, .btn:active {
	background-image: none;
	outline: 0;
	-webkit-box-shadow: inset 0 2px 4px rgba(0, 0, 0, 0.15), 0 1px 2px
		rgba(0, 0, 0, 0.05);
	-moz-box-shadow: inset 0 2px 4px rgba(0, 0, 0, 0.15), 0 1px 2px
		rgba(0, 0, 0, 0.05);
	box-shadow: inset 0 2px 4px rgba(0, 0, 0, 0.15), 0 1px 2px
		rgba(0, 0, 0, 0.05);
}

.btn.disabled, .btn[disabled] {
	cursor: default;
	background-image: none;
	opacity: 0.65;
	filter: alpha(opacity = 65);
	-webkit-box-shadow: none;
	-moz-box-shadow: none;
	box-shadow: none;
}
</style>
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
					<li class="active">修改</li>
				</ol>
				<div class="form-group">
					<label for="exampleInputPassword1">用户名:</label> <input id="name"
						type="text" style="width:20%" class="form-control"
						name="company.redistheme">
				</div>
				<div class="form-group">
					<label for="exampleInputPassword1">使用者:</label> <input id="contact"
						type="text" style="width:20%" class="form-control"
						name="company.redistheme">
				</div>
				<div class="form-group">
					<label for="exampleInputPassword1">手机号:</label> <input id="phone"
						type="text" style="width:20%" class="form-control"
						name="company.redistheme">
				</div>

				<div id="btns">
					<table>
						<tr>
							<td>
								<button class="btn" onclick="getContent()">提交</button>&nbsp;
							</td>
						</tr>
					</table>
				</div>

				<div>
					<h3 id="focush2"></h3>
				</div>
</body>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script type="text/javascript" src="bs/js/bootstrap.min.js"></script>

<script src="layer/layer.js"></script>
<script type="text/javascript">
	var str = window.location.href;
	var id = str.split('=')[1];

	$.ajax({
		url : "../superadmin/findUseradminById.do",
		data : {
			Id : id
		},
		dataType : "json",
		success : function(data) {
			console.log(data);
			document.getElementById("name").value = data[0].data.name;
			document.getElementById("phone").value = data[0].data.phone;
			document.getElementById("contact").value = data[0].data.contact;
		}
	});
	function getContent() {
		if (document.getElementById("contact").value == null || document.getElementById("contact").value == "" || document.getElementById("contact").value.length <= 0) {
			alert("使用者不允许为空");
			return;
		}
		if (document.getElementById("phone").value == null || document.getElementById("phone").value == "" || document.getElementById("phone").value.length <= 0) {
			alert("手机号不允许为空");
			return;
		}
		if (document.getElementById("name").value == null || document.getElementById("name").value == "" || document.getElementById("name").value.length <= 0) {
			alert("使用者不允许为空");
			return;
		}
		$.ajax({
			url : "../superadmin/updateUseradmin.do",
			data : {
				Id : id,
				contact : document.getElementById("contact").value,
				phone : document.getElementById("phone").value,
				name : document.getElementById("name").value
			},
			dataType : "json",
			success : function(data) {
				console.log(data[0].message);
				self.location = document.referrer;
			}
		});
	}
</script>

</html>
