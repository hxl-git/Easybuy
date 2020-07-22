package com.service.news.impl;
import java.sql.Connection;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dao.BaseDao;
import com.dao.NewsDao;
import com.entity.EasybuyNews;
import com.service.news.NewsService;
import com.utils.DataBaseUtil;
import com.utils.MyBatisUtil;
import com.utils.Pager;
/**
 * 资讯列表业务逻辑层实现类！
 * @author Administrator
 *
 */
@Service("NewsService")
@Transactional
public class NewsServiceImpl implements NewsService {
	/**
	 * Dao层对象引用
	 */
	@Autowired
	private NewsDao dao;
	/**
	 * 使用Logger记录日志！
	 */
	public static Logger logger = Logger.getLogger(BaseDao.class.getName());
	
	@Override
	/**
	 * 获取资讯列表业务！
	 * @param pager
	 * @return
	 */
	public List<EasybuyNews> queryNewsList(Pager pager) {
		List<EasybuyNews> list = null;
		try {
			list= this.dao.queryNewsList((pager.getCurrentPage()-1)*pager.getRowPerPage(),pager.getRowPerPage());
		} catch (Exception e) {
			logger.debug("获取资讯列表业务错误: "+e);
		}
		if(list!=null) {
			logger.debug("获取资讯列表业务成功!");
			return list;
		}else {
			logger.debug("获取资讯列表业务失败!");
			return null;
		}
		
		
	}

	@Override
	/**
	 * 获取资讯列表总记录数业务！
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public int getTotalCount() {
		int row = 0;
		try {
			row=this.dao.getTotalCount();
		} catch (Exception e) {
			logger.debug("获取资讯列表总记录数业务错误: "+e);
			return -1;
		}
		if(row>1) {
			logger.debug("获取资讯列表总记录数业务成功!");
		}else {
			logger.debug("获取资讯列表总记录数业务失败!");
		}
		return row;
	}
	
	/**
	 * 根据ID获取资讯列表详情业务！
	 * @param id
	 * @return
	 */
	public EasybuyNews findNewsById(int id) {
		EasybuyNews list = null;
		try {
			list=this.dao.getNewsById(id);
		} catch (Exception e) {
			logger.debug("根据ID获取资讯列表详情业务错误: "+e);
		}
		if(list!=null) {
			logger.debug("根据ID获取资讯列表详情业务成功!");
			return list;
		}else {
			logger.debug("根据ID获取资讯列表详情业务失败!");
			return null;
		}
	}

}
