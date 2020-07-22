package com.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.entity.EasybuyUser;
import com.utils.Pager;
/**
 * 用户信息数据访问层！
 * @author Administrator
 *
 */
public interface EasybuyUserDao {
	/**
	 * 根据用户名和密码查询对应信息！
	 * @param easybuyUser
	 * @return
	 */
	EasybuyUser findEasybuyUserInfo(@Param("loginName")String loginName);
	
	/**
	 * 根据用户信息注册
	 * @return
	 */
	int addRegisterInfo(EasybuyUser easybuyUser);
	
	/**
	 * 查询所有用户信息！
	 * @return
	 */
	List<EasybuyUser> findEasybuyUserAll(@Param("from")Integer currnpage , @Param("rowCount")Integer rowcount);
	
	/**
	 * 根据用户ID删除信息！
	 * @param id
	 * @return
	 */
	int delEasybuyUserById(int id);
	
	/**
	 * 查询用户信息总记录数！
	 * @return
	 */
	int getTotalCount();
	
	/**
	 * 根据用户Id查询对应信息！
	 * @param id
	 * @return
	 */
	EasybuyUser findEasybuyUserById(@Param("userId")Integer id);
	
	/**
	 * 根据用户ID更新用户信息！
	 * @return
	 */
	int modifyEasybuyUserById(EasybuyUser easybuyUser);
	
	/**
	 * 查询是否存在相同的用户名！
	 * @param name
	 * @return
	 */
	int findLoginNameByName(@Param("loginName")String loginName);
	
	//************
	/**
	 * 修改密码操作验证！
	 * @param name
	 * @param emailMobile
	 * @param value
	 * @return
	 */
	int findUserPasswordBy(String name,String emailMobile,String value);
	
	/**
	 * 根据用户名修改该用户密码！
	 * @param name
	 * @param password
	 * @return
	 */
	int updateUserPasswordBy(String name,String password);
	//************
}
