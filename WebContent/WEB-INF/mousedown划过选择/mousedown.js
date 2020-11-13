$.fn.serializeObject = function () {
  var o = {};
  var a = this.serializeArray();
  $.each(a, function () {
    if (o[this.name] !== undefined) {
      if (!o[this.name].push) {
        o[this.name] = [o[this.name]];
      }
      o[this.name].push(this.value || "");
    } else {
      o[this.name] = this.value || "";
    }
  });
  return o;
};
/* 生成 dom */
var domStr = "";
for (var i = 0; i < 500; i++) {
  domStr +=
    "" +
    '<div class="col-xs-2" style="border:1px solid black">' +
    '<label style="display:inline-block;border:1px solid red;width:100%" for="checkbox' +
    i +
    '">' +
    '<input value="' +
    i +
    '" type="checkbox" name="test" id="checkbox' +
    i +
    '"> value' +
    i +
    "</label> asdasdas" +
    "</div>";
}
$("#container").append(domStr);
/* 事件监听 */
$(document)
  .on("mousedown", function (event) {
    console.log(event);
    if (event.target.tagName === "LABEL") {
      // 寻找目标label，给复选框添加类名“moving”,为了下面通过类名选择和取消复选框
      //$(event.target).find("input").addClass("moving");
    }
    $(document).on("mousemove", function (e) {
       console.log(e)
      if ($(e.target).closest("#container").length === 1) {
        //   自定以目标复选框为“aa”
        var aa = $(e.target).find("input");
        // 判断是不是复选框的所在的内容
        if (
          $(e.target).attr("id") !== "container" &&
          e.target.tagName !== "DIV") {
			  // 判断在没有此类名的情况下
			  if (!aa.hasClass("moving")) {
				//   如果判断到有这个类名，则判断其是否已经勾选
				if (aa.prop("checked")) {
				  aa.prop("checked", false);
				} else {
				  aa.prop("checked", true);
				}
			  }
			  // 否则，就直接在鼠标移动的过程中添加此类名即选则复选框
			  aa.addClass("moving");
        }
      }
    });
  })
  .on("mouseup", function () {
    //   当鼠标抬起移除类名
    $("#container input").removeClass("moving");
    // 停止触发mouseup事件
    $(document).off("mousemove");
  });
//   .on("click", "#checkData", function () {
//     var data = JSON.stringify($("form").serializeObject(), null, 4);

//     $("#data")
//       .empty()
//       .append("<pre>" + data + "</pre>");
//   });
