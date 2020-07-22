package com.service.product;

import java.util.List;

import com.entity.EasybuyCollect;
import com.entity.EasybuyOrderDetail;
import com.entity.EasybuyProduct;
import com.utils.Pager;
/**
 * 商品信息业务逻辑层！
 * @author Administrator
 *
 */
public interface ProductService {
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
	public int getProductRowCount(int categoryId);
	
	/**
	 * 获取模糊查询继续数
	 * @param categoryName
	 * @return
	 */
	public int getProductRowCountByName(String categoryName);
	/**
	 * 获取二级分类总记录数！
	 * @param categoryId
	 * @return
	 */
	public int getProductRowCount2(int categoryId);
	/**
	 * 获取三级分类总记录数！
	 * @param categoryId
	 * @return
	 */
	public int getProductRowCount3(int categoryId);
	/**
	 * 获得商品一级分类！
	 * @param categoryId
	 * @param pager
	 * @return
	 */
	public List<EasybuyProduct> getEasybuyProductListByCategoryId(int categoryId, Pager pager);
	/**
	 * 获得商品二级分类！
	 * @param categoryId
	 * @param pager
	 * @return
	 */
	public List<EasybuyProduct> getEasybuyProductListByCategoryId2(int categoryId, Pager pager);
	/**
	 * 获取商品三级分类！
	 * @param categoryId
	 * @param pager
	 * @return
	 */
	public List<EasybuyProduct> getEasybuyProductListByCategoryId3(int categoryId, Pager pager);
	/**
	 * 根据ID获取信息！
	 * @param tid
	 * @return
	 */
	public EasybuyProduct findById(int tid);
	/**
	 * 查询用户收藏夹！
	 * @param list
	 * @return
	 */
	public List<EasybuyProduct> getEasybuyProductListByUser(List<EasybuyCollect> list);
	/**
	 * 订单表！
	 * @param list
	 * @return
	 */
	public List<EasybuyProduct> getEasybuyProductListByOrder(List<EasybuyOrderDetail> list);
	
	/**
	 * 商品管理！
	 * @param pager
	 * @return
	 */
	List<EasybuyProduct> getEasybuyProductAll(Pager pager);
	
	/**
	 * 获取总记录数！
	 * @return
	 */
	public int getTotalCount();
	
	/**
	 * 根据ID删除指定商品信息！
	 * @param id
	 * @return
	 */
	int delEasybuyProductById(int id);
	
	/**
	 * 商品上架/修改商品业务！
	 * @param easybuyProduct
	 * @return
	 */
	public int addEasybuyProduct(EasybuyProduct easybuyProduct);
	
	/**
	 * 根据Id查询对应的商品信息！
	 * @param id
	 * @return
	 */
	EasybuyProduct getEasybuyProductById(int id);
	/**
	 * 模糊查询
	 * @param categoryId
	 * @param pager
	 * @return
	 */
	public List<EasybuyProduct> getEasybuyProductListByCategoryName(String categoryName, Pager pager);
	
	
	/**
	 * 修改商品库存
	 * @param id
	 * @param stock
	 * @return
	 */
	public int upStock(EasybuyProduct product , int quantity);
}
