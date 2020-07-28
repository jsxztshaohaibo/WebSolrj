<!DOCTYPE html>
<html>
<head>
<script type="text/javascript" src="../../js/jquery/jquery-1.12.4.js"></script>
<script type="text/javascript" src="../../js/jquery/jquery.SuperSlide.2.1.3.js"></script>
<style>
	/* css 重置 */
	*{margin:0; padding:0; list-style:none; }
	html{border:0px solid black; height:100%;}
	body{ background:#fff; font:normal 12px/22px 宋体; height:100%}
	img{ border:0;  }
	a{ text-decoration:none; color:#333;  }
	a:hover{ color:#1974A1;  }


	/* 本例子css */
	.slideTxtBox{height:100%;width:100%; border:1px solid #ddd; text-align:left;  }
	.slideTxtBox .hd{ height:30px; line-height:30px; background:#f4f4f4; padding:0 10px 0 20px; border:1px solid #ddd; position:relative; }
	.slideTxtBox .hd ul{ float:left;  position:absolute; left:20px; top:-1px; height:32px;   }
	.slideTxtBox .hd ul li{ float:left; padding:0 15px; cursor:pointer; border:1px solid #ddd; }
	/*.slideTxtBox .hd ul li.on{ height:30px;  background:#fff; border:1px solid #ddd; border-bottom:2px solid #fff; }*/
	.ooxx{ height:30px;  background:#fff;  border-bottom:2px solid #fff; }
	.slideTxtBox .bd { padding:15px;  zoom:1; width:99%;height:90%}
	

</style>
<script type="text/JavaScript"> 
	$(document).ready(function(){
		$(".slideTxtBox").slide();
		$("li").click(function(){
				$("li").removeClass("ooxx");
			    $(this).addClass("ooxx");
				var  target = $(this).attr("target");
				$("#content").attr("src",target);
		});
		
		$("li").eq(0).click();//默认成绩选项卡为当前活动选项卡
	});

</script>
</head>
<body style="border:0px solid red;">
<form action="${ctx}/toudang/TouDangTemplateAction.a" method="post">
		<div id="titlearea">
			<h1>当前操作：</h1>
			<h2>投档模板条件设置</h2>
			<c:if test="${not empty message}">
				<div id="successmsg">${message }</div>
			</c:if>
		</div>
		<div id="resulttitlearea">
			<span style="float:left"><input name="toDefaultPage" type="submit"id="toDefaultPage" value=" 返回  " />
			</span>
			<input type="hidden" name ="mbid" id ="mbid" value="${mbid}"/>
			<span>模板编号：${touDangTemplateModel.no } &nbsp;</span>
			<span>模板名称：${touDangTemplateModel.name }&nbsp;</span>
			<span>批次:<app:dispDictNameByCodeAndType dictCode="${touDangTemplateModel.pcdm }" dictType="212"/>&nbsp;</span>
			<span>科类：<app:dispDictNameByCodeAndType dictCode="${touDangTemplateModel.kldm }" dictType="1"/>&nbsp;</span>
			
		</div>
		<div id="resultlistarea" style="height:85vh;border :0px red solid">
			<div class="slideTxtBox" >
				<div class="hd">
					<ul>
						<li target="${ctx}/toudang/TouDangTemplateAction.a?showTiaoTian&mbid=${mbid}&type=cj">考生成绩限制</li>
						<li target="${ctx}/toudang/TouDangTemplateAction.a?showTiaoTian&mbid=${mbid}&type=xb">考生性别限制</li>
						<li target="${ctx}/toudang/TouDangTemplateAction.a?showTiaoTian&mbid=${mbid}&type=mz">考生民族限制</li>
						<li target="${ctx}/toudang/TouDangTemplateAction.a?showTiaoTian&mbid=${mbid}&type=nl">考生年龄限制</li>
						<li target="${ctx}/toudang/TouDangTemplateAction.a?showTiaoTian&mbid=${mbid}&type=wyyz">考生外语语种限制</li>
						<li target="${ctx}/toudang/TouDangTemplateAction.a?showTiaoTian&mbid=${mbid}&type=tz">考生特征限制</li>
						<li target="${ctx}/toudang/TouDangTemplateAction.a?showTiaoTian&mbid=${mbid}&type=dq">考生地区限制</li>
					</ul>
				</div>
				<div class="bd">
					<iframe id="content" height="100%" align="top" 
							style="width:99%;margin-top:0px; marign-bottom:0px" 
						    marginheight="0" marginwidth="0"  frameborder="0" scrolling="auto"/>
				</div>
			</div>
		</div>
		
</form>
</body>
</html>


