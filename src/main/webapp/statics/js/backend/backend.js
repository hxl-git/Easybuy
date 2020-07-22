/**
 * 商品管理删除点击事件！
 * 
 * @returns
 */
function _confirm2(id) {
	mizhu.confirm('温馨提醒', '要删除该商品？', "确定", "取消", function(flag) {
		if (flag) {
			delProduct(id);
		}
	});
}
/**
 * 根据ID删除商品信息！
 * 
 * @param id
 * @returns
 */
function delProduct(id) {
	$
			.ajax({
				url : contextPath + "/deleteById.html",
				method : "post",
				data : {
					"id" : id
				},
				success : function(jsonStr) {
					var result = eval("(" + jsonStr + ")");
					// 删除成功与否弹出信息！
					mizhu.toast(result.message, 1500);
					if (result.status == 1) {
						var currentPage = $("#thisCurrentPage").val();
						var ise = {
							"currentPage" : currentPage
						};
						$(".m_right").load(contextPath+ "/product-index.html .m_right>*",ise);
					}
				}
			});

}

/**
 *新增/ 修改信息点击事件！
 * 
 * @returns
 */
function s_btn(thisId, currentPage) {
	var id = thisId;
	// 获取用户输入的数据！
	var yname = $("#yloginName").val();
	var name = $("#loginName").val();
	var userName = $("#userName").val();
	var password = $("#password").val();
	var repPassword = $("#repPassword").val();
	var identityCode = $("#identityCode").val();
	var sex = $("input[name='ra']:checked").val();
	var email = $("#email").val();
	var mobile = $("#mobile").val();
	var user = $("#user").val();


	/* 非空验证 */
	if (name == "" || name == null) {
		mizhu.alert('操作提醒', "用户名不能为空！");
		return;
	}else if(yname != name){
		// 拼接参数！
		var item = {
			"name" : name
		};
		
		// 异步请求！
		$.ajax({
				async : false,
				url : contextPath
					+ "/loginNameCount.html",
				data : item,
				type : "POST",
				success : function(jsonStr) {
					var count = eval("(" + jsonStr
							+ ")");
						if (count.status != 1) {
							mizhu.alert('操作提醒', "该用户名重复！");
							return;
						}
				
				}
		});
	
		
	}
	
	if (userName == "" || userName == null) {
		mizhu.alert('操作提醒', "真实姓名不能为空！");
		return;
	}
	/* 非空验证 */
	if (id == null || id == "") {
		if (password == "" || password == null) {
			mizhu.alert('操作提醒', "密码不能为空！");
			return;
		} else if (repPassword == null || repPassword == "") {
			mizhu.alert('操作提醒', "确认密码不能为空！");
			return;
		} else if (password != repPassword) {
			mizhu.alert('操作提醒', "两次密码输入不一致请核对！");
			return;
		}
	}
	/* 非空验证 */
	if (identityCode == "" || identityCode == null) {
		mizhu.alert('操作提醒', "身份证号不能为空！！");
		return;
	} else if (mobile == "" || mobile == null) {
		mizhu.alert('操作提醒', "手机号不能为空！");
		return;
		// 验证邮箱格式
	} else if (email != null && email != "" && !checkEamil(email)) {
		mizhu.alert('操作提醒', "邮箱格式不正确！");
		return;
		// 验证手机
	} else if (mobile != null && mobile != "" && !checkMobile(mobile)) {
		mizhu.alert('操作提醒', "手机格式不正确！");
		return;
		// 验证身份证
	} else if (identityCode != null && identityCode != ""
			&& !checkIdentityCode(identityCode)) {
		mizhu.alert('操作提醒', "身份证号格式不正确！");
		return;
	} else {
		var item = {
			"name" : name,
			"userName" : userName,
			"password" : password,
			"identityCode" : identityCode,
			"sex" : sex,
			"email" : email,
			"mobile" : mobile,
			"user" : user,
			"id" : thisId
		};
		
		$.post(contextPath + "/modify.html", item,
				function(data) {
					var result = eval("(" + data + ")");
					if (result.status == 1) {
						mizhu.toast('操作成功！', 2000);
						var ise = {
							"id" : thisId,
							"currentPage" : currentPage
						};
						$(".m_right").load(
								contextPath + "/adduser.html .m_right>*", ise);
					}else{
						mizhu.alert('操作提醒', result.message);
					}
				});
	}
};

/**
 * 验证邮箱正则表达式！
 */
function checkEamil(eamil) {
	var reg = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	if (reg.test(eamil)) {
		return true;
	} else {
		return false;
	}
}

/**
 * 验证手机号码正则表达式！
 */
function checkMobile(phone) {
	var reg = /^\d{5,11}$/;
	if (reg.test(phone)) {
		return true;
	} else {
		return false;
	}
}

/**
 * 验证身份证号码正则表达式！
 */
function checkIdentityCode(mem) {
	var reg = /^\w{18}$/;
	if (reg.test(mem)) {
		return true;
	} else {
		return false;
	}
}
