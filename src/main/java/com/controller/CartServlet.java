package com.controller;

import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.entity.EasybuyCollect;
import com.entity.EasybuyOrder;
import com.entity.EasybuyProduct;
import com.entity.EasybuyUser;
import com.entity.EasybuyUserAddress;
import com.entity.EasybuyProductCategory;
import com.service.address.EasybuyUserAddressService;
import com.service.address.impl.EasybuyUserAddressServiceImpl;
import com.service.order.CartService;
import com.service.order.EasybuyOrderService;
import com.service.order.impl.CartServiceImpl;
import com.service.order.impl.EasybuyOrderServiceImpl;
import com.service.product.ProductService;
import com.service.product.impl.ProductCategoryServiceImpl;
import com.service.product.impl.ProductServiceImpl;
import com.utils.Constants;
import com.utils.EmptyUtils;
import com.utils.MyBatisUtil;
import com.utils.PrintUtil;
import com.utils.ReturnResult;
import com.utils.ShoppingCart;
import com.utils.ShoppingCartItem;
import com.utils.StringUtils;

/**
 * 购物车控制层！
 * @author Administrator
 *
 */
@Controller
public class CartServlet {

	@Autowired
	private CartService cservice;
	@Autowired
	private ProductService pservice;
	@Autowired
	private EasybuyUserAddressService aservice;
	@Autowired
	private EasybuyOrderService oservice;
	/**
	 * 添加到购物车！
	 *
	 * @return
	 */
	@RequestMapping("/cart-add.html")
	public void add(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//购物车
		ShoppingCart cart = getCartFromSession(request);
		//ReturnResult 对象
		ReturnResult rr = new ReturnResult();
		//商品id
		int entityId = Integer.parseInt(request.getParameter("entityId"));
		//数量
		int quantity = Integer.parseInt(request.getParameter("quantity"));
		//购物车类型
		int type=1;
		//获取用户
		EasybuyUser user = getUserFromSession(request);
		boolean fosl = true;
		//判断该商品是否已在购物车
		for(ShoppingCartItem item : cart.items) {
			if(item.getProduct().getId() == entityId) {
				//修改收藏表数量
				EasybuyCollect coll = new EasybuyCollect(0, user.getId(), entityId, quantity+item.getQuantity(), type);
				cservice.upCollect(coll);
				//修改购物车数量
				item.setQuantity(quantity+item.getQuantity());
				fosl = false;
				rr.returnSuccess();
			}
			
		}
		
		if(fosl) {
		//添加到收藏表
		int row = cservice.addCollect(user.getId(), entityId, quantity, type);
		//根据id查询商品信息
		EasybuyProduct product = pservice.getEasybuyProductById(entityId);
		//购物车对象
		ShoppingCartItem item = new ShoppingCartItem(product, quantity);
		//加入购物车
		cart.items.add(item);
		if(row==1) {
			rr.returnSuccess();
		}else {
			rr.returnFail("添加商品失败!");
		}
		}
		
		PrintUtil.getJsonString(response, rr);
	}

	/**
	 * 添加到收藏！
	 *
	 * @return
	 */
	@RequestMapping("/addCollect.html")
	public void addCollect(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// ReturnResult 对象
		ReturnResult rr = new ReturnResult();
		// 获取商品id
		int productId = Integer.parseInt(request.getParameter("id"));
		// 收藏类型 0 收藏
		int type = 0;
		// 默认收藏1
		int productNum = 1;
		// 获取用户
		EasybuyUser user = getUserFromSession(request);
		// 添加收藏表
		if (user == null) {
			rr.returnFail("未登录不能收藏!!");
		} else {
			boolean fols = true;
			// 判断该商品是否收藏
			// 获取用户收藏列表
			List<EasybuyCollect> listC = cservice.selectByUserId(user.getId());
			for (EasybuyCollect c : listC) {
				if (c.getProductId() == productId) {
					rr.returnFail("该商品已在收藏夹!");
					fols = false;
				}
			}

			if(fols) {
				int row = cservice.addCollect(user.getId(), productId, productNum, type);
				if (row == 1) {
					rr.setMessage("收藏成功!");
					rr.returnSuccess();
				} else {
					rr.returnFail("收藏失败!");
				}
			}
		}
		PrintUtil.getJsonString(response, rr);
	}
		

	/**
	 * 刷新购物车！
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/refreshCart.html")
	public String refreshCart(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//刷新购物车金额
		ShoppingCart cart = getCartFromSession(request);
		cart.setSum(new CartServiceImpl().calculate(cart).getSum());
		return "/common/pre/searchBar";
	}

	/**
	 * 从session中获取购物车
	 *
	 * @param request
	 * @return
	 */
	private ShoppingCart getCartFromSession(HttpServletRequest request) throws Exception {
		//session中获取购物车
		ShoppingCart cart =(ShoppingCart) request.getSession().getAttribute("cart");
		//如果不存在购物车则新建
		if(cart==null) {
		cart = new ShoppingCart();
		cart.setSum(0.0);
		}
		return cart;
	}

