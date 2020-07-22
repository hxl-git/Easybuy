package com.service.product.impl;

import java.sql.Connection;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dao.BaseDao;
import com.dao.ProductDao;
import com.entity.EasybuyCollect;
import com.entity.EasybuyOrderDetail;
import com.entity.EasybuyProduct;
import com.service.product.ProductService;
import com.utils.MyBatisUtil;
import com.utils.Pager;

/**
 * 商品信息业务逻辑层实现类！
 * 
 * @author Administrator
 *
 */
@Service("ProductService")
@Transactional
public class ProductServiceImpl implements ProductService {

	/**
	 * dao层对象引用
	 */
	@Autowired
	private ProductDao dao;
	
	
	
	/**
	 * 使用Logger记录日志！
	 */
	public static Logger logger = Logger.getLogger(BaseDao.class.getName());

	@Override
	/**
	 * 获取所有商品信息！
	 * 
	 * @return
	 */
	public List<EasybuyProduct> getEasybuyProductList() {
		List<EasybuyProduct> list =null;
		try {
			list = this.dao.getEasybuyProductList();
		} catch (Exception e) {
			logger.debug("获取所有商品信息业务错误: "+e);
		}
		if(list!=null) {
			logger.info("获取所有商品信息业务成功!!");
			return list;
		}else {
			logger.info("获取所有商品信息业务失败!!");
			return null;
		}
		
	}

	@Override
	/**
	 * 获取一级分类总记录数！
	 * 
	 * @param categoryId
	 * @return
	 */
	public int getProductRowCount(int categoryId) {
		int row = 0;
		try {
			row = this.dao.getProductRowCount(categoryId);
		} catch (Exception e) {
			logger.debug("获取一级商品总条数业务错误: "+e);
		}
		if(row>0) {
			logger.debug("获取一级商品总条数业务成功!!");
			return row;
		}else {
			logger.debug("获取一级商品总条数业务失败!!");
			return -1;
		}
		
	}

	/**
	 * 获取二级分类总记录数！
	 * 
	 * @param categoryId
	 * @return
	 */
	public int getProductRowCount2(int categoryId) {
		int row = 0;
		try {
			row = this.dao.getProductRowCount2(categoryId);
		} catch (Exception e) {
			logger.debug("获取二级商品总条数业务错误: "+e);
		}
		if(row>0) {
			logger.debug("获取二级商品总条数业务成功!!");
			return row;
		}else {
			logger.debug("获取二级商品总条数业务失败!!");
			return -1;
		}
	}

	/**
	 * 获取三级分类总记录数！
	 * 
	 * @param categoryId
	 * @return
	 */
	public int getProductRowCount3(int categoryId) {
		int row = 0;
		try {
			row = this.dao.getProductRowCount3(categoryId);
		} catch (Exception e) {
			logger.debug("获取三级商品总条数业务错误: "+e);
		}
		if(row>0) {
			logger.debug("获取三级商品总条数业务成功!!");
			return row;
		}else {
			logger.debug("获取三级商品总条数业务失败!!");
			return -1;
		}
	}

	@Override
	/**
	 * 获得商品一级分类！
	 * 
	 * @param categoryId
	 * @param pager
	 * @return
	 */
	public List<EasybuyProduct> getEasybuyProductListByCategoryId(int categoryId, Pager pager) {
		List<EasybuyProduct> list =null;
		try {
			list = this.dao.getEasybuyProductListByCategoryId(categoryId, (pager.getCurrentPage()-1)*pager.getRowPerPage(),pager.getRowPerPage());
		} catch (Exception e) {
			logger.debug("获取一级分类商品信息业务错误: "+e);
		}
		if(list!=null) {
			logger.info("获取一级分类商品信息业务成功!!");
			return list;
		}else {
			logger.info("获取一级分类商品信息业务失败!!");
			return null;
		}
	}

	/**
	 * 获得商品二级分类！
	 * 
	 * @param categoryId
	 * @param pager
	 * @return
	 */
	public List<EasybuyProduct> getEasybuyProductListByCategoryId2(int categoryId, Pager pager) {
		List<EasybuyProduct> list =null;
		try {
			list = this.dao.getEasybuyProductListByCategoryId2(categoryId, (pager.getCurrentPage()-1)*pager.getRowPerPage(),pager.getRowPerPage());
		} catch (Exception e) {
			logger.debug("获取二级分类商品信息业务错误: "+e);
		}
		if(list!=null) {
			logger.info("获取二级分类商品信息业务成功!!");
			return list;
		}else {
			logger.info("获取二级分类商品信息业务失败!!");
			return null;
		}
	}

	/**
	 * 获得商品三级分类！
	 * 
	 * @param categoryId
	 * @param pager
	 * @return
	 */
	public List<EasybuyProduct> getEasybuyProductListByCategoryId3(int categoryId, Pager pager) {
		List<EasybuyProduct> list =null;
		try {
			list = this.dao.getEasybuyProductListByCategoryId3(categoryId, (pager.getCurrentPage()-1)*pager.getRowPerPage(),pager.getRowPerPage());
		} catch (Exception e) {
			logger.debug("获取三级分类商品信息业务错误: "+e);
		}
		if(list!=null) {
			logger.info("获取三级分类商品信息业务成功!!");
			return list;
		}else {
			logger.info("获取三级分类商品信息业务失败!!");
			return null;
		}
	}

	@Override
	/**
	 * 根据ID获取信息！
	 * 
	 * @param tid
	 * @return
	 */
	public EasybuyProduct findById(int tid) {
		return null;
	}

