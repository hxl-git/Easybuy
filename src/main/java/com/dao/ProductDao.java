package com.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.entity.EasybuyCollect;
import com.entity.EasybuyOrderDetail;
import com.entity.EasybuyProduct;
import com.utils.Pager;
/**
 * 商品信息数据访问层！
 * @author Administrator
 *
 */
public interface ProductDao {
	/**
	 * 获取所有商品信息！
	 * @return
	 */
	public List<EasybuyProduct> getEasybuyProductList();
	/**
	 * 获取一级分类总记录数！
	 * @param categoryId
	 * @return
	 */
	public int getProductRowCount(@Param("categoryId")Integer categoryId);
	/**
	 * 获取二级分类总记录数！
	 * @param categoryId
	 * @return
	 */
	public int getProductRowCount2(@Param("categoryId")Integer categoryId);
	/**
	 * 获取三级分类总记录数！
	 * @param categoryId
	 * @return
	 */
	public int getProductRowCount3(@Param("categoryId")Integer categoryId);
	/**
	 * 获取一级分类所有商品信息！
	 * @param categoryId
	 * @param pager
	 * @return
	 */
	public List<EasybuyProduct> getEasybuyProductListByCategoryId(@Param("categoryId")Integer categoryId, @Param("from")Integer from , @Param("rowCount")Integer rowCount);
	/**
	 * 获取二级分类所有商品信息！
	 * @param categoryId
	 * @param pager
	 * @return
	 */
	public List<EasybuyProduct> getEasybuyProductListByCategoryId2(@Param("categoryId")Integer categoryId, @Param("from")Integer from , @Param("rowCount")Integer rowCount);
	/**
	 * 获取三级分类所有商品信息！
	 * @param categoryId
	 * @param pager
	 * @return
	 */
	public List<EasybuyProduct> getEasybuyProductListByCategoryId3(@Param("categoryId")Integer categoryId, @Param("from")Integer from , @Param("rowCount")Integer rowCount);
	/**
	 * 根据商品编号查询商品信息！
	 * @param id
	 * @return
	 */
	public EasybuyProduct getById(int id);
	/**
	 * 修改商品库存信息！
	 * @param id
	 * @param quantity
	 * @return 
	 */
	public int updateStock(@Param("id")Integer id, @Param("stock")Integer stock);
	/**
	 * 查询用户收藏列表！
	 * @param list
	 * @return
	 */
	public List<EasybuyProduct> getEasybuyProductListByUser(List<EasybuyCollect> list);
	/**
	 * 查询订单表！
	 * @param list
	 * @return
	 */
	public List<EasybuyProduct> getEasybuyProductListByOrder(List<EasybuyOrderDetail> list);
	
	/**
	 * 商品管理！
	 * @param pager
	 * @return
	 */
	List<EasybuyProduct> findEasybuyProductAll(@Param("from")Integer from , @Param("rowCount")Integer rowCount);
	
	/**
	 * 获取总记录数！
	 * @return
	 */
	public int findTotalCount();
	
	/**
	 * 根据ID删除指定商品信息！
	 * @param id
	 * @return
	 */
	int deleteEasybuyProductById(@Param("id")Integer id);
	
	/**
	 * 商品上架！/修改商品
	 * @param easybuyProduct
	 * @return
	 */
	int insertEasybuyProduct(EasybuyProduct easybuyProduct);
	
	int updateEasybuyProduct(EasybuyProduct easybuyProduct);

	
	/**
	 * 根据Id查询对应的商品信息！
	 * @param id
	 * @return
	 */
	EasybuyProduct findEasybuyProductById(@Param("id")Integer id);
	
	/**
	 * 获取模糊查询继续数
	 * @param categoryName
	 * @return
	 */
	public int getProductRowCountByName(@Param("name")String categoryName);
	/**
	 * 模糊查询
	 * @param categoryId
	 * @param pager
	 * @return
	 */
	public List<EasybuyProduct> getEasybuyProductListByCategoryName(@Param("name")String categoryName, @Param("from")Integer from , @Param("rowCount")Integer rowCount);
	
}
