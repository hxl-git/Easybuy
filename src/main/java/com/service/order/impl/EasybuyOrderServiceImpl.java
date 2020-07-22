package com.service.order.impl;

//import static org.hamcrest.CoreMatchers.nullValue;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dao.BaseDao;
import com.dao.EasybuyOrderDao;
import com.dao.OrderDao;
import com.dao.OrderDetailDao;
import com.entity.DetailProduct;
import com.entity.EasybuyOrder;
import com.entity.EasybuyOrderDetail;
import com.entity.EasybuyProduct;
import com.entity.EasybuyUser;
import com.service.order.EasybuyOrderService;
import com.service.product.ProductService;
import com.service.product.impl.ProductServiceImpl;
import com.utils.DataBaseUtil;
import com.utils.MyBatisUtil;
import com.utils.Pager;
import com.utils.SecurityUtils;
import com.utils.ShoppingCart;
import com.utils.ShoppingCartItem;
import com.utils.StringUtils;

/**
 * 订单信息业务逻辑层实现类！
 * 
 * @author Administrator
 *
 */
@Service("EasybuyOrderService")
@Transactional
public class EasybuyOrderServiceImpl implements EasybuyOrderService {
	/**
	 * dao层对象引用
	 */
	@Autowired
	private EasybuyOrderDao dao;
	@Autowired
	private OrderDao orderDao;
	@Autowired
	private OrderDetailDao orderDetailDao;
	@Autowired
	private ProductService pservice;
	/**
	 * 使用Logger记录日志！
	 */
	public static Logger logger = Logger.getLogger(BaseDao.class.getName());

	@Override
	/**
	 * 根据用户ID得到对应订单信息业务！
	 */
	public List<EasybuyOrder> getEasybuyOrderAll(int userId , Pager pager) {
		List<EasybuyOrder> list = null;
		try {
			list = this.dao.findEasybuyOrderList(userId,(pager.getCurrentPage()-1)*pager.getRowPerPage(),pager.getRowPerPage());
		} catch (Exception e) {
			logger.debug("根据用户ID得到对应订单信息业务错误 "+e);
		}
		if(list!=null) {
			logger.info("根据用户ID得到对应订单信息业务成功!");
			return list;
		}else {
			logger.info("根据用户ID得到对应订单信息业务失败!");
			return null;
		}
		
	}

	/**
	 * 购物！
	 * 
	 * @param cart
	 * @param user
	 * @param adress
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public EasybuyOrder payShoppingCart(ShoppingCart cart, EasybuyUser user, String adress) {
		EasybuyOrder order = new EasybuyOrder();
		order.setUserId(user.getId());
		order.setCost(cart.getSum());
		order.setUserAddress(adress);
		order.setLoginName(user.getLoginName());
		order.setStatus(1);
		order.setType(1);
		order.setCreateTime(StringUtils.getCurrentTime());
		//订单号
		String serialNumber = StringUtils.createValidateCode() +StringUtils.randomNumbers(4);
		serialNumber = SecurityUtils.md5Hex(serialNumber);	//加密1次
		order.setSerialNumber(serialNumber);
		try {
		//保存订单
		orderDao.saveOrder(order);
		 
		 //保存商品详情
		 for(ShoppingCartItem item : cart.items) {
			 //相应商品减去库存
			 pservice.upStock(item.getProduct(), item.getQuantity());
			 //保存详情
			 EasybuyOrderDetail detail = new EasybuyOrderDetail(0, order.getId(), item.getProduct().getId(), item.getQuantity(), item.getCost());
			 orderDetailDao.saveOrderDetail(detail);
		 }
		 logger.info("购物业务成功!!");
		} catch (SQLException e) {
		logger.debug("购物业务失败: "+ e);
			e.printStackTrace();
		}
		return order;
	}

	@Override
	/**
	 * 获得总计数！
	 * 
	 * @return
	 */
	public int getTotalCount() {
		int row = 0;
		try {
			row = this.dao.getTotalCount();
		} catch (Exception e) {
			logger.debug("获取订单总数业务错误: "+e);
			e.printStackTrace();
			return -1;
		}
		if(row>0) {
			logger.info("获取订单总数业务成功!");
		}else {
			logger.info("获取订单总数业务失败!");
		}
		return row;
	}

	@Override
	/**
	 * 获得购物信息！
	 * 
	 * @return
	 */
	public List<DetailProduct> getEasybuyOrderDetail(List<EasybuyOrderDetail> list) {
		List<DetailProduct> dpList = new ArrayList<DetailProduct>();
		for(EasybuyOrderDetail d : list) {
			EasybuyProduct p = pservice.getEasybuyProductById(d.getProductId());
			DetailProduct dp = new DetailProduct();
			dp.setFileName(p.getFileName());
			dp.setOrderId(d.getOrderId());
			dp.setQuantity(d.getQuantity());
			dp.setId(p.getId());
			dp.setName(p.getName());
			dp.setCost(d.getCost());
			dpList.add(dp);
		}
		return dpList;
	}

	/**
	 * 订单号查询购物订单信息！
	 * 
	 * @param orderId
	 * @return
	 */
	public List<EasybuyOrderDetail> getEasybuyOrderDetail(int orderId) {
		List<EasybuyOrderDetail> list = null;
		try {
			list = orderDao.getEasybuyOrderDetail(orderId);
		} catch (Exception e) {
			logger.debug("订单号查询购物订单信息业务错误 "+e);
		}
		if(list!=null) {
			logger.info("订单号查询购物订单信息业务成功!");
			return list;
		}else {
			logger.info("订单号查询购物订单信息业务失败!");
			return null;
		}
	}

	// *********
	@Override
	/**
	 * 根据用户ID查询订单总数！
	 * 
	 * @param id
	 * @return
	 */
	public int getUserByIdOrder(int userId) {
		int row = 0;
		try {
			row = this.dao.findUserByIdOrder(userId);
		} catch (Exception e) {
			logger.debug("根据用户ID查询订单总数业务错误: "+e);
			e.printStackTrace();
			return -1;
		}
		if(row>0) {
			logger.info("根据用户ID查询订单总数业务成功!");
		}else {
			logger.info("根据用户ID查询订单总数业务失败!");
		}
		return row;
	}
	// *********

	/**
	 * 查询所有订单信息
	 */
	@Override
	public List<EasybuyOrder> allEasyBuyOrderList(Pager pager) {
		List<EasybuyOrder> list = null;
		try {
			list = this.dao.allEasyBuyOrderList((pager.getCurrentPage()-1)*pager.getRowPerPage(),pager.getRowPerPage());
		} catch (Exception e) {
			logger.debug("查询所有订单信息业务错误 "+e);
		}
		if(list!=null) {
			logger.info("查询所有订单信息业务成功!");
			return list;
		}else {
			logger.info("查询所有订单信息业务失败!");
			return null;
		}
	}
}
