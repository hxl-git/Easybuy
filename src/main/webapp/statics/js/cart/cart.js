function addCart() {
	var entityId = $("input[name='entityId']").val();
	var quantity = $("input[name='quantity']").val();
	// 添加到购物车
	addCartByParam(entityId, quantity);
}
/**
 * 商品列表页添加到购物车
 * 
 * @param entityId
 * @param quantity
 */
function addCartByParam(entityId, quantity) {
	// 添加到购物车
	$.ajax({
		url : contextPath + "/cart-add.html",
		method : "post",
		data : {
			entityId : entityId,
			quantity : quantity
		},
		success : function(jsonStr) {
			var result = eval("(" + jsonStr + ")");
			// 状态判断
			if (result.status == 1) {
				mizhu.alert('操作提醒', "添加成功！");
				refreshCart();
			} else {
				if(result.message!="商品数量不足"){
					mizhu.confirm('温馨提醒', result.message, '去登录', '先逛逛', function(
							flag) {
						if (flag) {
							location.href = contextPath+ "/pre/Login.jsp";
						}
					});
				}else{
					mizhu.alert('操作提醒',result.message);
				}
				

			}
		}
	})
}

/**
 * 刷新购物车 searchBar DIV
 */
function refreshCart() {
	$.ajax({
		url : contextPath + "/refreshCart.html",
		method : "post",
		success : function(jsonStr) {
			$("#searchBar").html(jsonStr);
		}
	})
}

/**
 * 结算 加载购物车列表
 */
function settlement1() {
	$.ajax({
		url : contextPath + "/settlement1.html",
		method : "post",
		success : function(jsonStr) {
			refreshCart();
			$("#settlement").html(jsonStr);
		}
	});
}

/**
 * 结算 形成预备订单
 */
function settlement2() {
	$.ajax({
		url : contextPath + "/settlement2.html",
		method : "post",
		success : function(jsonStr) {
			$("#settlement").html(jsonStr);
		}
	});
}
/**
 * 结算 订单支付提醒
 */
function settlement3() {
	// 判断地址
	var addressId = $("input[name='selectAddress']:checked").val();
	var newAddress = $("input[name='address']").val();
	var newRemark = $("input[name='remark']").val();
	if (addressId == "" || addressId == null) {
		mizhu.alert('操作提醒',"请选择收货地址");
		return;
	} else if (addressId == "-1") {
		if (newAddress == "" || newAddress == null) {
			mizhu.alert('操作提醒',"请填写新的收货地址");
			return;
		} else if (newAddress.length <= 2 || newAddress.length >= 50) {
			
			mizhu.alert('操作提醒',"收货地址为3-50个字符");
			return;
		}
	}
	var item = {
		"addressId" : addressId,
		"newAddress" : newAddress,
		"newRemark" : newRemark
	};
	$.post(contextPath + "/settlement3.html", item, function(jsonStr) {
		if (jsonStr.substr(0, 1) == "{") {
			var result = eval("(" + jsonStr + ")");
			mizhu.alert('操作提醒',result.message);
		} else {
			$("#settlement").html(jsonStr);
		
		}
	});
}
/**
 * 商品详情页的 数量加
 * 
 * @param obj
 * @param entityId
 */
function addQuantity(obj, entityId, stock) {
	var quantity = Number(getPCount(jq(obj))) + 1;
	if (stock < quantity) {
		mizhu.alert('操作提醒',"商品数量不足");
		return;
	}
	modifyCart(entityId, quantity, jq(obj));
}
/**
 * 减去 数量减
 * 
 * @param obj
 * @param entityId
 */
function subQuantity(obj, entityId) {
	var quantity = getPCount(jq(obj)) - 1;
	if (quantity == 0)
		quantity = 1;
	modifyCart(entityId, quantity, jq(obj));
}
/**
 * 修改购物车列表
 * 
 * @param entityId
 * @param quantity
 */
function modifyCart(entityId, quantity, obj) {
	$.ajax({
		url : contextPath + "/modCart.html",
		method : "post",
		data : {
			entityId : entityId,
			quantity : quantity
		},
		success : function(jsonStr) {
			var result = eval("(" + jsonStr + ")");
			// 状态判断
			if (result.status == 1) {
				obj.parent().find(".car_ipt").val(quantity);
				settlement1();
			} else {
				mizhu.alert('操作提醒',result.message);
			}
		}
	});
}
/**
 * 清空购物车
 */
function clearCart() {
	$.ajax({
		url : contextPath + "/clearCart.html",
		method : "post",
		success : function(jsonStr) {
			$("#settlement").html(jsonStr);
			mizhu.alert('操作提醒',"操作成功");
		}
	});
}
/**
 * 删除购物车
 * 
 * @param entityId
 */
function removeCart(entityId) {
	$.ajax({
		url : contextPath + "/modCart.html",
		method : "post",
		success : function(jsonStr) {
			var result = eval("(" + jsonStr + ")");
			// 状态判断
			if (result.status == 1) {
				settlement1();
			} else {
				mizhu.alert('操作提醒', result.message);
				
			}
		}
	});
}


/**
 * 收藏
 */
function addFavorite(th,id) {
	
	var item = {
		"type" : 0,
		"id" : id,
	};
	$.post(contextPath + "/addCollect.html", item, function(jsonStr) {
		var result = eval("(" + jsonStr + ")");
		if (result.status == 1) {
			mizhu.toast(result.message, 1200);
//			mizhu.alert('操作提醒', "收藏成功！");
			if(result.message=="收藏成功!"){
				$(th).css("background-image", "url("+contextPath+"/statics/images/heart_h.png)");
			}else{
				$(th).css("background-image", "");
			}
			//刷新当前页面
			location.reload();
			
		} else {
			
			if(result.message=="该商品已在收藏夹!"){
				mizhu.alert('操作提醒', result.message);
			}else{
				mizhu.confirm('温馨提醒', result.message, '去登录', '先逛逛', function(
						flag) {
					if (flag) {
						location.href = contextPath+ "/pre/Login.jsp";
					}
				});
			}
			
			
			
		}
		
	});
}
/**
 * 取消收藏
 */
function addFavoriteDeatil(th,id) {

	var item = {
		"type" : 0,
		"id" : id,
	};
	$.post(contextPath + "/delCollect.html", item, function(jsonStr) {
		var result = eval("(" + jsonStr + ")");
		if (result.status == 1) {
			mizhu.toast(result.message, 1200);
//			mizhu.alert('操作提醒', "收藏成功！");
			if(result.message=="取消成功!"){
				$(th).css("background", "url("+contextPath+"/statics/images/heart_h.png) no-repeat left center");
			}else{
				$(th).css("background", "url("+contextPath+"/statics/images/heart.png) no-repeat left center");
			}
			//刷新当前页面
			location.reload();
		} else {
			if(result.message=="取消收藏失败!"){
				mizhu.alert('操作提醒', result.message);
			}else{
				mizhu.confirm('温馨提醒', result.message, '去登录', '先逛逛', function(
						flag) {
					if (flag) {
						location.href = contextPath+ "/pre/Login.jsp";
					}
				});
			}
			
		}
	
	});
}



