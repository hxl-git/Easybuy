package com.service.order;

import java.util.List;

import com.entity.DetailProduct;
import com.entity.EasybuyOrder;
import com.entity.EasybuyOrderDetail;
import com.entity.EasybuyUser;
import com.utils.Pager;
import com.utils.ShoppingCart;

/**
 * 订单信息业务逻辑层！
 * 
 * @author Administrator
 *
 */
public interface EasybuyOrderService {

	/**
	 * 查询所有订单信息
	 */
	List<EasybuyOrder> allEasyBuyOrderList(Pager pager);
	
	
	/**
	 * 根据用户ID得到对应信息业务！
	 * 
	 * @param userId
	 * @return
	 */
	List<EasybuyOrder> getEasybuyOrderAll(int userId ,Pager pager);

	/**
	 * 获得总计数！
	 * 
	 * @return
	 */
	public int getTotalCount();

	/**
	 * 购物！
	 * 
	 * @param cart
	 * @param user
	 * @param adress
	 * @return
	 */
	EasybuyOrder payShoppingCart(ShoppingCart cart, EasybuyUser user, String adress);

	/**
	 * 获得购物信息！
	 * 
	 * @return
	 */
	public List<DetailProduct> getEasybuyOrderDetail( List<EasybuyOrderDetail> list);

	/**
	 * 获得下单购物订单信息！
	 * 
	 * @param orderId
	 * @return
	 */
	public List<EasybuyOrderDetail> getEasybuyOrderDetail(int orderId);

	// *********
	/**
	 * 根据用户ID查询订单总数！
	 * 
	 * @param id
	 * @return
	 */
	int getUserByIdOrder(int userId);
	// *********
}