	/**
	 * 跳到结算页面！
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/toSettlement.html")
	public String toSettlement(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "/pre/settlement/toSettlement";

	}

	/**
	 * 跳转到购物车页面！
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/settlement1.html")
	public String settlement1(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "/pre/settlement/settlement1";
	}

	/**
	 * 获取用户地址
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/settlement2.html")
	public String settlement2(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//获取用户
		EasybuyUser user =getUserFromSession(request);
		//根据id获取对应地址
		List<EasybuyUserAddress> userAddressList = aservice.getEasybuyUserAddressAll(user.getId());
		request.setAttribute("userAddressList", userAddressList);
		return "/pre/settlement/settlement2";
	}

	/**
	 * 生成订单！
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/settlement3.html")
	public Object settlement3(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//获取用户
		EasybuyUser user = getUserFromSession(request);
		//购物车
		ShoppingCart cart = getCartFromSession(request);
		//获取用户选择地址
		EasybuyUserAddress address = null;
		//用户选择地址id  -1为新地址
		int addressId = Integer.parseInt(request.getParameter("addressId"));
		//选择新地址
		if(addressId == -1) {
			//新地址名称
			String newAddress = request.getParameter("newAddress");
			//新住址描述
			String newRemark = request.getParameter("newRemark");
			//不是默认地址
			int isDefault = 0;
			//当前时间
			String createTime = StringUtils.getCurrentTime();
			int index = createTime.indexOf(".");
			createTime = createTime.substring(0, index);
		    address = new EasybuyUserAddress(0, newAddress, createTime, user.getId(), isDefault, newRemark);		    
			//添加用户地址
			int row = aservice.addUserAddress(address);
			
			System.out.println(row+"row");
		}else {
			//根据id获取地址信息
			address = aservice.getUserAddressById(addressId);
		}
		//购物
		EasybuyOrder currentOrder = oservice.payShoppingCart(cart, user, address.getAddress());
		
		//订单信息返回前台
		request.setAttribute("currentOrder", currentOrder);
		clearCart(request, response);
		return "/pre/settlement/settlement3";                                                                                                                                                                                                                                                                                                    
	}

	/**
	 * 修改购物车信息
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/modCart.html")
	public void modCart(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//购物车
		ShoppingCart cart = getCartFromSession(request);
		//ReturnResult 对象
		ReturnResult rr = new ReturnResult();
		//商品id
		int entityId = Integer.parseInt(request.getParameter("entityId"));
		//数量
		int quantity = Integer.parseInt(request.getParameter("quantity"));
		//获取用户
		EasybuyUser user = getUserFromSession(request);
		//购物车类型1
		int type = 1;
		EasybuyCollect coll = new EasybuyCollect(0, user.getId(), entityId, quantity, type);
		//判断是否为删除购物车商品
		if(quantity==0) {
			//删除收藏表该商品
			cservice.delCollect(coll);
			//删除购物车中该商品
			for(int i=0;i<cart.items.size();i++) {
				if(cart.items.get(i).getProduct().getId() == entityId) {
					cart.items.remove(i);
				}
			}
			
			//重算购物车金额
			new CartServiceImpl().calculate(cart);
			
			rr.returnSuccess();
		}else {

			// 修改收藏表
			int row = cservice.upCollect(coll);
			if (row == 1) {
				// 修改购物车

				for (ShoppingCartItem item : cart.items) {
					if (item.getProduct().getId() == entityId) {
						item.setQuantity(quantity);
						item.setCost(item.getQuantity() * item.getProduct().getPrice());
					}
				}
				rr.returnSuccess();

			} else {
				rr.returnFail("失败!");
			}

		}
		PrintUtil.getJsonString(response, rr);
	}

	/**
	 * 获取用户
	 * @param request
	 * @return
	 */
	private EasybuyUser getUserFromSession(HttpServletRequest request) {
		return (EasybuyUser) request.getSession().getAttribute("easybuyUserLogin");
	}

	/**
	 * 判断购物车信息！
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private ReturnResult checkCart(HttpServletRequest request) throws Exception {
		return null;
	}

	/**
	 * 清空购物车！
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping("/clearCart.html")
	public void clearCart(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//购物车
		ShoppingCart cart = getCartFromSession(request);
		//ReturnResult 对象
	    ReturnResult rr = new ReturnResult();
		//清空购物车
		//获取用户
		EasybuyUser user = getUserFromSession(request);
		//获取用户商品
		List<EasybuyCollect> collect = cservice.selectId(user.getId());
		int row =0;
		for(EasybuyCollect c: collect) {
			//根据用户删除收藏表购物车商品
			row= cservice.delCollect(c);
			if(row==0) {
				break;
			}
		}
		if(row==1) {
			//清空购物车商品
			cart.items.clear();
			rr.returnSuccess();
		}else {
			rr.returnFail("清空购物车失败");
		}
		//PrintUtil.getJsonString(response, rr);
		
	}
	
	/**
	 * 取消收藏
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delCollect.html")
	public void delCollect(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//ReturnResult 对象
		ReturnResult rr = new ReturnResult();
		//获取商品id
		int productId = Integer.parseInt(request.getParameter("id"));
		//收藏类型 0 收藏
		int type = 0;	
		//默认收藏1
		int productNum = 1;	
		//获取用户
		EasybuyUser user = getUserFromSession(request);
		//删除收藏表
		if(user==null) {
			rr.returnFail("未登录不能取消收藏!!");
		}else {
		EasybuyCollect easybuyCollect = new EasybuyCollect(0, user.getId(), productId, productNum, type);
		int row = cservice.delCollect(easybuyCollect);
		if(row==1) {
			rr.setMessage("取消成功!");
			rr.returnSuccess();
		}else {
			rr.returnFail("该商品未被收藏");
		}
		}
		PrintUtil.getJsonString(response, rr);
		
	}

}
