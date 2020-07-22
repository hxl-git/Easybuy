package com.controller;
import java.util.List;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.entity.EasybuyProductCategory;
import com.service.product.ProductCategoryService;
import com.service.product.impl.ProductCategoryServiceImpl;
import com.utils.Pager;
import com.utils.ReturnResult;

/**
 * 商品管理控制层！ Servlet implementation class ProductCategoryService
 */
@Controller
public class ProductCategoryServlet {

	@Autowired
	private ProductCategoryService pcservice;
	
	/**
	 * 获取所有商品分类信息！
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@RequestMapping("/category.html")
	public String category(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//获取当前页数
		String currentPage1 = request.getParameter("currentPage");
		int currentPage = 1;
		if(currentPage1 != null) {
			currentPage = Integer.parseInt(currentPage1);
		}
		//全部商品条数
		int count = pcservice.getTotalCount();
		Pager pager = new Pager(count, 8, currentPage);
		pager.setUrl("/category.html");
		//查询所有商品分类
		List<EasybuyProductCategory> listCategory = pcservice.getEasybuyProductCategoryAll(pager);
		//查询所有商品分类信息父类
		for(EasybuyProductCategory p : listCategory) {
			//根据父类id查询父类名称
			if(p.getParentId()!=0) {
			String name = pcservice.getparentName(p.getParentId());
			p.setParentName(name);
			}
		}
		//获取分类信息
		//一级
		List<EasybuyProductCategory> productCategoryList1 = pcservice.getProductCategoryListOne();
		//二级
		List<EasybuyProductCategory> productCategoryList2 = pcservice.getProductCategoryListTwo();
		request.setAttribute("productCategoryList1",productCategoryList1);
		request.setAttribute("productCategoryList2",productCategoryList2);
		request.setAttribute("listCategory", listCategory);
		request.setAttribute("pager", pager);
		return "backend/user/Member_Money";
	}

	/**
	 * 删除/修改成功后返回商品列表！
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/ac.html")
	public String ac(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//获取当前页数
		String currentPage1 = request.getParameter("currentPage");
		int currentPage = 1;
		if(currentPage1 != null) {
			currentPage = Integer.parseInt(currentPage1);
		}
		//全部商品条数
		int count = pcservice.getTotalCount();
		Pager pager = new Pager(count, 8, currentPage);
		pager.setUrl("/ac.html");
		//查询所有商品
		List<EasybuyProductCategory> listCategory = pcservice.getEasybuyProductCategoryAll(pager);
		//查询所有商品分类信息父类
				for(EasybuyProductCategory p : listCategory) {
					//根据父类id查询父类名称
					if(p.getParentId()!=0) {
					String name = pcservice.getparentName(p.getParentId());
					p.setParentName(name);
					}
				}
		request.setAttribute("listCategory", listCategory);
		request.setAttribute("pager", pager);
		return "backend/user/Member_Money";
	}

	/**
	 * 删除商品分类信息！
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	public ReturnResult delCategory(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//获取分类id
		int id = Integer.parseInt(request.getParameter("id"));
		int type=Integer.parseInt(request.getParameter("type"));
		
		
		return null;
	}

	/**
	 * 添加分类！
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/AddProductCategory.html")
	public String AddProductCategory(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//获取分类信息
		//一级
		List<EasybuyProductCategory> productCategoryList1 = new ProductCategoryServiceImpl().getProductCategoryListOne();
		request.setAttribute("productCategoryList1",productCategoryList1);
		return "backend/category/toAddProductCategory";
	}

	/**
	 * 添加二级分类！
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addCategoryLevel2.html")
	public String addCategoryLevel2(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//根据一级分类id查询二级分类
		int parentId = Integer.parseInt(request.getParameter("parentId"));
		//二级
		List<EasybuyProductCategory> productCategoryList2 = new ProductCategoryServiceImpl().getProductCategoryListByparentId(2, parentId);
		request.setAttribute("productCategoryList2",productCategoryList2);
		return "backend/category/toAddProductCategory";
	}

	/**
	 * 添加分类根据一级分类获取二级分类信息！
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getProductCategoryTwo.html")
	public String getProductCategoryTwo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//获取父级id
		int parentId = Integer.parseInt(request.getParameter("parentId"));
		int typeId = 2;
		//查询二级分类
		List<EasybuyProductCategory> listTwo = pcservice.getProductCategoryListByparentId(typeId, parentId);
		
		request.setAttribute("listTwo", listTwo);
		
		return "backend/product/toAddProduct";
	}

	/**
	 * 添加分类根据二级分类获取三级分类信息！
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getProductCategoryThree.html")
	public String getProductCategoryThree(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//获取父级id
		int parentId = Integer.parseInt(request.getParameter("parentId"));
		int typeId = 3;
		//查询二级分类
		List<EasybuyProductCategory> listThree = pcservice.getProductCategoryListByparentId(typeId, parentId);
		request.setAttribute("listThree", listThree);			
		return "backend/product/toAddProduct";
	}

	/**
	 * 新增
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ReturnResult insertCategory(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		return null;
	}

	/**
	 * 修改商品分类！
	 * @param reuest
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/upProductCategory.html")
	public String upProductCategory(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//根据id获取分类信息
		int id = Integer.parseInt(request.getParameter("id"));
		EasybuyProductCategory productCategory = pcservice.getProductCategoryById(id);
		
		if(productCategory.getType()==2) {
			//一级
			List<EasybuyProductCategory> productCategoryList1 = pcservice.getProductCategoryListOne();
			request.setAttribute("productCategoryList1",productCategoryList1);
		}
		
		if(productCategory.getType()==3) {
			
			List<EasybuyProductCategory> productCategoryList2 = pcservice.getProductCategoryListTwo();
			//二级
			request.setAttribute("productCategoryList2",productCategoryList2);
			//一级
			List<EasybuyProductCategory> productCategoryList1 = pcservice.getProductCategoryListOne();
			request.setAttribute("productCategoryList1",productCategoryList1);		
		}
		
		request.setAttribute("productCategory", productCategory);
		return "backend/category/toAddProductCategory";

	}


}
