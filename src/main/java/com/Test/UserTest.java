package com.Test;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.entity.EasybuyUser;
import com.service.user.impl.EasybuyUserServiceImpl;

@RunWith(Suite.class)
@SuiteClasses({})
public class UserTest {
         
        //测试GitHub
 
	/**
	 * 测试根据用户名查询用户信息方法
	 */
	@Test
	public void ceshi() {
		EasybuyUser user = new EasybuyUserServiceImpl().getEasybuyUserInfo("hxl");
		System.out.println(user.getLoginName());
		System.out.println(user.getPassword());
		System.out.println(user.getUserName());
		
	}
	
	/**
	 * 测试用户名是否重复
	 */
	@Test
	public void ceshi1() {
		int row = new EasybuyUserServiceImpl().getLoginNameByName("hxl");
		if(row==1) {
		System.out.println("重复");
		}else {
		System.out.println("不重复");
		}
	}
}
