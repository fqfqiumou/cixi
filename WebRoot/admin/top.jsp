<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<script type="text/javascript" >
function butEnd() {
	$.ajax({
		url: "../logout.do",
		dataType:"json",
		success: function(data) {
			alert(data[0].message);
			if(data[0].message=="成功"){
			window.location.replace("../login.html");
			}
		}
	});
}
</script>
<nav class="navbar navbar-default" role="navigation">
	<div class="container-fluid">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target="#example-navbar-collapse">
				<span class="sr-only">切换导航</span> <span class="icon-bar"></span> <span
					class="icon-bar"></span> <span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="#"> </a>
		</div>
		<div class="collapse navbar-collapse" id="example-navbar-collapse">
		<ul class="nav navbar-nav">
				<li><h2>慈溪共青团网站管理</h2></li>
			<!-- 	<li><a href="user.jsp">用户列表</a></li>
				<li><a href="message.jsp">留言列表</a></li>
				<li><a href="log.jsp">日志列表</a></li> -->
			</ul> 
			<ul class="nav navbar-nav navbar-right">
				<li><a>欢迎您,${sessionScope.useradmin.contact}</a></li>
				<li onclick="butEnd();"><a >安全退出</a></li>
			</ul>
		</div>
	</div>
</nav>