package com.service.address.impl;

import java.sql.Connection;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dao.BaseDao;
import com.dao.EasybuyUserAddressDao;
import com.entity.EasybuyUserAddress;
import com.service.address.EasybuyUserAddressService;
import com.utils.DataBaseUtil;
import com.utils.MyBatisUtil;

/**
 * 用户地址信息业务逻辑层实现类！
 * 
 * @author Administrator
 *
 */
@Transactional
@Service("EasybuyUserAddressService")
public class EasybuyUserAddressServiceImpl implements EasybuyUserAddressService {

	/**
	 * Dao层对象引用
	 */
	@Autowired
	private EasybuyUserAddressDao dao;
	

	/**
	 * 使用Logger记录日志！
	 */
	public static Logger logger = Logger.getLogger(BaseDao.class.getName());

	
	@Override
	/**
	 * 根据用户ID查找对应的地址信息！
	 * 
	 * @param userId
	 * @return
	 */
	public List<EasybuyUserAddress> getEasybuyUserAddressAll(int userId) {
		List<EasybuyUserAddress>list = null;
		try {
			list = this.dao.findEasybuyUserAddressAll(userId);
		} catch (Exception e) {
			logger.debug("根据用户ID查找对应的地址信息业务错误: "+e);
			e.printStackTrace();
		}
		if(list!=null) {
			logger.info("根据用户ID查找对应的地址信息业务成功!");
			return list;
		}else {
			logger.info("根据用户ID查找对应的地址信息业务失败!");
			return null;
		}
		
		
	}

	@Override
	/**
	 * 新增地址！
	 * 
	 * @param easybuyUserAddress
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public int addUserAddress(EasybuyUserAddress easybuyUserAddress) {
		int row = 0;
		try {
			row = this.dao.updateEasybuyUserAddressById(easybuyUserAddress);
		} catch (Exception e) {
			logger.debug("新增地址业务错误: "+e);
			return -1;
		}
		if(row==1) {
			logger.info("新增地址业务成功!");
			return row;
		}else {
			logger.info("新增地址业务失败!");
			return 0;
		}
		
	}

	@Override
	/**
	 * 根据用户ID获得相依地址信息！
	 * 
	 * @param id
	 * @return
	 */
	public EasybuyUserAddress getUserAddressById(int id) {
		EasybuyUserAddress list = null;
		try {
			list = this.dao.getUserAddressById(id);
		} catch (Exception e) {
			logger.debug("根据ID查找对应的地址信息业务错误: "+e);
		}
		if(list!=null) {
			logger.info("根据ID查找对应的地址信息业务成功!");
			return list;
		}else {
			logger.info("根据ID查找对应的地址信息业务失败!");
			return null;
		}
	}

	// ********
	@Override
	/**
	 * 根据用户Id判断该编号是否存在地址信息！
	 * 
	 * @param userId
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public int getUserByIdAddress(int userId) {
		return 0;
	}
	// ********
}
