package com.controller;

import com.entity.EasybuyCollect;
import com.entity.EasybuyNews;
import com.entity.EasybuyProduct;
import com.entity.EasybuyUser;
import com.entity.EasybuyProductCategory;
import com.service.news.impl.NewsServiceImpl;
import com.service.order.CartService;
import com.service.order.impl.CartServiceImpl;
import com.service.product.ProductCategoryService;
import com.service.product.ProductService;
import com.service.product.impl.ProductCategoryServiceImpl;
import com.service.product.impl.ProductServiceImpl;
import com.utils.EmptyUtils;
import com.utils.Pager;
import com.utils.PrintUtil;
import com.utils.ReturnResult;
import com.utils.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
//import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * 商品信息控制层！
 * Servlet implementation class ProductServlet
 */

@Controller
public class ProductServlet  {

	@Autowired
	private ProductService pservice;
	@Autowired
	private CartService cservice;
	@Autowired
	private ProductCategoryService pcservice;
	/**
	 * 商品的主页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/product-index.html")
	public String index(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//获取当前页数
		String currentPage1 = request.getParameter("currentPage");
		int currentPage = 1;
		if(currentPage1 != null) {
			currentPage = Integer.parseInt(currentPage1);
		}
		//获取一级分类总数
		int count = pservice.getTotalCount();
		//pager工具类
		Pager pager = new Pager(count, 8, currentPage);		
		pager.setUrl("/product-index.html");
		//获取商品列表
		List<EasybuyProduct> productList = pservice.getEasybuyProductAll(pager);
		request.setAttribute("pager", pager);
		request.setAttribute("productList", productList);
		return "/backend/product/productList";
	}
	
	/**
	 * 修改商品信息！
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateAndDel.html")
	public String updateAndDel(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//获取当前页数
		String currentPage1 = request.getParameter("currentPage");
		int currentPage = 1;
		if(currentPage1 != null) {
			currentPage = Integer.parseInt(currentPage1);
		}
		//获取一级分类总数
		int count = pservice.getTotalCount();
		//pager工具类
		Pager pager = new Pager(count, 8, currentPage);		
		pager.setUrl("/updateAndDel.html");
		//获取商品列表
		List<EasybuyProduct> productList = pservice.getEasybuyProductAll(pager);
		
		request.setAttribute("pager", pager);
		request.setAttribute("productList", productList);
		return "/backend/product/productList";
	}
	
	/**
	 * @param request
	 * @return
	 */
	private List<EasybuyCollect> getUserFromSession(HttpServletRequest request) {
		return null;
	}

