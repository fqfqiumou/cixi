<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
#Mybtns2 .btn-fl {
	margin-left: 20px;
	margin-bottom: 15px;
}
#Mybtns3 .btn-fl {
	margin-left: 20px;
	margin-bottom: 15px;
}
#Mybtns4 .btn-fl {
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
				<ul id="myTab" class="nav nav-tabs">
					<li class="active">
						<a href="#Mybtns" data-toggle="tab">
							 待审核
						</a>
					</li>
					<li><a href="#Mybtns2" data-toggle="tab">已通过</a></li>
					<li><a href="#Mybtns3" data-toggle="tab">已驳回</a></li>
				</ul>
				<br/>
				<div id="myTabContent" class="tab-content">
					<div class="tab-pane fade in active" id="Mybtns">
						<a :href="'JCDTsonadd.jsp?id='+Id" class="btn btn-info btn-fl">添加</a>
						<c:if  test="${sessionScope.useradmin.power==413}">
						<a href="javascript:void(0)" @click="batchReview(2)" class="btn btn-info btn-fl">通过</a>
						<a href="javascript:void(0)" @click="batchReview(3)" class="btn btn-info btn-fl">驳回</a>
						</c:if>
						<div>
							<div class="table-responsive">
								<table class="table table-striped text-center">
									<thead>
										<tr>
										<c:if  test="${sessionScope.useradmin.power==413}">
											<td><input type="checkbox" id="cbheader1"  @click="click()"/></td>
										</c:if>
											<td>ID</td>
											<td>标题</td>
											<td>时间</td>
											<td>状态</td>
											<td>操作</td>
										</tr>
									</thead>
									<tbody id="demo">
										<tr v-for="data in datas">
											<c:if  test="${sessionScope.useradmin.power==413}">
											<td><input type="checkbox" v-bind:value=data.id @click="singleclick(data)" name="cb1"/></td>
											</c:if>
											<td>{{data.id}}</td>
											<td>{{unescape(data.title)}}</td>
											<td>{{formatDate(data.date.time)}}</td>
											<td>{{transStatus(data.status)}}</td>
											<td><a :href="'JCDTsonsee.jsp?id='+data.id">查看</a></td>
											<c:if  test="${sessionScope.useradmin.power==413}">
											<td @click="pass(data)"><a href="javascript:void(0)">通过</a></td>
											<td @click="reject(data)"><a href="javascript:void(0)">驳回</a></td>
											<td @click="del(data)"><a href="javascript:void(0)">删除</a></td>
											</c:if>
										</tr>
									</tbody>
								</table>
							</div>
							<div id="btn btn-info center-block"
								class="btn btn-info
							center-block" style="width:100px;"
								id="more" onclick="Mybtns.loadMore();">加载更多</div>
						</div>
					</div>
					<div class="tab-pane fade" id="Mybtns2">
						<c:if  test="${sessionScope.useradmin.power==413}">
						<a :href="'JCDTsonadd.jsp?id='+Id" class="btn btn-info btn-fl">添加</a>
						<a href="javascript:void(0)" @click="batchReview(3)" class="btn btn-info btn-fl">驳回</a>
						</c:if>
						<div>
							<div class="table-responsive">
								<table class="table table-striped text-center">
									<thead>
										<tr>
										<c:if  test="${sessionScope.useradmin.power==413}">
										<td><input type="checkbox" id="cbheader2" @click="click(2)"/></td>
										</c:if>
											<td>ID</td>
											<td>标题</td>
											<td>时间</td>
											<td>状态</td>
											<td>操作</td>
										</tr>
									</thead>
									<tbody id="demo">
										<tr v-for="data in datas">
											<c:if  test="${sessionScope.useradmin.power==413}">
											<td><input type="checkbox"  v-bind:value=data.id @click="singleclick(data)" name="cb2"/></td>
											</c:if>
											<td>{{data.id}}</td>
											<td>{{unescape(data.title)}}</td>
											<td>{{formatDate(data.date.time)}}</td>
											<td>{{transStatus(data.status)}}</td>
											<td><a :href="'JCDTsonsee.jsp?id='+data.id">查看</a></td>
											<c:if  test="${sessionScope.useradmin.power==413}">
											<td @click="reject(data)"><a href="javascript:void(0)">驳回</a></td>
											<td @click="del(data)"><a href="javascript:void(0)">删除</a></td>
											</c:if>
										</tr>
									</tbody>
								</table>
							</div>
							<div id="btn2 btn-info center-block"
								class="btn btn-info
							center-block" style="width:100px;"
								id="more" onclick="Mybtns2.loadMore();">加载更多</div>
						</div>
					</div>
					<div class="tab-pane fade" id="Mybtns3">
						<c:if  test="${sessionScope.useradmin.power==413}">
						<a :href="'JCDTsonadd.jsp?id='+Id" class="btn btn-info btn-fl">添加</a>
						<a href="javascript:void(0)" @click="batchReview(2)" class="btn btn-info btn-fl">通过</a>
						</c:if>
						<div>
							<div class="table-responsive">
								<table class="table table-striped text-center">
									<thead>
										<tr>
										<c:if  test="${sessionScope.useradmin.power==413}">
										<td><input type="checkbox" id="cbheader3" @click="click()"/></td>
										</c:if>
											<td>ID</td>
											<td>标题</td>
											<td>时间</td>
											<td>状态</td>
											<td>操作</td>
										</tr>
									</thead>
									<tbody id="demo">
										<tr v-for="data in datas">
										<c:if  test="${sessionScope.useradmin.power==413}">
										<td><input type="checkbox"  v-bind:value=data.id @click="singleclick(data)" name="cb3"/></td>
										</c:if>	
											<td>{{data.id}}</td>
											<td>{{unescape(data.title)}}</td>
											<td>{{formatDate(data.date.time)}}</td>
											<td>{{transStatus(data.status)}}</td>
											<td><a :href="'JCDTsonsee.jsp?id='+data.id">查看</a></td>
											<c:if  test="${sessionScope.useradmin.power==413}">
											<td @click="pass(data)"><a href="javascript:void(0)">通过</a></td>
											</c:if>
											<td @click="del(data)"><a href="javascript:void(0)">删除</a>
											</td>
										</tr>
									</tbody>
								</table>
							</div>
							<div id="btn3 btn-info center-block"
								class="btn btn-info
							center-block" style="width:100px;"
								id="more" onclick="Mybtns3.loadMore();">加载更多</div>
						</div>
					</div>
				</div>
				
			</div>
		</div>
	</div>
