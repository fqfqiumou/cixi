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
					<li class="active">意见建议</li>
					<li class="active">查看</li>
				</ol>
				<ul id="myTab" class="nav nav-tabs">
					<li class="active">
						<a href="#Mybtns" data-toggle="tab">
							 待回复
						</a>
					</li>
					<li><a href="#Mybtns2" data-toggle="tab">已回复</a></li>
				</ul>
				<br/>
				<div id="myTabContent" class="tab-content">
					<div class="tab-pane fade in active" id="Mybtns">
						<div>
							<div class="table-responsive">
								<table class="table table-striped text-center">
									<thead>
										<tr>
											<td>ID</td>
											<td>标题</td>
											<td>姓名</td>
											<td>电话</td>
											<td>时间</td>
											<td>操作</td>
										</tr>
									</thead>
									<tbody id="demo">
										<tr v-for="data in datas" id="data.id">
											<td>{{data.msgId}}</td>
											<td>{{unescape(data.title)}}</td>
											<!--td><a href="javascript:void(0)"
												@click="showMessage(data.content)">留言信息</a></td-->
											<td>{{data.name}}</td>
											<td>{{data.phone}}</td>
											<td>{{formatDate(data.date.time)}}</td>
											<td><a :href="'adviceReply.jsp?id='+data.msgId">查看</a></td>
											<td><a @click="del(data)">删除</a></td>
										</tr>
									</tbody>
								</table>
							</div>
							<div id="btn btn-info center-block"
								class="btn btn-info
							center-block" style="width:100px;"
								id="more" onclick="demo.loadMore();">加载更多</div>
						</div>
					</div>
					<div class="tab-pane fade" id="Mybtns2">
						<div>
							<div class="table-responsive">
								<table class="table table-striped text-center">
									<thead>
										<tr>
											<td>ID</td>
											<td>标题</td>
											<td>姓名</td>
											<td>电话</td>
											<td>时间</td>
											<td>操作</td>
										</tr>
									</thead>
									<tbody id="demo2">
										<tr v-for="data in datas" id="data.id">
											<td>{{data.msgId}}</td>
											<td>{{unescape(data.title)}}</td>
											<!--td><a href="javascript:void(0)"
												@click="showMessage(data.content)">留言信息</a></td-->
											<td>{{data.name}}</td>
											<td>{{data.phone}}</td>
											<td>{{formatDate(data.date.time)}}</td>
											<td><a :href="'adviceReply.jsp?id='+data.msgId">查看</a></td>
											<td><a @click="del(data)">删除</a></td>
										</tr>
									</tbody>
								</table>
							</div>
							<div id="btn2 btn-info center-block"
								class="btn btn-info
							center-block" style="width:100px;"
								id="more" onclick="demo2.loadMore();">加载更多</div>
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
<script src="layer/layer.js"></script>
<script type="text/javascript">
	var Start = 0;

	var demo = new Vue({
		el : "#Mybtns",
		data : {
			datas : {}
		},
		created : function() {
			this.enterSearch(); //定义方法

		},
		methods : {
			enterSearch : function() {
				$.ajax({
					url : "getMsgAll.do",
					data : {
						start : Start,
						size : 10,
						status : 1, 
					},
					dataType : "json",
					success : function(data) {
						demo.datas = data[0].data;
						if (data[0].data.length >= 10) {
							document.getElementById("btn btn-info center-block").style.display = "block";
						} else {
							document.getElementById("btn btn-info center-block").style.display = "none";
						}
						Start = Start + data[0].data.length;
					}
				});
			},
			formatDate : function(value) {
				var date = new Date(value);
				var y = date.getFullYear();
				var MM = date.getMonth() + 1;
				MM = MM < 10 ? ('0' + MM) : MM;
				var d = date.getDate();
				d = d < 10 ? ('0' + d) : d;
				var h = date.getHours();
				h = h < 10 ? ('0' + h) : h;
				var m = date.getMinutes();
				m = m < 10 ? ('0' + m) : m;
				var s = date.getSeconds();
				s = s < 10 ? ('0' + s) : s;
				return y + '-' + MM + '-' + d + ' ' + h + ':' + m + ':' + s;
			},
			loadMore : function() {
				$.ajax({
					url : "getMsgAll.do",
					data : {
						start : Start,
						size : 10
					},
					dataType : "json",
					success : function(data) {
						demo.datas = demo.datas.concat(data[0].data);

						if (data[0].data.length >= 10) {
							document.getElementById("btn btn-info center-block").style.display = "block";
						} else {
							document.getElementById("btn btn-info center-block").style.display = "none";
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

					$.ajax({
						url : "delMsg.do",
						data : {
							id : obj.msgId
						},
						dataType : "json",
						success : function(data) {
							alert(data[0].message);
							if (data[0].message == '成功') {
								var index = demo.datas.indexOf(obj);
								if (index > -1) {
									demo.datas.splice(index, 1);
								}
							}
						}
					});
				}
			},
			showMessage : function(obj) {
				obj = unescape(obj);
				layer.alert(obj, {
					skin : 'layui-layer-lan', //样式类名
					closeBtn : 0,
					shadeClose : true,
					closeBtn : 1,
					anim : 4
				});
			}
		}
	});
	
	var demo2 = new Vue({
		el : "#Mybtns2",
		data : {
			datas : {}
		},
		created : function() {
			this.enterSearch(); //定义方法

		},
		methods : {
			enterSearch : function() {
				$.ajax({
					url : "getMsgAll.do",
					data : {
						start : Start,
						size : 10,
						status : 2, 
					},
					dataType : "json",
					success : function(data) {
						demo2.datas = data[0].data;
						if (data[0].data.length >= 10) {
							document.getElementById("btn2 btn-info center-block").style.display = "block";
						} else {
							document.getElementById("btn2 btn-info center-block").style.display = "none";
						}
						Start = Start + data[0].data.length;
					}
				});
			},
			formatDate : function(value) {
				var date = new Date(value);
				var y = date.getFullYear();
				var MM = date.getMonth() + 1;
				MM = MM < 10 ? ('0' + MM) : MM;
				var d = date.getDate();
				d = d < 10 ? ('0' + d) : d;
				var h = date.getHours();
				h = h < 10 ? ('0' + h) : h;
				var m = date.getMinutes();
				m = m < 10 ? ('0' + m) : m;
				var s = date.getSeconds();
				s = s < 10 ? ('0' + s) : s;
				return y + '-' + MM + '-' + d + ' ' + h + ':' + m + ':' + s;
			},
			loadMore : function() {
				$.ajax({
					url : "getMsgAll.do",
					data : {
						start : Start,
						size : 10
					},
					dataType : "json",
					success : function(data) {
						demo2.datas = demo2.datas.concat(data[0].data);

						if (data[0].data.length >= 10) {
							document.getElementById("btn2 btn-info center-block").style.display = "block";
						} else {
							document.getElementById("btn2 btn-info center-block").style.display = "none";
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

					$.ajax({
						url : "delMsg.do",
						data : {
							id : obj.msgId
						},
						dataType : "json",
						success : function(data) {
							alert(data[0].message);
							if (data[0].message == '成功') {
								var index = demo2.datas.indexOf(obj);
								if (index > -1) {
									demo2.datas.splice(index, 1);
								}
							}
						}
					});
				}
			},
			showMessage : function(obj) {
				obj = unescape(obj);
				layer.alert(obj, {
					skin : 'layui-layer-lan', //样式类名
					closeBtn : 0,
					shadeClose : true,
					closeBtn : 1,
					anim : 4
				});
			}
		}
	});
</script>

</html>