	/**
	 * 获取一级分类！
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryProductList.html")
	public String queryProductList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//获取一级分类Id
		int category = Integer.parseInt(request.getParameter("category"));
		//获取当前页数
		String currentPage1 = request.getParameter("currentPage");
		int currentPage = 1;
		if(currentPage1 != null) {
			currentPage = Integer.parseInt(currentPage1);
		}
		//获取一级分类总数
		int count = pservice.getProductRowCount(category);
		//pager工具类
		Pager pager = new Pager(count, 4, currentPage);
		pager.setUrl("/queryProductList.html?category="+category);
		//获取一级分类
		List<EasybuyProduct> productList = pservice.getEasybuyProductListByCategoryId(category, pager);
		//返回总条数
		request.setAttribute("total", count);
		//返回商品信息
		request.setAttribute("productList", productList);
		//返回分页Pager
		request.setAttribute("pager", pager);
		//获取用户
		EasybuyUser user = (EasybuyUser)request.getSession().getAttribute("easybuyUserLogin");
		if(user!=null) {
		//根据用户id查询收藏列表
		List<EasybuyCollect> listC = cservice.selectByUserId(user.getId());
		request.setAttribute("listCollect",listC );		
		}
		return "/pre/product/queryProductList";
	}
	/**
	 * 模糊查询
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryLikeProductList.html")
	public String queryLikeProductList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//获取商品名称
		String name = request.getParameter("keyWord");
		//获取当前页数
		String currentPage1 = request.getParameter("currentPage");
		int currentPage = 1;
		if(currentPage1 != null) {
			currentPage = Integer.parseInt(currentPage1);
		}
		//获取模糊查询总数
		int count = pservice.getProductRowCountByName(name);
		//pager工具类
		Pager pager = new Pager(count, 4, currentPage);
		pager.setUrl("/queryLikeProductList.html?keyWord="+name);
		//获取商品
		List<EasybuyProduct> productList = pservice.getEasybuyProductListByCategoryName(name, pager);
		request.setAttribute("total", count);
		//返回商品信息
		request.setAttribute("productList", productList);
		//回显查询商品名
		request.setAttribute("name", name);
		//返回分页Pager
		request.setAttribute("pager", pager);		
		//获取用户
		EasybuyUser user = (EasybuyUser)request.getSession().getAttribute("easybuyUserLogin");
		if(user!=null) {
		//根据用户id查询收藏列表
		List<EasybuyCollect> listC = cservice.selectByUserId(user.getId());
		request.setAttribute("listCollect",listC );
		}
		return "/pre/product/queryProductList";

	}
	

	/**
	 * 获取二级分类！
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryProductList2.html")
	public String queryProductList2(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//获取二级分类Id
		int category = Integer.parseInt(request.getParameter("category"));
		//获取当前页数
		String currentPage1 = request.getParameter("currentPage");
		int currentPage = 1;
		if(currentPage1 != null) {
			currentPage = Integer.parseInt(currentPage1);
		}
		//获取二级分类总数
		int count = pservice.getProductRowCount2(category);
		//pager工具类
		Pager pager = new Pager(count, 4, currentPage);
		pager.setUrl("/queryProductList2.html?category="+category);
		//获取二级分类
		List<EasybuyProduct> productList = pservice.getEasybuyProductListByCategoryId2(category, pager);
		//返回总条数
		request.setAttribute("total", count);
		//返回商品信息
		request.setAttribute("productList", productList);
		//返回分页Pager
		request.setAttribute("pager", pager);		
		//获取用户
		EasybuyUser user = (EasybuyUser)request.getSession().getAttribute("easybuyUserLogin");
		if(user!=null) {
		//根据用户id查询收藏列表
		List<EasybuyCollect> listC = cservice.selectByUserId(user.getId());
		request.setAttribute("listCollect",listC );
		}
		return "/pre/product/queryProductList";
		
	}

	/**
	 * 获取三级分类！
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryProductList3.html")
	public String queryProductList3(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//获取三级分类Id
		int category = Integer.parseInt(request.getParameter("category"));
		//获取当前页数
		String currentPage1 = request.getParameter("currentPage");
		int currentPage = 1;
		if(currentPage1 != null) {
			currentPage = Integer.parseInt(currentPage1);
		}
		//获取三级分类总数
		int count = pservice.getProductRowCount3(category);
		//pager工具类
		Pager pager = new Pager(count, 4, currentPage);
		pager.setUrl("/queryProductList3.html?category="+category);
		//获取三级分类
		List<EasybuyProduct> productList = pservice.getEasybuyProductListByCategoryId3(category, pager);
		//返回总条数
		request.setAttribute("total", count);
		//返回商品信息
		request.setAttribute("productList", productList);
		//返回分页Pager
		request.setAttribute("pager", pager);
		
		//获取用户
		EasybuyUser user = (EasybuyUser)request.getSession().getAttribute("easybuyUserLogin");
		if(user!=null) {
		//根据用户id查询收藏列表
		List<EasybuyCollect> listC = cservice.selectByUserId(user.getId());
		request.setAttribute("listCollect",listC );
		}
		return "/pre/product/queryProductList";
	}
	/**
	 * 收藏夹
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryProductList4.html")
	public String queryProductList4(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//获取用户
		EasybuyUser user = (EasybuyUser)request.getSession().getAttribute("easybuyUserLogin");
		//根据用户id查询收藏列表
		List<EasybuyCollect> listC = cservice.selectByUserId(user.getId());
		if(listC!=null && listC.size()>0) {
			//查询用户收藏商品列表
			List<EasybuyProduct> productList = pservice.getEasybuyProductListByUser(listC);
		if(productList!=null) {
			request.setAttribute("listCollect",listC );
			request.setAttribute("productList", productList);
			request.setAttribute("total", productList.size());
		}
		}else {
			request.setAttribute("total", 0);
		}
		return "/pre/product/queryProductList";
	}

	/**
	 *根据id查询对应商品信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/queryProductDeatil.html.a")
	public String queryProductDeatil(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//获取用户点击商品Id
		int id = Integer.parseInt(request.getParameter("id"));
		//根据id查询对应商品信息
		EasybuyProduct product = pservice.getEasybuyProductById(id);
		//获取用户
		EasybuyUser user = (EasybuyUser)request.getSession().getAttribute("easybuyUserLogin");
		if(user!=null) {
			//根据用户id查询收藏列表
			List<EasybuyCollect> easybuyCollect = cservice.selectByUserId(user.getId());
			//判断该商品是不是已经收藏
			for(EasybuyCollect c : easybuyCollect) {
				if(c.getProductId()==product.getId()) {
					//返回为收藏的商品
					request.setAttribute("easybuyCollect",c);
				}	
			}
		}
		//返回商品
		request.setAttribute("product", product);
		return "/pre/product/productDeatil";
	}

	/**
	 * 根据id删除商品！
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/deleteById.html")
	public void deleteById(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//ReturnResult
		ReturnResult rr = new ReturnResult();
		//商品id
		int id = Integer.parseInt(request.getParameter("id"));
		//根据id删除商品
		int row = new ProductServiceImpl().delEasybuyProductById(id);
		if(row==1) {
			rr.returnSuccess();
		}else {
			rr.returnFail("删除失败!!");
		}
		PrintUtil.getJsonString(response, rr);
	}

	/**
	 * 修改或者上架操作！
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ReturnResult tomodify(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		return null;
	}

	/**
	 * 商品上架！
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/toAddUpdate.html")
	public String toAddUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//获取一级分类
		List<EasybuyProductCategory> listOne = pcservice.getProductCategoryListOne();
		request.setAttribute("listOne", listOne);
		return "/backend/product/toAddProduct";
	}

	/**
	 * 根据Id查询对应的商品信息传递到修改页面！
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getProduct.html")
	public String getProduct(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 获取id
		int id = Integer.parseInt(request.getParameter("id"));
		// 获取当前页数
		String currentPage1 = request.getParameter("currentPage");
		int currentPage = 1;
		if (currentPage1 != null) {
			currentPage = Integer.parseInt(currentPage1);
		}
		// 根据id获取商品信息
		EasybuyProduct easybuyProduct = pservice.getEasybuyProductById(id);

		// 获取一级分类
		List<EasybuyProductCategory> listOne = pcservice.getProductCategoryListOne();
		request.setAttribute("listOne", listOne);
		// 二
		List<EasybuyProductCategory> listTwo = pcservice.getProductCategoryListTwo();
		request.setAttribute("listTwo", listTwo);
		// 三
		List<EasybuyProductCategory> listThree = pcservice.getProductCategoryListThree();
		request.setAttribute("listThree", listThree);

		request.setAttribute("easybuyProduct", easybuyProduct);
		request.setAttribute("currentPage", currentPage);

		return "/backend/product/toAddProduct";
	}

	/**
	 * 商品三级分类！！
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public void getCategoryList(HttpServletRequest request, HttpServletResponse response) throws Exception {
	
	}

/**
 * 上传
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
	@RequestMapping("/getImgs.html")
	public void getImgs(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//ReturnResult
		ReturnResult rr = new ReturnResult();
		

		boolean isMultipart = ServletFileUpload.isMultipartContent(request);  
        if(!isMultipart){  
            throw new RuntimeException("请检查您的表单的enctype属性，确定是multipart/form-data");  
        }  
        DiskFileItemFactory dfif = new DiskFileItemFactory();  
        ServletFileUpload parser = new ServletFileUpload(dfif);  
      
        parser.setFileSizeMax(4*1024*1024);//设置单个文件上传的大小  
        parser.setSizeMax(10*1024*1024);//多文件上传时总大小限制  

        List<FileItem> items = null;  
        try {  
            items = parser.parseRequest(request);  
        }catch(FileUploadBase.FileSizeLimitExceededException e) {  
         
        }catch(FileUploadBase.SizeLimitExceededException e){  
         
        }
        int id = 0;
        String fileName = null;
        InputStream inputStream = null;
        EasybuyProduct product = new EasybuyProduct();
        if(items!=null){  
            for(FileItem item:items){  
                if(!item.isFormField()){  
					// 该表单项是file类型的   
                    fileName = item.getName();  
                    inputStream = item.getInputStream();  
                }else {
                	//该表单项是普通类型的
                	String fieldName = item.getFieldName();
                	String fieldValue = item.getString("UTF-8");
                	
                	if(fieldName.equals("id")) {
                		String pId = fieldValue;
              
                		if(pId=="0" || pId.equals("0")) {
                			
                		}else {
                			id = Integer.parseInt(pId);
                		}
                	}
                	if(fieldName.equals("name")) {
                		product.setName(fieldValue);
                	}
                	if(fieldName.equals("categoryLevel1Id")) {
                		product.setCategoryLevel1(Integer.parseInt(fieldValue));
                	}
                	if(fieldName.equals("categoryLevel2Id")) {
                		product.setCategoryLevel2(Integer.parseInt(fieldValue));
                	}
                	if(fieldName.equals("categoryLevel3Id")) {
                		product.setCategoryLevel3(Integer.parseInt(fieldValue));
                	}
                	if(fieldName.equals("price")) {
                		product.setPrice(Float.parseFloat(fieldValue));
                	}
                	if(fieldName.equals("stock")) {
                		product.setStock(Integer.parseInt(fieldValue));
                	}
            
                	if(fieldName.equals("description")) {
                		product.setDescription(fieldValue);
                	}
                	
                }
            }  
        }  
     //判断用户是否上传新图片
        if(inputStream!=null) {
        
		/**校验上传文件的大小*/
		int available = inputStream.available();	//文件大小byte
		//1M=1024k=1048576字节 
		if(available > 4*1048576) {		//文件不能大于4M
			rr.returnFail("图片不能大于4M");
		}else {
		//避免文件名重复使用uuid来避免,产生一个随机的uuid字符
		   String realFileName=UUID.randomUUID().toString()+".jpg";
		   realFileName = realFileName.toUpperCase();
		   product.setFileName(realFileName);
		   //上架商品
		   product.setIsDelete(0);
		//图片上传到服务器端 /files 文件夹
		   //想要保存的目标文件的目录下
		   String tagDir=request.getServletContext().getRealPath("/files");
		   OutputStream output=new FileOutputStream(new File(tagDir,realFileName));
		   int len=0;
		   byte[] buff=new byte[1024*8];  
		   while ((len = inputStream.read(buff)) > -1) {
		     output.write(buff, 0, len);
		   }
		   inputStream.close();
	        output.close();
        }
          product.setId(id);
		   //添加一个商品 //修改一个商品
		   int row = new ProductServiceImpl().addEasybuyProduct(product);
		   
		   if(row!=1) {
			   rr.returnFail("添加或修改失败!!");
		   } else {
			   rr.returnSuccess();
		   }   
        }
		PrintUtil.getJsonString(response, rr);
		  
	}
	

}
