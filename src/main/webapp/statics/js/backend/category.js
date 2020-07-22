/**
 * 绑定商品信息删除点击事件！
 */
 function delCategory(type,id) {
	 mizhu.confirm('温馨提醒', '要删除该分类吗？',"确定","取消", function(flag) {
			if(flag) {
				$.ajax({
					"url" : contextPath + "/delCategory.html",
					"data" : {
						"type":type,
						"id" : id
					},
					"method" : "post",
					"success" : function(data) {
						var result = eval("(" + data + ")");
						// 删除成功与否弹出信息！
						mizhu.alert('操作提醒', result.message);
						if (result.status == 1) {
							var currentPage=$("#thisCurrentPage").val();
							var ise={"currentPage":currentPage};	
							$(".m_right").load(contextPath+"/category.html .m_right>*",ise);
						}
					}
				});
				
			}
		});
	
};

/**
 * 添加分类
 * 
 * @returns
 */
function toAddProductCategory() {
	$("#AddProductCategory").load(contextPath + "/AddProductCategory.html");
}
/**
 * 分类级别change事件！
 */
function selectProductCategoryLevel(odd) {
	var ood = $(odd).val();
	if (ood == 3) {
		$("#productCategoryLevel2").parent().parent().show();
		$("#productCategoryLevel1").parent().parent().show();
	} else if (ood == 2) {
		$("#productCategoryLevel2").parent().parent().hide();
		$("#productCategoryLevel1").parent().parent().show();
	} else {
		$("#productCategoryLevel1").parent().parent().hide();
		$("#productCategoryLevel2").parent().parent().hide();
	}
}
/**
 * 二级分类！
 */
function queryProductCategoryList(onedd) {
	var ood = $(onedd).val();
	var item = {
		"parentId" : ood
	};
	$("#productCategoryLevel2")
			.load(
					contextPath
							+ "addCategoryLevel2.html #productCategoryLevel2>*",
					item);
}

/**
 * 新增！
 * @returns
 */
function saveinsert() {
	var type = $("#type").val();
	var parentId = 0;
	var name = $("#name").val();
	if (name == "") {
		mizhu.alert('操作提醒', "请输入分类名称！");
		return;
	}

	if (type == 1) {
		parentId = 0;

	} else if (type == 2) {
		parentId = $("#productCategoryLevel1").val();
		if (parentId == 0) {
			mizhu.alert('操作提醒',"请选择父分类！");
			return;
		}
	} else if (type == 3) {

		parentId = $("#productCategoryLevel2").val();
		if (parentId == 0) {
			mizhu.alert('操作提醒',"请选择父分类！");
			return;
		}
	}else{
		if (parentId == 0) {
			mizhu.alert('操作提醒',"请选择分类级别！");
			return;
		}
	}
	var item = {
		"name" : name,
		"parentId" : parentId,
		"type" : type
	};
	$.post(contextPath + "/insertCategory.html", item, function(data) {
		var result = eval("(" + data + ")");
		// 添加成功与否弹出信息！
		mizhu.alert('操作提醒', result.message);
		if (result.status == 1) {
			$(".m_right").load(contextPath+"ac.html .m_right>*");
		}

	});
}
/**
 * 更新！
 * @returns
 */
function saveOrUpdate() {
	var id=$("#id").val();
	var type = $("#type").val();
	var parentId = 0;
	var name = $("#name").val();
	if (name == "") {
		mizhu.alert('操作提醒',"请输入分类名称！");
		return;
	}

	if (type == 1) {
		parentId = 0;

	} else if (type == 2) {
		parentId = $("#productCategoryLevel1").val();
		if (parentId == 0) {
			mizhu.alert('操作提醒',"请选择父分类！");
			return;
		}
	} else if (type == 3) {

		parentId = $("#productCategoryLevel2").val();
		if (parentId == 0) {
			mizhu.alert('操作提醒',"请选择父分类！");
			return;
		}
	}else{
		if (parentId == 0) {
			mizhu.alert('操作提醒',"请选择分类级别！");
			return;
		}
	}
	var item = {
			"id":id,
			"name" : name,
			"parentId" : parentId,
			"type" : type
		};
	$.post(contextPath + "/insertCategory.html", item, function(data) {
		var result = eval("(" + data + ")");
		// 更新成功与否弹出信息！
		mizhu.alert('操作提醒',result.message);
		if (result.status == 1) {
			var currentPage=$("#thisCurrentPage").val();
			var ise={"id":id,"currentPage":currentPage};	
			$(".m_right").load(contextPath+"/ac.html .m_right>*",ise);
		}

	});
	
}

/**
 * 根据Id修改商品信息！！
 * @param tid
 * @returns
 */
function update(tid) {
	var item={"id":tid};
	$("#AddProductCategory").load(contextPath + "/upProductCategory.html",item);
} 

