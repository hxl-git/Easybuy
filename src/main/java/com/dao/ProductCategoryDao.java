package com.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.entity.EasybuyProductCategory;
import com.utils.Pager;
/**
 * 商品信息数访问数据层！
 * @author Administrator
 *
 */
public interface ProductCategoryDao {
	
	/**
	 *  查询商品分类信息
	 * @param typeId
	 * @return
	 */
	public List<EasybuyProductCategory> getProductCategoryList(int typeId);
	
	/**
	 * 根据父类Id 查询父类名称
	 */
	public String getParentName(@Param("parentId")Integer parentId);
	
	/**
	 *  根据id查询商品信息
	 * @param id
	 * @return
	 */
	public EasybuyProductCategory getProductCategoryById(@Param("id")Integer id);
	
	/**
	 * 查询所有商品分类信息！
	 * @return
	 */
	List<EasybuyProductCategory> findEasybuyProductCategoryAll(@Param("from")Integer currentPage, @Param("rowCount")Integer rowCount);
	
	/**
	 * 获取总记录数！
	 * @return
	 */
	int getTotalCount();
	
	/**
	 * 根据Id删除商品信息！
	 * @param id
	 * @return
	 */
	int delEasybuyProductCategoryById(@Param("id")Integer id);

	/**
	 * 一级分类！
	 * @return
	 */
	List<EasybuyProductCategory> findProductCategoryListOne();
	
	/**
	 * 二级分类！
	 * @return
	 */
	List<EasybuyProductCategory> findProductCategoryListTwo();
	
	/**
	 * 三级分类！
	 * @return
	 */
	List<EasybuyProductCategory> findProductCategoryListThree();
	/**
	 * 根据父分类查询商品分类信息
	 * @param typeId
	 * @return
	 */
	List<EasybuyProductCategory> getProductCategoryListByparentId(@Param("typeId")Integer typeId,@Param("parentId")Integer parentId);
	/**
	 * 新增商品分类
	 * @param easybuyProductCategory
	 * @return
	 */
	int insertEasybuyProductCategory(EasybuyProductCategory easybuyProductCategory);
	
	/**
	 * 根据删除的商品分类父ID编号，去查询一遍外键表中是否有数据！
	 * @param parentId
	 * @return
	 */
	int findParentId(@Param("id")Integer parentId);
	/**
	 * 查询分类是否存在商品商品
	 */
	int getProductById(@Param("type")Integer type,@Param("id")Integer id);
	
}
