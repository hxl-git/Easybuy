package com.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.entity.EasybuyCollect;
import com.entity.EasybuyUser;
import com.service.order.CartService;
import com.service.user.EasybuyUserService;
import com.utils.Pager;
import com.utils.PrintUtil;
import com.utils.ReturnResult;
import com.utils.SecurityUtils;
import com.utils.ShoppingCart;

@Controller
public class EasybuyUserServlet {
	/**
	 * Service层对象引用
	 */
	@Autowired
	private EasybuyUserService uservice;
	@Autowired
	private CartService cservice;
	
	/**
	 * 加载用户列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/user.html")
	public String user(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//当前页数
		String currentPage1 = request.getParameter("currentPage");
		int currentPage = 1;
		if(currentPage1!=null) {
			currentPage=Integer.parseInt(currentPage1);
		}
		//用户总条数
		int count = uservice.getTotalCount();
		//pager
		Pager pager = new Pager(count, 8, currentPage);
		pager.setUrl("/EasybuyUserServlet?action=user");
		//查询用户列表
		List<EasybuyUser> list = uservice.getEasybuyUserAll(pager);
		
		request.setAttribute("list", list);
		request.setAttribute("pager", pager);
		return "backend/user/Member_Packet";
	}

	/**
	 * 添加/修改用户成功后调用方法
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/adduser.html")
	public String adduser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//当前页数
		String currentPage1 = request.getParameter("currentPage");
		int currentPage = 1;
		if(currentPage1!=null && currentPage1!= "") {
			currentPage=Integer.parseInt(currentPage1);
		}
		//用户总条数
		int count = uservice.getTotalCount();
		//pager
		Pager pager = new Pager(count, 8, currentPage);
		pager.setUrl("/EasybuyUserServlet?action=user");
		//查询用户列表
		List<EasybuyUser> list = uservice.getEasybuyUserAll(pager);
		
		request.setAttribute("list", list);
		request.setAttribute("pager", pager);					
		return "backend/user/Member_Packet";
	}

	/**
	 * 传递参数到修改信息窗口
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toUpdateUser.html")
	public String toUpdateUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//当前页数
		String currentPage1 = request.getParameter("currentPage");
		int currentPage = 1;
		if(currentPage1!=null && currentPage1!= "") {
			currentPage=Integer.parseInt(currentPage1);
		}
		request.setAttribute("currentPage", currentPage);
		//获取用户Id
		int userId = Integer.parseInt(request.getParameter("id"));
		//根据用户Id获取用户信息
		EasybuyUser user = uservice.getEasybuyUserById(userId);
		request.setAttribute("user", user);
		return "/backend/user/toUpdateUser";
	}

	/**
	 * 修改用户信息/添加用户信息！
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/modify.html")
	public void modify(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//ReturnResult
		ReturnResult rr = new ReturnResult();
		
		//获取id
		int id = Integer.parseInt(request.getParameter("id"));
		//用户名
		String name = request.getParameter("name");
		//密码
		String password = request.getParameter("password");
		
		//性别
		int sex = Integer.parseInt(request.getParameter("sex"));
		//身份证
		String identityCode = request.getParameter("identityCode");
		//邮箱
		String email = request.getParameter("email");
		//真实姓名
		String userName = request.getParameter("userName");
		//电话
		String mobile = request.getParameter("mobile");
		//类型
		int type = Integer.parseInt(request.getParameter("user"));
		
		//用户对象
		EasybuyUser user = new EasybuyUser(id, name, userName, password, sex, identityCode, email, mobile, type);
		
		//判断新增还是修改
		//新增
		if(id==0) {
			//新增用户
			int row = uservice.addRegisterInfo(user);
			if(row==1) {
				rr.returnSuccess();
			}else {
				rr.returnFail("新增用户失败!");
			}
			
			
		}//修改
		else {
			//修改用户
			int row = uservice.updateEasybuyUserById(user);
			if(row==1) {
				rr.returnSuccess();
			}else {
				rr.returnFail("修改用户失败!");
			}
			

		}
		PrintUtil.getJsonString(response, rr);
		
	}

	/**
	 * 点击新增用户！
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/add.html")
	public String add(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "backend/user/toUpdateUser";

	}

	/**
	 * 用户详情！
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/user-index.html")
	public String index(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "backend/user/Member_User";

	}

	/**
	 * 登录
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/loginBtn.html")
	public void loginBtn(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//ReturnResult对象返回结果
		ReturnResult rr = new ReturnResult();

		//获取用户登录的用户名密码
		String name = request.getParameter("name");
		String pwd  = request.getParameter("pwd");
		String pwd1 =SecurityUtils.md5Hex(pwd);	
		//根据用户名查询信息
		EasybuyUser user = uservice.getEasybuyUserInfo(name);
		
		
		//判断是否有该用户
		if(user==null) {
		rr.returnFail("用户名不存在!!");
		}else {
	
		//判断密码是否正确
		if(pwd.equals(user.getPassword()) ||pwd1.equals(user.getPassword())) {
			request.getSession().setAttribute("easybuyUserLogin", user);
			//获取用户购物车商品
			List<EasybuyCollect> collect = cservice.selectId(user.getId());
			//加载用户购物车
			ShoppingCart  cart =  new ShoppingCart();
			cart = cservice.modifyShoppingCart(cart, collect);
			//核算购物车金额
			cart.setSum(cservice.calculate(cart).getSum());
			//保存session
			request.getSession().setAttribute("cart", cart);
			 rr.returnSuccess();
			
		}else {
			 rr.returnFail("密码输入错误!!");
		}
		}
		//转发json结果集
		PrintUtil.getJsonString(response, rr);
	
	}

	/**
	 * 注册
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addUser.html")
	public void addUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//ReturnResult对象
		ReturnResult rr = new ReturnResult();
		//获取注册信息
		EasybuyUser user = new EasybuyUser();
		user.setLoginName(request.getParameter("name"));
		user.setUserName(request.getParameter("numName"));
		user.setPassword(request.getParameter("password"));
		//md5加密加密密码
		user.setPassword(SecurityUtils.md5Hex(user.getPassword()));
		user.setMobile(request.getParameter("phone"));
		user.setSex(Integer.parseInt(request.getParameter("sex")));
		user.setEmail(request.getParameter("email"));
		user.setIdentityCode(request.getParameter("mem"));
		//普通用户
		user.setType(0);
		//注册用户
		int row = uservice.addRegisterInfo(user);
		if(row==1) {
			//将用户保存
			request.getSession().setAttribute("easybuyUserLogin", user);
			//创建用户购物车功能
			ShoppingCart  cart =  new ShoppingCart();
			//核算购物车金额
			cart.setSum(0.0);
			//保存session
			request.getSession().setAttribute("cart", cart);
			rr.returnSuccess();
		}else {
			rr.returnFail("注册失败!!");
		}
		//转发json结果集
		PrintUtil.getJsonString(response, rr);
		
	}

	/**
	 * 删除
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/del.html")
	public ReturnResult del(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return null;
	}

	/**
	 * 查询是否相同用户名！
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/loginNameCount.html")
	public void loginNameCount(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//ReturnResult对象
		ReturnResult rr = new ReturnResult();
		//获取用户名
		String name = request.getParameter("name");
		int row = uservice.getLoginNameByName(name);
		if(row==1) {
			rr.returnFail("该用户名已存在!!");
		}else {
			rr.returnSuccess();
		}
		//转发json结果集
		PrintUtil.getJsonString(response, rr);
	}

	/// *************************
	/**
	 * 跳转至验证身份！
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findLoginNamePassword.html")
	public String findLoginNamePassword(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "backend/userPassWord/forgetPwdTwo";
	}

	/**
	 * 查询邮箱或者手机输入是否输入正确！
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/loginNameBy.html")
	public ReturnResult loginNameBy(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return null;
	}

	/**
	 * 跳转至设置新密码！
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/modifyPwd.html")
	public String modifyPwd(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "backend/userPassWord/forgetPwdThree";
	}

	/**
	 * 修改密码！
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/modifyPassWord.html")
	public ReturnResult modifyPassWord(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//转发json结果集
		//PrintUtil.getJsonString(response, rr);
		return null;
	}

	/**
	 * 跳转至设置修改成功页面！！
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/forgetPwd.html")
	public String forgetPwd(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "backend/userPassWord/forgetPwdLast";
	}

	// ***********************

	/**
	 * 注销
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/remove.html")
	public String remove(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//清空用户信息
		request.getSession().removeAttribute("easybuyUserLogin");
		return "pre/Login";
	}


	
}
