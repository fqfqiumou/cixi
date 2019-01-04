<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
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
	<link href="themes/default/css/umeditor.css" type="text/css" rel="stylesheet">
    <script type="text/javascript" src="third-party/jquery.min.js"></script>
    <!--script type="text/javascript" charset="utf-8"
	src="js/umeditor.config.js"></script-->
<!--script type="text/javascript" charset="utf-8" src="js/umeditor.min.js"></script-->
<script type="text/javascript" charset="utf-8" src="js/ue/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="js/ue/ueditor.all.min.js"></script>
<!--script type="text/javascript" charset="utf-8" src="js/ueditor/lang/zh-cn/zh-cn.js"></script-->
    <style type="text/css">
        h1{
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
            background-image: -webkit-gradient(linear, 0 0, 0 100%, from(#ffffff), to(#e6e6e6));
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
            filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#ffffffff', endColorstr='#ffe6e6e6', GradientType=0);
            filter: progid:DXImageTransform.Microsoft.gradient(enabled=false);
            *zoom: 1;
            -webkit-box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.2), 0 1px 2px rgba(0, 0, 0, 0.05);
            -moz-box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.2), 0 1px 2px rgba(0, 0, 0, 0.05);
            box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.2), 0 1px 2px rgba(0, 0, 0, 0.05);
        }

        .btn:hover,
        .btn:focus,
        .btn:active,
        .btn.active,
        .btn.disabled,
        .btn[disabled] {
            color: #333333;
            background-color: #e6e6e6;
            *background-color: #d9d9d9;
        }

        .btn:active,
        .btn.active {
            background-color: #cccccc \9;
        }

        .btn:first-child {
            *margin-left: 0;
        }

        .btn:hover,
        .btn:focus {
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

        .btn.active,
        .btn:active {
            background-image: none;
            outline: 0;
            -webkit-box-shadow: inset 0 2px 4px rgba(0, 0, 0, 0.15), 0 1px 2px rgba(0, 0, 0, 0.05);
            -moz-box-shadow: inset 0 2px 4px rgba(0, 0, 0, 0.15), 0 1px 2px rgba(0, 0, 0, 0.05);
            box-shadow: inset 0 2px 4px rgba(0, 0, 0, 0.15), 0 1px 2px rgba(0, 0, 0, 0.05);
        }

        .btn.disabled,
        .btn[disabled] {
            cursor: default;
            background-image: none;
            opacity: 0.65;
            filter: alpha(opacity=65);
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
					<li class="active">文件库</li>
					<li class="active">修改</li>
				</ol>
				<div class="form-group">
						<label for="exampleInputPassword1">标题:</label> <input id="title" type="text" style="width:20%" class="form-control" name="company.redistheme">
					</div>
 

<script id="myEditor" type="text/plain" style="width:1000px;height:240px;">
				</script>	<div class="clear"></div>
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
<script type="text/javascript">
	var str = window.location.href;
	var id = str.split('=')[1];
	  function unescape(text) {
	    var temp = document.createElement("div"); 
	    temp.innerHTML = text; 
	    var output = temp.innerText || temp.textContent; 
	    temp = null; 
	    return output; 
	}
	$.ajax({
				url : "findFilelibraryById.do",
				data : {
					Id : id
				},
				dataType : "json",
				success : function(data) {
					console.log(data);
					document.getElementById("title").value =unescape(data[0].data.title);
					ue.addListener("ready", function () { 
						ue.setContent(HTMLDecode(data[0].data.content)); 
						});
					
					//setContent(data[0].data.content);
				}
			});
	

</script>
<script type="text/javascript">

					//实例化编辑器
					var ue = UE.getEditor('myEditor',{toolbars: [
					[
					   'source', //锚点
					   'undo', //撤销
					   'redo', //重做
					   'bold', //加粗
					   'italic', //首行缩进
					   'underline', //截图
					   'strikethrough', //斜体
					   'superscript', //下划线
					   'subscript', //删除线
					   'forecolor', //下标
					   'backcolor', //字符边框
					   'removeformat', //上标
					   'justifyleft', //格式刷
					   'justifycenter', //源代码
					   'justifyright', //引用
					   'justifyjustify', //纯文本粘贴模式
					   'link', //全选
					   'unlink', //打印
					   'emotion', //预览
					   'image', //分隔线
					   'attachment', //清除格式
					   'map', //时间
					   'horizontal', //日期
					   'print', //取消链接
					   'preview', //前插入行
					   'fullscreen', //前插入列
					   'drafts', //右合并单元格
					   'formula', //下合并单元格
					]
					]});
					
					function HTMLDecode(text) {
						var temp = document.createElement("div");
						temp.innerHTML = text;
						var output = temp.innerText || temp.textContent;
						temp = null;
						return output;
					}
					
					 function isFocus(e){
					        alert(UE.getEditor('myEditor').isFocus());
					        UE.dom.domUtils.preventDefault(e)
					    }
					    function setblur(e){
					        UE.getEditor('myEditor').blur();
					        UE.dom.domUtils.preventDefault(e)
					    }
					    function insertHtml() {
					        var value = prompt('插入html代码', '');
					        UE.getEditor('myEditor').execCommand('insertHtml', value)
					    }
					    function createEditor() {
					        enableBtn();
					        UE.getEditor('myEditor');
					    }
					    function getAllHtml() {
					        alert(UE.getEditor('myEditor').getAllHtml())
					    }
					
					    function getPlainTxt() {
					        var arr = [];
					        arr.push("使用editor.getPlainTxt()方法可以获得编辑器的带格式的纯文本内容");
					        arr.push("内容为：");
					        arr.push(UE.getEditor('myEditor').getPlainTxt());
					        alert(arr.join('\n'))
					    }
					    function setContent(isAppendTo) {
					    	var arr = [];
							isAppendTo = HTMLDecode(isAppendTo);
							var editor = UE.getEditor('myEditor');
							editor.ready(editor.setContent(isAppendTo));
					    }
					    function setDisabled() {
					        UE.getEditor('myEditor').setDisabled('fullscreen');
					        disableBtn("enable");
					    }

					    function setEnabled() {
					        UE.getEditor('myEditor').setEnabled();
					        enableBtn();
					    }

					    function getText() {
					        //当你点击按钮时编辑区域已经失去了焦点，如果直接用getText将不会得到内容，所以要在选回来，然后取得内容
					        var range = UE.getEditor('myEditor').selection.getRange();
					        range.select();
					        var txt = UE.getEditor('myEditor').selection.getText();
					        alert(txt)
					    }

					    function getContentTxt() {
					        var arr = [];
					        arr.push("使用editor.getContentTxt()方法可以获得编辑器的纯文本内容");
					        arr.push("编辑器的纯文本内容为：");
					        arr.push(UE.getEditor('myEditor').getContentTxt());
					        alert(arr.join("\n"));
					    }
					    function hasContent() {
					        var arr = [];
					        arr.push("使用editor.hasContents()方法判断编辑器里是否有内容");
					        arr.push("判断结果为：");
					        arr.push(UE.getEditor('myEditor').hasContents());
					        alert(arr.join("\n"));
					    }
					    function setFocus() {
					        UE.getEditor('myEditor').focus();
					    }
					    function deleteEditor() {
					        disableBtn();
					        UE.getEditor('myEditor').destroy();
					    }
					    function disableBtn(str) {
					        var div = document.getElementById('btns');
					        var btns = UE.dom.domUtils.getElementsByTagName(div, "button");
					        for (var i = 0, btn; btn = btns[i++];) {
					            if (btn.id == str) {
					                UE.dom.domUtils.removeAttributes(btn, ["disabled"]);
					            } else {
					                btn.setAttribute("disabled", "true");
					            }
					        }
					    }
					    function enableBtn() {
					        var div = document.getElementById('btns');
					        var btns = UE.dom.domUtils.getElementsByTagName(div, "button");
					        for (var i = 0, btn; btn = btns[i++];) {
					            UE.dom.domUtils.removeAttributes(btn, ["disabled"]);
					        }
					    }

					    function getLocalData () {
					        alert(UE.getEditor('myEditor').execCommand( "getlocaldata" ));
					    }

					    function clearLocalData () {
					        UE.getEditor('myEditor').execCommand( "clearlocaldata" );
					        alert("已清空草稿箱")
					    }
					function getContent() {
						var arr = [];
						arr.push(UE.getEditor('myEditor').getContent());
					//	alert(arr.join("\n"));
						if (arr.join("\n") == null || arr.join("\n").length <= 0) {
							alert("内容不允许为空");
							return;
						}
						if (document.getElementById("title").value == null||document.getElementById("title").value=="" || document.getElementById("title").value.length <= 0) {
							alert("标题不允许为空");
							return;
						}
							$.ajax({
								url : "updateFilelibrary.do",
								data : {
									Id : id,
									title: document.getElementById("title").value,
									content:arr.join("\n")
								},
								type:"post",
								dataType : "json",
								success : function(data) {
									console.log(data);
									self.location=document.referrer;
								}
							});
					    }
					    </script>	
</body>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script type="text/javascript" src="bs/js/bootstrap.min.js"></script>

<script src="layer/layer.js"></script>

</html>
