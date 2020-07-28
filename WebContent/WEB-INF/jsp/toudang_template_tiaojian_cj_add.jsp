
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript" src="../../js/jquery/jquery-1.12.4.js"></script>
<script type="text/JavaScript"> 
		$(document).ready(function(){
			$("input[type='radio']").bind("click",function(){
				var cjyq = $(this).val() ;
			    if(cjyq==1){
			    	$("#zdf").attr("disabled",false);
			    	$("#zgf").attr("disabled",true);
	    	    }
	    	    if(cjyq==2){
	    	    	$("#zdf").attr("disabled",true);
			    	$("#zgf").attr("disabled",false);
	    	    }	
	    	    if(cjyq==3){
	    	    	$("#zdf").attr("disabled",false);
			    	$("#zgf").attr("disabled",false);
	    	    }	
			});
			
			$("#doAddCJCommit").bind("click",checkForm);
		});
		//提交保存前检验
       function checkForm(){
    	   var kmgbm=$.trim($("#kmgbm").val());//科目名称
    	   var cjyq=$.trim($("input[type='radio']:checked").val());//成绩要求
    	   var zdf=$.trim($("#zdf").val());//最低分
    	   var zgf=$.trim($("#zgf").val());//最高分
    	   if(kmgbm==""){
    		   alert("科目名称不能为空");
    		   return false;
    	   }
    	   if(cjyq==""){
    		   alert("成绩要求不能为空");
    		   return false;
    	   }
    	   var reg = /^\d+$/ ;//非负整数表达式
    	   if(cjyq==1 || cjyq==3){
    		   if(zdf==""){
        		   alert("最低分不能为空");
        		   return false;
        	   }
    		   if(!reg.test(zdf)){
    			   alert("最低分只能为非负整数");
        		   return false;
    		   }
    	   }
    	   if(cjyq==2 || cjyq==3){
    		   if(zgf==""){
        		   alert("最高分不能为空");
        		   return false;
        	   }
    		   if(!reg.test(zgf)){
    			   alert("最高分只能为非负整数");
        		   return false;
    		   }
    	   }
    	   var data ={mbid:$("#mbid").val() ,type:$("#type").val(),kmgbm:kmgbm,cjyq:cjyq,zdf:zdf,zgf:zgf};
    	   $.post("${ctx}/toudang/TouDangTemplateAction.a?doAddCJCommit",
    			  data,
    	   		  function(res){
    				  if(res.msg=="success"){
    					  alert("保存成功!");
    				  }else{
    					  alert("保存失败，请稍后再试!");
    				  }
    				  parent.location.reload(true);
    			  }
    	   		 );
    	   return false;
       }
</script>
</head>
<body >
<form action="${ctx}/toudang/TouDangTemplateAction.a" method="post" id="cjForm">
		<div id="resultlistarea">
				<table width="100%" id="myTable">
					<tr class="even">
						<td style="width:45%;line-height:40px"><div align="right">科目名称：</div></td>
						<td>
							<div align="left">
							   <select name="kmgbm" style="width:150px" id="kmgbm">
				          	 	  <option value="">--请选择--</option>
					              <app:dictselect dictType="2" ifDisplayDictCode="true" isUseDictCode="true"/>
					            </select>
							   <span style="color:red;font-weight: bolder;">*</span>
							</div>
						</td>
					</tr>
					<tr class="even">
						<td style="width:45%;line-height:100px;height:100px">
							<div align="right" style="margin-right:20px">
								<div style="line-height: 30px; height:30px;"><input type="radio" name="cjyq" id="yx_zdf" value="1"/><label for="yx_zdf">限制最低成绩</label></div>
								<div style="line-height: 30px; height:30px;"><input type="radio" name="cjyq" id="yx_zgf" value="2"/><label for="yx_zgf">限制最高成绩</label></div>
								<div style="line-height: 30px; height:30px;"><input type="radio" name="cjyq" id="yx_qbdx" value="3"/><label for="yx_qbdx">两者都有限制</label></div>
							</div>
						</td>
						<td>
							<div align="left" style="margin-left:20px">
								<div  style="line-height: 45px; height:45px;">最低分数：<input type="text" name="zdf" id="zdf" style="width:50px" disabled="disabled"/></div>
								<div  style="line-height: 45px; height:45px;">最高分数：<input type="text" name="zgf" id="zgf" style="width:50px" disabled="disabled"/></div>
							</div>
						</td>
					</tr>
				</table>
		</div>
		<div id="operationarea">
			<div id="operationcontent">
				<div align="center">
					<input type="hidden" name="mbid" id ="mbid" />
					<input type="hidden" name="type" id ="type" />
					<input name="doAddCJCommit" type="button"  id="doAddCJCommit" value="  提交  " > 
				</div>
			</div>
		</div>
</form>
</body>
</html>


