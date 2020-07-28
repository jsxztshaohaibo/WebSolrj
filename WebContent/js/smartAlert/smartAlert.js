$(document).ready(function(){
		var ipt =  document.getElementById("ipt");//搜索输入框
		var ser =  document.getElementById("ser_box");//搜索输入框父DIV
		var bot =  document.getElementById("bot_box");//搜索结果展示列表DIV
		var oul =  document.getElementById("oul");//搜索结果展示列表DIV中的有序列表
		ipt.oninput = function() {
			var ss = ipt.value;
			$.ajax({
			   type: "POST",
			   url: smartAlertAction,
			   data: "name="+ss,
			   success: function(msg){
				   queryList(msg);
			   }
			}); 
		};
		ipt.onfocus = function() {
			var ss = ipt.value;
			$.ajax({
			   type: "POST",
			   url: smartAlertAction,
			   data: "name="+ss,
			   success: function(msg){
				   queryList(msg);
			   }
			});
		};
		/*取li*/
		lis = document.getElementsByTagName("li");
		/*按键*/
		var i = 0;
		document.onkeydown = function(ev) {
			if(bot.style.display == "block") {//结果展示的下拉列表DIV是否显示
				if(ev.keyCode == 40) {//下箭头
					for(var j = 0; j < lis.length; j++) {
						if(lis[j].className == "sel") {
							lis[j].className = "";
						}
					}
					if(i < lis.length) {
						lis[i].className = "sel";
						i++;
						if(i == lis.length) {
							i = 0;
						}
					}
				}
				if(ev.keyCode == 38) {//上箭头
					m = 0
					for(; m < lis.length; m++) {
						if(lis[m].className == "sel") {
							lis[m].className = "";
							break;
						}
					}
					i = m;
					if(m > 0) {
						lis[m - 1].className = "sel";
					} else {
						lis[lis.length - 1].className = "sel";
					}
				}
				if(ev.keyCode == 13) {//回车键
					for(var n = 0; n < lis.length; n++) {
						if(lis[n].className == "sel") {
							ipt.value = lis[n].innerHTML;
						}
					}
					bot.style.display = "none";
				}
			} else {
				i = 0;
				m = 0;
			}
		}
		function queryList(data) {
			var arr = data.data;
			oul.innerHTML = "";
			if(arr.length == 0) {
				bot.style.display = "none";
			} else {
				bot.style.display = "block";
			}
			for(var i = 0; i < arr.length; i++) {
				li = document.createElement("li");
				li.innerHTML = arr[i];
				li.onclick = function() {
					var res = this.innerHTML.split("@#$");
					//ipt.value = this.innerHTML;
					ipt.value =  res[0];
					document.getElementById("code").value=res[1];
					oul.innerHTML = "";
					bot.style.display = "none";
				}
				oul.appendChild(li);
			}
		}
}); 