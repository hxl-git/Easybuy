<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<div class="m_left">
	<div class="left_n">管理中心</div>
	<c:if test="${!empty sessionScope.easybuyUserLogin}">
		<div class="left_m">
			<div class="left_m_t t_bg1">订单中心</div>
			<ul>

				<li><a href="javascript:;"
					onclick="clickLeft(this,'/order-index.html?userId=${sessionScope.easybuyUserLogin.id}')"
					<c:if test="${menu==1}"> class="now" </c:if>>我的订单</a></li>
				<c:if test="${sessionScope.easybuyUserLogin.type==1}">
					<li><a href="javascript:;"
						onclick="clickLeft(this,'/queryAllOrder.html')"
						<c:if test="${menu==9}"> class="now" </c:if>>全部订单</a></li>
				</c:if>
			</ul>
		</div>
	</c:if>
	<c:if test="${!empty sessionScope.easybuyUserLogin}">
		<div class="left_m">
			<div class="left_m_t t_bg2">会员中心</div>
			<ul>

				<li><a href="javascript:;"
					onclick="clickLeft(this,'/user-index.html')"
					<c:if test="${menu==2}"> class="now" </c:if>>用户信息</a></li>
				<c:if test="${sessionScope.easybuyUserLogin.type==1}">
					<li><a href="javascript:;"
						onclick="clickLeft(this,'/user.html')"
						<c:if test="${menu==8}"> class="now" </c:if>>用户列表</a></li>
				</c:if>
			</ul>
		</div>
	</c:if>
	<c:if test="${sessionScope.easybuyUserLogin.type==1}">
		<div class="left_m">
			<div class="left_m_t t_bg2">商品管理</div>
			<ul>
				<li><a href="javascript:;"
					onclick="clickLeft(this,'/category.html')"
					<c:if test="${menu==4}"> class="now" </c:if>> 分类管理</a></li>
				<li><a href="javascript:;"
					onclick="clickLeft(this,'/product-index.html')"
					<c:if test="${menu==5}"> class="now" </c:if>> 商品管理</a></li>
				<li><a href="javascript:;"
					onclick="clickLeft(this,'/toAddUpdate.html')"
					<c:if test="${menu==6}"> class="now" </c:if>> 商品上架</a></li>
			</ul>
		</div>
	</c:if>
	<div class="left_m">
		<div class="left_m_t t_bg2">资讯中心</div>
		<ul>
			<li><a href="javascript:;"
				onclick="clickLeft(this,'/queryNewsList.html')"
				<c:if test="${menu==7}"> class="now" 
				</c:if>>资讯列表</a></li>
		</ul>
	</div>
</div>
<script type="text/javascript" src="${ctx}/statics/js/leftBar/left.js"></script>
<script src="${ctx}/statics/js/backend/Product.js"></script>
<script src="${ctx}/statics/js/backend/backend.js"></script>