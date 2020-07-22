package com.service.order;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.entity.EasybuyCollect;
import com.entity.EasybuyProduct;
import com.utils.ShoppingCart;
/**
 * 购物车业务逻辑层！！
 * @author Administrator
 *
 */
public interface CartService {
	/**
	 * 修改收藏表购物车
	 * @param coll
	 * @return
	 */
	 public int upCollect(EasybuyCollect coll);
	/**
	 * 获得购物车商品所有信息！
	 * @param productId
	 * @param quantityStr
	 * @param cart
	 * @return
	 * @throws Exception
	 */
    public ShoppingCart modifyShoppingCart(ShoppingCart cart,List<EasybuyCollect> list) throws Exception;
    /**
     * 核算购物车的金额！
     * @param cart
     * @return
     * @throws Exception
     */
    public ShoppingCart calculate(ShoppingCart cart)throws Exception;

	/**
	 * 查询收藏表！
	 * 
	 * @return
	 */
    List<EasybuyCollect> select();
    /**
     * 添加收藏记录！
     * @param userId
     * @param productId
     * @param productNum
     * @param type
     * @return
     */
    public int addCollect(int userId, int productId,int productNum,int type);
    /**
     * 根据ID查询购物车！
     * @param userId
     * @param productId
     * @return
     */
    public List<EasybuyCollect> selectId(int userId);
    /**
     * 
     * 查询用户的收藏夹！
     * @param userId
     * @return
     */
    public List<EasybuyCollect> selectByUserId(int userId);
    
	/**
	 * 删除收藏
	 * @param userId
	 * @param productId
	 * @return
	 */
	int delCollect(EasybuyCollect easybuyCollect);
    
}
