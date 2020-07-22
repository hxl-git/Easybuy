package com.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.entity.EasybuyCollect;

/**
 * 收藏列表数据访问层！！
 * 
 * @author Administrator
 *
 */
public interface EasybuyCollectDao {
	/**
	 * 修改收藏表
	 * @param userId
	 * @param productId
	 * @param productNum
	 * @param type
	 * @return
	 */
	int upCollect(@Param("userId")Integer userId, @Param("productId")Integer productId,@Param("productNum") Integer productNum,@Param("type")Integer type);
	
	
	/**
	 * 收藏商品！
	 * @param userId
	 * @param productId
	 * @param productNum
	 * @param type
	 * @return
	 */
	int addCollect(@Param("userId")Integer userId, @Param("productId")Integer productId,@Param("productNum") Integer productNum,@Param("type")Integer type);
	/**
	 * 获得收藏列表信息！
	 * @return
	 */
	List<EasybuyCollect> select1();
	/**
	 * 查询商品信息！
	 * @param userId
	 * @param productId
	 * @return
	 */
	/**
	 * 购物车
	 * @param userId
	 * @param productId
	 * @return
	 */
	EasybuyCollect selectId(@Param("userId")Integer userId);
	/**
	 * 根据用户查询商品信息！
	 * @param userId
	 * @return
	 */
	List<EasybuyCollect> selectByUserId(int userId);
	/**
	 * 删除收藏
	 * @param userId
	 * @param productId
	 * @return
	 */
	int delCollect(EasybuyCollect easybuyCollect);
	
}
