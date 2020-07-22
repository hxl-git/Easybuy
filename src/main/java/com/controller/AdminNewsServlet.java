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
import com.service.news.NewsService;
import com.service.news.impl.NewsServiceImpl;
import com.utils.EmptyUtils;
import com.utils.Pager;


/**
 * 新闻资讯列表控制层！
 * Servlet implementation class AdminNewsServlet
 */
@Controller
public class AdminNewsServlet {

	@Autowired
	private NewsService nservuce;
	
	
	/**
	 * 获取资讯列表信息！
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryNewsList.html")
	public String queryNewsList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//获取当前页数
		String currentPage1 = request.getParameter("currentPage");
		int currentPage = 1;
		if(currentPage1!=null) {
			currentPage = Integer.parseInt(currentPage1);
		}
		//获取新闻总条数
		int count = new NewsServiceImpl().getTotalCount();
		//pager对象
		Pager pager = new Pager(count, 10, currentPage);
		pager.setUrl("/AdminNewsServlet?action=queryNewsList");
		//获取新闻列表
		List<EasybuyNews> newsList = nservuce.queryNewsList(pager);
		
		//返回新闻列表分页Pager
		request.setAttribute("pager", pager);
		request.setAttribute("newsList", newsList);
		return "/backend/news/newsList";
	}
	
   
	
	/**
	 * 新闻详情！
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/newsDeatil.html")
	public String newsDeatil(HttpServletRequest request,HttpServletResponse response)throws Exception{
		return "/backend/news/newsDetail";
	}

}
