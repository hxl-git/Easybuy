// JavaScript Document

var jq = jQuery.noConflict();
jQuery(function(){
	jq(".leftNav ul li").hover(
		function(){
			jq(this).find(".fj").addClass("nuw");
			jq(this).find(".zj").show();
		}
		,
		function(){
			jq(this).find(".fj").removeClass("nuw");
			jq(this).find(".zj").hide();
		}
	)
})


function pros(id) {
	var item={"action":"queryProductList","category":id};
	location.href = contextPath+"/queryProductList?category="+id;
}

function bianliang(thisa) {
	// 移除所有类样式！
	$(thisa).parent().children("li").removeClass();
	// 添加类样式！
	$(thisa).addClass("checked");
}


