package com.service.user.impl;

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
import com.dao.EasybuyUserDao;
import com.entity.EasybuyUser;
import com.service.user.EasybuyUserService;
import com.utils.DataBaseUtil;
import com.utils.MyBatisUtil;
import com.utils.Pager;

/**
 * 用户信息业务逻辑层操作！
 * 
 * @author Administrator
 *
 */
@Service("EasybuyUserService")
@Transactional
public class EasybuyUserServiceImpl implements EasybuyUserService {
	/**
	 * Dao层对象引用
	 */
	@Autowired
	private EasybuyUserDao dao;
	
	/**
	 * 使用Logger记录日志！
	 */
	public static Logger logger = Logger.getLogger(BaseDao.class.getName());

	@Override
	/**
	 * 根据用户名和密码查询相应信息业务！
	 */
	public EasybuyUser getEasybuyUserInfo(String loginName) {
		EasybuyUser user=null;
		try {
			user = this.dao.findEasybuyUserInfo(loginName);
		} catch (Exception e) {
			logger.debug("根据用户名和密码查询相应信息业务出现错误: "+e);
			e.printStackTrace();
		}
		if(user!=null) {
			logger.info("根据用户名和密码查询相应信息业务执行成功!");
			return user;
		}else {
			logger.info("根据用户名和密码查询相应信息业务执行失败!");
			return null;
		}
	
	}
	
	
	

	/**
	 * 实现用户注册业务操作！
	 */
	@Override
	public int addRegisterInfo(EasybuyUser easybuyUser) {
		int row = 0;
		try {
			row = this.dao.addRegisterInfo(easybuyUser);
		} catch (Exception e) {
			logger.debug("用户注册业务操作出现错误: "+e);
			return -1;
		}
		if(row==1) {
			logger.info("用户注册业务操作执行成功!");
			return row;
		}else {
			logger.info("用户注册业务操作执行失败!");
			return 0;
		}
		
	}

	@Override
	/**
	 * 获得所有用户信息业务！
	 * 
	 * @return
	 */
	public List<EasybuyUser> getEasybuyUserAll(Pager pager) {
		List<EasybuyUser> list = null;
		try {
			list=this.dao.findEasybuyUserAll((pager.getCurrentPage()-1)*pager.getRowPerPage(),pager.getRowPerPage());
		} catch (Exception e) {
			logger.debug("获得所有用户信息业务错误: "+e);
			e.printStackTrace();
		}
		if(list!=null) {
			logger.info("获得所有用户信息业务成功!");
			return list;
		}else {
			logger.info("获得所有用户信息业务失败!");
			return null;
		}
		
	}

	@Override
	/**
	 * 根据用户ID删除信息！
	 * 
	 * @param id
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public int removeEasybuyUserById(int id) {
		return 0;
	}

	@Override
	/**
	 * 获取用户信息总记录数业务！
	 */
	public int getTotalCount() {
		int row = 0;
		try {
			row = this.dao.getTotalCount();
		} catch (Exception e) {
			logger.debug(" 获取用户信息总记录数业务错误: "+e);
			e.printStackTrace();
			return -1;
		}
		if(row>0) {
			logger.info("获取用户信息总记录数业务成功!");
		}else {
			logger.info("获取用户信息总记录数业务失败!");
		}
		return row;
	}

	@Override
	/**
	 * 根据用户Id查询对应信息！
	 * 
	 * @param id
	 * @return
	 */
	public EasybuyUser getEasybuyUserById(int id) {
		EasybuyUser user = null;
		try {
			user = this.dao.findEasybuyUserById(id);
		} catch (Exception e) {
			logger.debug("根据用户Id查询对应信息业务错误: "+e);
			e.printStackTrace();
		}
		if(user!=null) {
			logger.info("根据用户Id查询对应信息业务成功!");
			return user;
		}else {
			logger.info("根据用户Id查询对应信息业务失败!");
			return null;
		}
		
	}

	@Override
	/**
	 * 根据用户ID更新用户信息！
	 * 
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public int updateEasybuyUserById(EasybuyUser easybuyUser) {
		int row = 0; 
		try {
			row = this.dao.modifyEasybuyUserById(easybuyUser);
		} catch (Exception e) {
			logger.debug("根据用户ID更新用户信息业务错误 :"+e);
			e.printStackTrace();
			return -1;
		}
		if(row==1) {
			logger.info("根据用户ID更新用户信息业务成功!");
		}else {
			logger.info("根据用户ID更新用户信息失败!");
		}
		return row;
	}

	@Override
	/**
	 * 查询是否存在相同的用户名业务！
	 * 
	 * @param name
	 * @return
	 */
	public int getLoginNameByName(String name) {
		int row=0;
		try {
			row = this.dao.findLoginNameByName(name);
		} catch (Exception e) {
			logger.debug("判断是否存在相同用户名业务出现错误: "+e);
		}
		if(row==1) {
			logger.info("用户名重复!");
			return 1;
		}else {
			logger.info("用户名可用!");
			return 0;
		}
	}

	// ************
	@Override
	/**
	 * 修改密码操作验证业务！
	 * 
	 * @param name
	 * @param emailMobile
	 * @param value
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public int getUserPasswordBy(String name, String emailMobile, String value) {
		return 0;
	}
	// ************

	@Override
	/**
	 * 根据用户名修改该用户密码！
	 * 
	 * @param name
	 * @param password
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public int modifyUserPasswordBy(String name, String password) {
		return 0;
	}
}
