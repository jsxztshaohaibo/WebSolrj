<html>
<head>
<script type="text/javascript" src="../../js/jquery/jquery-1.12.4.js"></script>
<script type="text/javascript" src="../../js/layer/layer.js" ></script>

<style type="text/css">
<!--
/*重点：固定行头样式*/
.scrollRowThead
{
     position: relative;
     left: expression(this.parentElement.parentElement.parentElement.parentElement.scrollLeft);
     z-index:0;
}
/*重点：固定表头样式*/
.scrollColThead {
     position: relative;
     top: expression(this.parentElement.parentElement.parentElement.scrollTop);
     z-index:2;
}
/*行列交叉的地方*/
.scrollCR {
     z-index:3;
}
/*div外框*/
.scrollDiv {
height:500px;
clear: both;
border: 1px solid #EEEEEE;
OVERFLOW: scroll;
width: 100%;
}
/*行头居中*/
.scrollColThead td,.scrollColThead th
{
     text-align: center ;
}
/*表格的线*/
.scrolltable
{
border-bottom:1px solid #CCCCCC;
border-right:1px solid #CCCCCC;
}
/*单元格的线等*/
.scrolltable td,.scrollTable th
{
     border-left: 1px solid #CCCCCC;
     border-top: 1px solid #CCCCCC;
     padding: 5px;
}
-->
</style>

<script type="text/javascript">
	$(document).ready(function(){
		$("#toAdd").bind("click",showAdd);
	});
	function toUpdateXX(id,type){
		if("0"==type){
			layer.open({
		        type: 2,
		        title:"考生成绩限制",
		        content: ['${ctx}/toudang/TouDangTemplateAction.a?showCJDetail&mbid='+$("#mbid").val()+'&cjId='+id,'no'],
		        area: ['450px', '300px'],
		        offset: '80px',
		        success: function(layero, index){
		            var body = layer.getChildFrame('body', index);
		        }
		    });
		}else{
			if(confirm("确定要删除该条数据?")){
				$.post("${ctx}/toudang/TouDangTemplateAction.a?doDeleteCJCommit&cjId="+id,
		    	   		  function(res){
		    				  if(res.msg=="success"){
		    					  alert("删除成功!");
		    				  }else{
		    					  alert("删除失败，请稍后再试!");
		    				  }
		    				  parent.location.reload(true);
		    			  }
		    	   		 );
			}
		}
		
	}
	
	
	function showAdd(){
		layer.open({
	        type: 2,
	        title:"考生成绩限制",
	        content: ['./toudang_template_tiaojian_cj_add.jsp','no'],
	        area: ['450px', '300px'],
	        offset: '80px',
	        success: function(layero, index){
	            var body = layer.getChildFrame('body', index);
	            body.find('#mbid').val($("#mbid").val());
	            body.find('#type').val($("#type").val());
	        }
	    });
	}

</script>
</head>
<body >
<form action="${ctx}/toudang/TouDangTemplateAction.a" method="post" name="toudangTemplateCJForm" id="toudangTemplateCJForm">
<div id="main">
  
 <div id="searcharea">
    <table border="0" cellpadding="0" cellspacing="1">
        <tr>
          <td width="20%">
          		<input type="hidden" name ="mbid" id ="mbid" value="${mbid }" />
          		<input type="hidden" name ="type" id ="type" value="${type }" />
          		<input name="toAdd" type="button" id="toAdd" value="增加" />
        </tr>
    </table>
  </div>
 <!--  <div id="resulttitlearea"> <span>投档模板条件设置列表--成绩</span> </div> -->
  <div id="resultlistarea" height="500px;">
  <table height="500px;">
  <tr valign="top">
 	<td width="31%" id="gg4" >
    <div id="scrollDiv" class="scrollDiv" >
    <table border="0" cellpadding="0" cellspacing="1" width="100%" id="myTable" class="scrollTable">
      <tr class="scrollColThead">
        <th class="scrollRowThead scrollCR" style="width:50px">科目代码</th>
        <th class="scrollRowThead scrollCR" style="width:50px">科目名称</th>
        <th class="scrollRowThead scrollCR" style="width:50px">成绩要求</th>
        <th class="scrollRowThead scrollCR" style="width:50px">最低分数</th>
        <th class="scrollRowThead scrollCR" style="width:50px">最高分数</th>
        <th class="scrollRowThead scrollCR" style="width:160px">操作</th>
      </tr>
       <c:if test="${not empty cjList}">
	      	<c:forEach items="${cjList}" var="item" varStatus="s">
		      <tr class="${s.index%2==0?'even':'odd'}" style="height: 30px;">
		        <td>${item.kmgbm }</td>
		        <td>${item.kmgbmmc }</td>
		        <td>${item.cjyqms }</td>
		        <td>${item.zdf}</td>
		        <td>${item.zgf}</td>
		        <td>
		        	<input name="toUpdate" type="button" id="toUpdate" value="  修改  " onclick="toUpdateXX(${item.id },0)" />
		        	<input name="toDelete" type="button" id="toDelete" value="  删除  " onclick="return toUpdateXX(${item.id },3);"/> 
		        </td>
		      </tr>
	      </c:forEach>
      </c:if>
    </table>
	</div>	
	</td>
	</tr>
	</table>
  </div>
  <div id="bottomarea"><div id="bottomarea_l"></div><div id="bottomarea_r"></div></div>
</div>
</form>
</body>
</html>

