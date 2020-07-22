package com.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.entity.EasybuyNews;
import com.entity.EasybuyProduct;
import com.entity.EasybuyProductCategory;
import com.service.news.NewsService;
import com.service.news.impl.NewsServiceImpl;
import com.service.product.ProductCategoryService;
import com.service.product.ProductService;
import com.service.product.impl.ProductCategoryServiceImpl;
import com.service.product.impl.ProductServiceImpl;
import com.utils.Pager;

/**
 * Servlet implementation class IndexServlet
 */
@Controller
public class IndexServlet {
	
	@Autowired
	private ProductService pservice;
	@Autowired
	private ProductCategoryService pcservice;
	@Autowired
	private NewsService nservice;
	/**
	 * 主页面加载
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/index.no")
	public String index(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//获取全部商品
		List<EasybuyProduct> productList = pservice.getEasybuyProductList();
		//一级分类
		List<EasybuyProductCategory> list = pcservice.getProductCategoryListOne();
		//二级分类
		List<EasybuyProductCategory> list2 = pcservice.getProductCategoryListTwo();
		//三级分类
		List<EasybuyProductCategory> list3 = pcservice.getProductCategoryListThree();
		//新闻最新5条数
		Pager pager = new Pager(5, 5,1);
		List<EasybuyNews> newsList = nservice.queryNewsList(pager);
		//返回前台数据
		request.setAttribute("newsList", newsList);
		request.setAttribute("productList",productList);
		request.getSession().setAttribute("list", list);
		request.getSession().setAttribute("list2", list2);
		request.getSession().setAttribute("list3", list3);
		return "pre/Index";
	}
	
}