	@Override
	/**
	 * 查询用户收藏夹！
	 * 
	 * @param list
	 * @return
	 */
	public List<EasybuyProduct> getEasybuyProductListByUser(List<EasybuyCollect> list) {
		List<EasybuyProduct> list1 =null;
		try {
			list1 = this.dao.getEasybuyProductListByUser(list);
		} catch (Exception e) {
			logger.debug("查询用户收藏夹业务错误: "+e);
		}
		if(list1!=null) {
			logger.info("查询用户收藏夹业务成功!!");
			return list1;
		}else {
			logger.info("查询用户收藏夹业务失败!!");
			return null;
		}
	}

	@Override
	/**
	 * 订单表！
	 * 
	 * @param list
	 * @return
	 */
	public List<EasybuyProduct> getEasybuyProductListByOrder(List<EasybuyOrderDetail> list) {
		
		return null;
	}

	// ***************************
	@Override
	/**
	 * 商品管理全部商品信息！
	 * 
	 * @param pager
	 * @return
	 */
	public List<EasybuyProduct> getEasybuyProductAll(Pager pager) {
		List<EasybuyProduct> list =null;
		try {
			list = this.dao.findEasybuyProductAll((pager.getCurrentPage()-1)*pager.getRowPerPage(),pager.getRowPerPage());
		} catch (Exception e) {
			logger.debug("商品管理全部商品信息业务错误: "+e);
		}
		if(list!=null) {
			logger.info("商品管理全部商品信息业务成功!!");
			return list;
		}else {
			logger.info("商品管理全部商品信息业务失败!!");
			return null;
		}
		
	}

	@Override
	/**
	 * 获取总记录数！
	 */
	public int getTotalCount() {
		int row = 0;
		try {
			row = this.dao.findTotalCount();
		} catch (Exception e) {
			logger.debug("获获取总记录数业务错误: "+e);
		}
		if(row>0) {
			logger.debug("获取总记录数业务成功!!");
			return row;
		}else {
			logger.debug("获取总记录数业务失败!!");
			return -1;
		}
	}

	@Override
	/**
	 * 根据ID删除指定商品信息！
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public int delEasybuyProductById(int id) {
		int row = 0;
		try {
			row = this.dao.deleteEasybuyProductById(id);
				logger.info("根据ID删除指定商品信息业务成功");
		} catch (Exception e) {
			logger.info("根据ID删除指定商品信息业务错误 "+e);
			e.printStackTrace();
		}
		logger.info("根据ID删除指定商品信息业务失败");
		return row;
	}

	@Override
	/**
	 * 商品上架/修改商品业务！
	 * 
	 * @param easybuyProduct
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public int addEasybuyProduct(EasybuyProduct easybuyProduct) {
		int row = 0;
		try {
			//添加
			if(easybuyProduct.getId()==0) {
				row=this.dao.insertEasybuyProduct(easybuyProduct);
			}else {
				//修改
				row=this.dao.updateEasybuyProduct(easybuyProduct);
			}
		} catch (Exception e) {
			logger.debug("商品上架/修改商品业务错误: "+e);
			e.printStackTrace();
			return -1;
		}
		if(row==1) {
			logger.info("商品上架/修改商品业务成功!");
		}else {
			logger.info("商品上架/修改商品业务失败!");
		}
		return row;
	}

	@Override
	/**
	 * 根据Id查询对应的商品信息！
	 * 
	 * @param id
	 * @return
	 */
	public EasybuyProduct getEasybuyProductById(int id) {
		EasybuyProduct product =null;
		try {
			product = this.dao.findEasybuyProductById(id);
		} catch (Exception e) {
			logger.debug("根据Id查询对应的商品信息业务错误: "+e);
		}
		if(product!=null) {
			logger.info("根据Id查询对应的商品信息业务成功!!");
			return product;
		}else {
			logger.info("根据Id查询对应的商品信息业务失败!!");
			return null;
		}
		
	}

	/**
	 * 获取模糊查询继续数
	 */
	public int getProductRowCountByName(String categoryName) {
		int row = 0;
		try {
			row = this.dao.getProductRowCountByName(categoryName);
		} catch (Exception e) {
			logger.debug("查询商品信息总数业务错误: "+e);
		}
		if(row>0) {
			logger.debug("查询商品信息总数业务成功!!");
			return row;
		}else {
			logger.debug("查询商品信息总数业务失败!!");
			return -1;
		}
	}

	/**
	 * 模糊查询
	 */
	public List<EasybuyProduct> getEasybuyProductListByCategoryName(String categoryName, Pager pager) {
		List<EasybuyProduct> list =null;
		try {
			list = this.dao.getEasybuyProductListByCategoryName(categoryName, (pager.getCurrentPage()-1)*pager.getRowPerPage(),pager.getRowPerPage());
		} catch (Exception e) {
			logger.debug("查询商品信息业务错误: "+e);
		}
		if(list!=null) {
			logger.info("查询商品信息业务成功!!");
			return list;
		}else {
			logger.info("查询商品信息业务失败!!");
			return null;
		}
	}

	/**
	 * 修改商品库存
	 * @param id
	 * @param stock
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public int upStock(EasybuyProduct product, int quantity) {
		int row = 0;
		try {
			row=this.dao.updateStock(product.getId(),product.getStock()-quantity);
		} catch (Exception e) {
			logger.debug("修改商品库存业务出现错误 "+e);
			e.printStackTrace();
			return -1;
		}
		if(row==1) {
			logger.info("修改商品库存业务成功!");
		}else {
			logger.info("修改商品库存业务失败!");
		}
		return row;
	}

}
