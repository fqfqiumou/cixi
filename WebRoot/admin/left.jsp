<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if  test="${sessionScope.useradmin.power==413}">
<div class="panel panel-primary hidden-xs" style="width:80%;">
	<div class="panel-heading" role="tab" id="headingOne">
		<h4 class="panel-title">
			<a role="button" href="sglfastMessage.jsp" >慈团快讯</a>
		</h4>
	</div>
	
</div>
</c:if>

<div id="left" class="panel panel-primary hidden-xs" style="width:80%">
	<div class="panel-heading" role="tab" id="headingOne">
		<h4 class="panel-title">
			<a role="button" href="sglbaseShow.jsp" >基层动态</a>
		</h4>
	</div>
	<div v-for="data in datas" id="accordion3" class="panel-collapse collapse in" role="tabpanel"
		aria-labelledby="headingTwo">
		<div class="list-group">
			<a :href="'JCDTsee.jsp?id='+data.id" class="list-group-item">{{data.name}}</a>
		</div>
	</div>
</div>

<c:if  test="${sessionScope.useradmin.power==413}">
<div class="panel panel-primary hidden-xs" style="width:80%">
	<div class="panel-heading" role="tab" id="headingTwo">
		<h4 class="panel-title">
			<a role="button" href="sglworkPrefecture.jsp" >工作专区</a>
		</h4>
	</div>
</div>
</c:if>

<div class="panel panel-primary hidden-xs" style="width:80%">
	<div class="panel-heading" role="tab" id="headingTwo">
		<h4 class="panel-title">
			<a class="collapsed" role="button" data-toggle="collapse"
				data-parent="#accordion" href="#accordion13" aria-expanded="true"
				aria-controls="collapseTwo">互动平台</a>
		</h4>
	</div>
	<c:if  test="${sessionScope.useradmin.power==413}">
	<div id="accordion3" class="panel-collapse collapse in" role="tabpanel"
		aria-labelledby="headingTwo">
		<div class="list-group">
			<a href="sgltuanshi.jsp" class="list-group-item">团史团识</a>
		</div>
	</div>
	<div id="accordion3" class="panel-collapse collapse in" role="tabpanel"
		aria-labelledby="headingTwo">
		<div class="list-group">
			<a href="sglDoc.jsp" class="list-group-item">文件库</a>
		</div>
	</div>
	</c:if>
	<div id="accordion3" class="panel-collapse collapse in" role="tabpanel"
		aria-labelledby="headingTwo">
		<div class="list-group">
			<a href="monthReportList.jsp" class="list-group-item">月度工作</a>
		</div>
	</div>
	<!--div id="accordion3" class="panel-collapse collapse in" role="tabpanel"
		aria-labelledby="headingTwo">
		<div class="list-group">
			<a href="sglinformation.jsp" class="list-group-item">信息报送</a>
		</div>
	</div-->
	<c:if  test="${sessionScope.useradmin.power==413}">
	<div id="accordion3" class="panel-collapse collapse in" role="tabpanel"
		aria-labelledby="headingTwo">
		<div class="list-group">
			<a href="sgladvice.jsp" class="list-group-item">意见建议</a>
		</div>
	</div>
	<!--div id="accordion3" class="panel-collapse collapse in" role="tabpanel"
		aria-labelledby="headingTwo">
		<div class="list-group">
			<a href="sglinformation.jsp" class="list-group-item">信息系统</a>
		</div>
	</div-->
	<div id="accordion3" class="panel-collapse collapse in" role="tabpanel"
		aria-labelledby="headingTwo">
		<div class="list-group">
			<a href="sglsupervisionSystem.jsp" class="list-group-item">月报统计</a>
		</div>
	</div>
	<div id="accordion3" class="panel-collapse collapse in" role="tabpanel"
		aria-labelledby="headingTwo">
		<div class="list-group">
			<a href="jcdtStat.jsp" class="list-group-item">信息统计</a>
		</div>
	</div>
	</c:if>
</div>

<c:if  test="${sessionScope.useradmin.power==413}">
<div class="panel panel-primary hidden-xs" style="width:80%">
	<div class="panel-heading" role="tab" id="headingTwo">
		<h4 class="panel-title">
			<a role="button" href="sglopenPublic.jsp" >党务公开</a>
		</h4>
	</div>
</div>


<div class="panel panel-primary hidden-xs" style="width:80%">
	<div class="panel-heading" role="tab" id="headingTwo">
		<h4 class="panel-title">
			<a role="button" href="sglnoticeBoard.jsp" >公示公告</a>
		</h4>
	</div>
</div>
<div class="panel panel-primary hidden-xs" style="width:80%">
	<div class="panel-heading" role="tab" id="headingTwo">
		<h4 class="panel-title">
			<a role="button" href="newsPicture.jsp" >图片新闻</a>
		</h4>
	</div>
</div>

<div class="panel panel-primary hidden-xs" style="width:80%">
	<div class="panel-heading" role="tab" id="headingTwo">
		<h4 class="panel-title">
			<a role="button" href="user.jsp" >用户管理</a>
		</h4>
	</div>
</div>
</c:if>


<!-- -->
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="js/jquery-1.11.3.min.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script type="text/javascript" src="bs/js/bootstrap.min.js"></script>
<script src="js/vue.min.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
	var Start = 0;
	var left = new Vue({
		el : "#left",
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
							left.datas = data[0].data;
							Start = Start + data[0].data.length;
						}
					});
			}
		}
	});
</script>