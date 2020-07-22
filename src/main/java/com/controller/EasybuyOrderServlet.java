package com.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.entity.DetailProduct;
import com.entity.EasybuyOrder;
import com.entity.EasybuyOrderDetail;
import com.service.order.EasybuyOrderService;
import com.utils.Pager;

/**
 * 订单详情控制层！ 
 * Servlet implementation class EasybuyOrderServlet
 */
@Controller
public class EasybuyOrderServlet{
	@Autowired
	private EasybuyOrderService oservice;
	
	/**
	 * 订单列表
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/order-index.html")
	public String index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取用户id
		int userId = Integer.parseInt(request.getParameter("userId"));
		//获取当前页数
		String currentPage1 = request.getParameter("currentPage");
		int currentPage = 1;
		if(currentPage1 !=null) {
			currentPage = Integer.parseInt(currentPage1);
		}
		//获取用户订单总数
		int count = oservice.getUserByIdOrder(userId);
		//pager
		Pager pager = new Pager(count, 4, currentPage);
		pager.setUrl("/order-index.html?userId="+userId);
		//获取用户全部订单
		List<EasybuyOrder> orderList = oservice.getEasybuyOrderAll(userId,pager);
		for(EasybuyOrder o : orderList) {
			//订单号获取订单详情
			List<EasybuyOrderDetail> list = oservice.getEasybuyOrderDetail(o.getId());
			//订单号获取购物商品详情
			List<DetailProduct>  list2= oservice.getEasybuyOrderDetail(list);
			List<DetailProduct>  list3 = new ArrayList<DetailProduct>();
			list3.add(list2.get(0));
			o.setListOrderDetail(list3);
		}
		request.setAttribute("pager", pager);
		request.setAttribute("orderList", orderList);
		return "backend/order/orderList";
	}

	/**
	 * 查询订单明细！
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/queryOrderDeatil.html")
	public String queryOrderDeatil(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int id = Integer.parseInt(request.getParameter("id"));
		//订单号Id获取订单详情
		List<EasybuyOrderDetail> list = oservice.getEasybuyOrderDetail(id);
		//订单号获取购物商品详情
		List<DetailProduct>  list2= oservice.getEasybuyOrderDetail(list);
		request.setAttribute("orderId", id);
		request.setAttribute("listOrderDetail",list2);
		return "/backend/order/orderDetailList";
	}

	/**
	 * 全部订单！
	 */
	@RequestMapping("/queryAllOrder.html")
	public String queryAllOrder(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//获取当前页数
		String currentPage1 = request.getParameter("currentPage");
		int currentPage = 1;
		if(currentPage1 !=null) {
			currentPage = Integer.parseInt(currentPage1);
		}
		//获取订单总条数
		int count = oservice.getTotalCount();
		
		//pager 
		Pager pager = new Pager(count, 3, currentPage);
		pager.setUrl("/queryAllOrder.html");
		//获取所有用户订单
		List<EasybuyOrder> orderList = oservice.allEasyBuyOrderList(pager);
		for(EasybuyOrder o : orderList) {
			
			//订单号获取订单详情
			List<EasybuyOrderDetail> list = oservice.getEasybuyOrderDetail(o.getId());
			//订单号获取购物商品详情
			List<DetailProduct>  list2= oservice.getEasybuyOrderDetail(list);
			List<DetailProduct>  list3 = new ArrayList<DetailProduct>();
			list3.add(list2.get(0));
			o.setListOrderDetail(list3);
		}
		request.setAttribute("pager", pager);
		request.setAttribute("orderList", orderList);
		return "/backend/order/orderList";
	}

}
