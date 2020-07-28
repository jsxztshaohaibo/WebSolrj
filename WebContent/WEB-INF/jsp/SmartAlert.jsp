<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>信息列表</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/smartAlert/smartAlert.css" />
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery/jquery-1.12.4.js"></script>
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath }/js/smartAlert/smartAlert.js"></script>
<script type="text/javascript" >
	var smartAlertAction = "${pageContext.request.contextPath}/product/test.action";
</script>
<style type="text/css">
tr{height:35px}
</style>
</head>
<body>
<table width="100%"  border ="1" style="border:1px solid green ;border-collapse:collapse;" align="center">
<tr>
	<td>院校地址：</td>
	<td><input type ="text" value="123"></td>
</tr>
<tr>
	<td>专业名称：</td>
	<td>
		<!-- 智能提醒  开始-->
		<div id="out">
			<div id="ser_box">
				<input type="text" id="ipt" />
			</div>
			<div id="bot_box">
				<ul id="oul"></ul>
			</div>
		</div>
		<!-- 智能提醒  结束 -->
	</td>
</tr>

<tr>
	<td>专业代码：</td>
	<td><input type ="text" id="code"></td>
</tr>
<tr>
	<td>院校代码：</td>
	<td><input type ="text" value="110001"></td>
</tr>
<tr>
	<td>院校名称：</td>
	<td><input type ="text" value="1清华大学"></td>
</tr>
</table>
</body>
</html>