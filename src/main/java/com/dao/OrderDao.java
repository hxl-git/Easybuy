package com.dao;

import java.util.List;

import com.entity.DetailProduct;
import com.entity.EasybuyOrder;
import com.entity.EasybuyOrderDetail;
/**
 * 订单信息数据访问层！
 * @author Administrator
 *
 */
public interface OrderDao {
	/**
	 * 保存订单
	 * @param order
	 */
	public void saveOrder(EasybuyOrder order);
	
	
	/**
	 * 获取用户订单信息！
	 * @return
	 */
	public List<DetailProduct> getEasybuyOrderDetail(List<EasybuyOrderDetail> list);
	/**
	 * 根据订单号获取订单详情信息！
	 * @param orderId
	 * @return
	 */
	public List<EasybuyOrderDetail> getEasybuyOrderDetail(int orderId);
}
