package com.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.log4j.Logger;

import com.entity.EasybuyOrder;

/**
 * 订单信息数据访问层！
 * @author Administrator
 *
 */
public interface EasybuyOrderDao {
	
	/**
	 * 使用Logger记录日志！
	 */
	public static Logger logger = Logger.getLogger(BaseDao.class.getName());
	
	/**
	 * 根据用户信息查询对应订单信息！
	 * @param id
	 * @return
	 */
	List<EasybuyOrder> findEasybuyOrderList(@Param("userId")Integer userId ,@Param("from")Integer currentPage , @Param("rowCount")Integer rowPerPage);
	
	/**
	 * 获得总记录数！
	 * @return
	 */
	public int getTotalCount();
	
	/**
	 * 查询所有订单信息
	 */
	List<EasybuyOrder> allEasyBuyOrderList(@Param("from")Integer currentPage , @Param("rowCount")Integer rowPerPage);
	
	//*********
	/**
	 * 根据用户ID查询订单总数！
	 * @param id
	 * @return
	 */
	int findUserByIdOrder(@Param("userId")Integer userId);
	//*********
}
