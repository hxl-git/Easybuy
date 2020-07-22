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
import com.dao.ProductCategoryDao;
import com.entity.EasybuyProductCategory;
import com.service.product.ProductCategoryService;
import com.utils.DataBaseUtil;
import com.utils.MyBatisUtil;
import com.utils.Pager;
/**
 * 商品信息数据访问层实现类！
 * @author Administrator
 *
 */
@Service("ProductCategoryService")
@Transactional
public class ProductCategoryServiceImpl implements ProductCategoryService {

	/**
	 * dao层对象引用
	 */
	@Autowired
	private ProductCategoryDao dao;
	
	/**
	 * 使用Logger记录日志！
	 */
	public static Logger logger = Logger.getLogger(BaseDao.class.getName());
	
	@Override
	/**
	 * 查询商品！
	 * @param typeId
	 * @return
	 */
	public List<EasybuyProductCategory> getProductCategoryList(int typeId) {
		return null;
	}

	@Override
	/**
	 * 获得所有商品信息！商品管理
	 * @return
	 */
	public List<EasybuyProductCategory> getEasybuyProductCategoryAll(Pager pager) {
		List<EasybuyProductCategory> list = null;
		try {
			list = this.dao.findEasybuyProductCategoryAll((pager.getCurrentPage()-1)*pager.getRowPerPage(), pager.getRowPerPage());
		} catch (Exception e) {
			logger.debug("获得所有商品信息！商品管理业务错误 "+e);
			e.printStackTrace();
		}
		if(list!=null) {
			logger.info("获得所有商品信息！商品管理业务成功!");
			return list;	
		}else {
			logger.info("获得所有商品信息！商品管理业务失败!");
			return null;	
		}
		
	}

	@Override
	/**
	 * 获取商品分类总记录数！
	 * @return
	 */
	public int getTotalCount() {
		int row = 0;
		try {
			row = this.dao.getTotalCount();
		} catch (Exception e) {
			logger.debug(" 获取商品分类总记录数业务错误 "+e);
			e.printStackTrace();
		}
		if(row>0) {
			logger.info("获取商品分类总记录数业务成功!");
		}else {
			logger.info("获取商品分类总记录数业务失败!");
		}
		return row;
	}

	@Override
	/**
	 * 根据商品ID删除指定商品信息业务！
	 * @param id
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public int deleteEasybuyProductCategoryById(int id) {
		return 0;
	}

	@Override
	/**
	 * 一级分类！
	 * @return
	 */
	public List<EasybuyProductCategory> getProductCategoryListOne() {
		List<EasybuyProductCategory> list = null;
		try {
			list = this.dao.findProductCategoryListOne();
		} catch (Exception e) {
			logger.debug("获取一级分类业务出现错误: "+e);
		}
		if(list!=null) {
			logger.info("获取一级分类业务成功!!");
			return list;
		}else {
			logger.info("获取一级分类业务失败!!");
			return null;
		}
		
	}

	@Override
	/**
	 * 二级分类！
	 * @return
	 */
	public List<EasybuyProductCategory> getProductCategoryListTwo() {
		List<EasybuyProductCategory> list = null;
		try {
			list = this.dao.findProductCategoryListTwo();
		} catch (Exception e) {
			logger.debug("获取二级分类业务出现错误: "+e);
		}
		if(list!=null) {
			logger.info("获取二级分类业务成功!!");
			return list;
		}else {
			logger.info("获取二级分类业务失败!!");
			return null;
		}
	}

	@Override
	/**
	 * 三级分类！
	 * @return
	 */
	public List<EasybuyProductCategory> getProductCategoryListThree() {
		List<EasybuyProductCategory> list = null;
		try {
			list = this.dao.findProductCategoryListThree();
		} catch (Exception e) {
			logger.debug("获取三级分类业务出现错误: "+e);
		}
		if(list!=null) {
			logger.info("获取三级分类业务成功!!");
			return list;
		}else {
			logger.info("获取三级分类业务失败!!");
			return null;
		}
	}

	@Override
	/**
	 * 根据父分类查询商品分类信息
	 * @param typeId
	 * @return
	 */
	public List<EasybuyProductCategory> getProductCategoryListByparentId(int typeId,int parentId) {
		List<EasybuyProductCategory> list = null;
		try {
			list = this.dao.getProductCategoryListByparentId(typeId, parentId);
		} catch (Exception e) {
			logger.debug("根据父分类查询商品分类信息业务错误 "+e);
			e.printStackTrace();
		}
		if(list!=null) {
			logger.info("根据父分类查询商品分类信息业务成功!");
			return list;
		}else {
			logger.info("根据父分类查询商品分类信息失败!");
		}
		
		
		
		return null;
	}

	@Override
	/**
	 * 新增商品分类
	 * @param easybuyProductCategory
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public int insertEasybuyProductCategory(EasybuyProductCategory easybuyProductCategory) {
		return 0;
	}

	@Override
	/**
	 *  根据id查询商品信息
	 * @param id
	 * @return
	 */
	public EasybuyProductCategory getProductCategoryById(int id) {
		EasybuyProductCategory p = null;
		try {
			p=this.dao.getProductCategoryById(id);
		} catch (Exception e) {
			logger.debug("根据id查询商品信息错误 "+e);
			e.printStackTrace();
		}
		if(p!=null) {
			logger.info("根据id查询商品信息成功");
			return p;
		}else {
			logger.info("根据id查询商品信息失败");
			return null;	
		}
		
		
		
	}

	@Override
	/**
	 * 根据删除的商品分类父ID编号，去查询一遍外键表中是否有数据业务！！
	 * @param parentId
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public int getdParentId(int parentId) {
		return 0;
	}

	/**
	 * 查询分类是否存在该商品
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public int getProductById(String type,int id) {
		return 0;
	}
	/**
	 * id 获取分类名称业务
	 */
	@Override
	public String getparentName(int id) {
		String name = "";
		try {
			name=this.dao.getParentName(id);
		} catch (Exception e) {
			logger.debug("id 获取分类名称业务错误 "+e);
			e.printStackTrace();
		}
		if(name!="") {
			logger.info("id 获取分类名称业务成功!");
			return name;
		}else {
			logger.info("id 获取分类名称业务失败!");
			return null;
		}
		
	}
	
	
}
