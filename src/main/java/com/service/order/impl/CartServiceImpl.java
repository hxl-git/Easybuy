package com.service.order.impl;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dao.BaseDao;
import com.dao.EasybuyCollectDao;
import com.dao.ProductDao;
import com.entity.EasybuyCollect;
import com.entity.EasybuyProduct;
import com.service.order.CartService;
import com.service.product.impl.ProductServiceImpl;
import com.utils.DataBaseUtil;
import com.utils.EmptyUtils;
import com.utils.MyBatisUtil;
import com.utils.ShoppingCart;
import com.utils.ShoppingCartItem;
/**
 * 购物车业务逻辑层实现类！！
 * @author Administrator
 *
 */
@Service("CartService")
@Transactional
public class CartServiceImpl implements CartService {
	
	/**
	 * dao层对象引用
	 */
	@Autowired
	private EasybuyCollectDao dao;
	@Autowired
	private ProductDao productDao;
	/**
	 * 使用Logger记录日志！
	 */
	public static Logger logger = Logger.getLogger(BaseDao.class.getName());
	
	
	
	/**
	 * 修改购物车商品数量
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	 public int upCollect(EasybuyCollect coll) {
		 int row = 0;
		 try {
			row = this.dao.upCollect(coll.getUserId(), coll.getProductId(),
					coll.getProductNum(), coll.getType());
		} catch (Exception e) {
			logger.debug("修改购物车数量业务错误:" + e);
			return -1;
		}
		 if(row==1) {
			 logger.info("修改购物车数量业务成功!");
			 return row;
		 }else {
			 logger.info("修改购物车数量业务失败!");
			 return 0;
		 }
		
	 }
	
	
    @Override
    /**
	 * 获得购物车商品所有信息！
	 * @param productId
	 * @param quantityStr
	 * @param cart
	 * @return
	 * @throws Exception
	 */
    public ShoppingCart modifyShoppingCart(ShoppingCart cart,List<EasybuyCollect> list) throws Exception {
    	for(EasybuyCollect c : list) {
    		//根据id获取商品
    		EasybuyProduct p = productDao.findEasybuyProductById(c.getProductId());
    		ShoppingCartItem item = new ShoppingCartItem(p,c.getProductNum());
    		cart.items.add(item);
    	}
    	return cart;
    }

    /**
     * 核算购物车的金额！
     *
     * @param cart
     * @return
     * @throws Exception
     */
    @Override
    public ShoppingCart calculate(ShoppingCart cart) throws Exception {
    	cart.setSum(cart.getTotalCost());
        return cart;
    }

    /**
     * 添加收藏！
     */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public int addCollect(int userId, int productId, int productNum, int type) {
		int row = 0;
		try {
			row = this.dao.addCollect(userId, productId, productNum, type);
		} catch (Exception e) {
			logger.info("添加收藏表业务错误: "+e);
			return -1;
		}
		if(row==1) {
			logger.info("添加收藏表业务成功!!");
			return row;
		}else {
			logger.info("添加收藏表业务失败!!");
			return 0;
		}
		
	}

	@Override
	 /**
     * 查询收藏表！
     * @return
     */
	public List<EasybuyCollect> select() {
		List<EasybuyCollect> list=null;
		try {
			list = this.dao.select1();
		} catch (Exception e) {
			logger.debug("查询收藏表业务出现错误: "+e);
		}
		if(list!=null) {
			logger.info("查询收藏表业务成功!!");
			return list;
		}else {
			logger.info("查询收藏表业务失败!!");
			return null;
		}
		
	}

	public static void main(String[] args) {
		List<EasybuyCollect> list  = new CartServiceImpl().select();
		System.out.println(list.size());
	}
	@Override
	/**
     * 根据ID查询购物车！
     * @param userId
     * @param productId
     * @return
     */
	public List<EasybuyCollect> selectId(int userId) {
		List<EasybuyCollect> list = new ArrayList<EasybuyCollect>(); 
		for(EasybuyCollect c : select()) {
			if(c.getType()==1 && c.getUserId()==userId) {
				list.add(c);
			}
		}
		
		return list;
	}

	@Override
	/**
     * 查询用户的收藏夹！
     * @param userId
     * @return
     */
	public List<EasybuyCollect> selectByUserId(int userId) {
		List<EasybuyCollect> list = new ArrayList<EasybuyCollect>(); 
		for(EasybuyCollect c : select()) {
			if(c.getType()==0 && c.getUserId()==userId) {
				list.add(c);
			}
		}
		
		return list;
	}
	/**
	 * 删除收藏表
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public int delCollect(EasybuyCollect easybuyCollect) {
		int row = 0;
		try {
			row = this.dao.delCollect(easybuyCollect);
		} catch (Exception e) {
			logger.info("删除收藏表业务错误: "+e);
			return -1;
		}
		if(row==1) {
			logger.info("删除收藏表业务成功!!");
			return row;
		}else {
			logger.info("删除收藏表业务失败!!");
			return 0;
		}
	}
	
	
	
}
