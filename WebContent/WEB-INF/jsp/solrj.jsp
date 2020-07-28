<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery/jquery-1.12.4.js"></script>
<title>Solrj</title>
<style type="text/css">
* {margin: 0;padding: 0;}
#nav {
	padding: 10px 10px 0;
	font-size: 12px;
	font-weight: bold;
	font-family: Arial, Helvetica, sans-serif, 宋体;
	margin: 1em 0 0;
	list-style: none;
}
#nav li {float: left;  /* display:inline-block;*/margin-right: 10px;}

#nav li a, #nav li a:hover span {
	line-height: 20px;
	text-decoration: none;
	background: #DDDDDD;
	color: #666666;
	display: block;
	width: 80px;
	text-align: center;
	overflow: hidden;
}
#nav li a span {display: none;}

#nav li a:hover {position: relative;color: #FFFFFF;background: #DC4E1B;}

#nav li a:hover span {
	display: block;
	position: absolute;
	top: 0;
	left: 0;
	cursor: pointer;
	padding-top: 2px;
	color: #FFFFFF;
	background: #DC4E1B;
}
#navbar {background: #DC4E1B;height: 18px;overflow: hidden;clear: both;}
/* li {display: inline-block;} */


div.refresh{ 
			font-size:16px;
			background-color: #e11512;
			width:180px;
			height:30px; 
			line-height:30px; 
			text-align:center; 
			vertical-align:middle; 
			color: white;
			cursor: pointer ;
			margin-left:0px;
	}
</style>
<script type="text/javascript">
	function toDoQuery() {
		if (event.keyCode == 13) {
			doQuery();
		}
	}
	function doQuery() {
		//执行关键字查询时，清空隐藏域中的值
		$("#catalog").val("");
		$("#price").val("");
		$("#sort").val("");
		formSubmit();
	}
	//提交表单
	function formSubmit() {
		$("#actionForm").submit();
	}
	//设置查询条件
	function setFilter(key, value) {
		$("#" + key + "").val(value);
		formSubmit();
	}
	//设置排序
	function setSort() {
		var sort = $("#sort").val();
		if (1 == sort) {
			$("#sort").val("0");
		} else {
			$("#sort").val("1");
		}
		formSubmit();
	}
	
	
	//自动刷新
	function autoRefresh(){
		window.location.reload();
	}
	$(function(){
		setInterval(autoRefresh,3000);
	});
	
</script>



</head>
<body style="border: 1px solid red">
<p align="left"><div  id="open" class='refresh'>每3s自动刷新一次</div></p>
	<form id="actionForm" action="list.action"  method="POST" >
		<input type="text"  id="key"  name ="key" value="${key}" accesskey="s" onkeydown="toDoQuery()"/>
		<input type="button" id ="search" name="serach" value="点我搜索" onclick="doQuery()" />
		<div>
		<input type="text"  id="catalog"  name ="catalog" value="${catalog }" />
		<input type="text"  id="price"  name ="price" value="${price }" />
		<input type="text"  id="sort"  name ="sort" value="${sort }" />
		</div>
	</form>
	<div><span>排序:</span><span style="display: inline-block;width:50px;cursor: pointer; border: 1px solid gray;text-align: center;vertical-align: middle;" onclick="setSort();">价格</span></div>
	<ul id="nav">
		<li><a href="####" onclick="setFilter('catalog','家居')">家居<span>jj</span></a></li>
		<li><a href="####" onclick="setFilter('catalog','花瓶花艺')">花瓶花艺<span>hphy</span></a></li>
		<li><a href="####" onclick="setFilter('catalog','果盘/果篮')">果盘/果篮<span>gp/gl</span></a></li>
		<li><a href="####" onclick="setFilter('catalog','装饰摆件')">装饰摆件<span>zsbj</span></a></li>
		<li><a href="####" onclick="setFilter('catalog','工艺礼品')">工艺礼品<span>gylp</span></a></li>
		<li><a href="####" onclick="setFilter('catalog','酒杯/酒具')">酒杯/酒具<span>jb/jj</span></a></li>
	</ul>
	<div id="navbar"></div>
	<ul id="nav">
		<li><a href="####" onclick="setFilter('price','0-99')">0-99<span>0到99</span></a></li>
		<li><a href="####" onclick="setFilter('price','100-999')">100-999<span>1百到999</span></a></li>
		<li><a href="####" onclick="setFilter('price','1000-9999')">1000-9999<span>1千到9999</span></a></li>
		<li><a href="####" onclick="setFilter('price','10000-99999')">10000-99999<span>1万到99999</span></a></li>
		<li><a href="####" onclick="setFilter('price','100000-*')">100000以上<span>10万以上</span></a></li>
	</ul>
	<div id="navbar"></div>
	<div>
		<c:forEach items="${productVos}" var="item"  varStatus="status" >
		<div  style="display:inline-block ;width:150px; border: 1px solid gray;text-align: center;vertical-align: middle;">
			<div  style="width:50px;cursor: pointer; border: 1px solid gray;text-align: center;vertical-align: middle;">${status.index}</div>
			id：${item.pid}<br/>
			商品名：${item.name}<br/>
			分类：${item.catalog_name}<br/>
			价格：${item.price}<br/>
		</div>
		<c:if test="${(status.index+1)%2 ==0 }"><br/></c:if>	
		</c:forEach>
	</div>
</body>
</html>