</body>
<!-- -->
<script src="js/jquery-1.11.3.min.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script type="text/javascript" src="bs/js/bootstrap.min.js"></script>

<script src="js/vue.min.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
	var str = window.location.href;
	var Id = str.split('=')[1];
	console.log(Id);

	var Start1 = 0;
	var Start2 = 0;
	var Start3 = 0;
	
	var Mybtns = new Vue(
		{
			el : "#Mybtns",
			data : {
				datas : {},
				id : Id,
				selecteddatas : [],
				flag : true,
			},
			created : function() {
				this.enterSearch(); //定义方法

			},
			methods : {
				enterSearch : function() {
					Start1 = 0;
					$
						.ajax({
							url : "getDynoteAll.do",
							data : {
								start : 0,
								size : 10,
								id : Id,
								status : 1
							},
							dataType : "json",
							success : function(data) {
								Mybtns.datas = data[0].data;
								if (data[0].data.length >= 10) {
									document
										.getElementById("btn btn-info center-block").style.display = "block";
								} else {
									document
										.getElementById("btn btn-info center-block").style.display = "none";
								}
								Start1 = Start1 + data[0].data.length;
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
				transStatus : function(value) {
					if(value == 1)
					{
						return "审核中";	
					}
					if(value == 2)
					{
						return "已通过";	
					}
					else if(value == 3)
					{
						return "已驳回";
					}
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
					$
						.ajax({
							url : "getDynoteAll.do",
							data : {
								start : Start1,
								size : 10,
								id : Id,
								status: 1
							},
							dataType : "json",
							success : function(data) {
								Mybtns.datas = Mybtns.datas
									.concat(data[0].data);

								if (data[0].data.length >= 10) {
									document
										.getElementById("btn btn-info center-block").style.display = "block";
								} else {
									document
										.getElementById("btn btn-info center-block").style.display = "none";
								}
								Start1 = Start1 + data[0].data.length;
								console.log(Start1);
							}
						});
				},
				
				pass : function(obj) {
					if (obj != null) {

						$
							.ajax({
								url : "reviewDynote.do",
								data : {
									id : obj.id,
									status : 2
								},
								type : "post",
								dataType : "json",
								success : function(data) {
									alert(data[0].message);
									if (data[0].message == '成功') {
										Mybtns.unclick();
										Mybtns.selecteddatas = [];
										Mybtns.enterSearch();
										Mybtns2.enterSearch();
										Mybtns3.enterSearch();
									}
								}
							});
					}
				},
				
				batchReview : function(review) {
					$
						.ajax({
							url : "batchReviewDynote.do",
							data : JSON.stringify({
								dynotes : Mybtns.selecteddatas,
								status : review
							}), 
							type : "post",
							contentType : 'application/json;charset=utf-8',
							dataType : "json",
							success : function(data) {
								if (data[0].message == '成功') {
									Mybtns.unclick();
									Mybtns.enterSearch();
									Mybtns2.enterSearch();
									Mybtns3.enterSearch();
								}
							},
							
							
						});
					
				},
				
				reject : function(obj) {
					if (obj != null) {

						$
							.ajax({
								url : "reviewDynote.do",
								data : {
									id : obj.id,
									status : 3
								},
								type : "post",
								dataType : "json",
								success : function(data) {
									alert(data[0].message);
									if (data[0].message == '成功') {
										Mybtns.unclick();
										Mybtns.enterSearch();
										Mybtns2.enterSearch();
										Mybtns3.enterSearch();
									}
								}
							});
					}
				},
				
				
				del : function(obj) {
					if (obj != null) {

						$
							.ajax({
								url : "delDynote.do",
								data : {
									id : obj.id
								},
								dataType : "json",
								success : function(data) {
									alert(data[0].message);
									if (data[0].message == '成功') {
										var index = Mybtns.datas
											.indexOf(obj);
										if (index > -1) {
											Mybtns.datas.splice(index, 1);
										}
									}
								}
							});
					}
				},
				
			    click : function() {
			    	document.getElementById("cbheader1").checked = Mybtns.flag;
			        var cb = $("[name='cb1']");
			        for(var i = 0; i < cb.length; i++) {
			            cb[i].checked = Mybtns.flag;
			            if(Mybtns.flag)
				        {
			            	if($.inArray(Number(cb[i].value), Mybtns.selecteddatas) == -1)
							{
			            		Mybtns.selecteddatas.push(Number(cb[i].value));	
							}
				        }
			            else
			            {
			            	Mybtns.selecteddatas = [];
			            }
			        }
			        Mybtns.flag = !Mybtns.flag;
			    },
			    
			    unclick : function() {
			    	document.getElementById("cbheader1").checked = false;
			        var cb = $("[name='cb1']");
			        for(var i = 0; i < cb.length; i++) {
			            cb[i].checked = false;
			        }
			        Mybtns.flag = true;
			    },
				
				singleclick : function(data){
					if($.inArray(data.id, Mybtns.selecteddatas) == -1)
					{
						Mybtns.selecteddatas.push(data.id);	
					}
					else
					{
						var index = $.inArray(data.id, Mybtns.selecteddatas);
						Mybtns.selecteddatas.splice(index,1);
					}
					
				}
			}
		});
	
	
	
	var Mybtns2 = new Vue(
			{
				el : "#Mybtns2",
				data : {
					datas : {},
					id : Id,
					selecteddatas : [],
					flag : true,
				},
				created : function() {
					this.enterSearch(); //定义方法

				},
				methods : {
					enterSearch : function() {
						Start2 = 0;
						$
							.ajax({
								url : "getDynoteAll.do",
								data : {
									start : 0,
									size : 10,
									id : Id,
									status : 2
								},
								dataType : "json",
								success : function(data) {
									Mybtns2.datas = data[0].data;
									if (data[0].data.length >= 10) {
										document
											.getElementById("btn2 btn-info center-block").style.display = "block";
									} else {
										document
											.getElementById("btn2 btn-info center-block").style.display = "none";
									}
									Start2 = Start2 + data[0].data.length;
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
					transStatus : function(value) {
						if(value == 1)
						{
							return "审核中";	
						}
						if(value == 2)
						{
							return "已通过";	
						}
						else if(value == 3)
						{
							return "已驳回";
						}
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
						$
							.ajax({
								url : "getDynoteAll.do",
								data : {
									start : Start2,
									size : 10,
									id : Id,
									status : 2
								},
								dataType : "json",
								success : function(data) {
									Mybtns2.datas = Mybtns2.datas
										.concat(data[0].data);

									if (data[0].data.length >= 10) {
										document
											.getElementById("btn2 btn-info center-block").style.display = "block";
									} else {
										document
											.getElementById("btn2 btn-info center-block").style.display = "none";
									}
									Start2 = Start2 + data[0].data.length;
									console.log(Start2);
								}
							});
					},
					
					pass : function(obj) {
						if (obj != null) {

							$
								.ajax({
									url : "reviewDynote.do",
									data : {
										id : obj.id,
										status : 2
									},
									type : "post",
									dataType : "json",
									success : function(data) {
										alert(data[0].message);
										if (data[0].message == '成功') {
											Mybtns2.unclick();
											Mybtns.enterSearch();
											Mybtns2.enterSearch();
											Mybtns3.enterSearch();
										}
									}
								});
						}
					},
					
					batchReview : function(review) {
						$
							.ajax({
								url : "batchReviewDynote.do",
								data : JSON.stringify({
									dynotes : Mybtns2.selecteddatas,
									status : review
								}), 
								type : "post",
								contentType : 'application/json;charset=utf-8',
								dataType : "json",
								success : function(data) {
									alert(data[0].message);
									if (data[0].message == '成功') {
										Mybtns2.unclick();
										Mybtns2.selecteddatas = [];
										Mybtns.enterSearch();
										Mybtns2.enterSearch();
										Mybtns3.enterSearch();
									}
								}
							});
						
					},
					
					reject : function(obj) {
						if (obj != null) {

							$
								.ajax({
									url : "reviewDynote.do",
									data : {
										id : obj.id,
										status : 3
									},
									type : "post",
									dataType : "json",
									success : function(data) {
										Mybtns2.unclick();
										Mybtns.enterSearch();
										Mybtns2.enterSearch();
										Mybtns3.enterSearch();
									}
								});
						}
					},
					
					
					del : function(obj) {
						if (obj != null) {

							$
								.ajax({
									url : "delDynote.do",
									data : {
										id : obj.id
									},
									dataType : "json",
									success : function(data) {
										alert(data[0].message);
										if (data[0].message == '成功') {
											var index = Mybtns2.datas
												.indexOf(obj);
											if (index > -1) {
												Mybtns2.datas.splice(index, 1);
											}
										}
									}
								});
						}
					},
					
				    click : function() {
				    	document.getElementById("cbheader2").checked = Mybtns2.flag;
				        var cb = $("[name='cb2']");
				        for(var i = 0; i < cb.length; i++) {
				            cb[i].checked = Mybtns2.flag;
				            if(Mybtns2.flag)
					        {
				            	if($.inArray(Number(cb[i].value), Mybtns2.selecteddatas) == -1)
								{
				            		Mybtns2.selecteddatas.push(Number(cb[i].value));	
								}
					        }
				            else
				            {
				            	Mybtns2.selecteddatas = [];
				            }
				        }
				        Mybtns2.flag = !Mybtns2.flag;
				    },
				    
				    unclick : function() {
				    	document.getElementById("cbheader2").checked = false;
				        var cb = $("[name='cb2']");
				        for(var i = 0; i < cb.length; i++) {
				            cb[i].checked = false;
				        }
				        Mybtns2.flag = true;
				    },
					
					singleclick : function(data){
						if($.inArray(data.id, Mybtns2.selecteddatas) == -1)
						{
							Mybtns2.selecteddatas.push(data.id);	
						}
						else
						{
							var index = $.inArray(data.id, Mybtns2.selecteddatas);
							Mybtns2.selecteddatas.splice(index,1);
						}
						
					}
				}
			});
	
	var Mybtns3 = new Vue(
			{
				el : "#Mybtns3",
				data : {
					datas : {},
					id : Id,
					selecteddatas : [],
					flag : true,
				},
				created : function() {
					this.enterSearch(); //定义方法

				},
				methods : {
					enterSearch : function() {
						Start3 = 0;
						$
							.ajax({
								url : "getDynoteAll.do",
								data : {
									start : 0,
									size : 10,
									id : Id,
									status : 3
								},
								dataType : "json",
								success : function(data) {
									Mybtns3.datas = data[0].data;
									if (data[0].data.length >= 10) {
										document
											.getElementById("btn3 btn-info center-block").style.display = "block";
									} else {
										document
											.getElementById("btn3 btn-info center-block").style.display = "none";
									}
									Start3 = Start3 + data[0].data.length;
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
					transStatus : function(value) {
						if(value == 1)
						{
							return "审核中";	
						}
						if(value == 2)
						{
							return "已通过";	
						}
						else if(value == 3)
						{
							return "已驳回";
						}
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
						$
							.ajax({
								url : "getDynoteAll.do",
								data : {
									start : Start3,
									size : 10,
									id : Id,
									status : 3
								},
								dataType : "json",
								success : function(data) {
									Mybtns3.datas = Mybtns3.datas
										.concat(data[0].data);

									if (data[0].data.length >= 10) {
										document
											.getElementById("btn3 btn-info center-block").style.display = "block";
									} else {
										document
											.getElementById("btn3 btn-info center-block").style.display = "none";
									}
									Start3 = Start3 + data[0].data.length;
									console.log(Start3);
								}
							});
					},
					
					pass : function(obj) {
						if (obj != null) {

							$
								.ajax({
									url : "reviewDynote.do",
									data : {
										id : obj.id,
										status : 2
									},
									type : "post",
									dataType : "json",
									success : function(data) {
										alert(data[0].message);
										if (data[0].message == '成功') {
											Mybtns3.unclick();
											Mybtns.enterSearch();
											Mybtns2.enterSearch();
											Mybtns3.enterSearch();
										}
									}
								});
						}
					},
					
					batchReview : function(review) {
						$
							.ajax({
								url : "batchReviewDynote.do",
								data : JSON.stringify({
									dynotes : Mybtns3.selecteddatas,
									status : review
								}), 
								type : "post",
								contentType : 'application/json;charset=utf-8',
								dataType : "json",
								success : function(data) {
									alert(data[0].message);
									if (data[0].message == '成功') {
										Mybtns3.unclick();
										Mybtns3.selecteddatas = [];
										Mybtns.enterSearch();
										Mybtns2.enterSearch();
										Mybtns3.enterSearch();
									}
								}
							});
						
					},
					
					reject : function(obj) {
						if (obj != null) {

							$
								.ajax({
									url : "reviewDynote.do",
									data : {
										id : obj.id,
										status : 3
									},
									type : "post",
									dataType : "json",
									success : function(data) {
										alert(data[0].message);
										if (data[0].message == '成功') {
											Mybtns3.unclick();
											Mybtns.enterSearch();
											Mybtns2.enterSearch();
											Mybtns3.enterSearch();
										}
									}
								});
						}
					},
					
					
					del : function(obj) {
						if (obj != null) {

							$
								.ajax({
									url : "delDynote.do",
									data : {
										id : obj.id
									},
									dataType : "json",
									success : function(data) {
										alert(data[0].message);
										if (data[0].message == '成功') {
											var index = Mybtns3.datas
												.indexOf(obj);
											if (index > -1) {
												Mybtns3.datas.splice(index, 1);
											}
										}
									}
								});
						}
					},
					
				    click : function() {
				        var cb = $("[name='cb3']");
				        for(var i = 0; i < cb.length; i++) {
				            cb[i].checked = Mybtns3.flag;
				            if(Mybtns3.flag)
					        {
				            	if($.inArray(Number(cb[i].value), Mybtns3.selecteddatas) == -1)
								{
				            		Mybtns3.selecteddatas.push(Number(cb[i].value));	
								}
				            	
					        }
				            else
				            {
				            	Mybtns3.selecteddatas = [];
				            }
				        }
				        Mybtns3.flag = !Mybtns3.flag;
				    },
				    
				    unclick : function() {
				    	document.getElementById("cbheader3").checked = false;
				        var cb = $("[name='cb3']");
				        for(var i = 0; i < cb.length; i++) {
				            cb[i].checked = false;
				        }
				        Mybtns3.flag = true;
				    },
					
					singleclick : function(data){
						if($.inArray(data.id, Mybtns3.selecteddatas) == -1)
						{
							Mybtns3.selecteddatas.push(data.id);	
						}
						else
						{
							var index = $.inArray(data.id, Mybtns3.selecteddatas);
							Mybtns3.selecteddatas.splice(index,1);
						}
						
					}
				}
			});
</script>

</html>
